import { PropsWithChildren, createContext, useCallback, useEffect, useMemo, useState } from "react"
import WebShopApi from "../api/WebShopApi"

interface IAuthContext {
	login: (email: string, password: string) => Promise<void>
	logout: () => void
	signUp: (email: string, password: string, name: string) => Promise<void>
	token: string | null
	loggedIn: boolean
	user: {
		id: number
		name: string
		email: string
	} | null
}

const defaultContextValue: IAuthContext = {
	login: () => Promise.resolve(),
	logout: () => void {},
	signUp: () => Promise.resolve(),
	loggedIn: false,
	token: null,
	user: null,
}

export const AuthContext = createContext<IAuthContext>(defaultContextValue)

export const AuthContextProvider = ({ children }: PropsWithChildren) => {
	const login = useCallback(async (email: string, password: string) => {
		try {
			const response = await WebShopApi.auth.login(email, password)
			if (response.token) {
				setToken(response.token)
			} else {
				throw new Error(response.message ?? "Unknown error")
			}
		} catch (e) {
			if (e instanceof Error) throw e
			else throw new Error("Unknown error")
		}
	}, [])

	const logout = useCallback(() => {
		setToken(null)
		setUser(null)
	}, [])

	const [token, setToken] = useState<string | null>(window.localStorage.getItem("token") ?? null)
	const [user, setUser] = useState<IAuthContext["user"]>(null)

	useEffect(() => {
		if (token) {
			window.localStorage.setItem("token", token)
			WebShopApi.auth.profile().then((response) => {
				setUser(response)
			})
		} else {
			window.localStorage.removeItem("token")
		}
	}, [token])

	const signUp = useCallback(async (email: string, password: string, name: string) => {
		try {
			const response = await WebShopApi.auth.signUp(email, password, name)
			if (response.token) {
				setToken(response.token)
			} else {
				throw new Error(response.message ?? "Unknown error")
			}
		} catch (e) {
			if (e instanceof Error) throw e
			else throw new Error("Unknown error")
		}
	}, [])

	const contextValue: IAuthContext = useMemo(
		() => ({
			login,
			logout,
			signUp,
			token,
			user,
			loggedIn: token !== null,
		}),
		[login, logout, signUp, token, user]
	)

	return <AuthContext.Provider value={contextValue}>{children}</AuthContext.Provider>
}

import { PropsWithChildren, createContext, useCallback, useEffect, useState } from "react"
import WebShopApi from "./WebShopApi"

interface IAuthContext {
	login: (email: string, password: string) => Promise<void>
	logout: () => void
	token: string | null
	loggedIn: boolean
}

const defaultContextValue: IAuthContext = {
	// eslint-disable-next-line @typescript-eslint/no-empty-function
	login: () => Promise.resolve(),
	// eslint-disable-next-line @typescript-eslint/no-empty-function
	logout: () => {},
	loggedIn: false,
	token: null,
}

export const AuthContext = createContext<IAuthContext>(defaultContextValue)

export const AuthContextProvider = ({ children }: PropsWithChildren) => {
	const login = async (email: string, password: string) => {
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
	}

	const logout = useCallback(() => {
		setToken(null)
	}, [])

	const [token, setToken] = useState<string | null>(window.localStorage.getItem("token") ?? null)

	useEffect(() => {
		if (token) {
			window.localStorage.setItem("token", token)
		} else {
			window.localStorage.removeItem("token")
		}
	}, [token])

	const contextValue: IAuthContext = {
		login,
		logout,
		token,
		loggedIn: token !== null,
	}

	return <AuthContext.Provider value={contextValue}>{children}</AuthContext.Provider>
}

/* eslint-disable @typescript-eslint/no-empty-function */
import { PropsWithChildren, createContext, useCallback, useEffect, useState } from "react"
import IProduct from "../types/IProduct"

interface ICartItem {
	product: IProduct
	quantity: number
}

interface ICartContext {
	content: ICartItem[]
	add: (product: IProduct, quantity: number) => void
	remove: (product: IProduct) => void
	changeQuantity: (product: IProduct, quantity: number) => void
	empty: () => void
}

const defaultContextValue: ICartContext = {
	content: [],
	add: () => {},
	remove: () => {},
	changeQuantity: () => {},
	empty: () => {},
}

export const CartContext = createContext<ICartContext>(defaultContextValue)

export const CartContextProvider = ({ children }: PropsWithChildren) => {
	const [content, setContent] = useState<ICartItem[]>(
		window.localStorage.getItem("cart") ? JSON.parse(window.localStorage.getItem("cart") ?? "") : []
	)
	const add = useCallback((product: IProduct, quantity: number) => {
		setContent((prev) => {
			const existing = prev.find((item) => item.product.id === product.id)
			if (existing) {
				return prev.map((item) => {
					if (item.product.id === product.id) {
						return {
							...item,
							quantity: item.quantity + quantity,
						}
					} else {
						return item
					}
				})
			} else {
				return [
					...prev,
					{
						product,
						quantity,
					},
				]
			}
		})
	}, [])

	const remove = useCallback((product: IProduct) => {
		setContent((prev) => prev.filter((item) => item.product.id !== product.id))
	}, [])

	const changeQuantity = useCallback((product: IProduct, quantity: number) => {
		setContent((prev) => {
			const existing = prev.find((item) => item.product.id === product.id)
			if (existing) {
				return prev.map((item) => {
					if (item.product.id === product.id) {
						return {
							...item,
							quantity,
						}
					} else {
						return item
					}
				})
			} else {
				return [
					...prev,
					{
						product,
						quantity,
					},
				]
			}
		})
	}, [])

	const empty = useCallback(() => {
		setContent([])
	}, [])

	useEffect(() => {
		window.localStorage.setItem("cart", JSON.stringify(content))
	}, [content])

	const contextValue: ICartContext = {
		content,
		add,
		remove,
		changeQuantity,
		empty,
	}

	return <CartContext.Provider value={contextValue}>{children}</CartContext.Provider>
}

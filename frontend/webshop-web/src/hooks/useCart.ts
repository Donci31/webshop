import { useContext } from "react"
import { CartContext } from "../context/CartContext"

const useCart = () => {
	const context = useContext(CartContext)
	if (!context) {
		throw new Error("useAuth must be used within an AuthContextProvider")
	}
	return context
}

export default useCart

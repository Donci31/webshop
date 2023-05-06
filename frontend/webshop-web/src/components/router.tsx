import { createBrowserRouter } from "react-router-dom"
import Login from "../pages/Login"
import Logout from "../pages/Logout"
import Home from "../pages/Home"
import Root from "./Root"
import Checkout from "../pages/Checkout"
import CheckoutSuccess from "../pages/CheckoutSuccess"
const router = createBrowserRouter([
	{
		path: "/",
		element: <Root />,
		children: [
			{
				path: "/",
				element: <Home />,
			},
			{
				path: "/login",
				element: <Login />,
			},
			{
				path: "/logout",
				element: <Logout />,
			},
			{
				path: "/checkout/success",
				element: <CheckoutSuccess />,
			},
			{
				path: "/checkout",
				element: <Checkout />,
			},
		],
	},
])

export default router

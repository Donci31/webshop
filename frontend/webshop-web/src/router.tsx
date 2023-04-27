import { createBrowserRouter } from "react-router-dom"
import Login from "./pages/Login"
import Logout from "./pages/Logout"
import Home from "./pages/Home"
const router = createBrowserRouter([
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
])

export default router

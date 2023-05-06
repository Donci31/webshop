import Header from "./Header"
import { Outlet } from "react-router-dom"

const Root = () => (
	<>
		<Header />
		<Outlet />
	</>
)

export default Root

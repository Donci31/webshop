import { Navigate } from "react-router-dom"
import useAuth from "../hooks/useAuth"

const Logout = () => {
	const auth = useAuth()
	auth.logout()
	return <Navigate to="/" />
}

export default Logout

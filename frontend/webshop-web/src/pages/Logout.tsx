import { Navigate } from "react-router-dom"
import useAuth from "../hooks/useAuth"

const Logout = () => {
	const auth = useAuth()
	auth.logout()
	return <Navigate to="/login" />
}

export default Logout

import useAuth from "../hooks/useAuth"
import { Navigate } from "react-router-dom"
import { Dialog, Typography } from "@mui/material"
import { LoginForm } from "../components/LoginForm"

const Login = () => {
	const auth = useAuth()
	/*
	if (auth.loggedIn) {
		return <Navigate to="/" />
	}
	*/
	return (
		<Dialog open maxWidth="xs" fullWidth>
			<Typography
				variant="h5"
				align="center"
				sx={{
					my: 2,
					fontWeight: "bold",
				}}
			>
				Login
			</Typography>
			<LoginForm />
		</Dialog>
	)
}

export default Login

import { Dialog, Typography } from "@mui/material"
import { LoginForm } from "../components/LoginForm"

const Login = () => {
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

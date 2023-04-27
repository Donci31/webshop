import { FormEvent, useState } from "react"
import useAuth from "../hooks/useAuth"
import { Navigate } from "react-router-dom"
import { Alert, AlertTitle, Button, Dialog, Stack, TextField, Typography } from "@mui/material"
import { AxiosError } from "axios"

const Login = () => {
	const auth = useAuth()

	const [email, setEmail] = useState("")
	const [password, setPassword] = useState("")
	const [error, setError] = useState<string | null>(null)

	const handleSubmit = async (e: FormEvent) => {
		e.preventDefault()
		setError(null)
		try {
			await auth.login(email, password)
		} catch (e) {
			setError(e instanceof AxiosError ? e.response?.data?.message : "Unknown error")
		}
	}

	if (auth.loggedIn) {
		return <Navigate to="/" />
	}

	return (
		<Dialog open maxWidth="xs" fullWidth>
			<form onSubmit={handleSubmit}>
				<Stack spacing={2} padding={2}>
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
					{error && (
						<Alert severity="error">
							<AlertTitle>Error</AlertTitle>
							{error}
						</Alert>
					)}
					<TextField
						required
						label="Email"
						value={email}
						onChange={(e) => setEmail(e.target.value)}
						type="email"
					/>
					<TextField
						required
						label="Password"
						type="password"
						value={password}
						onChange={(e) => setPassword(e.target.value)}
					/>
					<Button variant="contained" size="large" type="submit">
						Login
					</Button>
				</Stack>
			</form>
		</Dialog>
	)
}

export default Login

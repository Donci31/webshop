import { FormEvent, useState } from "react"
import useAuth from "../hooks/useAuth"
import { Alert, AlertTitle, Button, Stack, TextField, Typography } from "@mui/material"
import { AxiosError } from "axios"

export const LoginForm = () => {
	const [email, setEmail] = useState("")
	const [password, setPassword] = useState("")
	const [error, setError] = useState<string | null>(null)

	const auth = useAuth()

	const handleSubmit = async (e: FormEvent) => {
		e.preventDefault()
		setError(null)
		try {
			await auth.login(email, password)
		} catch (e) {
			setError(e instanceof AxiosError ? e.response?.data?.message : "Unknown error")
		}
	}

	return (
		<form onSubmit={handleSubmit}>
			<Stack spacing={2}>
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
					autoComplete="email"
				/>
				<TextField
					required
					label="Password"
					type="password"
					value={password}
					onChange={(e) => setPassword(e.target.value)}
					autoComplete="current-password"
				/>
				<Button variant="contained" size="large" type="submit">
					Login
				</Button>
			</Stack>
		</form>
	)
}

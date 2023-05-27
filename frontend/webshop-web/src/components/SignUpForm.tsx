import { FormEvent, useState } from "react"
import useAuth from "../hooks/useAuth"
import { Alert, AlertTitle, Button, Stack, TextField } from "@mui/material"
import { AxiosError } from "axios"

export const SignUpForm = () => {
	const [email, setEmail] = useState("")
	const [password, setPassword] = useState("")
	const [name, setName] = useState("")
	const [error, setError] = useState<string | null>(null)

	const auth = useAuth()

	const handleSubmit = async (e: FormEvent) => {
		e.preventDefault()
		setError(null)
		try {
			await auth.signUp(email, password, name, "USER")
		} catch (e) {
			console.log(e)
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
					label="Name"
					type="text"
					value={name}
					onChange={(e) => setName(e.target.value)}
					autoComplete="name"
					required
				/>
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
					autoComplete="new-password"
				/>
				<Button variant="contained" size="large" type="submit">
					Sign up
				</Button>
			</Stack>
		</form>
	)
}

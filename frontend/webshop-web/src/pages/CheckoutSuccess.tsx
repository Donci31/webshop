import { Paper, Typography } from "@mui/material"

const CheckoutSuccess = () => {
	return (
		<Paper sx={{ p: 2, m: 2 }}>
			<Typography variant="h4" gutterBottom>
				Succesful order!
			</Typography>
			<Typography variant="body1">
				Thank you for your order. You will receive an email confirmation shortly.
			</Typography>
		</Paper>
	)
}

export default CheckoutSuccess

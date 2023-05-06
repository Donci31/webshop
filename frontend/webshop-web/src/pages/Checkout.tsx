import { Box, Button, Card, Divider, Grid, IconButton, Stack, TextField, Typography } from "@mui/material"
import useCart from "../hooks/useCart"
import currency from "../util/currency"
import DeleteRoundedIcon from "@mui/icons-material/DeleteRounded"
import useAuth from "../hooks/useAuth"
import { LoginForm } from "../components/LoginForm"
import { SignUpForm } from "../components/SignUpForm"
import LocalMallRoundedIcon from "@mui/icons-material/LocalMallRounded"

const Checkout = () => {
	const cart = useCart()
	const auth = useAuth()

	return (
		<Box sx={{ p: 2 }}>
			<Typography variant="h4" gutterBottom>
				Checkout
			</Typography>
			<Grid container spacing={2}>
				<Grid item xs={12} md={6}>
					<Card sx={{ p: 2 }}>
						<Typography variant="h6">Cart content</Typography>
						<Stack spacing={2} sx={{ my: 2 }} divider={<Divider flexItem />}>
							{cart.content.map((item) => (
								<Stack direction="row" key={item.product.id} spacing={2}>
									<Box
										sx={{
											backgroundImage: `url('https://picsum.photos/500/200/?${item.product.id}')`,
											backgroundSize: "cover",
											backgroundPosition: "center",
											width: "50px",
											height: "50px",
										}}
									/>
									<Stack direction="column" sx={{ flexGrow: 1 }}>
										<Typography>{item.product.name}</Typography>
										<Typography
											sx={{
												opacity: 0.5,
											}}
										>
											{currency(item.product.price)}
										</Typography>
									</Stack>
									<Stack direction="column" sx={{ flexGrow: 1 }} alignItems={"flex-end"}>
										<Typography>x {item.quantity}</Typography>
										<Typography
											sx={{
												fontWeight: "bold",
											}}
										>
											{item.product.price * item.quantity} Ft
										</Typography>
									</Stack>
									<Divider orientation="vertical" flexItem />
									<Stack direction="column" justifyContent={"center"}>
										<IconButton onClick={() => cart.remove(item.product)}>
											<DeleteRoundedIcon />
										</IconButton>
									</Stack>
								</Stack>
							))}
							<Stack
								direction="row"
								spacing={2}
								sx={{
									mb: 2,
								}}
							>
								<Typography
									variant="h6"
									flexGrow={1}
									sx={{
										fontWeight: "normal",
									}}
								>
									Total:
								</Typography>
								<Typography variant="h6">
									{currency(
										cart.content.reduce((acc, item) => acc + item.product.price * item.quantity, 0)
									)}
								</Typography>
							</Stack>
						</Stack>
					</Card>
				</Grid>
				<Grid item xs={12} md={6}>
					<Card sx={{ p: 2 }}>
						{!auth.loggedIn && (
							<>
								<Typography variant="body2" sx={{ opacity: 0.5, mb: 2 }}>
									You need to be logged in to complete your order.
								</Typography>
								<Stack
									direction="row"
									sx={{ width: "100%" }}
									spacing={2}
									divider={<Divider flexItem orientation="vertical" />}
								>
									<Stack spacing={2} flexGrow={1}>
										<Typography variant="h6">Already have an account?</Typography>
										<LoginForm />
									</Stack>
									<Stack spacing={2} flexGrow={1}>
										<Typography variant="h6">Create a new account</Typography>
										<SignUpForm />
									</Stack>
								</Stack>
							</>
						)}
						{auth.loggedIn && (
							<>
								<Typography variant="h6">
									Welcome back, {auth.user?.name || auth.user?.email}!
								</Typography>
								<Stack sx={{ my: 2 }}>
									<Typography variant="body2" sx={{ opacity: 0.5, mb: 2 }}>
										Please fill in your shipping address.
									</Typography>
									<Grid container spacing={2} sx={{ width: "100%", mb: 2 }}>
										<Grid item xs={12}>
											<TextField label="Name" variant="outlined" fullWidth />
										</Grid>
										<Grid item xs={4} sm={3}>
											<TextField label="ZIP code" variant="outlined" fullWidth />
										</Grid>
										<Grid item xs={8} sm={9}>
											<TextField label="City" variant="outlined" fullWidth />
										</Grid>
										<Grid item xs={12} sm={12}>
											<TextField label="Address" variant="outlined" fullWidth />
										</Grid>
									</Grid>

									<TextField
										multiline
										rows={2}
										label="Comments, special requests"
										variant="outlined"
									/>
								</Stack>

								<Typography variant="body2" sx={{ opacity: 0.5 }} gutterBottom>
									After placing your order, a confirmation email will be sent to you.
								</Typography>
								<Button variant="contained" size="large" startIcon={<LocalMallRoundedIcon />}>
									Place order
								</Button>
							</>
						)}
					</Card>
				</Grid>
			</Grid>
		</Box>
	)
}

export default Checkout

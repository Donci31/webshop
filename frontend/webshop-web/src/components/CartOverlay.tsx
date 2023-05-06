import { Typography, Button, Box, Paper, ClickAwayListener, Divider, Stack, IconButton } from "@mui/material"
import DeleteRoundedIcon from "@mui/icons-material/DeleteRounded"
import useCart from "../hooks/useCart"
import useAuth from "../hooks/useAuth"
import currency from "../util/currency"
import { Link } from "react-router-dom"

interface CartOverlayProps {
	open: boolean
	onClose: () => void
}
export const CartOverlay = ({ open, onClose }: CartOverlayProps) => {
	const cart = useCart()
	const auth = useAuth()
	if (!open) {
		return null
	}
	return (
		<Box
			sx={{
				position: "fixed",
				top: 0,
				left: 0,
				width: "100%",
				height: "100%",
				backgroundColor: "rgba(0, 0, 0, 0.7)",
				zIndex: 1000,
			}}
		>
			<ClickAwayListener onClickAway={onClose}>
				<Paper sx={{ position: "fixed", top: 74, right: 5, width: 400, height: "auto", p: 2 }}>
					<Typography variant="h6">Cart ({cart.content.length})</Typography>
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
					</Stack>
					{!cart.content.length && (
						<Typography
							variant="body2"
							sx={{
								opacity: 0.5,
								pt: 1,
								pb: 2,
							}}
							align="center"
						>
							Cart is empty
						</Typography>
					)}
					<Divider sx={{ my: 2 }} />
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
							{currency(cart.content.reduce((acc, item) => acc + item.product.price * item.quantity, 0))}
						</Typography>
					</Stack>
					<Button
						fullWidth
						variant="contained"
						disabled={!cart.content.length}
						component={Link}
						to="/checkout"
						onClick={onClose}
					>
						{auth.loggedIn ? "Checkout" : "Login or register to checkout"}
					</Button>
				</Paper>
			</ClickAwayListener>
		</Box>
	)
}

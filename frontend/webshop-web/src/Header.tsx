import { AppBar, Toolbar, Typography, Button, Box, Paper, ClickAwayListener, Divider } from "@mui/material"
import ShoppingCartRoundedIcon from "@mui/icons-material/ShoppingCartRounded"
import useCart from "./hooks/useCart"
import { useState } from "react"
import useAuth from "./hooks/useAuth"

interface CartOverlayProps {
	open: boolean
	onClose: () => void
}
const CartOverlay = ({ open, onClose }: CartOverlayProps) => {
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
			}}
		>
			<ClickAwayListener onClickAway={onClose}>
				<Paper sx={{ position: "fixed", top: 74, right: 5, width: 400, height: "auto", p: 2 }}>
					<Typography variant="h6">Cart ({cart.content.length})</Typography>
					{cart.content.map((item) => (
						<Box key={item.product.id}>
							<Typography>{item.product.name}</Typography>
							<Typography>{item.quantity}</Typography>
						</Box>
					))}
					{!cart.content.length && (
						<Typography
							variant="body2"
							sx={{
								opacity: 0.5,
								pt: 4,
								pb: 2,
							}}
							align="center"
						>
							Cart is empty
						</Typography>
					)}
					<Divider sx={{ my: 2 }} />
					<Button fullWidth variant="contained" disabled={!cart.content.length}>
						{auth.loggedIn ? "Checkout" : "Login or register to checkout"}
					</Button>
				</Paper>
			</ClickAwayListener>
		</Box>
	)
}

const Header = () => {
	const cart = useCart()
	const [cartOverlayOpen, setCartOverlayOpen] = useState(false)

	return (
		<AppBar position="static">
			<Toolbar>
				<Typography
					variant="h6"
					sx={{
						flexGrow: 1,
					}}
				>
					AlfWebShop
				</Typography>
				<Button
					variant="contained"
					startIcon={<ShoppingCartRoundedIcon />}
					onClick={() => setCartOverlayOpen(true)}
				>
					Cart ({cart.content.length})
				</Button>
				<CartOverlay open={cartOverlayOpen} onClose={() => setCartOverlayOpen(false)} />
			</Toolbar>
		</AppBar>
	)
}

export default Header

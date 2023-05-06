import { AppBar, Toolbar, Typography, Button, IconButton, Tooltip } from "@mui/material"
import ShoppingCartRoundedIcon from "@mui/icons-material/ShoppingCartRounded"
import useCart from "../hooks/useCart"
import { useEffect, useMemo, useState } from "react"
import { CartOverlay } from "./CartOverlay"
import { Link } from "react-router-dom"
import useAuth from "../hooks/useAuth"
import LogoutRoundedIcon from "@mui/icons-material/LogoutRounded"

const Header = () => {
	const cart = useCart()
	const auth = useAuth()
	const [cartOverlayOpen, setCartOverlayOpen] = useState(false)
	const cartSize = useMemo(() => cart.content.reduce((acc, item) => acc + item.quantity, 0), [cart.content])

	const [buttonGrow, setButtonGrow] = useState(false)

	useEffect(() => {
		if (cartSize > 0) {
			setButtonGrow(true)
			setTimeout(() => setButtonGrow(false), 200)
		}
	}, [cartSize])

	return (
		<AppBar position="static">
			<Toolbar>
				<Typography
					variant="h6"
					sx={{
						flexGrow: 1,
						color: "white",
						textDecoration: "none",
					}}
					component={Link}
					to="/"
				>
					AlfWebShop
				</Typography>
				{auth.user && (
					<>
						<Typography sx={{ mr: 2 }}>{auth.user.name || auth.user.email}</Typography>
						<Tooltip title="Logout" arrow>
							<IconButton onClick={() => auth.logout()} sx={{ mr: 2 }} color="inherit">
								<LogoutRoundedIcon />
							</IconButton>
						</Tooltip>
					</>
				)}
				<Button
					variant="contained"
					startIcon={<ShoppingCartRoundedIcon />}
					onClick={() => setCartOverlayOpen(true)}
					sx={{
						transition: "all 200ms ease-in-out",
						transform: buttonGrow ? "scale(1.05)" : "scale(1)",
					}}
				>
					Cart ({cartSize})
				</Button>
				<CartOverlay open={cartOverlayOpen} onClose={() => setCartOverlayOpen(false)} />
			</Toolbar>
		</AppBar>
	)
}

export default Header

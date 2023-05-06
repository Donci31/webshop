import { AuthContextProvider } from "./context/AuthContext"
import { ThemeProvider, CssBaseline } from "@mui/material"
import { RouterProvider } from "react-router-dom"
import router from "./components/router"
import theme from "./theme"
import { CartContextProvider } from "./context/CartContext"

const App = () => {
	return (
		<AuthContextProvider>
			<CartContextProvider>
				<ThemeProvider theme={theme}>
					<CssBaseline />
					<RouterProvider router={router} />
				</ThemeProvider>
			</CartContextProvider>
		</AuthContextProvider>
	)
}

export default App

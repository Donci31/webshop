import { AuthContextProvider } from "./AuthContext"
import { ThemeProvider, CssBaseline } from "@mui/material"
import { RouterProvider } from "react-router-dom"
import router from "./router"
import theme from "./theme"
import Header from "./Header"
import { CartContextProvider } from "./CartContext"

const App = () => {
	return (
		<AuthContextProvider>
			<CartContextProvider>
				<ThemeProvider theme={theme}>
					<CssBaseline />
					<Header />
					<RouterProvider router={router} />
				</ThemeProvider>
			</CartContextProvider>
		</AuthContextProvider>
	)
}

export default App

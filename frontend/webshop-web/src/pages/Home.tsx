import { useEffect, useState } from "react"
import WebShopApi from "../api/WebShopApi"
import IProduct from "../types/IProduct"
import ProductCard from "../components/ProductCard"
import { Box, Grid, Typography } from "@mui/material"

const Home = () => {
	const [products, setProducts] = useState<IProduct[]>([])
	useEffect(() => {
		WebShopApi.products.all().then(setProducts)
	}, [])

	return (
		<Box sx={{ p: 2 }}>
			<Typography variant="h4" gutterBottom>
				Products
			</Typography>
			<Grid container spacing={2}>
				{products.map((product) => (
					<Grid item xs={12} sm={6} md={4} lg={3} xl={2} key={product.id}>
						<ProductCard key={product.id} product={product} />
					</Grid>
				))}
			</Grid>
		</Box>
	)
}

export default Home

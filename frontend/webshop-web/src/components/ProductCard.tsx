import { Button, Card, CardActions, CardContent, CardMedia, Typography } from "@mui/material"
import IProduct from "../types/IProduct"
import useCart from "../hooks/useCart"
import currency from "../util/currency"

const ProductCard = ({ product }: { product: IProduct }) => {
	const cart = useCart()

	return (
		<Card>
			<CardMedia
				component="img"
				height="200"
				image={"https://picsum.photos/500/200/?" + product.id}
				alt={product.name}
			/>
			<CardContent>
				<Typography variant="h5" component="div">
					{product.name}
				</Typography>
				<Typography sx={{ mb: 1.5 }} color="text.secondary">
					{currency(product.price)}
				</Typography>
				<Typography variant="body2">{product.description}</Typography>
			</CardContent>
			<CardActions sx={{ justifyContent: "flex-end" }}>
				<Button variant="contained" onClick={() => cart.add(product, 1)}>
					Add to cart
				</Button>
			</CardActions>
		</Card>
	)
}

export default ProductCard

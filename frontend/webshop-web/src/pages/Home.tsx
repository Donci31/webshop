import { useEffect, useState } from "react"
import WebShopApi from "../WebShopApi"
import IProduct from "../types/IProduct"

const Home = () => {
	const [products, setProducts] = useState<IProduct[]>([])
	useEffect(() => {
		WebShopApi.products.all().then(setProducts)
	}, [])

	return (
		<div>
			<h1>Home</h1>
			<pre>{JSON.stringify(products, null, 2)}</pre>
		</div>
	)
}

export default Home

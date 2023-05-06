interface IOrder {
	shipping_zip: string
	shipping_city: string
	shipping_address: string
	comment: string
	products: {
		product_id: number
		quantity: number
	}[]
}

export default IOrder

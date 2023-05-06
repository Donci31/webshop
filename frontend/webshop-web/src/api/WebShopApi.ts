import ICategory from "../types/ICategory"
import IOrder from "../types/IOrder"
import IProduct from "../types/IProduct"
import { api } from "./api"

const WebShopApi = {
	auth: {
		login: async (email: string, password: string) =>
			api.post<unknown, { message: string; token: string | null }>("/login", {
				email,
				password,
			}),
		signUp: (email: string, password: string, name: string) =>
			api.post<unknown, { message: string; token: string | null }>("/register", { email, password, name }),
		profile: () => api.get<{ id: number; name: string; email: string }>("/profile"),
	},
	products: {
		all: () => api.get<IProduct[]>("/products"),
		get: (id: number) => api.get<IProduct>(`/products/${id}`),
		create: (product: IProduct) => api.post<IProduct>("/products", product),
		update: (product: IProduct) => api.put<IProduct>(`/products/${product.id}`, product),
		delete: (id: number) => api.delete<IProduct>(`/products/${id}`),
		getByCategory: (categoryId: number) => api.get<IProduct[]>(`/categories/${categoryId}/products`),
	},
	category: {
		all: () => api.get<ICategory[]>("/categories"),
		get: (id: number) => api.get<ICategory>(`/categories/${id}`),
		create: (category: ICategory) => api.post<ICategory>("/categories", category),
		update: (category: ICategory) => api.put<ICategory>(`/categories/${category.id}`, category),
		delete: (id: number) => api.delete<ICategory>(`/categories/${id}`),
	},
	order: {
		submit: (order: IOrder) => api.post<IOrder>("/orders", order),
	},
}

export default WebShopApi

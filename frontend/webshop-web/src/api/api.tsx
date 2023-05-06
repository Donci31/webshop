import axios from "axios"

const instance = axios.create({
	baseURL: "http://localhost:8080/api/",
})

instance.interceptors.request.use((config) => {
	const token = localStorage.getItem("token")
	if (token) {
		config.headers.Authorization = `Bearer ${token}`
	}
	return config
})

export const api = {
	get: async <TResponse,>(url: string) => {
		const response = await instance.get(url)
		return response.data as TResponse
	},
	post: async <TRequest, TResponse = unknown>(url: string, data: TRequest) => {
		const response = await instance.post(url, data)
		return response.data as TResponse
	},
	put: async <TRequest, TResponse = unknown>(url: string, data: TRequest) => {
		const response = await instance.put(url, data)
		return response.data as TResponse
	},
	delete: async <TResponse = unknown,>(url: string) => {
		const response = await instance.delete(url)
		return response.data as TResponse
	},
}

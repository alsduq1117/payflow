import axios from 'axios'
import { useAuthStore } from '@/stores/auth'
import router from "@/router";

axios.defaults.withCredentials = true

axios.interceptors.request.use((config) => {
  const isS3 = config.url?.includes('.s3.') && config.url.includes('.amazonaws.com')

  if (!isS3) {
    const auth = useAuthStore()
    if (auth.accessToken) {
      config.headers.Authorization = `Bearer ${auth.accessToken}`
    }
  }

  return config
})

axios.interceptors.response.use(
  (res) => res,
  async (error) => {
    const auth = useAuthStore()
    const originalRequest = error.config

    const isUnauthorized = error.response?.status === 401
    const isRefreshRequest = originalRequest.url?.includes('/api/v1/auth/refresh')
    const isLoggedIn = !!auth.accessToken

    if (isUnauthorized && !originalRequest._retry && !isRefreshRequest && isLoggedIn) {
      originalRequest._retry = true
      const success = await auth.tryRefreshToken()

      if (success) {
        originalRequest.headers.Authorization = `Bearer ${auth.accessToken}`
        return axios(originalRequest)
      } else {
        auth.clearAuth()
        router.push('/login')
      }
    }

    return Promise.reject(error)
  }
)

export default axios

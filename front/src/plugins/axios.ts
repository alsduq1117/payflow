import axios from 'axios'
import { useAuthStore } from '@/stores/auth'
import router from "@/router";

axios.defaults.withCredentials = true

axios.interceptors.request.use((config) => {
  const auth = useAuthStore()
  if (!config.url?.startsWith('/api/v1/auth') && auth.accessToken) {
    config.headers.Authorization = `Bearer ${auth.accessToken}`
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
    const isLoggedIn = !!auth.accessToken // ✅ 이 조건 추가

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

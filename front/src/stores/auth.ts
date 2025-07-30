import { defineStore } from 'pinia'
import axios from '@/plugins/axios'
import type { User } from '@/types'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    accessToken: '',
    user: null as User | null,
  }),

  getters: {
    isAuthenticated: (state) => !!state.accessToken && !!state.user,
  },

  actions: {
    setToken(token: string) {
      this.accessToken = token
    },

    setUser(user: User) {
      this.user = user
    },

    clearAuth() {
      this.accessToken = ''
      this.user = null
    },

    async fetchUser() {
      try {
        const res = await axios.get('/api/v1/users/me')
        this.setUser(res.data)
        return true
      } catch {
        this.clearAuth()
        return false
      }
    },

    async tryRefreshToken() {
      if (this.accessToken) return true // 이미 있음

      try {
        const res = await axios.post('/api/v1/auth/refresh', {}, { withCredentials: true })
        this.setToken(res.data.accessToken)
        return await this.fetchUser()
      } catch {
        this.clearAuth()
        return false
      }
    },

    async initAuth() {
      if (this.accessToken && this.user) return true
      return await this.tryRefreshToken()
    },
  },
})

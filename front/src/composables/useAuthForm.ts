import {ref} from 'vue'
import {useRouter} from 'vue-router'
import {useAuthStore} from '@/stores/auth'
import axios from '@/plugins/axios'

export function useAuthForm() {
  const router = useRouter()
  const auth = useAuthStore()
  const errorMessage = ref('')

  // 소셜 로그인 공통 함수
  const loginWithProvider = (provider: 'google' | 'kakao' | 'naver') => {
    const base = (import.meta.env.VITE_API_BASE_URL || '').trim().replace(/\/+$/, '');
    const path = `/oauth2/authorization/${provider}`;
    const url  = base ? `${base}${path}` : path; // base가 없으면 동일 도메인으로
    window.location.href = url;
  }

  // 이메일 로그인
  const emailLogin = async (email: string, password: string) => {
    try {
      const res = await axios.post('/api/v1/auth/login', {email, password}, {withCredentials: true})
      auth.setToken(res.data.accessToken)
      await auth.fetchUser()
      router.push('/')
      return true
    } catch (err) {
      console.error('로그인 실패', err)
      errorMessage.value = '이메일 또는 비밀번호가 잘못되었습니다.'
      return false
    }
  }

  // 회원가입
  const emailSignup = async (email: string, password: string) => {
    try {
      await axios.post('/api/v1/auth/signup', {email, password})
      router.push('/login')
      return true
    } catch (err) {
      console.error('회원가입 실패', err)
      errorMessage.value = '회원가입에 실패했습니다. 다시 시도해주세요.'
      return false
    }
  }

  return {
    loginWithProvider,
    emailLogin,
    emailSignup,
    errorMessage
  }
}

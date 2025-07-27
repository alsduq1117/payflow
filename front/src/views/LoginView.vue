<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const email = ref('')
const password = ref('')
const emailError = ref('')

function submit() {
  emailError.value = email.value ? '' : '이메일을 입력해주세요.'
  if (!email.value) return

  // TODO: 로그인 처리
  console.log('로그인 요청', { email: email.value, password: password.value })
  router.push('/')
}

function loginWithProvider(provider: 'google' | 'kakao' | 'naver') {
  console.log(`${provider} 로그인 시도`)
  // TODO: 각 provider에 맞는 OAuth2 로그인 창 열기
  window.location.href = `http://localhost:8080/oauth2/authorization/${provider}` // 예시 URI
}
</script>

<template>
  <v-container
    class="d-flex flex-column align-center justify-center"
    style="max-width: 400px; min-height: 100vh;"
  >
  <h2 class="text-h5 font-weight-bold mb-6">로그인</h2>

  <v-form class="w-100" @submit.prevent="submit">
    <v-text-field
      label="이메일 주소"
      v-model="email"
      :error-messages="emailError"
      variant="outlined"
      rounded="lg"
      hide-details="auto"
      class="mb-4"
    />
    <v-text-field
      label="비밀번호"
      v-model="password"
      type="password"
      variant="outlined"
      rounded="lg"
      class="mb-4"
    />
    <v-btn type="submit" block color="#1E88E5" class="mt-4 py-3 rounded-lg" size="large">
      로그인
    </v-btn>
  </v-form>

  <!-- 소셜 로그인 아이콘 -->
  <div class="d-flex justify-center mt-10">
    <img
      src="/social/google.png"
      alt="Google"
      @click="loginWithProvider('google')"
      class="sns-icon me-10"
    />
    <img
      src="/social/kakao.png"
      alt="Kakao"
      @click="loginWithProvider('kakao')"
      class="sns-icon me-10"
    />
    <img
      src="/social/naver.png"
      alt="Naver"
      @click="loginWithProvider('naver')"
      class="sns-icon"
    />
  </div>

  <div class="mt-8 text-body-2">
    <router-link to="/signup" class="me-4">회원가입</router-link>|
    <router-link to="/forgot" class="ms-4">비밀번호 찾기</router-link>
  </div>
  </v-container>
</template>


<style scoped>
.v-btn img {
  margin-right: 8px;
}

.sns-icon {
  width: 48px;
  height: 48px;
  border-radius: 50%;
  cursor: pointer;
  transition: 0.2s ease;
  box-shadow: 0 1px 3px rgba(0,0,0,0.1);
}
.sns-icon:hover {
  transform: scale(1.1);
  opacity: 0.85;
}
</style>


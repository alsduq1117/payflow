<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const email = ref('')
const password = ref('')
const passwordConfirm = ref('')
const name = ref('')
const agree = ref(false)

const emailError = ref('')
const passwordError = ref('')
const passwordConfirmError = ref('')
const agreeError = ref('')

function validate() {
  emailError.value = email.value ? '' : '이메일을 입력해주세요.'
  passwordError.value = password.value ? '' : '비밀번호를 입력해주세요.'
  passwordConfirmError.value = password.value === passwordConfirm.value ? '' : '비밀번호가 일치하지 않습니다.'
  agreeError.value = agree.value ? '' : '개인정보 수집 및 이용에 동의해주세요.'

  return !(emailError.value || passwordError.value || passwordConfirmError.value || agreeError.value)
}

function submit() {
  if (!validate()) return

  console.log('회원가입 요청', {
    email: email.value,
    password: password.value,
    name: name.value,
  })

  router.push('/login')
}

function loginWithProvider(provider: 'google' | 'kakao' | 'naver') {
  console.log(`${provider} 로그인 시도`)
  // TODO: 각 provider에 맞는 OAuth2 로그인 창 열기
  window.location.href = `http://localhost:8080/oauth2/authorization/${provider}` // 예시 URI
}
</script>

<template>
  <v-container fluid class="d-flex justify-center">
    <div class="w-100" style="max-width: 420px; margin-top: 0px;">
      <div class="text-center mb-6">
        <v-img src="/logo.svg" width="80" class="mx-auto mb-2" />
        <h1 class="text-h6 font-weight-bold">회원가입</h1>
      </div>

      <v-form @submit.prevent="submit">
        <v-text-field
          label="이메일 주소"
          v-model="email"
          :error-messages="emailError"
          variant="outlined"
          density="comfortable"
          class="mb-3 rounded-lg"
        />
        <v-text-field
          label="비밀번호"
          v-model="password"
          :error-messages="passwordError"
          type="password"
          variant="outlined"
          density="comfortable"
          class="mb-3 rounded-lg"
        />
        <v-text-field
          label="비밀번호 확인"
          v-model="passwordConfirm"
          :error-messages="passwordConfirmError"
          type="password"
          variant="outlined"
          density="comfortable"
          class="mb-3 rounded-lg"
        />

        <v-checkbox
          v-model="agree"
          label="[필수] 개인정보 수집 및 이용동의"
          :error-messages="agreeError"
          class="mb-4 rounded-lg"
        />

        <v-btn type="submit" block color="#1E88E5" size="large" class="mt-4 rounded-lg">가입하기</v-btn>

        <div class="d-flex align-center my-6">
          <div class="flex-grow-1" style="height: 1px; background-color: #E0E0E0;"></div>
          <span class="px-4 text-caption text-grey">간편 회원가입</span>
          <div class="flex-grow-1" style="height: 1px; background-color: #E0E0E0;"></div>
        </div>

        <div class="d-flex justify-center mt-5">
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
      </v-form>
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


<script setup lang="ts">
import { ref } from 'vue'
import { useAuthForm, useFormValidators } from '@/composables'
import SocialLogin from '@/components/SocialLogin.vue'

const { validateRequired, validatePasswordMatch, validateAgreement } = useFormValidators()
const { emailSignup, loginWithProvider, errorMessage } = useAuthForm()

const email = ref('')
const password = ref('')
const passwordConfirm = ref('')
const agree = ref(false)

const emailError = ref('')
const passwordError = ref('')
const passwordConfirmError = ref('')
const agreeError = ref('')

async function submit() {
  emailError.value = validateRequired(email.value, '이메일')
  passwordError.value = validateRequired(password.value, '비밀번호')
  passwordConfirmError.value = validatePasswordMatch(password.value, passwordConfirm.value)
  agreeError.value = validateAgreement(agree.value)

  if (emailError.value || passwordError.value || passwordConfirmError.value || agreeError.value) return

  await emailSignup(email.value, password.value)
}
</script>

<template>
  <v-container fluid class="d-flex justify-center">
    <div class="w-100" style="max-width: 400px; min-height: 100vh;">
      <div class="text-center mb-6">
        <v-img src="/logo.svg" width="80" class="mx-auto mb-2" />
        <h1 class="text-h6 font-weight-bold">회원가입</h1>
      </div>

      <v-alert v-if="errorMessage" type="error" class="mb-4">{{ errorMessage }}</v-alert>

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
          :error-messages="agreeError"
          label="[필수] 개인정보 수집 및 이용동의"
          class="mb-4 rounded-lg"
        />

        <v-btn type="submit" block color="#1E88E5" size="large" class="mt-4 rounded-lg">가입하기</v-btn>

        <SocialLogin label="회원가입" :providers="['google', 'kakao', 'naver']" @login="loginWithProvider" />
      </v-form>
    </div>
  </v-container>
</template>

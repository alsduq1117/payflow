<script setup lang="ts">
import { ref } from 'vue'
import { useAuthForm, useFormValidators } from '@/composables'
import SocialLogin from '@/components/SocialLogin.vue'

const { validateRequired } = useFormValidators()
const { emailLogin, loginWithProvider, errorMessage } = useAuthForm()

const email = ref('')
const password = ref('')
const emailError = ref('')
const passwordError = ref('')

async function submit() {
  emailError.value = validateRequired(email.value, '이메일')
  passwordError.value = validateRequired(password.value, '비밀번호')

  if (emailError.value || passwordError.value) return

  await emailLogin(email.value, password.value)
}
</script>

<template>
  <v-container
    class="d-flex flex-column align-center justify-center"
    style="max-width: 400px; min-height: 100vh;"
  >
    <h2 class="text-h5 font-weight-bold mb-6">로그인</h2>

    <v-alert v-if="errorMessage" type="error" class="mb-4">{{ errorMessage }}</v-alert>

    <v-form class="w-100" @submit.prevent="submit">
      <v-text-field
        label="이메일 주소"
        v-model="email"
        :error-messages="emailError"
        variant="outlined"
        rounded="lg"
        class="mb-4"
      />
      <v-text-field
        label="비밀번호"
        v-model="password"
        type="password"
        :error-messages="passwordError"
        variant="outlined"
        rounded="lg"
        class="mb-4"
      />
      <v-btn type="submit" block color="#1E88E5" class="mt-4 py-3 rounded-lg" size="large">
        로그인
      </v-btn>

      <SocialLogin label="로그인" @login="loginWithProvider" />

      <div class="mt-10 text-body-2 d-flex justify-center">
        <router-link to="/signup" class="me-4 text-black text-decoration-none">회원가입</router-link>|
        <router-link to="/forgot" class="ms-4 text-black text-decoration-none">비밀번호 찾기</router-link>
      </div>
    </v-form>
  </v-container>
</template>

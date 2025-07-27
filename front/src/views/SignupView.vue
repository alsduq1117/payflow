<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const email = ref('')
const password = ref('')
const passwordConfirm = ref('')
const name = ref('')

const emailError = ref('')
const passwordError = ref('')
const passwordConfirmError = ref('')

function validate() {
  emailError.value = email.value ? '' : '이메일을 입력해주세요.'
  passwordError.value = password.value ? '' : '비밀번호를 입력해주세요.'
  passwordConfirmError.value = password.value === passwordConfirm.value ? '' : '비밀번호가 일치하지 않습니다.'

  return !(emailError.value || passwordError.value || passwordConfirmError.value)
}

function submit() {
  if (!validate()) return

  // TODO: 실제 회원가입 API 호출
  console.log('회원가입 요청', {
    email: email.value,
    password: password.value,
    name: name.value,
  })

  router.push('/login')
}
</script>

<template>
  <v-container class="d-flex flex-column align-center mt-10" style="max-width: 400px">
    <h2 class="text-h5 font-weight-bold mb-6">회원가입</h2>

    <v-form class="w-100" @submit.prevent="submit">
      <v-text-field
        label="이메일 주소"
        v-model="email"
        :error-messages="emailError"
        variant="outlined"
        density="comfortable"
        hide-details="auto"
        class="mb-3"
      />
      <v-text-field
        label="비밀번호"
        v-model="password"
        :error-messages="passwordError"
        type="password"
        variant="outlined"
        density="comfortable"
        hide-details="auto"
        class="mb-3"
      />
      <v-text-field
        label="비밀번호 확인"
        v-model="passwordConfirm"
        :error-messages="passwordConfirmError"
        type="password"
        variant="outlined"
        density="comfortable"
        hide-details="auto"
        class="mb-3"
      />
      <v-text-field label="이름" v-model="name" variant="outlined" class="mb-3" />

      <v-btn type="submit" block color="primary" class="mt-4" size="large">회원가입</v-btn>
    </v-form>
  </v-container>
</template>

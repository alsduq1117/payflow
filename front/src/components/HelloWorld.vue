<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

function goToTutorial() {
  router.push('/tutorial')
}
</script>

<template>
  <button
    @click="goToTutorial"
    class="bg-blue-500 hover:bg-blue-700 text-white px-4 py-2 rounded"
  >
    튜토리얼 가기
  </button>
</template>

<style scoped>
</style>

<script setup lang="ts">
defineProps<{
  label?: string
}>()

const emit = defineEmits(['login'])

const providers = [
  { id: 'google', name: 'Google' },
  { id: 'kakao', name: 'Kakao' },
  { id: 'naver', name: 'Naver' }
]
</script>

<template>
  <div>
    <div class="d-flex align-center my-6">
      <div class="flex-grow-1" style="height: 1px; background-color: #E0E0E0;"></div>
      <span class="px-4 text-caption text-grey">간편 {{ label || '로그인' }}</span>
      <div class="flex-grow-1" style="height: 1px; background-color: #E0E0E0;"></div>
    </div>

    <div class="d-flex justify-center">
      <img
        v-for="provider in providers"
        :key="provider.id"
        :src="`/social/${provider.id}.png`"
        :alt="provider.name"
        @click="emit('login', provider.id)"
        class="sns-icon mx-4"
      />
    </div>
  </div>
</template>

<style scoped>
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

<script setup lang="ts">
import { onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/auth';

const router = useRouter();
const auth = useAuthStore();

onMounted(async () => {
  const hash = window.location.hash.substring(1);
  const params = new URLSearchParams(hash);
  const accessToken = params.get('access_token');

  if (!accessToken) {
    alert('로그인에 실패했거나 토큰이 없습니다.');
    return router.push('/login');
  }

  try {
    auth.setToken(accessToken);
    await auth.fetchUser();
    router.push('/');
  } catch (error) {
    console.error('로그인 처리 실패:', error);
    router.push('/login');
  }
});
</script>

<template>
  <div>로그인 처리 중...</div>
</template>

<script setup lang="ts">
import {ref, onMounted, onUnmounted, computed} from 'vue';
import {useAuthStore} from '@/stores/auth.ts';
import {storeToRefs} from 'pinia';
import HeaderNav from './HeaderNav.vue';
import HeaderAuth from './HeaderAuth.vue';
import axios from "axios";

const auth = useAuthStore();
const {accessToken} = storeToRefs(auth);
const isLoggedIn = computed(() => !!accessToken.value);
const isScrolled = ref(false);

const handleScroll = () => {
  isScrolled.value = window.scrollY > 0;
};

const logout = async () => {
  try {
    await axios.post('/api/v1/auth/logout');
  } catch (e) {
    console.warn('서버 로그아웃 실패:', e);
  }
  window.location.href = '/';
};

onMounted(() => {
  window.addEventListener('scroll', handleScroll);
});

onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll);
});
</script>

<template>
  <v-app-bar app height="52" color="white" :elevation="isScrolled ? 2 : 0" flat>
    <v-container fluid class="header-container">
      <HeaderNav/>
      <HeaderAuth :is-logged-in="isLoggedIn" @logout="logout"/>
    </v-container>
  </v-app-bar>
</template>

<style scoped>
.header-container {
  padding-left: 30px;
  padding-right: 16px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
</style>

<script setup lang="ts">
import {ref, onMounted, onUnmounted, computed} from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/auth';
import { storeToRefs } from 'pinia';
import HeaderNav from './HeaderNav.vue';
import HeaderAuth from './HeaderAuth.vue';

const router = useRouter();
const auth = useAuthStore();
const { accessToken } = storeToRefs(auth);
const isLoggedIn = computed(() => !!accessToken.value);
const isScrolled = ref(false);

const handleScroll = () => {
  isScrolled.value = window.scrollY > 0;
};

const logout = () => {
  auth.clearAuth();
  router.push('/');
};

onMounted(() => {
  window.addEventListener('scroll', handleScroll);
});

onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll);
});
</script>

<template>
  <v-app-bar
    app
    height="52"
    color="white"
    :elevation="isScrolled ? 2 : 0"
    flat
  >
    <v-container fluid class="header-container">
      <HeaderNav />
      <HeaderAuth :is-logged-in="isLoggedIn" @logout="logout" />
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

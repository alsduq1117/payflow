<script setup lang="ts">
import {computed, onMounted, ref} from 'vue';
import { useRoute } from 'vue-router';
import { useAuthStore } from '@/stores/auth';
import AppHeader from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';

const route = useRoute();
const auth = useAuthStore();
const showHeader = computed(() => !route.meta.hideHeader);
const isAppReady = ref(false)

onMounted(async () => {
  await auth.initAuth();
  isAppReady.value = true
});
</script>

<template>
  <v-app v-if="isAppReady">
    <AppHeader v-if="showHeader" />
    <v-main style="background-color: #ffffff">
      <router-view/>
    </v-main>
    <Footer />
  </v-app>
</template>

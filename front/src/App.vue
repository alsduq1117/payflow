<script setup lang="ts">
import { computed, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { useAuthStore } from '@/stores/auth';
import AppHeader from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';

const route = useRoute();
const auth = useAuthStore();
const showHeader = computed(() => !route.meta.hideHeader);

onMounted(() => {
  auth.initAuth();
});
</script>

<template>
  <v-app>
    <AppHeader v-if="showHeader" />
    <v-main style="background-color: #ffffff">
      <router-view/>
    </v-main>
    <Footer />
  </v-app>
</template>

<style>
</style>

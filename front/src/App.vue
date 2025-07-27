<script setup lang="ts">
import { useRoute } from 'vue-router'
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";

const route = useRoute();

const isAuthPage = () => {
  const authPaths = ['/login', '/signup']; // 실제 경로로 수정
  return authPaths.includes(route.path);
};
</script>


<template>
  <v-app>
    <Header v-if="!isAuthPage()" />
    <v-main style="background-color: #ffffff">
      <router-view/>
    </v-main>
    <Footer />
  </v-app>
</template>

<style>
</style>

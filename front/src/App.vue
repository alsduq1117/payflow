<script setup lang="ts">
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
</script>

<template>
  <v-app>
    <Header />
    <v-main style="background-color: #ffffff">
      <router-view/>
    </v-main>
    <Footer />
  </v-app>
</template>

<style>
</style>

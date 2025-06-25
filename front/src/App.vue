<script setup lang="ts">
import Header from "@/components/Header.vue";
</script>

<template>
  <v-app>
    <Header />
    <v-main>
      <router-view/>
    </v-main>
    <Footer />
  </v-app>
</template>

<style>
</style>

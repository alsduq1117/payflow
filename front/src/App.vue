<script setup lang="ts">
import {computed, onMounted, ref} from 'vue';
import {useRoute} from 'vue-router';
import {useAuthStore} from '@/stores/auth';
import AppHeader from '@/components/Header.vue';
import AppFooter from '@/components/Footer.vue';
import SidebarMenu from "@/components/SidebarMenu.vue";

const route = useRoute();
const auth = useAuthStore();
const showHeader = computed(() => !route.meta.hideHeader);
const showFooter = computed(() => !route.meta.hideFooter);
const showSidebar = computed(() => route.matched.some(r => r.meta?.showSidebar))
const isAppReady = ref(false)

onMounted(async () => {
  await auth.initAuth();
  isAppReady.value = true
});
</script>

<template>
  <v-app v-if="isAppReady">
    <AppHeader v-if="showHeader"/>
    <SidebarMenu v-if="showSidebar"/>
    <v-main style="background-color: #ffffff">
      <router-view/>
    </v-main>
    <AppFooter v-if="showFooter"/>
  </v-app>
</template>

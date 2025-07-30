<script setup lang="ts">
const navItems = [
  { path: '/product-create', label: '상품 등록' },
  { path: '/api', label: '장바구니' },
  { path: '/sandbox', label: '샌드박스' },
  { path: '/support', label: '커뮤니티·지원' },
  { path: '/blog', label: '블로그' },
];
</script>

<template>
  <div class="header-left">
    <router-link to="/" class="logo-text text-decoration-none">
      Payflow
    </router-link>

    <v-btn
      v-for="item in navItems"
      :key="item.path"
      text
      :to="item.path"
      class="nav-button"
    >
      {{ item.label }}
    </v-btn>
  </div>
</template>


<style scoped>
.header-left {
  display: flex;
  align-items: center;
  gap: 10px;
}

.logo-text {
  font-size: 1.5rem;
  font-weight: bold;
  text-decoration: none;
  color: #1E2D60;
}

.nav-button {
  color: #1E2D60;
  font-weight: 500;
  text-transform: none;
  font-size: 0.95rem;
  letter-spacing: 0.2px;
  padding: 6px 12px;
}
</style>

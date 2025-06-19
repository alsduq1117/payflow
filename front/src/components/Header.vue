<script setup lang="ts">
</script>
<template>
  <header class="bg-white shadow-sm fixed top-0 w-full z-50">
    <div class="max-w-full mx-auto px-10 py-5 flex items-center justify-between">

      <!-- 브랜드 이름 -->
      <h1 class="text-2xl font-bold text-blue-600 tracking-wide">
        Kirminov
      </h1>

      <!-- 내비게이션 -->
      <nav class="space-x-6 hidden md:flex">
        <RouterLink to="/" class="text-gray-700 hover:text-blue-500">Home</RouterLink>
        <RouterLink to="/dashboard" class="text-gray-700 hover:text-blue-500">Dashboard</RouterLink>
        <RouterLink to="/wallet" class="text-gray-700 hover:text-blue-500">Wallet</RouterLink>
        <RouterLink to="/settings" class="text-gray-700 hover:text-blue-500">Settings</RouterLink>
      </nav>

      <!-- 로그인/회원가입 -->
      <div class="hidden md:flex space-x-4">
        <button class="text-gray-600 hover:text-blue-600">Login</button>
        <button class="bg-blue-600 text-white px-4 py-1.5 rounded hover:bg-blue-700">Sign up</button>
      </div>

    </div>
  </header>
</template>

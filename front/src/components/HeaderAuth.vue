<script setup lang="ts">
import { ref } from 'vue';

defineProps({
  isLoggedIn: Boolean
});

const emit = defineEmits(['logout']);
const userMenuItems = ref([
  { to: '/mypage', title: '마이페이지' },
  { action: 'logout', title: '로그아웃' }
]);
</script>

<template>
  <template v-if="isLoggedIn">
    <div class="header-right">
      <v-menu open-on-hover location="bottom" origin="right">
        <template #activator="{ props }">
          <div v-bind="props" class="icon-wrapper">
            <v-icon size="30" color="#1E2D60">mdi-account-circle</v-icon>
          </div>
        </template>
        <v-list>
          <v-list-item
            v-for="item in userMenuItems"
            :key="item.title"
            :to="item.to"
            @click="item.action === 'logout' && emit('logout')"
            :title="item.title"
          />
        </v-list>
      </v-menu>

      <router-link to="/cart" class="cart-icon-link icon-wrapper" aria-label="장바구니">
        <v-icon start size="30" color="#1E2D60">mdi-cart</v-icon>
      </router-link>
    </div>
  </template>

  <template v-else>
    <div class="header-right">
      <router-link to="/login">
        <v-btn style="background-color: #1E88E5; color: white;">
          로그인
        </v-btn>
      </router-link>
      <router-link to="/signup">
        <v-btn color="#1E2D60">
          회원가입
        </v-btn>
      </router-link>
    </div>
  </template>
</template>

<style scoped>
.header-right {
  display: flex;
  align-items: center;
  gap: 8px;
}

.icon-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  cursor: pointer;
  text-decoration: none;
  color: #1E2D60;
  cursor: pointer;
}

.icon-wrapper:hover {
  opacity: 0.7;
}

.cart-icon-link {
  text-decoration: none;
  margin-left: 15px;
  margin-right: 30px;
}
</style>

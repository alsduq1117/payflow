<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'

const isLoggedIn = ref(false)
const isScrolled = ref(false)

const handleScroll = () => {
  isScrolled.value = window.scrollY > 0
}
function logout() {
  console.log('로그아웃 처리')
  // TODO: 실제 logout 처리 (토큰 제거 등)
}
onMounted(() => {
  window.addEventListener('scroll', handleScroll)
})

onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll)
})
</script>

<template>
  <v-app-bar app height="52" color="white" :elevation="isScrolled ? 2 : 0" flat>
    <v-container fluid class="header-container">

      <!-- 좌측: 로고 + 메뉴 -->
      <div class="header-left">
        <!-- 로고 -->
        <router-link to="/" class="logo-text text-decoration-none">Payflow</router-link>

        <!-- 메뉴 버튼 -->
        <v-btn text to="/guide" class="nav-button">상품</v-btn>
        <v-btn text to="/api" class="nav-button">장바구니</v-btn>
        <v-btn text to="/sandbox" class="nav-button">샌드박스</v-btn>
        <v-btn text to="/support" class="nav-button">커뮤니티·지원</v-btn>
        <v-btn text to="/blog" class="nav-button">블로그</v-btn>
      </div>

      <!-- 우측: 사용자 메뉴 + 장바구니 -->
      <template v-if="isLoggedIn">
        <div class="header-right">
          <v-menu open-on-hover>
            <template #activator="{ props }">
              <div v-bind="props" class="icon-wrapper">
                <v-icon size="30" color="#1E2D60">mdi-account-circle</v-icon>
              </div>
            </template>
            <v-list>
              <v-list-item to="/profile" title="주문목록"/>
              <v-list-item @click="logout" title="로그아웃"/>
            </v-list>
          </v-menu>

          <v-tooltip text="장바구니" location="bottom" color="white">
            <template #activator="{ props }">
              <router-link to="/cart" class="cart-icon-link icon-wrapper" v-bind="props">
                <v-icon start size="30" color="#1E2D60">mdi-cart</v-icon>
              </router-link>
            </template>
          </v-tooltip>
        </div>
      </template>

      <template v-else>
        <div class="header-right" >
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

.header-left {
  display: flex;
  align-items: center;
  gap: 10px;
}

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

.logo-text {
  font-size: 1.5rem;
  font-weight: bold;
  text-decoration: none;
  color: #1E2D60;
}

/* 네비게이션 버튼 스타일 */
.nav-button {
  color: #1E2D60;
  font-weight: 500;
  text-transform: none;
  font-size: 0.95rem;
  letter-spacing: 0.2px;
  padding: 6px 12px;
}

.cart-icon-link {
  text-decoration: none;
  margin-left: 15px;
  margin-right: 30px;
}
</style>

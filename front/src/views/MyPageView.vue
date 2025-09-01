<script setup lang="ts">
import {onMounted, ref} from "vue";
import axios from "@/plugins/axios.ts";
import {useAuthStore} from "@/stores/auth.ts";
import type {OrderList} from "@/types";
import DownloadButton from "@/components/DownloadButton.vue";

const orderItems = ref<OrderList[]>([])
const balance = ref(0)
const authStore = useAuthStore();


const fetchWalletBalance = async () => {
  try {
    const {data} = await axios.get<{ balance: number }>('/api/v1/users/me/wallet')
    balance.value = data.balance
  } catch (e: any) {
    console.error('잔액 불러오기 실패:', e.response?.data?.message || e.message)
  }
}


const fetchOrderList = async () => {
  try {
    const {data} = await axios.get<OrderList[]>("/api/v1/users/me/orders")
    orderItems.value = data
  } catch (e: any) {
    console.error('주문목록 불러오기 실패:', e.response?.data?.message || e.message)
    orderItems.value = []
  }
}

onMounted(() => {
  fetchWalletBalance()
  fetchOrderList()
})

</script>

<template>
  <v-container class="py-6">
    <!-- 프로필 카드 -->
    <v-card class="d-flex align-center pa-6 mb-8" rounded="xl" elevation="2">
      <v-avatar size="80" class="mr-6" color="grey-lighten-3">
        <v-icon size="40" color="grey-darken-1">mdi-account</v-icon>
      </v-avatar>
      <div>
        <div class="text-h6 font-weight-bold">
          {{ authStore.user?.nickname }} 님 안녕하세요.
        </div>
        <div class="text-body-2 text-grey">
          잔액: {{ balance }}원
        </div>
      </div>
    </v-card>

    <h2 class="text-h5 font-weight-bold mb-4">주문 조회</h2>

    <div v-if="orderItems.length === 0" class="text-grey">
      주문 내역이 없습니다.
    </div>

    <router-link
      v-for="order in orderItems"
      :key="order.id"
      :to="`/products/${order.productId}`"
      class="text-decoration-none text-black"
    >
      <v-card
        class="d-flex align-center pa-4 mb-4"
        outlined
        elevation="1"
      >
        <!-- 썸네일 -->
        <v-img
          v-if="order.thumbnailUrl"
          :src="order.thumbnailUrl"
          alt="상품 이미지"
          max-width="64"
          max-height="64"
          class="mr-4"
          contain
        />

        <!-- 주문 정보 -->
        <div class="flex-grow-1">
          <div class="text-caption text-grey">주문번호 {{ order.orderId }}</div>
          <div class="font-weight-medium">{{ order.productName }}</div>
          <div class="text-body-2">{{ order.amount.toLocaleString() }}원</div>
          <div class="text-body-2 text-success font-weight-medium mt-1">
            구매확정
          </div>
        </div>

        <!-- 버튼 영역 -->
        <div class="d-flex flex-column gap-2">
          <DownloadButton :file-url="order.fileUrl"
                          class="mb-2"
                          color="#3478ff"
                          size="small">다운로드
          </DownloadButton>
          <v-btn variant="outlined" size="small">구매평 작성</v-btn>
        </div>
      </v-card>
    </router-link>
  </v-container>
</template>

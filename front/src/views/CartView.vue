<script setup lang="ts">
import {computed, onMounted, ref} from 'vue'
import { useAuthStore } from '@/stores/auth'
import axios from "@/plugins/axios.ts";
import type {CartItem} from "@/types/cart.ts";
import PaymentButton from "@/components/PaymentButton.vue";


interface CartItemWithSelected extends CartItem {
  selected?: boolean
}

const cartItems = ref<CartItemWithSelected[]>([])
const authStore = useAuthStore()
const buyerId = Number(authStore.user?.id)

const allSelected = computed({
  get: () => cartItems.value.length > 0 && cartItems.value.every(cartItem => cartItem.selected),
  set: (val: boolean) => {
    cartItems.value.forEach(cartItem => (cartItem.selected = val))
  }
})
const selectedCount = computed(() => cartItems.value.filter(cartItem => cartItem.selected).length)

const fetchCartItems = async () => {
  try {
    const {data} = await axios.get<CartItem[]>('/api/v1/cart/items')
    cartItems.value = data.map(i => ({...i, selected: false}))
  } catch (e: any) {
    console.error('장바구니 불러오기 실패:', e.response?.data?.message || e.message)
    cartItems.value = []
  }
}

const removeSelected = async () => {
  const selectedIds = cartItems.value.filter(cartItem => cartItem.selected).map(cartItem => cartItem.productId)
  if (selectedIds.length === 0) return

  try {
    await axios.post('/api/v1/cart/items/delete', {productIds: selectedIds})
    cartItems.value = cartItems.value.filter(i => !i.selected)
  } catch (e: any) {
    console.error('선택삭제 실패:', e.response?.data?.message || e.message)
  }
}

const selectedTotalAmount = computed(() =>
  cartItems.value
    .filter(item => item.selected) // 선택된 것만
    .reduce((sum, item) => sum + item.price, 0)
)

onMounted(() => {
  fetchCartItems()
})
</script>


<template>
  <v-container>
    <v-row>
      <v-col cols="12" md="8">
        <h2 class="text-h5 mb-4">장바구니</h2>

        <!-- 상단 전체선택 / 선택삭제 -->
        <div class="d-flex align-center mb-2">
          <v-checkbox v-model="allSelected" hide-details density="compact"/>
          <span class="ml-2">전체선택 {{ selectedCount }} / {{ cartItems.length }}</span>
          <v-spacer></v-spacer>
          <v-btn size="small" variant="outlined" @click="removeSelected">선택삭제</v-btn>
        </div>

        <!-- 테이블 -->
        <v-row v-for="item in cartItems" :key="item.cartId" class="py-4 align-center">
          <!-- 체크박스 -->
          <v-col cols="auto">
            <v-checkbox v-model="item.selected" hide-details density="compact" />
          </v-col>

          <!-- 썸네일 -->
          <v-col cols="auto">
            <v-img
              :src="item.thumbnailUrl"
              width="140"
              height="140"
              contain
              class="rounded"
              style="background-color: #f5f5f5;"
            />
          </v-col>

          <!-- 상품 정보 -->
          <v-col>
            <router-link :to="`/products/${item.productId}`" class="text-decoration-none">
              <div class="text-h6 font-weight-bold text-black hover-underline">
                {{ item.productName }}
              </div>
            </router-link>
          </v-col>

          <!-- 가격 -->
          <v-col cols="auto" class="text-center">
            <div class="text-h6 font-weight-bold">
              {{ item.price.toLocaleString() }} 원
            </div>
          </v-col>
        </v-row>

        <v-divider class="my-4" />
      </v-col>

      <!-- 주문 요약 -->
      <v-col cols="12" md="4">
        <v-sheet class="pa-4" elevation="1">
          <div class="d-flex justify-space-between mb-2">
            <span>총 상품 가격</span>
            <span>{{ selectedTotalAmount.toLocaleString() }} 원</span>
          </div>
          <div class="d-flex justify-space-between mb-2">
            <span class="text-red">할인 금액</span>
            <span class="text-red">- 0 원</span>
          </div>
          <v-divider class="my-4"></v-divider>
          <div class="d-flex justify-space-between text-h6 mb-4">
            <span>총 결제금액</span>
            <span>{{ selectedTotalAmount.toLocaleString() }} 원</span>
          </div>
          <PaymentButton :buyer-id="buyerId"
                         :product-ids="cartItems.filter(cartItem => cartItem.selected).map(cartItem => cartItem.productId)"
                         block color="#3478ff">
            구매하기
          </PaymentButton>
        </v-sheet>
      </v-col>
    </v-row>
  </v-container>
</template>

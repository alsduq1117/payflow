<script setup lang="ts">
import axios from "@/plugins/axios.ts";

const props = defineProps<{
  productId: number
}>()


const addToCart = async () => {
  try {
    await axios.post(`/api/v1/carts/cart-item/${props.productId}`)
    alert('장바구니 추가 완료')
  } catch (e: any) {
    const message = e.response?.data?.message || e.message || '알 수 없는 오류'
    alert(`${message}`)
  }
}
</script>

<template>
  <v-btn @click="addToCart">
    장바구니 담기
  </v-btn>
</template>

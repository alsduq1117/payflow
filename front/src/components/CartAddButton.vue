<script setup lang="ts">
import axios from "@/plugins/axios.ts";

const props = defineProps<{
  productId: number
}>()


const addToCart = async () => {
  try {
    await axios.post(`/api/v1/cart/items`, {
      productId: props.productId
    })
    alert('장바구니 추가 완료')
  } catch (e: any) {
    alert(e.response?.data?.message || e.message)
  }
}
</script>

<template>
  <v-btn @click="addToCart">
    장바구니 담기
  </v-btn>
</template>

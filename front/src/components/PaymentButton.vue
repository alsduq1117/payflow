<script setup lang="ts">
import axios from "@/plugins/axios.ts";

const props = defineProps<{
  buyerId: number
  productIds: number[]
}>()


const openCheckout = (orderId: number, amount: number, orderName: string) => {
  window.open(
    `/checkout.html?orderId=${orderId}&amount=${amount}&orderName=${encodeURIComponent(orderName)}`,
    '_blank',
    'width=500,height=700'
  )
}

const generateSeed = () => {
  const timestamp = Math.floor(Date.now() / 10000);
  return `${timestamp}`;
};

const handleCheckout = async () => {
  const seed = generateSeed()
  if (!props.buyerId) {
    alert('로그인이 필요합니다. 먼저 로그인해 주세요.')
    return
  }
  try {
    const { data } = await axios.post('/api/v1/checkout', {
      buyerId: props.buyerId,
      productIds: props.productIds,
      seed,
    })

    const { orderId, amount, orderName } = data
    openCheckout(orderId, amount, orderName)
  } catch (e: any) {
    alert(e.message || '결제 처리 중 오류가 발생했습니다.')
  }
}
</script>

<template>
  <v-btn @click="handleCheckout">
    결제하기
  </v-btn>
</template>




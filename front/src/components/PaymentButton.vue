<script setup lang="ts">

const props = defineProps<{
  buyerId: number
  productIds: number[]
}>()

const openCheckout = (orderId, amount, orderName) => {
  window.open(`/checkout.html?orderId=${orderId}&amount=${amount}&orderName=${orderName}`, '_blank', 'width=500,height=700')
};

const generateSeed = (buyerId: number, productIds: number[]) => {
  const sorted = [...productIds].sort((a, b) => a - b).join(',')
  return `${buyerId}|${sorted}`
};

const seed = generateSeed(2, [3, 1, 2]);

const handleCheckout = async () => {
  try {
    const response = await fetch('/api/v1/checkout', {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify({
        buyerId: props.buyerId,
        productIds: props.productIds,
        seed: seed,
      }),
    })

    if (response.status === 401 || response.status === 403) {
      alert('로그인이 필요합니다. 먼저 로그인해 주세요.')
      return
    }

    if (!response.ok) {
      const err = await response.json().catch(() => ({}))
      throw new Error(err?.message || '결제 준비 실패')
    }

    const {orderId, amount, orderName} = await response.json()
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




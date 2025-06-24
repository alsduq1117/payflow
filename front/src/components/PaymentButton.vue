<script setup>
const openCheckout = (orderId, amount, orderName) => {
  const params = new URLSearchParams({
    orderId,
    amount: amount.toString(),
    orderName
  }).toString();

  window.open(`/checkout.html?${params}`, '_blank', 'width=500, height=700')
};

</script>

<template>
  <button @click="() => openCheckout('ORD123', 10000, '테스트 상품')">
    결제하기
  </button>
</template>

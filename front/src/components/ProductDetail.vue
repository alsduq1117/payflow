<script setup lang="ts">
import { onMounted, ref } from 'vue'
import axios from 'axios'
import PaymentButton from '@/components/PaymentButton.vue'

const props = defineProps<{ productId: string }>()

const product = ref<null | {
  id: number,
  name: string,
  price: number,
  fileUrl: string,
  thumbnailUrl: string,
  description: string,
  sellerId: number
}>(null)

onMounted(async () => {
  try {
    const productId = Number(props.productId)
    const res = await axios.get(`/api/v1/products/${productId}`)
    product.value = res.data
  } catch (e) {
    console.error('상품 정보를 불러오지 못했습니다', e)
  }
})
</script>

<template>
  <v-container class="py-12" v-if="product">
    <v-row>
      <v-col cols="12" md="6">
        <v-img :src="product.thumbnailUrl" height="400" class="rounded-lg" cover />
      </v-col>

      <v-col cols="12" md="6" class="d-flex flex-column justify-center">
        <h2 class="text-h4 font-weight-bold mb-2">{{ product.name }}</h2>
        <p class="text-subtitle-1 text-grey-darken-1 mb-4">{{ product.description }}</p>
        <h3 class="text-h5 font-weight-bold mb-6">{{ product.price.toLocaleString() }}원</h3>
        <PaymentButton :buyerId="2" :productIds="[product.id]" :seed="`${2}|${product.id}`"
        />
      </v-col>
    </v-row>

    <v-row class="mt-10">
      <v-col cols="12">
        <div class="text-caption text-grey-darken-1">
          <p>- 디지털 콘텐츠 특성상 구매 후 환불이 불가능합니다. 신중한 결제를 부탁드립니다.</p>
          <p>- 모든 콘텐츠는 저작권 보호를 받으며 무단 배포 및 재판매가 금지되어 있습니다.</p>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<style scoped>
.rounded-lg {
  border-radius: 12px;
}
</style>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import axios from 'axios'
import PaymentButton from '@/components/PaymentButton.vue'

const props = defineProps<{ productId: string }>()

const tab = ref('details')
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
  <v-container class="py-10" v-if="product">
    <v-row>
      <v-col cols="12" md="6">
        <v-img :src="product.thumbnailUrl" height="400" class="rounded-lg" />
      </v-col>

      <v-col cols="12" md="6" class="ps-md-15">
        <h1 class="text-h4 font-weight-bold mb-2">{{ product.name }}</h1>
        <p class="text-body-1 text-grey-darken-1 mb-10">키르미뇨프</p>
        <h2 class="text-h5 font-weight-bold mb-5">{{ product.price.toLocaleString() }}원</h2>


        <v-row class="mt-6" align="center" justify="start">
          <!-- 장바구니 버튼 -->
          <v-btn
            class="text-subtitle-1 font-weight-medium me-3"
            style="height: 48px; min-width: 160px; border-radius: 6px; border: 1.5px solid #3478ff; color: #3478ff; background-color: #fff;"
            variant="outlined"
            elevation="0"
          >
            장바구니 담기
          </v-btn>

          <!-- 바로 구매 버튼 -->
          <PaymentButton
            :buyer-id="1"
            :product-ids="[product.id]"
            class="text-subtitle-1 font-weight-bold d-flex align-center justify-center"
            style="height: 48px; min-width: 160px; border-radius: 6px; background-color: #3478ff; color: #fff;"
          >
            바로구매
            <v-icon size="18" class="ms-2">mdi-chevron-right</v-icon>
          </PaymentButton>
        </v-row>
        <p class="text-caption text-grey mt-4">
          ※ 디지털 콘텐츠 특성상 환불이 불가합니다. 신중한 결제 부탁드립니다.
        </p>
      </v-col>
    </v-row>


    <div class="mt-12 pt-6">
      <v-tabs v-model="tab" grow background-color="white" color="primary" class="text-subtitle-1" slider-color="primary">
        <v-tab value="details" class="text-subtitle-1">상품상세</v-tab>
        <v-tab value="reviews" class="text-subtitle-1">상품평</v-tab>
        <v-tab value="questions" class="text-subtitle-1">상품문의</v-tab>
      </v-tabs>
    </div>

    <v-window v-model="tab">
      <v-window-item value="details">
        <div class="pa-4">
          <h3 class="text-h6 font-weight-medium mb-2"></h3>
          <p>{{ product.description }}</p>
        </div>
      </v-window-item>

      <v-window-item value="reviews">
        <div class="pa-4">
          <h3 class="text-h6 font-weight-medium">아직 등록된 상품평이 없습니다.</h3>
          <p class="text-body-2 text-grey-darken-1">구매 후 이용하신 후기를 남겨주세요.</p>
        </div>
      </v-window-item>

      <v-window-item value="questions">
        <div class="pa-4">
          <h3 class="text-h6 font-weight-medium">아직 등록된 상품문의가 없습니다.</h3>
          <p class="text-body-2 text-grey-darken-1">상품에 대해 궁금한 점이 있다면 문의해주세요.</p>
        </div>
      </v-window-item>
    </v-window>
  </v-container>
</template>

<style scoped>
.rounded-lg {
  border-radius: 12px;
}
</style>

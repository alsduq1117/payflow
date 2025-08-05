<script setup lang="ts">
import { onMounted, ref } from 'vue'
import axios from 'axios'
import PaymentButton from '@/components/PaymentButton.vue'
import { useAuthStore } from '@/stores/auth'
import type {Product} from "@/types";
import DownloadButton from "@/components/DownloadButton.vue";

const props = defineProps<{ productId: string }>()
const product = ref<Product | null>(null)
const tab = ref('details')
const authStore = useAuthStore()
const buyerId = Number(authStore.user?.id)
const hasPurchased = ref<boolean>(false)

onMounted(async () => {
  try {
    const productId = Number(props.productId)

    const productRes = await axios.get(`/api/v1/products/${productId}`)
    product.value = productRes.data

    const purchasedRes = await axios.get(`/api/v1/purchases/check?productId=${productId}`)
    hasPurchased.value = purchasedRes.data

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
        <h1 class="text-h4 font-weight-bold mb-4">{{ product.name }}</h1>
        <p class="text-body-1 text-grey-darken-1 mb-16">{{ product.sellerNickname }}</p>
        <h2 class="text-h5 font-weight-bold mb-5">{{ product.price.toLocaleString() }}원</h2>


        <v-row class="mt-10" align="center" justify="start">
          <template v-if="hasPurchased === false">
            <v-btn
              class="text-subtitle-1 font-weight-medium me-3"
              style="height: 48px; min-width: 200px; border-radius: 6px; border: 1.5px solid #3478ff; color: #3478ff; background-color: #fff;"
              variant="outlined"
              elevation="0"
            >
              장바구니 담기
            </v-btn>

            <PaymentButton
              :buyer-id="buyerId"
              :product-ids="[product.id]"
              class="text-subtitle-1 font-weight-bold d-flex align-center justify-center"
              style="height: 48px; min-width: 200px; border-radius: 6px; background-color: #3478ff; color: #fff;"
            >
              바로구매
              <v-icon size="18" class="ms-2">mdi-chevron-right</v-icon>
            </PaymentButton>
          </template>

          <!-- 결제 완료한 경우: 다운로드 버튼만 노출 -->
          <template v-else-if="hasPurchased === true">
            <DownloadButton
              :file-url="product.fileUrl"
            />
          </template>
        </v-row>
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

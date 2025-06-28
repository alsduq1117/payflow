<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from 'axios'

interface Product {
  id: number
  name: string
  price: number
  thumbnailUrl: string
}

const products = ref<Product[]>([])

const fetchProducts = async () => {
  try {
    const res = await axios.get('/api/v1/products', {
      params: {
        page: 1,
        size: 12
      }
    })
    products.value = res.data.items
  } catch (error) {
    console.error('❌ 상품 목록 불러오기 실패:', error)
  }
}
onMounted(fetchProducts)
</script>

<template>
  <v-container>
    <v-row class="ma-10">
      <v-col
        v-for="product in products"
        :key="product.id"
        cols="12"
        sm="6"
        md="3"
      >
        <v-card class="product-card pa-5">
          <!-- 이미지: 서버의 thumbnailUrl 사용 -->
          <v-img
            :src="product.thumbnailUrl"
            height="250"
            cover
            class="mb-2"
          ></v-img>

          <!-- 상품명 -->
          <div class="text-body-1 font-weight-medium mb-4">
            {{ product.name }}
          </div>

          <!-- 가격 -->
          <div class="text-subtitle-1 font-weight-bold" style="color: black">
            {{ product.price.toLocaleString() }}원
          </div>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<style>
.product-card {
  outline: none;
  transition: outline 0.1s ease;
}

.product-card:hover {
  outline: 3px solid #1E2D60; /* outline은 레이아웃에 영향 없음 */
}
</style>

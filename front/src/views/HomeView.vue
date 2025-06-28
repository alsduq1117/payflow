<script setup lang="ts">
import {ref} from 'vue'

const products = ref([
  {
    id: 1,
    title: '프론트엔드 7일 완성 PDF',
    price: 4900,
    imageUrl: '/images/placeholder.png'
  },
  {
    id: 2,
    title: '스프링 시큐리티 정리 ZIP',
    price: 6900,
    imageUrl: '/images/placeholder.png'
  },
  {
    id: 3,
    title: 'JPA 핵심 요약 PDF',
    price: 5900,
    imageUrl: '/images/placeholder.png'
  },
  {
    id: 4,
    title: '쿠폰 이벤트 배너 PSD',
    price: 2900,
    imageUrl: '/images/placeholder.png'
  },
  {
    id: 5,
    title: '디자인 시스템 가이드',
    price: 7500,
    imageUrl: '/images/placeholder.png'
  },
  {
    id: 6,
    title: 'REST API 예제 모음',
    price: 3900,
    imageUrl: '/images/placeholder.png'
  },
  {
    id: 7,
    title: 'CI/CD 템플릿 ZIP',
    price: 4900,
    imageUrl: '/images/placeholder.png'
  },
  {
    id: 8,
    title: '핀테크 기획안 PPT',
    price: 8700,
    imageUrl: '/images/placeholder.png'
  },
])
</script>

<template>
    <v-container>
      <v-row class="ma-10">
        <v-col v-for="product in products" :key="product.id" cols="12" sm="6" md="3">
          <v-card class="product-card pa-5">
            <v-img :src="product.imageUrl" height="250" cover class="mb-2"></v-img>

            <div class="text-body-1 font-weight-medium mb-4">
              {{ product.title }}
            </div>

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

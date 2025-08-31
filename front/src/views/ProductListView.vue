<script setup lang="ts">
import { ref, onMounted, onUnmounted, computed } from 'vue'
import axios from '@/plugins/axios'
import type { Product } from '@/types/product'

const products = ref<Product[]>([])
const page = ref(1)
const size = 12
const totalItems = ref(0)
const totalPages = computed(() => Math.ceil(totalItems.value / size))
const isLoading = ref(false)

const fetchProducts = async () => {
  if (isLoading.value || (totalPages.value && page.value > totalPages.value)) return;
  isLoading.value = true;
  try {
    const res = await axios.get('/api/v1/products', {
      params: { page: page.value, size }
    });

    products.value =
      page.value === 1
        ? res.data.items
        : [...products.value, ...res.data.items];

    totalItems.value = res.data.totalCount;
    page.value += 1
  } catch (e) {
    console.error('상품 목록 불러오기 실패:', e);
  } finally {
    isLoading.value = false;
  }
}

const handleScroll = () => {
  const nearBottom = window.innerHeight + window.scrollY >= document.body.offsetHeight - 200;
  if (nearBottom) fetchProducts()
};

// 이벤트 리스너 등록
onMounted(() => {
  fetchProducts();
  window.addEventListener('scroll', handleScroll);
});

onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll);
});
</script>


<template>
  <v-container>
    <v-row class="ma-10">
      <v-col v-for="product in products" :key="product.id" cols="12" sm="6" md="3">
        <v-card class="product-card pa-5" :to="`/products/${product.id}`" tag="router-link" hover>
          <v-img :src="product.thumbnailUrl" height="250" cover class="mb-2" />
          <div style="display: flex; justify-content: space-between;" class="text-body-1 font-weight-medium mb-4">
            <span>{{ product.name }}</span>
            <span style="font-size: 0.9em; color: #888;">{{ product.sellerNickname }}</span>
          </div>

          <div class="text-subtitle-1 font-weight-bold text-black">
            {{ product.price.toLocaleString() }}원
          </div>
        </v-card>
      </v-col>
    </v-row>

    <!-- 로딩 표시 -->
    <v-row justify="center" v-if="isLoading" class="py-4">
      <v-progress-circular indeterminate color="#1E2D60" />
    </v-row>

  </v-container>
</template>

<style scoped>
.product-card {
  outline: none;
  transition: outline 0.1s ease;
}

.product-card:hover {
  outline: 3px solid #1E2D60;
}
</style>

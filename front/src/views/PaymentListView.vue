<script setup lang="ts">
import { ref, reactive, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from '@/plugins/axios.ts'
import type { OrderList } from "@/types/orderList.ts";

type SortDirection = 'asc' | 'desc'

type Paged<T> = {
  page: number
  size: number
  totalCount: number
  items: T[]
}

const route = useRoute()
const router = useRouter()

// 포맷터
const nf  = new Intl.NumberFormat('ko-KR')
const won = (v: number, ccy='₩') => `${ccy}${nf.format(v)}`
const dt  = (iso?: string | null) => (iso ? new Date(iso).toLocaleString() : '-')

// 상태
const filters = reactive({
  buyerNickname: (route.query.buyerNickname as string) ?? '',
  status: (route.query.status) ?? '' as '',
  method: (route.query.method as string) ?? '',
  minAmount: route.query.minAmount ? Number(route.query.minAmount) : undefined,
  maxAmount: route.query.maxAmount ? Number(route.query.maxAmount) : undefined,
})

const tableOptions = reactive({
  page: Number(route.query.page ?? 1),
  itemsPerPage: Number(route.query.size ?? 20),
  sortBy: (() => {
    const s = (route.query.sort as string) || 'createdAt,desc'
    const [key, order] = s.split(',')
    return [{ key, order: (order as SortDirection) || 'desc' }]
  })(),
})

const loading = ref(false)
const errorMsg = ref<string | null>(null)
const rows = ref<OrderList[]>([])
const total = ref(0)

// 쿼리 빌더 + URL 동기화
function buildQuery() {
  const sort = tableOptions.sortBy?.[0]
    ? `${tableOptions.sortBy[0].key},${tableOptions.sortBy[0].order}`
    : undefined

  return {
    buyerNickname: filters.buyerNickname || undefined,
    status: filters.status || undefined,
    method: filters.method || undefined,
    minAmount: filters.minAmount,
    maxAmount: filters.maxAmount,
    page: tableOptions.page,
    size: tableOptions.itemsPerPage,
    sort,
  }
}

async function fetchOrders() {
  try {
    loading.value = true
    errorMsg.value = null

    const query = buildQuery()
    router.replace({ query }) // URL 동기화

    const { data } = await axios.get<Paged<OrderList>>('/api/v1/admin/orders', { params: query })
    rows.value = data.items
    total.value = data.totalCount
  } catch (e: any) {
    errorMsg.value = e?.message ?? '목록을 불러오지 못했습니다.'
  } finally {
    loading.value = false
  }
}

// 테이블 페이지/정렬 변경 시 자동 조회
watch(() => ({ ...tableOptions }), () => fetchOrders(), { deep: true })

onMounted(() => { fetchOrders() })

const statusItems = [
  { label: '전체',     value: '' },
  { label: '미시작',   value: 'NOT_STARTED' },
  { label: '처리중',   value: 'EXECUTING' },
  { label: '성공',     value: 'SUCCESS' },
  { label: '실패',     value: 'FAILURE' },
  { label: '미상',     value: 'UNKNOWN' },
]
const methodItems = [
  { title: '전체', value: '' },
  { title: '간편결제', value: 'EASY_PAY'},
  { title: '카드', value: 'CARD' },
  { title: '가상계좌', value: 'VIRTUAL_ACCOUNT' },
  { title: '계좌이체', value: 'TRANSFER' },
  { title: '휴대폰', value: 'MOBILE' },
]

const headers = [
  { title: '주문번호', key: 'id', sortable: true},
  { title: '구매자',   key: 'buyerNickname', sortable: true },
  { title: '결제수단', key: 'method',        sortable: true },
  { title: '금액',     key: 'amount',        sortable: true },
  { title: '상태',     key: 'status',        sortable: true },
  { title: '승인시각', key: 'approvedAt',    sortable: true },
  { title: '생성시각', key: 'createdAt',     sortable: true },
  { title: '',         key: 'actions',       sortable: false, width: 64 },
]

function statusColor(s: string) {
  switch (s) {
    case '성공':   return 'success'
    case '실패':   return 'error'
    case '처리중': return 'info'
    case '미시작': return 'grey-darken-1'
    case '미상':   return 'warning'
    default:       return 'grey'
  }
}
</script>

<template>
  <v-container fluid class="pa-0">
    <v-alert v-if="errorMsg" type="error" variant="tonal" class="ma-4">
      {{ errorMsg }}
    </v-alert>

    <!-- 필터 바 -->
    <v-card class="ma-4" elevation="0" variant="outlined">
      <v-card-text>
        <v-row class="align-end" dense>
          <v-col cols="12" md="3">
            <v-text-field
              v-model="filters.buyerNickname"
              label="검색(구매자 닉네임)"
              density="comfortable"
              clearable
              placeholder="닉네임"
              prepend-inner-icon="mdi-magnify"
              @keydown.enter="fetchOrders"
            />
          </v-col>

          <v-col cols="6" md="2">
            <v-select
              v-model="filters.status"
              :items="statusItems"
              item-title="label"
              item-value="value"
              label="상태"
              density="comfortable"
              clearable
            />
          </v-col>

          <v-col cols="6" md="2">
            <v-select
              v-model="filters.method"
              :items="methodItems"
              label="결제수단"
              density="comfortable"
              clearable
            />
          </v-col>

          <v-col cols="6" md="2">
            <v-text-field
              v-model.number="filters.minAmount"
              label="최소금액"
              type="number"
              density="comfortable"
              clearable
              prefix="₩"
            />
          </v-col>

          <v-col cols="6" md="2">
            <v-text-field
              v-model.number="filters.maxAmount"
              label="최대금액"
              type="number"
              density="comfortable"
              clearable
              prefix="₩"
            />
          </v-col>

          <v-col cols="12" md="3" class="d-flex gap-2">
            <v-btn variant="tonal" @click="fetchOrders" :loading="loading">검색</v-btn>
            <v-btn
              variant="text"
              @click="Object.assign(filters, { q:'', status:'', method:'', minAmount:undefined, maxAmount:undefined })"
            >
              초기화
            </v-btn>
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>

    <!-- 데이터 테이블 -->
    <v-card class="ma-4" elevation="0" variant="outlined">
      <v-data-table-server
        :items="rows"
        :items-length="total"
        :loading="loading"
        v-model:page="tableOptions.page"
        v-model:items-per-page="tableOptions.itemsPerPage"
        :items-per-page-options="[20, 50, 100]"
        v-model:sort-by="tableOptions.sortBy"
        :headers="headers"
        item-key="id"
        class="order-table"
        hover
      >
        <!-- 구매자 닉네임 -->
        <template #item.buyerNickname="{ item }">
          {{ item.buyerNickname ?? '-' }}
        </template>

        <!-- 금액 -->
        <template #item.amount="{ item }">
          {{ won(item.amount) }}
        </template>

        <!-- 상태 + 실패횟수 배지 -->
        <template #item.status="{ item }">
          <div class="d-flex align-center gap-2">
            {{ console.log('status:', item.status) }}
            <v-chip size="small" :color="statusColor(item.status)" variant="flat">
              {{ item.status }}
            </v-chip>
          </div>
        </template>

        <template #item.approvedAt="{ item }">
          {{ dt(item.approvedAt) }}
        </template>

        <template #item.createdAt="{ item }">
          {{ dt(item.createdAt) }}
        </template>

        <template #no-data>
          <div class="pa-8 text-medium-emphasis">데이터가 없습니다.</div>
        </template>
      </v-data-table-server>
    </v-card>
  </v-container>
</template>

<style scoped>
.order-table :deep(thead th) { white-space: nowrap; }
.gap-2 { gap: 8px; }
</style>


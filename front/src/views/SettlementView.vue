<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from '@/plugins/axios.ts'

type Preset = 'today'|'yesterday'|'thisWeek'|'thisMonth'|'custom'

type Overview = {
  gross: number
  net: number
  orderCount: number
  pending: number
}

type SellerSummary = {
  sellerId: number
  nickname?: string | null
  orderCount: number
  gross: number
  fee?: number
  net: number
  lastPayoutAt?: string | null
}

/* Router */
const route = useRoute()
const router = useRouter()

/* Formatter */
const nf  = new Intl.NumberFormat('ko-KR')
const won = (v:number, ccy='₩') => `${ccy}${nf.format(v)}`

/* State */
const loadingOverview = ref(false)
const overview = ref<Overview>({ gross:0, net:0, orderCount:0, pending:0 })

const filters = reactive({
  preset: (route.query.preset as Preset) ?? ('today' as Preset),
  from: (route.query.from as string) ?? '',
  to: (route.query.to as string) ?? '',
})

const snackbar = reactive({ show:false, text:'', color:'error' as 'error'|'success'|'info' })
const errorMsg = ref<string | null>(null)

/* Date utils */
function ymd(d: Date) {
  const y = d.getFullYear(), m = `${d.getMonth()+1}`.padStart(2,'0'), day = `${d.getDate()}`.padStart(2,'0')
  return `${y}-${m}-${day}`
}
function startOfWeek(d=new Date()){const day=d.getDay()||7, x=new Date(d); x.setDate(d.getDate()-(day-1)); x.setHours(0,0,0,0); return x}
function endOfWeek(d=new Date()){const x=new Date(startOfWeek(d)); x.setDate(x.getDate()+6); x.setHours(23,59,59,999); return x}
function startOfMonth(d=new Date()){return new Date(d.getFullYear(), d.getMonth(), 1)}
function endOfMonth(d=new Date()){return new Date(d.getFullYear(), d.getMonth()+1, 0, 23,59,59,999)}

function setDatesByPreset(p: Preset) {
  const now = new Date()
  if (p==='today'){filters.from=ymd(now); filters.to=ymd(now); return}
  if (p==='yesterday'){const y=new Date(now); y.setDate(now.getDate()-1); filters.from=ymd(y); filters.to=ymd(y); return}
  if (p==='thisWeek'){filters.from=ymd(startOfWeek(now)); filters.to=ymd(endOfWeek(now)); return}
  if (p==='thisMonth'){filters.from=ymd(startOfMonth(now)); filters.to=ymd(endOfMonth(now)); return}
}

/* Query builders */
function buildOverviewQuery() {
  return {
    preset: filters.preset || undefined,
    from: filters.from || undefined,
    to: filters.to || undefined,
  }
}

function toSortParam() {
  const s = tableOptions.sortBy?.[0]
  return s ? `${s.key},${s.order}` : undefined
}

function buildSellerQuery() {
  return {
    ...buildOverviewQuery(),
    nickname: sellerSearch.value || undefined,
    page: tableOptions.page,
    size: tableOptions.itemsPerPage,
    sort: toSortParam(),
  }
}

/* Fetchers */
async function fetchOverview() {
  try {
    loadingOverview.value = true
    errorMsg.value = null
    const { data } = await axios.get('/api/v1/admin/settlements/overview', { params: buildOverviewQuery() })
    const raw = data ?? data?.overview ?? {}
    overview.value = {
      gross:   Number(raw.gross ?? raw?.gross ?? 0),
      net:     Number(raw.net ?? raw?.net ?? 0),
      orderCount: Number(raw.orderCount ?? raw?.orderCount ?? 0),
      pending: Number(raw.pending ?? raw?.pending ?? 0),
    }
  } catch (e:any) {
    errorMsg.value = e?.message ?? '개요 데이터를 불러오지 못했습니다.'
  } finally {
    loadingOverview.value = false
  }
}

async function reloadSellers() {
  try {
    loadingSellers.value = true
    errorMsg.value = null

    const query = buildSellerQuery()
    router.replace({ query }) // URL 동기화

    const { data } = await axios.get('/api/v1/admin/settlements', { params: query })

    sellerRows.value  = Array.isArray(data?.content) ? data.content : []
    sellerTotal.value = Number(data?.totalElements ?? sellerRows.value.length)

    if (typeof data?.number === 'number') {
      tableOptions.page = data.number + 1
    }
    if (typeof data?.size === 'number') {
      tableOptions.itemsPerPage = data.size
    }
  } catch (e: any) {
    errorMsg.value = e?.message ?? '판매자별 데이터를 불러오지 못했습니다.'
  } finally {
    loadingSellers.value = false
  }
}

/* Preset & Date apply */
function applyPreset(p: Preset) {
  filters.preset = p
  if (p !== 'custom') setDatesByPreset(p)
  // 테이블 옵션은 유지, URL도 같이 갱신
  router.replace({ query: { ...buildSellerQuery() } })
  fetchOverview()
  reloadSellers()
}

function applyDateRange() {
  // 날짜 직접 입력 후 적용 버튼 눌렀을 때
  filters.preset = 'custom'
  router.replace({ query: { ...buildSellerQuery() } })
  fetchOverview()
  reloadSellers()
}

/* CSV */
function exportCsv() {
  const params = new URLSearchParams(buildOverviewQuery() as Record<string, string>)
  window.open(`/api/v1/admin/settlements/export?${params.toString()}`, '_blank')
}

/* Seller table */
const sellerSearch = ref<string>((route.query.nickname as string) ?? '')
const loadingSellers = ref(false)
const sellerRows = ref<SellerSummary[]>([])
const sellerTotal = ref(0)
const tableOptions = reactive({
  page: Number(route.query.page ?? 1),
  itemsPerPage: Number(route.query.size ?? 20),
  sortBy: (() => {
    const s = (route.query.sort as string) || 'net,desc'
    const [key, order] = s.split(',')
    return [{ key, order: (order as 'asc'|'desc') || 'desc' }]
  })(),
})

const sellerHeaders = [
  { title: '판매자ID', key: 'sellerId', sortable: true },
  { title: '닉네임', key: 'nickname', sortable: true },
  { title: '결제 건수', key: 'orderCount', sortable: true },
  { title: '총매출', key: 'gross', sortable: true },
  { title: '수수료', key: 'fee', sortable: true },
  { title: '순정산', key: 'net', sortable: true },
  { title: '최근지급일', key: 'lastPayoutAt', sortable: true },
]

/* Table option change → 서버 조회 */
import { watch } from 'vue'
watch(() => ({ ...tableOptions }), () => reloadSellers(), { deep: true })

/* Mount */
onMounted(() => {
  if (!filters.from && !filters.to) setDatesByPreset(filters.preset)
  fetchOverview()
  reloadSellers()
})
</script>

<template>
  <v-container fluid class="pa-4">
    <v-alert v-if="errorMsg" type="error" variant="tonal" class="mb-4">
      {{ errorMsg }}
    </v-alert>

    <!-- 기간 프리셋/날짜 + CSV -->
    <div class="d-flex flex-wrap align-center gap-2 mb-4">
      <v-chip :color="filters.preset==='today' ? 'primary' : undefined" variant="outlined" @click="applyPreset('today')">오늘</v-chip>
      <v-chip :color="filters.preset==='yesterday' ? 'primary' : undefined" variant="outlined" @click="applyPreset('yesterday')">어제</v-chip>
      <v-chip :color="filters.preset==='thisWeek' ? 'primary' : undefined" variant="outlined" @click="applyPreset('thisWeek')">이번 주</v-chip>
      <v-chip :color="filters.preset==='thisMonth' ? 'primary' : undefined" variant="outlined" @click="applyPreset('thisMonth')">이번 달</v-chip>

      <v-divider class="mx-2" vertical />

      <v-text-field v-model="filters.from" label="시작일(YYYY-MM-DD)" density="comfortable" style="max-width: 200px" clearable hide-details />
      <v-text-field v-model="filters.to"   label="종료일(YYYY-MM-DD)" density="comfortable" style="max-width: 200px" clearable hide-details />

      <v-btn class="ml-2" variant="tonal" @click="applyDateRange">적용</v-btn>

      <v-spacer />
      <v-btn variant="outlined" :disabled="loadingOverview" @click="exportCsv">CSV 내보내기</v-btn>
    </div>

    <!-- Overview 카드 -->
    <v-row dense class="mb-4">
      <v-col cols="12" md="3">
        <v-card elevation="0" variant="outlined" class="pa-4">
          <div class="text-caption">총매출</div>
          <div class="text-h5 font-weight-bold">{{ won(overview.gross) }}</div>
        </v-card>
      </v-col>
      <v-col cols="12" md="3">
        <v-card elevation="0" variant="outlined" class="pa-4">
          <div class="text-caption">순정산</div>
          <div class="text-h5 font-weight-bold">{{ won(overview.net) }}</div>
        </v-card>
      </v-col>
      <v-col cols="12" md="3">
        <v-card elevation="0" variant="outlined" class="pa-4">
          <div class="text-caption">결제 건수</div>
          <div class="text-h6 font-weight-bold">{{ nf.format(overview.orderCount) }}건</div>
        </v-card>
      </v-col>
      <v-col cols="12" md="3">
        <v-card elevation="0" variant="outlined" class="pa-4">
          <div class="text-caption">정산 대기건수</div>
          <div class="text-h6 font-weight-bold">{{ nf.format(overview.pending) }}건</div>
        </v-card>
      </v-col>
    </v-row>

    <!-- 판매자별 정산 테이블 -->
    <v-card elevation="0" variant="outlined" class="pa-0">
      <v-data-table-server
        :items="sellerRows"
        :items-length="sellerTotal"
        :loading="loadingSellers"
        v-model:page="tableOptions.page"
        v-model:items-per-page="tableOptions.itemsPerPage"
        :items-per-page-options="[20, 50, 100]"
        v-model:sort-by="tableOptions.sortBy"
        :headers="sellerHeaders"
        item-key="sellerId"
        hover
        density="comfortable"
        class="seller-table"
      >
        <template #top>
          <v-toolbar density="comfortable" color="transparent">
            <v-toolbar-title class="text-subtitle-1">판매자별 정산 개요</v-toolbar-title>
          </v-toolbar>
        </template>

        <template #item.gross="{ item }"><strong>{{ won(item.gross) }}</strong></template>
        <template #item.fee="{ item }">
          <span v-if="typeof item.fee === 'number'">{{ won(item.fee) }}</span>
          <span v-else class="text-medium-emphasis">-</span>
        </template>
        <template #item.net="{ item }"><strong>{{ won(item.net) }}</strong></template>
        <template #item.lastPayoutAt="{ item }">
          {{ item.lastPayoutAt ? new Date(item.lastPayoutAt).toLocaleString() : '-' }}
        </template>

        <template #no-data>
          <div class="pa-8 text-medium-emphasis">해당 기간에 데이터가 없습니다.</div>
        </template>
      </v-data-table-server>
    </v-card>
  </v-container>
</template>

<style scoped>
.seller-table :deep(thead th) { white-space: nowrap; }
</style>



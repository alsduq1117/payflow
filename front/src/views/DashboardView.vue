<script setup lang="ts">
import {ref, onMounted, onUnmounted, computed} from 'vue'
import axios from '@/plugins/axios'

type Metrics = {
  todayPayments: number
  approveRate1h: number
  settlement: { pending: number; failed: number }
  updatedAt?: string
}

const loading = ref(true)
const errorMsg = ref<string | null>(null)
const metrics = ref<Metrics | null>(null)

const REFRESH_MS = 0  // 일단 폴링은 쓰지 않는걸로
let pollTimer: number | null = null

const approveRatePercent = computed(() => {
  const r = metrics.value?.approveRate1h ?? 0
  return Math.round(r <= 1 ? r * 100 : r)
})

async function fetchMetrics() {
  if (loading.value) return
  try {
    errorMsg.value = null
    loading.value = true
    const {data} = await axios.get<Metrics>('/api/admin/metrics')
    metrics.value = data
  } catch (e: any) {
    errorMsg.value = e?.message ?? '지표를 불러오지 못했습니다.'
  } finally {
    loading.value = false
  }
}


function onVisibleOrFocus() {
  if (document.visibilityState === 'visible') {
    fetchMetrics()
  }
}

onMounted(async () => {
  loading.value = true
  try {
    const {data} = await axios.get<Metrics>('/api/admin/metrics')
    metrics.value = data
  } catch (e: any) {
    errorMsg.value = e?.message ?? '지표를 불러오지 못했습니다.'
  } finally {
    loading.value = false
  }

  document.addEventListener('visibilitychange', onVisibleOrFocus)
  window.addEventListener('focus', onVisibleOrFocus)
})

onUnmounted(() => {
  document.removeEventListener('visibilitychange', onVisibleOrFocus)
  window.removeEventListener('focus', onVisibleOrFocus)
})

</script>
<template>
  <v-container fluid class="pa-0">

    <!-- 에러 -->
    <v-alert v-if="errorMsg" type="error" variant="tonal" class="ma-4">
      {{ errorMsg }}
    </v-alert>

    <!-- 메트릭 카드들 -->
    <v-row class="pa-4" align="stretch">
      <!-- 오늘 결제건수 -->
      <v-col cols="12" md="4">
        <v-card class="metric-card">
          <v-card-text>
            <div class="card-head">
              <v-icon size="20" class="mr-2">mdi-cash-check</v-icon>
              <span class="card-title">오늘 결제건수</span>
            </div>

            <div class="metric-value">
              <template v-if="!loading && metrics">
                {{ metrics.todayPayments.toLocaleString() }}
              </template>
              <template v-else>
                <v-skeleton-loader type="text" :loading="true" class="metric-skeleton"/>
              </template>
            </div>

            <div class="metric-hint">금일 00:00 ~ 현재</div>
          </v-card-text>
        </v-card>
      </v-col>

      <!-- 최근 1시간 승인율 -->
      <v-col cols="12" md="4">
        <v-card class="metric-card">
          <v-card-text>
            <div class="card-head">
              <v-icon size="20" class="mr-2">mdi-check-decagram</v-icon>
              <span class="card-title">최근 1시간 승인율</span>
            </div>

            <div class="metric-value">
              <template v-if="!loading && metrics">
                {{ approveRatePercent }}%
              </template>
              <template v-else>
                <v-skeleton-loader type="text" :loading="true" class="metric-skeleton"/>
              </template>
            </div>

            <div class="mt-3">
              <v-progress-linear
                v-if="!loading && metrics"
                :model-value="approveRatePercent"
                height="8"
                rounded
              />
              <v-skeleton-loader
                v-else
                type="image"
                :loading="true"
                class="line-skeleton"
              />
            </div>

            <div class="metric-hint">최근 60분 승인 성공 비율</div>
          </v-card-text>
        </v-card>
      </v-col>

      <!-- 정산 대기 / 실패 -->
      <v-col cols="12" md="4">
        <v-card class="metric-card">
          <v-card-text>
            <div class="card-head">
              <v-icon size="20" class="mr-2">mdi-scale-balance</v-icon>
              <span class="card-title">정산 상태</span>
            </div>

            <div class="metric-row">
              <span class="label">대기</span>
              <span class="value">
                <template v-if="!loading && metrics">
                  {{ metrics.settlement.pending.toLocaleString() }}
                </template>
                <template v-else>
                  <v-skeleton-loader type="text" :loading="true" class="row-skeleton"/>
                </template>
              </span>
            </div>

            <div class="metric-row">
              <span class="label">실패</span>
              <span class="value failure">
                <template v-if="!loading && metrics">
                  {{ metrics.settlement.failed.toLocaleString() }}
                </template>
                <template v-else>
                  <v-skeleton-loader type="text" :loading="true" class="row-skeleton"/>
                </template>
              </span>
            </div>

            <div class="metric-hint">미처리/실패 배치 건수</div>

            <div class="mt-3">
              <v-btn size="small" variant="text" to="/admin/settlement">
                정산 상세 보기
                <v-icon end size="18">mdi-chevron-right</v-icon>
              </v-btn>
            </div>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>


<style>
.admin-toolbar {
  /* 헤더 높이만큼 위를 이미 v-main이 띄워줍니다 → sticky는 top:0 */
  position: sticky;
  top: 0;
  z-index: 2;

  /* Vuetify 기본 좌우 패딩 중 '왼쪽'만 0으로: 사이드바 경계와 수직 정렬 */
  --v-toolbar-padding-start: 0px;
  --v-toolbar-padding-end: 16px;

  background: #fafafa;
  border-bottom: 1px solid rgba(0,0,0,0.06);
}
</style>

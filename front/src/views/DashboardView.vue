<script setup lang="ts">
import { ref, computed, onMounted, onUnmounted } from 'vue'
import axios from 'axios'

export type Metrics = {
  todaySuccessCount: number
  todaySuccessAmount: number
  todayFailureCount : number
  todayFailureRate: number
  todayApproveRate: number
  lastHourApproveRate: number
  approvalDelayExceededCount: number
  highAmountCount: number
  highAmountSum: number
}

const loading = ref(true)
const errorMsg = ref<string | null>(null)
const metrics = ref<Metrics | null>(null)

// 포맷터
const nf = new Intl.NumberFormat('ko-KR')
const won = (v: number) => `₩${nf.format(v)}`

// 최근 1시간 승인률 (0~100 보정)
const approveRate1h = computed(() => {
  const r = metrics.value?.lastHourApproveRate ?? 0
  return Math.max(0, Math.min(100, Math.round(r)))
})

// 오늘 승인률/실패율도 0~100 보정
const todayApproveRate = computed(() =>
  Math.max(0, Math.min(100, Math.round(metrics.value?.todayApproveRate ?? 0)))
)
const todayFailureRate = computed(() =>
  Math.max(0, Math.min(100, Math.round(metrics.value?.todayFailureRate ?? 0)))
)

async function fetchMetrics() {
  try {
    errorMsg.value = null
    loading.value = true
    const { data } = await axios.get<Metrics>('/api/v1/admin/metrics')
    metrics.value = data
  } catch (e: any) {
    errorMsg.value = e?.message ?? '지표를 불러오지 못했습니다.'
  } finally {
    loading.value = false
  }
}

// 탭 복귀/포커스 시 새로고침
function onVisibleOrFocus() {
  if (document.visibilityState === 'visible') fetchMetrics()
}

onMounted(async () => {
  await fetchMetrics()
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
    <v-alert
      v-if="errorMsg"
      type="error"
      variant="tonal"
      class="ma-4"
    >
      {{ errorMsg }}
    </v-alert>

    <v-row class="pa-4" align="stretch">
      <!-- [1] 오늘 결제 현황 (통합 카드) -->
      <v-col cols="12" md="6">
        <v-card class="metric-card">
          <v-card-text>
            <div class="card-head">
              <v-icon size="20" class="mr-2">mdi-chart-box</v-icon>
              <span class="card-title">오늘 결제 현황</span>
            </div>

            <div class="grid-2">
              <div class="stat">
                <div class="stat-label">성공 건수</div>
                <div class="stat-value">
                  <template v-if="!loading && metrics">
                    {{ nf.format(metrics.todaySuccessCount) }}
                  </template>
                  <v-skeleton-loader v-else type="text" class="w-75" />
                </div>
              </div>

              <div class="stat">
                <div class="stat-label">성공 금액</div>
                <div class="stat-value">
                  <template v-if="!loading && metrics">
                    {{ won(metrics.todaySuccessAmount) }}
                  </template>
                  <v-skeleton-loader v-else type="text" class="w-75" />
                </div>
              </div>

              <div class="stat">
                <div class="stat-label">실패 건수</div>
                <div class="stat-value failure">
                  <template v-if="!loading && metrics">
                    {{ nf.format(metrics.todayFailureCount) }}
                  </template>
                  <v-skeleton-loader v-else type="text" class="w-50" />
                </div>
              </div>

              <div class="stat">
                <div class="stat-label">실패율</div>
                <div class="stat-value failure">
                  <template v-if="!loading && metrics">
                    {{ todayFailureRate }}%
                  </template>
                  <v-skeleton-loader v-else type="text" class="w-50" />
                </div>
              </div>
            </div>

            <div class="mt-4">
              <div class="mini-row">
                <span class="mini-label">오늘 승인률</span>
                <span class="mini-val">{{ todayApproveRate }}%</span>
              </div>
              <v-progress-linear
                :model-value="todayApproveRate"
                height="8"
                rounded
              />
            </div>

            <div class="metric-hint mt-2">금일 00:00 ~ 현재</div>
          </v-card-text>
        </v-card>
      </v-col>

      <!-- [2] 최근 1시간 승인률 -->
      <v-col cols="12" md="6">
        <v-card class="metric-card">
          <v-card-text>
            <div class="card-head">
              <v-icon size="20" class="mr-2">mdi-clock-check-outline</v-icon>
              <span class="card-title">최근 1시간 승인률</span>
            </div>

            <div class="metric-value">
              <template v-if="!loading && metrics">
                {{ approveRate1h }}%
              </template>
              <v-skeleton-loader v-else type="text" class="w-25" />
            </div>

            <div class="mt-3">
              <v-progress-linear
                v-if="!loading && metrics"
                :model-value="approveRate1h"
                height="8"
                rounded
              />
              <v-skeleton-loader v-else type="image" class="line-skeleton" />
            </div>
          </v-card-text>
        </v-card>
      </v-col>

      <!-- [3] 승인 지연 한도 초과 건수 -->
      <v-col cols="12" md="6">
        <v-card class="metric-card">
          <v-card-text>
            <div class="card-head">
              <v-icon size="20" class="mr-2">mdi-timer-sand</v-icon>
              <span class="card-title">승인 지연 한도 초과</span>
              <v-chip size="small" class="ml-2" color="grey-lighten-2" variant="tonal">
                > 120초
              </v-chip>
            </div>

            <div class="metric-value">
              <template v-if="!loading && metrics">
                {{ nf.format(metrics.approvalDelayExceededCount) }}
              </template>
              <v-skeleton-loader v-else type="text" class="w-25" />
            </div>
          </v-card-text>
        </v-card>
      </v-col>

      <!-- [4] 고액 결제 (건수/금액) -->
      <v-col cols="12" md="6">
        <v-card class="metric-card">
          <v-card-text>
            <div class="card-head">
              <v-icon size="20" class="mr-2">mdi-cash-multiple</v-icon>
              <span class="card-title">고액 결제</span>
              <v-chip size="small" class="ml-2" color="grey-lighten-2" variant="tonal">
                ≥ ₩100,000
              </v-chip>
            </div>

            <div class="grid-2">
              <div class="stat">
                <div class="stat-label">건수</div>
                <div class="stat-value">
                  <template v-if="!loading && metrics">
                    {{ nf.format(metrics.highAmountCount) }}
                  </template>
                  <v-skeleton-loader v-else type="text" class="w-50" />
                </div>
              </div>
              <div class="stat">
                <div class="stat-label">금액</div>
                <div class="stat-value">
                  <template v-if="!loading && metrics">
                    {{ won(metrics.highAmountSum) }}
                  </template>
                  <v-skeleton-loader v-else type="text" class="w-75" />
                </div>
              </div>
            </div>

            <div class="mt-2">
              <v-btn
                size="small"
                variant="text"
                :to="{
                  path: '/admin/orders',
                  query: { status: 'SUCCESS', minAmount: '100000', from: 'today' }
                }"
              >
                고액 결제 목록 보기
                <v-icon end size="18">mdi-chevron-right</v-icon>
              </v-btn>
            </div>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<style scoped>
.metric-card { height: 100%; }
.card-head { display: flex; align-items: center; font-weight: 600; margin-bottom: 6px; }
.card-title { font-size: 16px; }
.metric-value { font-size: 28px; font-weight: 700; line-height: 1.2; }
.metric-hint { font-size: 12px; color: #777; }
.grid-2 { display: grid; grid-template-columns: 1fr 1fr; gap: 12px; margin-top: 8px; }
.stat-label { font-size: 12px; color: #888; }
.stat-value { font-size: 20px; font-weight: 700; }
.failure { color: #e53935; }
.mini-row { display: flex; justify-content: space-between; align-items: center; margin-bottom: 8px; }
.mini-label { color: #666; font-size: 13px; }
.mini-val { font-weight: 700; }
.w-25 { width: 25%; } .w-50 { width: 50%; } .w-75 { width: 75%; }
.line-skeleton { height: 8px; border-radius: 8px; }
</style>

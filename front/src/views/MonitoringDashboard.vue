<script setup lang="ts">
</script>

<template>
  <div class="ops">
    <iframe :src="url" title="MonitoringDashboard" referrerpolicy="no-referrer" loading="lazy" allow="fullscreen"
    />
  </div>
</template>

<script setup>
import { computed } from 'vue'

// .env에서 설정(없으면 기본값)
const base = import.meta.env.VITE_GRAFANA_BASE ?? 'http://localhost:3030'
const uid  = import.meta.env.VITE_GRAFANA_UID   ?? 'c078d73e-5649-4201-b411-89fd84f85387'
const slug = import.meta.env.VITE_GRAFANA_SLUG  ?? 'payflow-minimal-ops-6-metrics'

// 시간/리프레시 간격은 필요에 맞게 바꾸세요
const url = computed(() =>
  `${base}/d/${uid}/${slug}?orgId=1&from=now-1h&to=now&timezone=browser&refresh=5s&kiosk`
)
</script>

<style scoped>
.ops { height: calc(100vh - 120px); min-height: 0; }
iframe { width: 100%; height: 100%; border: 0; display: block; }
</style>

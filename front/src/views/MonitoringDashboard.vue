<script setup lang="ts">
import { computed } from 'vue'

const base = import.meta.env.VITE_GRAFANA_BASE ?? 'http://localhost:3030'
const uid  = import.meta.env.VITE_GRAFANA_UID  ?? '10b4384a-24ec-4e0f-b324-ca6aee3edf05'
const slug = import.meta.env.VITE_GRAFANA_SLUG ?? 'ec2-core-ops-5-metrics'

const url = computed(() =>
  `${base}/d/${uid}/${slug}?orgId=1&from=now-1h&to=now&timezone=browser&refresh=5s&kiosk`
)
</script>

<template>
  <div class="ops">
    <iframe :src="url" title="MonitoringDashboard"
            referrerpolicy="no-referrer"
            loading="lazy"
            allow="fullscreen"
    />
  </div>
</template>

<style scoped>
.ops { height: calc(100vh - 120px); min-height: 0; }
iframe { width: 100%; height: 100%; border: 0; display: block; }
</style>

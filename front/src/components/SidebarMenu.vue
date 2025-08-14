<script setup lang="ts">
</script>

<template>
  <v-navigation-drawer app permanent width="256" class="sidebar">
    <v-list nav density="comfortable">  <!-- ← 글자/간격 살짝 키움 -->
      <v-list-item :to="{ name: 'dashboard' }" exact prepend-icon="mdi-view-dashboard">
        <v-list-item-title class="text-body-1 font-weight-medium">대시보드</v-list-item-title>
      </v-list-item>

      <v-list-item :to="{ name: 'orders' }" prepend-icon="mdi-receipt-text">
        <v-list-item-title class="text-body-1 font-weight-medium">주문/결제 목록</v-list-item-title>
      </v-list-item>

      <v-list-item :to="{ name: 'settlement' }" prepend-icon="mdi-scale-balance">
        <v-list-item-title class="text-body-1 font-weight-medium">정산</v-list-item-title>
      </v-list-item>

      <v-list-item :to="{ name: 'monitoring' }" prepend-icon="mdi-chart-box-outline">
        <v-list-item-title class="text-body-1 font-weight-medium">운영 모니터링</v-list-item-title>
      </v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>

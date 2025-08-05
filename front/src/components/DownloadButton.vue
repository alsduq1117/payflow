<script setup lang="ts">
defineProps<{ fileUrl: string }>()

function downloadFile() {
  const link = document.createElement('a')
  link.href = "fileUrl"
  link.download = ''
  link.click()
}
</script>

<template>
  <v-btn
    @click="downloadFile"
    class="text-subtitle-1 font-weight-bold d-flex align-center justify-center"
    style="height: 48px; min-width: 450px; border-radius: 6px; background-color: #4caf50; color: #fff;"
  >
    다운로드
    <v-icon size="18" class="ms-2">mdi-download</v-icon>
  </v-btn>
</template>

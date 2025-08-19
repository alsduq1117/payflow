<script setup lang="ts">
const props = defineProps<{ fileUrl: string }>();

function downloadFile() {
  if (!props.fileUrl) return;

  const a = document.createElement('a');
  a.href = props.fileUrl;
  a.download = props.fileUrl.split('/').pop() || 'download';
  a.target = '_blank'; // 일부 브라우저 호환
  document.body.appendChild(a);
  a.click();
  document.body.removeChild(a);
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

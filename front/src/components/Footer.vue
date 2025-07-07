<script setup lang="ts">
import PaymentButton from "@/components/PaymentButton.vue";
</script>

<template>
  <footer class="footer">
    <div class="footer-top">
      <div class="footer-info">
        <h3>Payflow</h3>
        <p>간편하고 직관적인 결제 테스트 플랫폼, Payflow는 김민엽의 포트폴리오 프로젝트입니다.</p>
        <p class="demo-alert">💡 비상업적 목적의 데모 사이트로 실제 결제가 이루어지지 않습니다.</p>
      </div>
      <div class="footer-links">
        <p><strong>📧 Email:</strong> alsduq1117@gmail.com</p>
        <p><strong>📝 Blog:</strong> https://kirminov.tistory.com/</p>
        <p><strong>💻 GitHub:</strong> https://github.com/alsduq1117</p>
      </div>
    </div>
    <div class="footer-bottom">
      <p>© 2025 Payflow. Created by 김민엽. All rights reserved.</p>
    </div>
  </footer>
</template>

<style scoped>
.footer {
  background-color: #f8f9fb;
  color: #1e2d60;
  font-size: 0.875rem;
  padding: 24px 24px;
  border-top: 1px solid #e0e0e0;
}

.footer-top {
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
  gap: 20px;
}

.footer-info h3 {
  margin: 0;
  font-size: 1.2rem;
  font-weight: 600;
  color: #1e2d60;
}

.footer-info p,
.footer-links p {
  margin: 4px 0;
  line-height: 1.4;
}

.footer-bottom {
  margin-top: 16px;
  text-align: center;
  font-size: 0.75rem;
  color: #777;
}

.demo-alert {
  color: #d32f2f;
  font-size: 0.85rem;
  margin-top: 6px;
}
</style>


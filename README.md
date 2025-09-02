# 🚀 Payflow
> **디지털 콘텐츠 거래 및 결제·정산 서비스**  
> PDF, PPT, .zip 파일 등 디지털 상품을 등록하고, 결제·정산까지 이어지는 전 과정을 자동화한 플랫폼입니다.  
<p align="center">
  <img src="https://github.com/user-attachments/assets/b3063d18-b9bf-4d45-b65c-f58111319748" width="300"/>
</p>

---

## 📌 프로젝트 개요
- **프로젝트명:** Payflow  
- **구분:** 개인 프로젝트  
- **서비스 링크:** [payflow.my](https://payflow.my)  

---

## 🛠️ 기술 스택

### Frontend
<div>
  <img src="https://img.shields.io/badge/Vue.js-4FC08D?style=flat-square&logo=vuedotjs&logoColor=white"/>
  <img src="https://img.shields.io/badge/Vuetify-1867C0?style=flat-square&logo=vuetify&logoColor=white"/>
  <img src="https://img.shields.io/badge/TypeScript-3178C6?style=flat-square&logo=typescript&logoColor=white"/>
  <img src="https://img.shields.io/badge/Vite-646CFF?style=flat-square&logo=vite&logoColor=white"/>
</div>

### Backend
<div>
<img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/Java-17-007396?style=flat-square&logo=openjdk&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring Security-6DB33F?style=flat-square&logo=springsecurity&logoColor=white"/>
<img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/JPA-Hibernate-59666C?style=flat-square&logo=hibernate&logoColor=white"/>
<img src="https://img.shields.io/badge/QueryDSL-0088CC?style=flat-square&logo=databricks&logoColor=white"/>
</div>

### DevOps
<div>
<img src="https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=docker&logoColor=white"/>
<img src="https://img.shields.io/badge/AWS EC2-FF9900?style=flat-square&logo=amazonec2&logoColor=white"/>
<img src="https://img.shields.io/badge/AWS RDS-527FFF?style=flat-square&logo=amazonrds&logoColor=white"/>
<img src="https://img.shields.io/badge/Jenkins-D24939?style=flat-square&logo=jenkins&logoColor=white"/>
<img src="https://img.shields.io/badge/Prometheus-E6522C?style=flat-square&logo=prometheus&logoColor=white"/>
<img src="https://img.shields.io/badge/Grafana-F46800?style=flat-square&logo=grafana&logoColor=white"/>
</div>

---

## 🌟 주요 기능
- **결제/정산**
  - 토스페이먼츠 PG 연동 (Checkout → 승인 → 결제 확정)
  - IdempotencyKey 기반 중복 결제 방지
  - Wallet · Ledger 도메인 분리 (지갑 거래 내역 & 장부 기록 관리)

- **콘텐츠 관리**
  - S3 Presigned URL 기반 파일 업로드 (썸네일/콘텐츠)
  - 디지털 상품 등록 및 구매 기능
  - 장바구니 기능 구현

- **보안/인증**
  - OAuth2 소셜 로그인 (Google, Kakao, Naver) + JWT 인증/인가
  - 결제 Webhook 검증 및 보안 처리

- **구조/설계**
  - Querydsl 기반 동적 쿼리
  - 이벤트 기반 비동기 처리 (`@TransactionalEventListener`)
---

## 📷 스크린샷

### 상품 목록
<img src="https://github.com/user-attachments/assets/4969513c-57df-4011-9877-e0daeeea3877" width="600"/>

---

### 상품 등록
<img src="https://github.com/user-attachments/assets/fd51de12-5fc3-4718-8117-734930cc3597" width="600"/>

---

### 관리자페이지 (대시보드)
<img src="https://github.com/user-attachments/assets/441ccfcf-223c-4b86-846e-fbdc29bd126b" width="600"/>

---

### 관리자페이지 (주문/결제 목록)
<img src="https://github.com/user-attachments/assets/ba73b9fe-2fb5-4cb8-a425-d20b238aeb8e" width="600"/>

---

### 관리자페이지 (정산)
<img src="https://github.com/user-attachments/assets/445bde15-5f65-455f-881f-514e7ac7d599" width="600"/>

---

### 관리자페이지 (운영 모니터링)
<img src="https://github.com/user-attachments/assets/b178d5dd-1611-42bf-96e0-d097e0695d05" width="600"/>

---

### 장바구니
<img src="https://github.com/user-attachments/assets/355c754e-c0d5-41c2-b3b8-0d9bc6e7ea15" width="600"/>

---

### 마이페이지
<img src="https://github.com/user-attachments/assets/ca7621b0-697c-428d-aa04-58bdf8f441c0" width="600"/>

---


<script setup lang="ts">
import {ref} from 'vue'
import {useRouter} from 'vue-router'
import type {VForm} from 'vuetify/components'
import axios from "axios";

// 유효성 검사 플래그 및 참조
const valid = ref(false)
const formRef = ref<VForm | null>(null)
const router = useRouter()

// 상품 폼 데이터 상태
const form = ref({
  name: '',
  price: null,
  fileUrl: '',        // 백엔드 DB에 저장될 콘텐츠 URL
  thumbnailUrl: '',   // 백엔드 DB에 저장될 썸네일 URL
  description: ''
})

// 파일 상태와 미리보기
const thumbnailFile = ref(null)
const contentFile = ref(null)
const thumbnailPreview = ref('')

// 유효성 검사 규칙
const rules = {
  required: (v: any) => !!v || '필수 입력입니다',
  positive: (v: number) => v > 0 || '0보다 큰 값을 입력하세요',
  requiredFile: (v: any) => !!v || '파일을 선택해주세요'
}

// Presigned URL 요청 함수
const getPresignedUrl = async (file: File, folder: string): Promise<string> => {
  const res = await axios.post('/api/v1/s3/presigned-url', {
    fileName: file.name,
    contentType: file.type,
    folder
  })
  return res.data
}

// presigned URL로 실제 파일 PUT (S3 업로드)
const uploadFileToS3 = async (file: File, presignedUrl: string): Promise<string> => {
  await axios.put(presignedUrl, file, {
    headers: {
      'Content-Type': file.type
    }
  })
  return presignedUrl.split('?')[0]
}

// 썸네일 업로드 핸들러
const handleThumbnailUpload = async () => {
  if (!thumbnailFile.value) return
  // 미리보기
  thumbnailPreview.value = URL.createObjectURL(thumbnailFile.value)

  // S3 업로드
  const presignedUrl = await getPresignedUrl(thumbnailFile.value, 'thumbnails')
  const uploadedUrl = await uploadFileToS3(thumbnailFile.value, presignedUrl)

  form.value.thumbnailUrl = uploadedUrl
}

// 콘텐츠 업로드 핸들러
const handleContentUpload = async () => {
  if (!contentFile.value) return

  const presignedUrl = await getPresignedUrl(contentFile.value, 'contents')
  const uploadedUrl = await uploadFileToS3(contentFile.value, presignedUrl)

  form.value.fileUrl = uploadedUrl
}

// 상품 등록 핸들러
const submitForm = async () => {
  const isValid = await formRef.value?.validate()
  if (!isValid) return
  try {
    await axios.post('/api/v1/products', form.value)
    alert('상품 등록 완료!')
    await router.push('/')
    // 폼 초기화, 라우터 이동 등 필요 시 추가
  } catch (e) {
    alert('등록 중 오류 발생')
    console.error(e)
  }
}
</script>

<template>
  <v-container class="py-12">
    <v-row justify="center">
      <v-col cols="12" md="8">
        <v-card class="pa-6" elevation="3">
          <v-card-title class="text-h5 font-weight-bold">
            상품 등록
          </v-card-title>

          <v-card-text>
            <v-form v-model="valid" ref="formRef">

              <!-- 썸네일 미리보기 -->
              <v-img v-if="thumbnailPreview" :src="thumbnailPreview" height="250" class="my-4"
                     contain style="border-radius: 8px; border: 1px solid #ccc"
              />

              <!-- 썸네일 업로드 -->
              <v-file-input v-model="thumbnailFile" accept=".jpg, .jpeg, .png, .gif"
                            label="대표 이미지 업로드" :rules="[rules.requiredFile]"
                            @change="handleThumbnailUpload"
              />

              <!-- 콘텐츠 파일 업로드 -->
              <v-file-input v-model="contentFile" accept=".pdf,.zip,.ppt,.pptx" label="콘텐츠 파일 업로드"
                            :rules="[rules.requiredFile]" @change="handleContentUpload"/>

              <v-row>
                <v-col cols="12" md="4">
                  <v-text-field v-model="form.name" label="상품명" :rules="[rules.required]"/>
                  <v-text-field v-model="form.price" label="가격" class="no-spinner" suffix="KRW"
                                type="number" :rules="[rules.required, rules.positive]"/>
                </v-col>

                <v-col cols="12" md="8">
                  <v-textarea v-model="form.description" label="상품 설명" rows="8" auto-grow outlined/>
                </v-col>
              </v-row>

              <!-- 등록 버튼 -->
              <v-row justify="center" class="mt-6">
                <v-col cols="auto">
                  <v-btn color="#1E2D60" :disabled="!valid" @click="submitForm">상품 등록하기</v-btn>
                  <v-btn color="black" variant="outlined" class="mr-2 ml-4">취소</v-btn>
                </v-col>
              </v-row>
            </v-form>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>


<style>
.no-spinner input[type="number"]::-webkit-inner-spin-button,
.no-spinner input[type="number"]::-webkit-outer-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

.no-spinner input[type="number"] {
  -moz-appearance: textfield; /* Firefox */
}
</style>

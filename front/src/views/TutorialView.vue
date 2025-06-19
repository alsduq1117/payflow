<script setup lang="ts">
</script>

<template>
  <div class="p-10 space-y-10 bg-gray-50 min-h-screen">

    <!-- 📏 w-full vs max-w-screen-lg -->
    <div>
      <p class="font-bold mb-2">📏 w-full vs max-w-screen-lg</p>
      <div class="flex gap-4">
        <div class="bg-gray-100 border rounded p-4 w-full text-center">
          w-full (브라우저 너비 전체 사용)
        </div>
        <div class="bg-gray-100 border rounded p-4 max-w-screen-lg w-full text-center">
          max-w-screen-lg (최대 너비 제한)
        </div>
      </div>
    </div>

    <!-- ↕️ flex-col vs ↔️ flex-row -->
    <div>
      <p class="font-bold mb-2">↕️ flex-col vs ↔️ flex-row</p>
      <div class="flex gap-4">
        <div class="flex flex-col gap-2 bg-white border rounded p-4 w-1/2 text-center">
          <span>flex-col</span>
          <span>세로 정렬</span>
        </div>
        <div class="flex flex-row gap-2 bg-white border rounded p-4 w-1/2 text-center">
          <span>flex-row</span>
          <span>가로 정렬</span>
        </div>
      </div>
    </div>

    <!-- 📦 padding vs 📐 margin -->
    <div>
      <p class="font-bold mb-2">📦 padding vs 📐 margin</p>
      <div class="flex gap-4 bg-gray-100 p-4">
        <div class="bg-white border rounded p-4 text-center w-1/2">
          p-4 (내부 여백)
        </div>
        <div class="bg-white border rounded m-4 text-center w-1/2">
          m-4 (외부 여백)
        </div>
      </div>
    </div>

    <!-- 🌑 shadow-none vs 🌕 shadow-xl -->
    <div>
      <p class="font-bold mb-2">🌑 shadow-none vs 🌕 shadow-xl</p>
      <div class="flex gap-4">
        <div class="bg-white border rounded shadow-none p-4 w-1/2 text-center">
          shadow-none (그림자 없음)
        </div>
        <div class="bg-white border rounded shadow-xl p-4 w-1/2 text-center">
          shadow-xl (강한 그림자)
        </div>
      </div>
    </div>

    <!-- ◾ rounded-none vs 🔵 rounded-2xl -->
    <div>
      <p class="font-bold mb-2">◾ rounded-none vs 🔵 rounded-2xl</p>
      <div class="flex gap-4">
        <div class="bg-blue-100 border rounded-none p-4 w-1/2 text-center">
          rounded-none (모서리 없음)
        </div>
        <div class="bg-blue-100 border rounded-2xl p-4 w-1/2 text-center">
          rounded-2xl (둥근 모서리)
        </div>
      </div>
    </div>

    <!-- 📍 justify-around vs 📍 justify-between -->
    <div>
      <p class="font-bold mb-2">📍 justify-around vs 📍 justify-between</p>
      <div class="flex gap-4">
        <div class="flex justify-around items-center w-1/2 border rounded p-2 bg-white">
          <span>A</span><span>B</span><span>C</span>
        </div>
        <div class="flex justify-between items-center w-1/2 border rounded p-2 bg-white">
          <span>A</span><span>B</span><span>C</span>
        </div>
      </div>
    </div>

  </div>
  <div class="p-10 space-y-10 bg-gray-50 min-h-screen">

    <!-- 📐 min-h-screen vs h-full -->
    <div>
      <p class="font-bold mb-2">📐 min-h-screen vs h-full</p>
      <div class="flex gap-4 h-96 bg-gray-200">
        <div class="bg-white border rounded p-4 w-1/2 min-h-screen text-center">
          min-h-screen (화면 높이 이상으로 최소 보장)
        </div>
        <div class="bg-white border rounded p-4 w-1/2 h-full text-center">
          h-full (부모 높이만큼만 차지)
        </div>
      </div>
    </div>

    <!-- 🌉 gap-2 vs gap-8 -->
    <div>
      <p class="font-bold mb-2">🌉 gap-2 vs gap-8</p>
      <div class="flex gap-4">
        <div class="flex gap-2 bg-white border rounded p-4 w-1/2">
          <div class="bg-blue-200 w-6 h-6"></div>
          <div class="bg-blue-200 w-6 h-6"></div>
          <div class="bg-blue-200 w-6 h-6"></div>
        </div>
        <div class="flex gap-8 bg-white border rounded p-4 w-1/2">
          <div class="bg-blue-200 w-6 h-6"></div>
          <div class="bg-blue-200 w-6 h-6"></div>
          <div class="bg-blue-200 w-6 h-6"></div>
        </div>
      </div>
    </div>

    <!-- 🧱 border vs border-2 -->
    <div>
      <p class="font-bold mb-2">🧱 border vs border-2</p>
      <div class="flex gap-4">
        <div class="bg-white border p-4 rounded w-1/2 text-center">
          border (기본 두께)
        </div>
        <div class="bg-white border-2 p-4 rounded w-1/2 text-center">
          border-2 (두꺼운 테두리)
        </div>
      </div>
    </div>

    <!-- 🎨 bg-white vs bg-gray-100 -->
    <div>
      <p class="font-bold mb-2">🎨 bg-white vs bg-gray-100</p>
      <div class="flex gap-4">
        <div class="bg-white border p-4 rounded w-1/2 text-center">
          bg-white (밝은 배경)
        </div>
        <div class="bg-gray-100 border p-4 rounded w-1/2 text-center">
          bg-gray-100 (연한 회색 배경)
        </div>
      </div>
    </div>

    <!-- 🧲 items-stretch vs items-center -->
    <div>
      <p class="font-bold mb-2">🧲 items-stretch vs items-center</p>
      <div class="flex gap-4 h-32">
        <div class="flex flex-col items-stretch bg-white border rounded p-4 w-1/2">
          <div class="bg-blue-300 h-6">stretch</div>
          <div class="bg-blue-300 h-6">stretch</div>
        </div>
        <div class="flex flex-col items-center bg-white border rounded p-4 w-1/2">
          <div class="bg-blue-300 h-6 w-1/2">center</div>
          <div class="bg-blue-300 h-6 w-1/2">center</div>
        </div>
      </div>
    </div>
    <div>
      <p class="font-bold mb-2">📱 반응형 정렬: flex-col → md:flex-row</p>
      <div class="flex flex-col md:flex-row gap-4 bg-white border rounded p-4">
        <div class="bg-blue-200 p-2 text-center w-full md:w-1/2">Box 1</div>
        <div class="bg-blue-200 p-2 text-center w-full md:w-1/2">Box 2</div>
      </div>
      <p class="text-sm mt-2 text-gray-500">📌 화면이 작을 땐 세로, 중간 이상부턴 가로 정렬됩니다.</p>
    </div>

  </div>

  <div class="p-10 space-y-10 bg-white min-h-screen">

    <!-- 🔠 text-base vs text-4xl -->
    <div>
      <p class="font-bold mb-2">🔠 text-base vs text-4xl</p>
      <div class="flex gap-4 items-end">
        <p class="text-base border p-2 bg-gray-100 rounded">기본 크기 (text-base)</p>
        <p class="text-4xl border p-2 bg-gray-100 rounded">큰 글씨 (text-4xl)</p>
      </div>
    </div>

    <!-- 🎨 text-gray-700 vs text-blue-600 -->
    <div>
      <p class="font-bold mb-2">🎨 text-gray-700 vs text-blue-600</p>
      <div class="flex gap-4">
        <span class="text-gray-700">회색 텍스트</span>
        <span class="text-blue-600">파란색 텍스트</span>
      </div>
    </div>

    <!-- 🧱 font-normal vs font-bold -->
    <div>
      <p class="font-bold mb-2">🧱 font-normal vs font-bold</p>
      <div class="flex gap-4">
        <span class="font-normal">보통 굵기</span>
        <span class="font-bold">굵은 글씨</span>
      </div>
    </div>

    <!-- 🔘 버튼: bg-blue-500 vs hover:bg-blue-700 -->
    <div>
      <p class="font-bold mb-2">🔘 버튼: 기본 vs hover</p>
      <div class="flex gap-4">
        <button class="bg-blue-500 text-white px-4 py-2 rounded">기본 버튼</button>
        <button class="bg-blue-500 hover:bg-blue-700 text-white px-4 py-2 rounded">hover 효과</button>
      </div>
    </div>

    <!-- 📝 input 스타일 비교 -->
    <div>
      <p class="font-bold mb-2">📝 입력창 스타일</p>
      <div class="flex gap-4">
        <input type="text" placeholder="기본 입력창" class="border px-3 py-2 rounded w-1/2" />
        <input type="text" placeholder="포커스 스타일" class="border px-3 py-2 rounded w-1/2 focus:outline-none focus:ring-2 focus:ring-blue-500" />
      </div>
    </div>

    <!-- 📏 text-center vs text-right -->
    <div>
      <p class="font-bold mb-2">📏 text-center vs text-right</p>
      <div class="flex gap-4 w-full">
        <p class="text-center border w-1/2 p-2">가운데 정렬</p>
        <p class="text-right border w-1/2 p-2">오른쪽 정렬</p>
      </div>
    </div>

    <!-- 🌐 링크: 기본 vs hover:underline -->
    <div>
      <p class="font-bold mb-2">🌐 링크: 기본 vs hover 효과</p>
      <div class="flex gap-4">
        <a href="#" class="text-blue-600">기본 링크</a>
        <a href="#" class="text-blue-600 hover:underline">hover 시 밑줄</a>
      </div>
    </div>

  </div>
</template>

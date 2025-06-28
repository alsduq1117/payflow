import { createRouter, createWebHistory } from 'vue-router'
import HomeView from "@/views/HomeView.vue";
import ProductCreateForm from "@/components/ProductCreateForm.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/product-create',
      name: 'product-create',
      component: ProductCreateForm,
    },
  ],
})

export default router

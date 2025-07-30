import {createRouter, createWebHistory} from 'vue-router'
import HomeView from "@/views/HomeView.vue";
import ProductCreateForm from "@/components/ProductCreateForm.vue";
import ProductDetail from "@/components/ProductDetail.vue";

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
    {
      path: '/product/:productId',
      name: 'product-detail',
      component: ProductDetail,
      props: true
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/LoginView.vue'),
      meta: { requiresAuth: false, hideHeader: true }
    },
    {
      path: '/signup',
      name: 'signup',
      component: () => import('@/views/SignupView.vue'),
      meta: { requiresAuth: false, hideHeader: true }
    },
    {
      path: '/oauth2/redirect',
      name: 'oauth2-redirect',
      component: () => import('@/components/LoginRedirect.vue'),
      meta: { requiresAuth: false, hideHeader: true }
    }
  ],
})

export default router

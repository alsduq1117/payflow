import {createRouter, createWebHistory} from 'vue-router'
import HomeView from "@/views/HomeView.vue";
import ProductCreateForm from "@/components/ProductCreateForm.vue";
import ProductDetail from "@/components/ProductDetail.vue";
import DashboardView from "@/views/DashboardView.vue";
import LoginRedirect from "@/components/LoginRedirect.vue";
import SignupView from "@/views/SignupView.vue";
import LoginView from "@/views/LoginView.vue";

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
      component: LoginView,
      meta: { requiresAuth: false, hideHeader: true }
    },
    {
      path: '/signup',
      name: 'signup',
      component: SignupView,
      meta: { requiresAuth: false, hideHeader: true }
    },
    {
      path: '/oauth2/redirect',
      name: 'oauth2-redirect',
      component: LoginRedirect,
      meta: { requiresAuth: false, hideHeader: true }
    },
    {
      path: '/admin',
      name: 'admin',
      component: DashboardView,
      meta: { showSidebar: true, hideHeader: false, hideFooter: true },
      children: [
        { path: 'dashboard', name: 'dashboard', component: DashboardView},
        { path: 'orders', name: 'orders', component: DashboardView},
        { path: 'settlement', name: 'settlement', component: DashboardView},
        { path: 'monitoring', name: 'monitoring', component: DashboardView}
      ],
    }
  ],
})

export default router

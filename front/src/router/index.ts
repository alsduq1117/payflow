import {createRouter, createWebHistory} from 'vue-router'
import HomeView from "@/views/HomeView.vue";
import ProductCreateForm from "@/components/ProductCreateForm.vue";
import ProductDetail from "@/components/ProductDetail.vue";
import DashboardView from "@/views/DashboardView.vue";
import LoginRedirect from "@/components/LoginRedirect.vue";
import SignupView from "@/views/SignupView.vue";
import LoginView from "@/views/LoginView.vue";
import PaymentListView from "@/views/PaymentListView.vue";
import SettlementView from "@/views/SettlementView.vue";
import MonitoringDashboard from "@/views/MonitoringDashboard.vue";
import CartView from "@/views/CartView.vue";
import MyPageView from "@/views/MyPageView.vue";

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
      path: '/products/:productId',
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
      path: '/admin/dashboard',
      name: 'dashboard',
      component: DashboardView,
      meta: { showSidebar: true, hideHeader: false, hideFooter: true },
    },
    {
      path: '/admin/orders',
      name: 'orders',
      component: PaymentListView,
      meta: { showSidebar: true, hideHeader: false, hideFooter: true },
    },
    {
      path: '/admin/settlement',
      name: 'settlement',
      component: SettlementView,
      meta: { showSidebar: true, hideHeader: false, hideFooter: true },
    },
    {
      path: '/admin/monitoring',
      name: 'monitoring',
      component: MonitoringDashboard,
      meta: { showSidebar: true, hideHeader: false, hideFooter: true },
    },
    {
      path: '/cart',
      name: 'cart',
      component: CartView,
    },
    {
      path: '/mypage',
      name: 'mypage',
      component: MyPageView,
    },
  ],
})

export default router

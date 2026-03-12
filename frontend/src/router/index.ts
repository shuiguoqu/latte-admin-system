import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: () => import('@/pages/LoginPage.vue'),
      meta: { requiresAuth: false }
    },
    {
      path: '/',
      redirect: '/users'
    },
    {
      path: '/users',
      name: 'UserList',
      component: () => import('@/pages/UserListPage.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/users/create',
      name: 'UserCreate',
      component: () => import('@/pages/UserFormPage.vue'),
      meta: { requiresAuth: true, adminOnly: true }
    },
    {
      path: '/users/:id/edit',
      name: 'UserEdit',
      component: () => import('@/pages/UserFormPage.vue'),
      meta: { requiresAuth: true }
    }
  ]
})

// 路由守卫 - 鉴权 + 权限检查
router.beforeEach((to, _from, next) => {
  const token = localStorage.getItem('token')
  const role = localStorage.getItem('role')

  if (to.meta.requiresAuth && !token) {
    next('/login')
  } else if (to.path === '/login' && token) {
    next('/users')
  } else if (to.meta.adminOnly && role !== 'ADMIN') {
    // 非管理员访问管理员路由 → 重定向到用户列表
    next('/users')
  } else {
    next()
  }
})

export default router

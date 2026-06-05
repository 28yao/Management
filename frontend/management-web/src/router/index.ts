import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

/**
 * 路由配置
 * 包含登录页、管理员页面和员工页面
 */

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/LoginView.vue'),
    meta: { requiresAuth: false }
  },
  {
    path: '/admin',
    component: () => import('@/components/Layout/AdminLayout.vue'),
    meta: { requiresAuth: true, role: 'ADMIN' },
    redirect: '/admin/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'AdminDashboard',
        component: () => import('@/views/admin/dashboard/AdminDashboard.vue'),
        meta: { title: '仪表盘' }
      },
      {
        path: 'departments',
        name: 'DepartmentManage',
        component: () => import('@/views/admin/department/DepartmentManage.vue'),
        meta: { title: '部门管理' }
      },
      {
        path: 'employees',
        name: 'EmployeeManage',
        component: () => import('@/views/admin/employee/EmployeeManage.vue'),
        meta: { title: '员工管理' }
      },
      {
        path: 'positions',
        name: 'PositionManage',
        component: () => import('@/views/admin/position/PositionManage.vue'),
        meta: { title: '职位管理' }
      },
      {
        path: 'employees/:id',
        name: 'EmployeeDetail',
        component: () => import('@/views/admin/employee/EmployeeDetail.vue'),
        meta: { title: '员工详情' }
      },
      {
        path: 'attendance',
        name: 'AttendanceManage',
        component: () => import('@/views/admin/attendance/AttendanceManage.vue'),
        meta: { title: '考勤管理' }
      },
      {
        path: 'attendance/statistics',
        name: 'AttendanceStatistics',
        component: () => import('@/views/admin/attendance/AttendanceStatistics.vue'),
        meta: { title: '考勤统计' }
      },
      {
        path: 'leaves',
        name: 'LeaveApproval',
        component: () => import('@/views/admin/leave/LeaveApproval.vue'),
        meta: { title: '请假审批' }
      },
      {
        path: 'makeup-clocks',
        name: 'MakeupClockApproval',
        component: () => import('@/views/admin/makeup-clock/MakeupClockApproval.vue'),
        meta: { title: '补卡审批' }
      },
      {
        path: 'notifications',
        name: 'AdminNotifications',
        component: () => import('@/views/admin/notification/NotificationList.vue'),
        meta: { title: '通知管理' }
      },
      {
        path: 'config',
        name: 'SystemConfig',
        component: () => import('@/views/admin/config/SystemConfig.vue'),
        meta: { title: '系统配置' }
      }
    ]
  },
  {
    path: '/employee',
    component: () => import('@/components/Layout/EmployeeLayout.vue'),
    meta: { requiresAuth: true, role: 'EMPLOYEE' },
    redirect: '/employee/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'EmployeeDashboard',
        component: () => import('@/views/employee/dashboard/EmployeeDashboard.vue'),
        meta: { title: '我的首页' }
      },
      {
        path: 'clock',
        name: 'ClockView',
        component: () => import('@/views/employee/clock/ClockView.vue'),
        meta: { title: '打卡' }
      },
      {
        path: 'profile',
        name: 'ProfileView',
        component: () => import('@/views/employee/profile/ProfileView.vue'),
        meta: { title: '个人信息' }
      },
      {
        path: 'leaves',
        name: 'LeaveApply',
        component: () => import('@/views/employee/leave/LeaveApply.vue'),
        meta: { title: '请假申请' }
      },
      {
        path: 'leaves/history',
        name: 'LeaveHistory',
        component: () => import('@/views/employee/leave/LeaveHistory.vue'),
        meta: { title: '请假记录' }
      },
      {
        path: 'makeup-clocks',
        name: 'MakeupClockApply',
        component: () => import('@/views/employee/makeup-clock/MakeupClockApply.vue'),
        meta: { title: '补卡申请' }
      },
      {
        path: 'notifications',
        name: 'EmployeeNotifications',
        component: () => import('@/views/employee/notification/MyNotifications.vue'),
        meta: { title: '我的通知' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

/**
 * 路由守卫
 * 处理登录验证和角色权限
 */
router.beforeEach((to, from, next) => {
  const authStore = useAuthStore()

  // 不需要认证的页面直接放行
  if (to.meta.requiresAuth === false) {
    // 已登录用户访问登录页，跳转到对应首页
    if (authStore.isLoggedIn) {
      next(authStore.isAdmin ? '/admin/dashboard' : '/employee/dashboard')
      return
    }
    next()
    return
  }

  // 需要认证但未登录，跳转登录页
  if (!authStore.isLoggedIn) {
    next('/login')
    return
  }

  // 检查角色权限
  const requiredRole = to.meta.role as string
  if (requiredRole === 'ADMIN' && !authStore.isAdmin) {
    next('/employee/dashboard')
    return
  }
  if (requiredRole === 'EMPLOYEE' && authStore.isAdmin) {
    next('/admin/dashboard')
    return
  }

  next()
})

export default router

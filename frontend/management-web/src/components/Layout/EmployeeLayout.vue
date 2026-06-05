<template>
  <el-container class="layout-container">
    <!-- 侧边栏 -->
    <el-aside width="200px" class="aside">
      <div class="logo">
        <h2>部门管理系统</h2>
      </div>
      <el-menu
        :default-active="activeMenu"
        router
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409eff"
      >
        <el-menu-item index="/employee/dashboard">
          <el-icon><HomeFilled /></el-icon>
          <span>我的首页</span>
        </el-menu-item>
        <el-menu-item index="/employee/clock">
          <el-icon><Clock /></el-icon>
          <span>打卡</span>
        </el-menu-item>
        <el-menu-item index="/employee/leaves">
          <el-icon><Calendar /></el-icon>
          <span>请假申请</span>
        </el-menu-item>
        <el-menu-item index="/employee/leaves/history">
          <el-icon><Document /></el-icon>
          <span>请假记录</span>
        </el-menu-item>
        <el-menu-item index="/employee/makeup-clocks">
          <el-icon><Timer /></el-icon>
          <span>补卡申请</span>
        </el-menu-item>
        <el-menu-item index="/employee/profile">
          <el-icon><User /></el-icon>
          <span>个人信息</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <!-- 主内容区 -->
    <el-container>
      <!-- 顶部导航 -->
      <el-header class="header">
        <div class="header-left">
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/employee/dashboard' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{ currentTitle }}</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="header-right">
          <el-badge :value="unreadCount" :hidden="unreadCount === 0" class="notification-badge">
            <el-button :icon="Bell" circle @click="$router.push('/employee/notifications')" />
          </el-badge>
          <el-dropdown @command="handleCommand">
            <span class="user-info">
              <el-icon><UserFilled /></el-icon>
              <span>{{ authStore.username }}</span>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>

      <!-- 页面内容 -->
      <el-main class="main">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { Bell } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const authStore = useAuthStore()

const unreadCount = ref(0)

const activeMenu = computed(() => route.path)
const currentTitle = computed(() => (route.meta.title as string) || '')

function handleCommand(command: string) {
  if (command === 'logout') {
    authStore.logout()
    router.push('/login')
  }
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

.aside {
  background-color: #304156;
  overflow-y: auto;
}

.logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
}

.logo h2 {
  font-size: 16px;
  font-weight: 600;
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: #fff;
  border-bottom: 1px solid #e6e6e6;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
}

.header-right {
  display: flex;
  align-items: center;
  gap: 16px;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
}

.notification-badge {
  margin-right: 8px;
}

.main {
  background-color: #f0f2f5;
  padding: 20px;
}
</style>

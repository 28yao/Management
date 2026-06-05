<template>
  <div class="profile-view">
    <el-card>
      <template #header>
        <span>个人信息</span>
      </template>

      <el-descriptions :column="2" border>
        <el-descriptions-item label="工号">{{ employee.empNo }}</el-descriptions-item>
        <el-descriptions-item label="姓名">{{ employee.name }}</el-descriptions-item>
        <el-descriptions-item label="手机号">{{ employee.phone }}</el-descriptions-item>
        <el-descriptions-item label="邮箱">{{ employee.email || '-' }}</el-descriptions-item>
        <el-descriptions-item label="入职日期">{{ employee.hireDate }}</el-descriptions-item>
        <el-descriptions-item label="职位">{{ employee.position }}</el-descriptions-item>
        <el-descriptions-item label="所属部门">{{ employee.deptName || '-' }}</el-descriptions-item>
        <el-descriptions-item label="登录账号">{{ employee.account }}</el-descriptions-item>
      </el-descriptions>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getUserInfo } from '@/api/auth'

const employee = ref<any>({})

onMounted(async () => {
  try {
    const res = await getUserInfo()
    employee.value = res.data
  } catch (error) {
    console.error('获取用户信息失败', error)
  }
})
</script>

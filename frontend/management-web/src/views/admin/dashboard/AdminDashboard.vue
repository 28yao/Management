<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>员工总数</span>
              <el-icon><User /></el-icon>
            </div>
          </template>
          <div class="card-value">{{ stats.employeeCount }}</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>部门数量</span>
              <el-icon><OfficeBuilding /></el-icon>
            </div>
          </template>
          <div class="card-value">{{ stats.departmentCount }}</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>今日出勤</span>
              <el-icon><Clock /></el-icon>
            </div>
          </template>
          <div class="card-value">{{ stats.todayAttendance }}</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>待审批</span>
              <el-icon><Bell /></el-icon>
            </div>
          </template>
          <div class="card-value">{{ stats.pendingCount }}</div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { User, OfficeBuilding, Clock, Bell } from '@element-plus/icons-vue'
import { getEmployeeList } from '@/api/employee'
import { getDepartmentList } from '@/api/department'
import { getAttendanceList } from '@/api/attendance'
import { getPendingLeaves } from '@/api/leave'

const stats = ref({
  employeeCount: 0,
  departmentCount: 0,
  todayAttendance: 0,
  pendingCount: 0
})

onMounted(async () => {
  try {
    const [empRes, deptRes, attRes, leaveRes] = await Promise.all([
      getEmployeeList({ current: 1, size: 1 }),
      getDepartmentList(),
      getAttendanceList({ current: 1, size: 1, month: new Date().toISOString().slice(0, 7) }),
      getPendingLeaves({ current: 1, size: 1 })
    ])

    stats.value.employeeCount = empRes.data?.total || 0
    stats.value.departmentCount = deptRes.data?.length || 0
    stats.value.todayAttendance = attRes.data?.total || 0
    stats.value.pendingCount = leaveRes.data?.total || 0
  } catch (error) {
    console.error('获取统计数据失败', error)
  }
})
</script>

<style scoped>
.dashboard {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-value {
  font-size: 32px;
  font-weight: bold;
  color: #409eff;
  text-align: center;
}
</style>

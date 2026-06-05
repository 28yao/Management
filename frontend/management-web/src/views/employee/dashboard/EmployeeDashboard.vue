<template>
  <div class="employee-dashboard">
    <el-row :gutter="20">
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>今日打卡状态</span>
          </template>
          <div class="clock-status">
            <p>上班：{{ todayRecord?.clockIn || '未打卡' }}</p>
            <p>下班：{{ todayRecord?.clockOut || '未打卡' }}</p>
            <p>状态：<el-tag :type="getStatusType(todayRecord?.status)">{{ getStatusText(todayRecord?.status) }}</el-tag></p>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>本月统计</span>
          </template>
          <div class="statistics">
            <p>正常：{{ stats.normalDays }} 天</p>
            <p>迟到：{{ stats.lateDays }} 次</p>
            <p>早退：{{ stats.earlyDays }} 次</p>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-card class="history-card">
      <template #header>
        <span>最近 7 天打卡记录</span>
      </template>
      <el-table :data="recentRecords" border stripe>
        <el-table-column prop="date" label="日期" width="120" />
        <el-table-column prop="clockIn" label="上班打卡" width="120">
          <template #default="{ row }">
            {{ row.clockIn || '--' }}
          </template>
        </el-table-column>
        <el-table-column prop="clockOut" label="下班打卡" width="120">
          <template #default="{ row }">
            {{ row.clockOut || '--' }}
          </template>
        </el-table-column>
        <el-table-column label="状态">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">{{ getStatusText(row.status) }}</el-tag>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getMyAttendance, getMyStatistics } from '@/api/attendance'

const todayRecord = ref<any>(null)
const recentRecords = ref<any[]>([])
const stats = ref({
  normalDays: 0,
  lateDays: 0,
  earlyDays: 0
})

function getStatusType(status?: number) {
  if (status === undefined || status === null) return 'info'
  const map: Record<number, string> = { 0: 'info', 1: 'success', 2: 'warning', 3: 'warning', 4: 'danger' }
  return map[status] || 'info'
}

function getStatusText(status?: number) {
  if (status === undefined || status === null) return '未打卡'
  const map: Record<number, string> = { 0: '缺勤', 1: '正常', 2: '迟到', 3: '早退', 4: '迟到且早退' }
  return map[status] || '未知'
}

onMounted(async () => {
  try {
    const today = new Date().toISOString().slice(0, 10)
    const month = new Date().toISOString().slice(0, 7)

    const [attRes, statsRes] = await Promise.all([
      getMyAttendance({ current: 1, size: 7, month }),
      getMyStatistics({ month })
    ])

    const records = attRes.data?.records || []
    recentRecords.value = records

    // 查找今日记录
    todayRecord.value = records.find((r: any) => r.date === today) || null

    stats.value = statsRes.data || { normalDays: 0, lateDays: 0, earlyDays: 0 }
  } catch (error) {
    console.error('获取数据失败', error)
  }
})
</script>

<style scoped>
.clock-status, .statistics {
  font-size: 16px;
  line-height: 2.5;
}

.history-card {
  margin-top: 20px;
}
</style>

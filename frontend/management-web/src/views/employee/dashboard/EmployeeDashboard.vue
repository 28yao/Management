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
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getMyAttendance, getMyStatistics } from '@/api/attendance'

const todayRecord = ref<any>(null)
const stats = ref({
  normalDays: 0,
  lateDays: 0,
  earlyDays: 0
})

onMounted(async () => {
  try {
    const today = new Date().toISOString().slice(0, 10)
    const month = new Date().toISOString().slice(0, 7)

    const [attRes, statsRes] = await Promise.all([
      getMyAttendance({ current: 1, size: 1, month }),
      getMyStatistics({ month })
    ])

    if (attRes.data?.records?.length > 0) {
      const record = attRes.data.records.find((r: any) => r.date === today)
      todayRecord.value = record || null
    }

    stats.value = statsRes.data || { normalDays: 0, lateDays: 0, earlyDays: 0 }
  } catch (error) {
    console.error('获取数据失败', error)
  }
})
</script>

<style scoped>
.clock-status, .statistics {
  font-size: 16px;
  line-height: 2;
}
</style>

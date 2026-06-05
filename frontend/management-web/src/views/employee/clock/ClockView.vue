<template>
  <div class="clock-view">
    <el-card>
      <template #header>
        <span>打卡</span>
      </template>

      <div class="clock-container">
        <div class="clock-time">{{ currentTime }}</div>
        <div class="clock-buttons">
          <el-button type="primary" size="large" :loading="clockInLoading" @click="handleClockIn">
            上班打卡
          </el-button>
          <el-button type="success" size="large" :loading="clockOutLoading" @click="handleClockOut">
            下班打卡
          </el-button>
        </div>
        <div class="clock-status">
          <p>今日状态：{{ todayRecord ? getStatusText(todayRecord.status) : '未打卡' }}</p>
          <p>上班时间：{{ todayRecord?.clockIn || '--' }}</p>
          <p>下班时间：{{ todayRecord?.clockOut || '--' }}</p>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { ElMessage } from 'element-plus'
import { clockIn, clockOut, getMyAttendance } from '@/api/attendance'

const currentTime = ref('')
const clockInLoading = ref(false)
const clockOutLoading = ref(false)
const todayRecord = ref<any>(null)
let timer: any = null

function updateTime() {
  currentTime.value = new Date().toLocaleString('zh-CN')
}

function getStatusText(status: number) {
  const map: Record<number, string> = { 0: '缺勤', 1: '正常', 2: '迟到', 3: '早退', 4: '迟到且早退' }
  return map[status] || '未知'
}

async function loadTodayRecord() {
  try {
    const today = new Date().toISOString().slice(0, 10)
    const month = new Date().toISOString().slice(0, 7)
    const res = await getMyAttendance({ current: 1, size: 31, month })
    todayRecord.value = res.data?.records?.find((r: any) => r.date === today) || null
  } catch (error) {
    console.error('获取打卡记录失败', error)
  }
}

async function handleClockIn() {
  clockInLoading.value = true
  try {
    await clockIn()
    ElMessage.success('上班打卡成功')
    loadTodayRecord()
  } catch (error) {
    // 错误已由拦截器处理
  } finally {
    clockInLoading.value = false
  }
}

async function handleClockOut() {
  clockOutLoading.value = true
  try {
    await clockOut()
    ElMessage.success('下班打卡成功')
    loadTodayRecord()
  } catch (error) {
    // 错误已由拦截器处理
  } finally {
    clockOutLoading.value = false
  }
}

onMounted(() => {
  updateTime()
  timer = setInterval(updateTime, 1000)
  loadTodayRecord()
})

onUnmounted(() => {
  if (timer) clearInterval(timer)
})
</script>

<style scoped>
.clock-container {
  text-align: center;
  padding: 40px 0;
}

.clock-time {
  font-size: 32px;
  font-weight: bold;
  color: #409eff;
  margin-bottom: 30px;
}

.clock-buttons {
  margin-bottom: 30px;
}

.clock-buttons .el-button {
  width: 150px;
  height: 60px;
  font-size: 18px;
}

.clock-status {
  font-size: 16px;
  line-height: 2;
}
</style>

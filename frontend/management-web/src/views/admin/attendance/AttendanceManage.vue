<template>
  <div class="attendance-manage">
    <el-card>
      <template #header>
        <span>考勤管理</span>
      </template>

      <el-form :inline="true" class="filter-form">
        <el-form-item label="月份">
          <el-date-picker v-model="filter.month" type="month" value-format="YYYY-MM" placeholder="选择月份" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadData">查询</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="records" border stripe>
        <el-table-column prop="empId" label="员工ID" width="80" />
        <el-table-column prop="date" label="日期" width="120" />
        <el-table-column prop="clockIn" label="上班打卡" width="100" />
        <el-table-column prop="clockOut" label="下班打卡" width="100" />
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">{{ getStatusText(row.status) }}</el-tag>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="pagination.current"
        v-model:page-size="pagination.size"
        :total="pagination.total"
        layout="total, prev, pager, next"
        @current-change="loadData"
      />
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getAttendanceList } from '@/api/attendance'

const records = ref<any[]>([])
const filter = ref({ month: '' })
const pagination = ref({ current: 1, size: 10, total: 0 })

function getStatusType(status: number) {
  const map: Record<number, string> = { 0: 'info', 1: 'success', 2: 'warning', 3: 'warning', 4: 'danger' }
  return map[status] || 'info'
}

function getStatusText(status: number) {
  const map: Record<number, string> = { 0: '缺勤', 1: '正常', 2: '迟到', 3: '早退', 4: '迟到且早退' }
  return map[status] || '未知'
}

async function loadData() {
  try {
    const res = await getAttendanceList({
      current: pagination.value.current,
      size: pagination.value.size,
      month: filter.value.month
    })
    records.value = res.data?.records || []
    pagination.value.total = res.data?.total || 0
  } catch (error) {
    console.error('获取考勤记录失败', error)
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.filter-form {
  margin-bottom: 20px;
}

.el-pagination {
  margin-top: 20px;
  justify-content: flex-end;
}
</style>

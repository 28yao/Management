<template>
  <div class="leave-history">
    <el-card>
      <template #header>
        <span>请假记录</span>
      </template>

      <el-table :data="leaves" border stripe>
        <el-table-column label="假期类型" width="80">
          <template #default="{ row }">
            {{ row.type === 1 ? '事假' : '病假' }}
          </template>
        </el-table-column>
        <el-table-column prop="startDate" label="开始日期" width="120" />
        <el-table-column prop="endDate" label="结束日期" width="120" />
        <el-table-column prop="reason" label="请假事由" />
        <el-table-column label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 0 ? 'warning' : row.status === 1 ? 'success' : 'danger'">
              {{ row.status === 0 ? '待审批' : row.status === 1 ? '已通过' : '已驳回' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createdAt" label="申请时间" width="180" />
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
import { getMyLeaves } from '@/api/leave'

const leaves = ref<any[]>([])
const pagination = ref({ current: 1, size: 10, total: 0 })

async function loadData() {
  try {
    const res = await getMyLeaves({
      current: pagination.value.current,
      size: pagination.value.size
    })
    leaves.value = res.data?.records || []
    pagination.value.total = res.data?.total || 0
  } catch (error) {
    console.error('获取请假记录失败', error)
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.el-pagination {
  margin-top: 20px;
  justify-content: flex-end;
}
</style>

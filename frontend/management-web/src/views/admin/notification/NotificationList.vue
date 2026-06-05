<template>
  <div class="notification-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>通知管理</span>
          <el-button @click="handleMarkAllRead">全部已读</el-button>
        </div>
      </template>

      <el-table :data="notifications" border stripe>
        <el-table-column prop="content" label="通知内容" />
        <el-table-column label="类型" width="100">
          <template #default="{ row }">
            {{ getTypeText(row.type) }}
          </template>
        </el-table-column>
        <el-table-column label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.isRead === 0 ? 'warning' : 'success'">
              {{ row.isRead === 0 ? '未读' : '已读' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createdAt" label="时间" width="180" />
        <el-table-column label="操作" width="100">
          <template #default="{ row }">
            <el-button v-if="row.isRead === 0" size="small" @click="handleMarkRead(row)">标记已读</el-button>
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
import { ElMessage } from 'element-plus'
import { getMyNotifications, markAsRead, markAllAsRead } from '@/api/notification'

const notifications = ref<any[]>([])
const pagination = ref({ current: 1, size: 10, total: 0 })

function getTypeText(type: number) {
  const map: Record<number, string> = {
    1: '请假申请', 2: '请假通过', 3: '请假驳回',
    4: '补卡申请', 5: '补卡通过', 6: '补卡驳回'
  }
  return map[type] || '未知'
}

async function loadData() {
  try {
    const res = await getMyNotifications({
      current: pagination.value.current,
      size: pagination.value.size
    })
    notifications.value = res.data?.records || []
    pagination.value.total = res.data?.total || 0
  } catch (error) {
    console.error('获取通知列表失败', error)
  }
}

async function handleMarkRead(row: any) {
  try {
    await markAsRead(row.id)
    ElMessage.success('已标记为已读')
    loadData()
  } catch (error) {
    // 错误已由拦截器处理
  }
}

async function handleMarkAllRead() {
  try {
    await markAllAsRead()
    ElMessage.success('已全部标记为已读')
    loadData()
  } catch (error) {
    // 错误已由拦截器处理
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.el-pagination {
  margin-top: 20px;
  justify-content: flex-end;
}
</style>

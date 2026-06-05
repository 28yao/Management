<template>
  <div class="leave-approval">
    <el-card>
      <template #header>
        <span>请假审批</span>
      </template>

      <el-table :data="leaves" border stripe>
        <el-table-column prop="empId" label="员工ID" width="80" />
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
        <el-table-column label="操作" width="180">
          <template #default="{ row }">
            <template v-if="row.status === 0">
              <el-button size="small" type="success" @click="handleApprove(row)">通过</el-button>
              <el-button size="small" type="danger" @click="handleReject(row)">驳回</el-button>
            </template>
            <span v-else>-</span>
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

    <!-- 驳回原因对话框 -->
    <el-dialog v-model="rejectDialogVisible" title="驳回原因" width="400px">
      <el-input v-model="rejectReason" type="textarea" placeholder="请输入驳回原因" />
      <template #footer>
        <el-button @click="rejectDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmReject">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getLeaveList, approveLeave, rejectLeave } from '@/api/leave'

const leaves = ref<any[]>([])
const pagination = ref({ current: 1, size: 10, total: 0 })
const rejectDialogVisible = ref(false)
const rejectReason = ref('')
const currentLeaveId = ref<number | null>(null)

async function loadData() {
  try {
    const res = await getLeaveList({
      current: pagination.value.current,
      size: pagination.value.size
    })
    leaves.value = res.data?.records || []
    pagination.value.total = res.data?.total || 0
  } catch (error) {
    console.error('获取请假列表失败', error)
  }
}

async function handleApprove(row: any) {
  try {
    await approveLeave(row.id)
    ElMessage.success('审批通过')
    loadData()
  } catch (error) {
    // 错误已由拦截器处理
  }
}

function handleReject(row: any) {
  currentLeaveId.value = row.id
  rejectReason.value = ''
  rejectDialogVisible.value = true
}

async function confirmReject() {
  if (!rejectReason.value) {
    ElMessage.warning('请输入驳回原因')
    return
  }
  try {
    await rejectLeave(currentLeaveId.value!, { rejectReason: rejectReason.value })
    ElMessage.success('已驳回')
    rejectDialogVisible.value = false
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
.el-pagination {
  margin-top: 20px;
  justify-content: flex-end;
}
</style>

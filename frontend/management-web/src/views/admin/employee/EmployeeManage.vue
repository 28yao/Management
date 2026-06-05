<template>
  <div class="employee-manage">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>员工管理</span>
          <el-button type="primary" @click="handleAdd">新增员工</el-button>
        </div>
      </template>

      <!-- 筛选条件 -->
      <el-form :inline="true" class="filter-form">
        <el-form-item label="部门">
          <el-select v-model="filter.deptId" placeholder="全部部门" clearable>
            <el-option v-for="dept in departments" :key="dept.id" :label="dept.name" :value="dept.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="关键词">
          <el-input v-model="filter.keyword" placeholder="姓名/工号/账号" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadEmployees">查询</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="employees" border stripe>
        <el-table-column prop="empNo" label="工号" width="100" />
        <el-table-column prop="name" label="姓名" width="100" />
        <el-table-column prop="phone" label="手机号" width="120" />
        <el-table-column prop="position" label="职位" width="120" />
        <el-table-column label="部门" width="120">
          <template #default="{ row }">
            {{ getDeptName(row.deptId) }}
          </template>
        </el-table-column>
        <el-table-column label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">
              {{ row.status === 1 ? '在职' : '离职' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="hireDate" label="入职日期" width="120" />
        <el-table-column label="操作" width="250">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" @click="handleDetail(row)">详情</el-button>
            <el-button size="small" type="warning" @click="handleResetPassword(row)">重置密码</el-button>
            <el-button v-if="row.status === 1" size="small" type="danger" @click="handleResign(row)">离职</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="pagination.current"
        v-model:page-size="pagination.size"
        :total="pagination.total"
        layout="total, prev, pager, next"
        @current-change="loadEmployees"
      />
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getEmployeeList, resignEmployee, resetPassword } from '@/api/employee'
import { getDepartmentList } from '@/api/department'

const router = useRouter()
const employees = ref<any[]>([])
const departments = ref<any[]>([])
const filter = ref({ deptId: null, keyword: '' })
const pagination = ref({ current: 1, size: 10, total: 0 })

function getDeptName(deptId: number) {
  const dept = departments.value.find(d => d.id === deptId)
  return dept ? dept.name : '-'
}

async function loadEmployees() {
  try {
    const res = await getEmployeeList({
      current: pagination.value.current,
      size: pagination.value.size,
      ...filter.value
    })
    employees.value = res.data?.records || []
    pagination.value.total = res.data?.total || 0
  } catch (error) {
    console.error('获取员工列表失败', error)
  }
}

async function loadDepartments() {
  try {
    const res = await getDepartmentList()
    departments.value = res.data || []
  } catch (error) {
    console.error('获取部门列表失败', error)
  }
}

function handleAdd() {
  router.push('/admin/employees/new')
}

function handleEdit(row: any) {
  router.push(`/admin/employees/${row.id}`)
}

function handleDetail(row: any) {
  router.push(`/admin/employees/${row.id}`)
}

async function handleResetPassword(row: any) {
  try {
    await ElMessageBox.confirm(`确定重置 ${row.name} 的密码吗？`, '提示', { type: 'warning' })
    const res = await resetPassword(row.id)
    ElMessage.success(res.message)
  } catch (error) {
    // 取消
  }
}

async function handleResign(row: any) {
  try {
    await ElMessageBox.confirm(`确定将 ${row.name} 标记为离职吗？`, '提示', { type: 'warning' })
    await resignEmployee(row.id)
    ElMessage.success('离职处理成功')
    loadEmployees()
  } catch (error) {
    // 取消
  }
}

onMounted(() => {
  loadDepartments()
  loadEmployees()
})
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.filter-form {
  margin-bottom: 20px;
}

.el-pagination {
  margin-top: 20px;
  justify-content: flex-end;
}
</style>

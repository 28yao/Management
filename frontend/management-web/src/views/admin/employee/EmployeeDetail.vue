<template>
  <div class="employee-detail">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>员工详情</span>
          <el-button @click="$router.back()">返回</el-button>
        </div>
      </template>

      <el-form :model="employee" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12" v-if="!isNew">
            <el-form-item label="工号">
              <el-input v-model="employee.empNo" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="姓名">
              <el-input v-model="employee.name" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号">
              <el-input v-model="employee.phone" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱">
              <el-input v-model="employee.email" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="入职日期">
              <el-date-picker v-model="employee.hireDate" type="date" value-format="YYYY-MM-DD" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="职位">
              <el-select v-model="employee.position" placeholder="请选择职位">
                <el-option v-for="pos in positions" :key="pos.id" :label="pos.name" :value="pos.name" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属部门">
              <el-select v-model="employee.deptId">
                <el-option v-for="dept in departments" :key="dept.id" :label="dept.name" :value="dept.id" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="登录账号">
              <el-input v-model="employee.account" />
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="isNew">
            <el-form-item label="初始密码">
              <el-input v-model="employee.password" type="password" placeholder="请输入初始密码" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item>
          <el-button type="primary" @click="handleSave">保存</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getEmployeeDetail, updateEmployee, createEmployee } from '@/api/employee'
import { getDepartmentList } from '@/api/department'
import { getPositionList } from '@/api/position'

const route = useRoute()
const router = useRouter()
const departments = ref<any[]>([])
const positions = ref<any[]>([])
const employee = ref<any>({
  empNo: '',
  name: '',
  phone: '',
  email: '',
  hireDate: '',
  position: '',
  deptId: null,
  account: '',
  password: ''
})

const isNew = route.params.id === 'new'

async function loadEmployee() {
  if (isNew) return
  try {
    const res = await getEmployeeDetail(Number(route.params.id))
    employee.value = res.data
  } catch (error) {
    console.error('获取员工详情失败', error)
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

async function loadPositions() {
  try {
    const res = await getPositionList()
    positions.value = res.data || []
  } catch (error) {
    console.error('获取职位列表失败', error)
  }
}

async function handleSave() {
  try {
    if (isNew) {
      await createEmployee(employee.value)
      ElMessage.success('新增成功')
    } else {
      await updateEmployee(Number(route.params.id), employee.value)
      ElMessage.success('保存成功')
    }
    router.push('/admin/employees')
  } catch (error) {
    // 错误已由拦截器处理
  }
}

onMounted(() => {
  loadDepartments()
  loadPositions()
  loadEmployee()
})
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>

<template>
  <div class="attendance-statistics">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>考勤统计</span>
          <div>
            <el-select v-model="filter.deptId" placeholder="全部部门" clearable style="width: 150px; margin-right: 10px;">
              <el-option v-for="dept in departments" :key="dept.id" :label="dept.name" :value="dept.id" />
            </el-select>
            <el-date-picker v-model="filter.month" type="month" value-format="YYYY-MM" placeholder="选择月份" style="width: 150px; margin-right: 10px;" />
            <el-button type="primary" @click="loadData">查询</el-button>
          </div>
        </div>
      </template>

      <el-table :data="statistics" border stripe>
        <el-table-column prop="empNo" label="工号" width="100" />
        <el-table-column prop="name" label="姓名" width="100" />
        <el-table-column label="部门" width="120">
          <template #default="{ row }">
            {{ getDeptName(row.deptId) }}
          </template>
        </el-table-column>
        <el-table-column prop="totalDays" label="应出勤" width="80" />
        <el-table-column prop="actualDays" label="实出勤" width="80" />
        <el-table-column prop="normalDays" label="正常" width="80">
          <template #default="{ row }">
            <el-tag type="success">{{ row.normalDays }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="lateDays" label="迟到" width="80">
          <template #default="{ row }">
            <el-tag :type="row.lateDays > 0 ? 'warning' : 'info'">{{ row.lateDays }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="earlyDays" label="早退" width="80">
          <template #default="{ row }">
            <el-tag :type="row.earlyDays > 0 ? 'warning' : 'info'">{{ row.earlyDays }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="absentDays" label="缺勤" width="80">
          <template #default="{ row }">
            <el-tag :type="row.absentDays > 0 ? 'danger' : 'info'">{{ row.absentDays }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="leaveDays" label="请假" width="80" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getEmployeeList } from '@/api/employee'
import { getAttendanceList } from '@/api/attendance'
import { getDepartmentList } from '@/api/department'

const departments = ref<any[]>([])
const employees = ref<any[]>([])
const statistics = ref<any[]>([])
const filter = ref({ deptId: null, month: '' })

function getDeptName(deptId: number) {
  const dept = departments.value.find(d => d.id === deptId)
  return dept ? dept.name : '-'
}

/**
 * 计算指定月份的工作日天数
 */
function getWorkDaysInMonth(month: string): number {
  const [year, monthNum] = month.split('-').map(Number)
  const daysInMonth = new Date(year, monthNum, 0).getDate()
  let count = 0

  for (let day = 1; day <= daysInMonth; day++) {
    const date = new Date(year, monthNum - 1, day)
    const dayOfWeek = date.getDay()
    // 排除周六(6)和周日(0)
    if (dayOfWeek !== 0 && dayOfWeek !== 6) {
      count++
    }
  }

  return count
}

async function loadDepartments() {
  try {
    const res = await getDepartmentList()
    departments.value = res.data || []
  } catch (error) {
    console.error('获取部门列表失败', error)
  }
}

async function loadData() {
  try {
    const month = filter.value.month || new Date().toISOString().slice(0, 7)

    // 获取员工列表
    const empRes = await getEmployeeList({
      current: 1,
      size: 1000,
      deptId: filter.value.deptId
    })
    employees.value = empRes.data?.records || []

    // 获取考勤记录
    const attRes = await getAttendanceList({
      current: 1,
      size: 10000,
      month: month
    })
    const attendances = attRes.data?.records || []

    // 计算每个员工的统计数据
    const stats = employees.value.map(emp => {
      const empAttendances = attendances.filter((a: any) => a.empId === emp.id)

      // 计算应出勤天数（工作日）
      const totalDays = getWorkDaysInMonth(month)

      // 统计考勤状态
      const normalDays = empAttendances.filter((a: any) => a.status === 1).length
      const lateDays = empAttendances.filter((a: any) => a.status === 2 || a.status === 4).length
      const earlyDays = empAttendances.filter((a: any) => a.status === 3 || a.status === 4).length
      const actualDays = empAttendances.length
      const absentDays = Math.max(0, totalDays - actualDays)

      return {
        ...emp,
        totalDays,
        actualDays,
        normalDays,
        lateDays,
        earlyDays,
        absentDays,
        leaveDays: 0
      }
    })

    statistics.value = stats
  } catch (error) {
    console.error('获取统计数据失败', error)
  }
}

onMounted(() => {
  loadDepartments()
  loadData()
})
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>

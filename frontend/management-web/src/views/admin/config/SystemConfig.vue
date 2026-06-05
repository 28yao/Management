<template>
  <div class="system-config">
    <el-card>
      <template #header>
        <span>系统配置</span>
      </template>

      <el-form :model="config" label-width="100px">
        <el-form-item label="上班时间">
          <el-time-picker v-model="config.workStartTime" format="HH:mm" value-format="HH:mm" />
        </el-form-item>
        <el-form-item label="下班时间">
          <el-time-picker v-model="config.workEndTime" format="HH:mm" value-format="HH:mm" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSave">保存</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getAttendanceConfig, updateAttendanceConfig } from '@/api/config'

const config = ref({
  workStartTime: '09:00',
  workEndTime: '18:00'
})

async function loadConfig() {
  try {
    const res = await getAttendanceConfig()
    config.value = res.data
  } catch (error) {
    console.error('获取配置失败', error)
  }
}

async function handleSave() {
  try {
    await updateAttendanceConfig(config.value)
    ElMessage.success('保存成功')
  } catch (error) {
    // 错误已由拦截器处理
  }
}

onMounted(() => {
  loadConfig()
})
</script>

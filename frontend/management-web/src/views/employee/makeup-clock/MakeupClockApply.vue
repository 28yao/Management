<template>
  <div class="makeup-clock-apply">
    <el-card>
      <template #header>
        <span>补卡申请</span>
      </template>

      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="补卡日期" prop="date">
          <el-date-picker v-model="form.date" type="date" value-format="YYYY-MM-DD" placeholder="选择补卡日期" />
        </el-form-item>
        <el-form-item label="打卡时段" prop="period">
          <el-radio-group v-model="form.period">
            <el-radio :label="1">上班</el-radio>
            <el-radio :label="2">下班</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="补卡原因" prop="reason">
          <el-input v-model="form.reason" type="textarea" rows="4" placeholder="请输入补卡原因" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="loading" @click="handleSubmit">提交申请</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { ElMessage, type FormInstance, type FormRules } from 'element-plus'
import { createMakeupClock } from '@/api/makeup-clock'

const formRef = ref<FormInstance>()
const loading = ref(false)

const form = ref({
  date: '',
  period: 1,
  reason: ''
})

const rules: FormRules = {
  date: [{ required: true, message: '请选择补卡日期', trigger: 'change' }],
  period: [{ required: true, message: '请选择打卡时段', trigger: 'change' }],
  reason: [{ required: true, message: '请输入补卡原因', trigger: 'blur' }]
}

async function handleSubmit() {
  const valid = await formRef.value?.validate().catch(() => false)
  if (!valid) return

  loading.value = true
  try {
    await createMakeupClock(form.value)
    ElMessage.success('申请提交成功')
    form.value = { date: '', period: 1, reason: '' }
  } catch (error) {
    // 错误已由拦截器处理
  } finally {
    loading.value = false
  }
}
</script>

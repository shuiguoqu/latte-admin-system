<template>
  <div class="page-container">
    <div class="page-header">
      <el-button
        icon="ArrowLeft"
        @click="$router.back()"
      >
        返回
      </el-button>
      <h1>{{ isEdit ? '编辑用户' : '新增用户' }}</h1>
    </div>

    <!-- 权限不足提示 -->
    <el-alert
      v-if="permissionDenied"
      title="权限不足"
      description="您没有权限编辑此用户的信息"
      type="error"
      show-icon
      :closable="false"
      style="margin-bottom: 16px"
    />

    <el-card
      v-if="!permissionDenied"
      class="form-card"
    >
      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="100px"
        label-position="right"
        @submit.prevent="handleSubmit"
      >
        <el-form-item
          label="用户名"
          prop="username"
        >
          <el-input
            v-model="form.username"
            placeholder="请输入用户名"
            :disabled="isEdit"
          />
        </el-form-item>
        <el-form-item
          v-if="!isEdit"
          label="密码"
          prop="password"
        >
          <el-input
            v-model="form.password"
            type="password"
            placeholder="请输入密码"
            show-password
          />
        </el-form-item>
        <el-form-item
          label="真实姓名"
          prop="realName"
        >
          <el-input
            v-model="form.realName"
            placeholder="请输入真实姓名"
          />
        </el-form-item>
        <el-form-item
          label="邮箱"
          prop="email"
        >
          <el-input
            v-model="form.email"
            placeholder="请输入邮箱"
          />
        </el-form-item>
        <el-form-item
          label="手机号"
          prop="phone"
        >
          <el-input
            v-model="form.phone"
            placeholder="请输入手机号"
          />
        </el-form-item>
        <el-form-item
          v-if="isAdmin"
          label="状态"
          prop="status"
        >
          <el-switch
            v-model="form.status"
            :active-value="1"
            :inactive-value="0"
            active-text="启用"
            inactive-text="禁用"
          />
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            :loading="submitting"
            native-type="submit"
          >
            {{ isEdit ? '保存修改' : '创建用户' }}
          </el-button>
          <el-button @click="$router.back()">
            取消
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, type FormInstance } from 'element-plus'
import { getUserById, createUser, updateUser } from '@/api/user'

const route = useRoute()
const router = useRouter()
const formRef = ref<FormInstance>()
const submitting = ref(false)
const permissionDenied = ref(false)

const isEdit = computed(() => !!route.params.id)
const isAdmin = computed(() => localStorage.getItem('role') === 'ADMIN')

const form = reactive({
  username: '',
  password: '',
  realName: '',
  email: '',
  phone: '',
  status: 1
})

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 50, message: '用户名长度 3-50 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码不少于 6 个字符', trigger: 'blur' }
  ],
  realName: [{ required: true, message: '请输入真实姓名', trigger: 'blur' }],
  email: [{ type: 'email' as const, message: '请输入正确的邮箱格式', trigger: 'blur' }],
  phone: [{ pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }]
}

/** 编辑模式加载用户数据 */
onMounted(async () => {
  if (isEdit.value) {
    // 普通用户只能编辑自己
    const currentUserId = localStorage.getItem('userId')
    const editId = String(route.params.id)
    if (!isAdmin.value && currentUserId !== editId) {
      permissionDenied.value = true
      ElMessage.error('权限不足，无法编辑其他用户')
      return
    }

    try {
      const res: any = await getUserById(Number(route.params.id))
      Object.assign(form, res.data)
    } catch (err: any) {
      if (err?.response?.status === 403 || err?.message?.includes('403') || err?.message?.includes('权限不足')) {
        permissionDenied.value = true
        ElMessage.error('权限不足，无法编辑此用户')
      } else {
        ElMessage.error('加载用户数据失败')
        router.back()
      }
    }
  } else {
    // 新增用户 - 只有管理员可以
    if (!isAdmin.value) {
      permissionDenied.value = true
      ElMessage.error('权限不足，仅管理员可创建用户')
    }
  }
})

/** 提交表单 */
const handleSubmit = async () => {
  const valid = await formRef.value?.validate().catch(() => false)
  if (!valid) return

  submitting.value = true
  try {
    if (isEdit.value) {
      await updateUser(Number(route.params.id), form)
      ElMessage.success('修改成功')
    } else {
      await createUser(form)
      ElMessage.success('创建成功')
    }
    router.push('/users')
  } catch {
    // 错误已在拦截器中处理
  } finally {
    submitting.value = false
  }
}
</script>

<style scoped>
.page-container {
  padding: var(--spacing-lg);
  max-width: 800px;
  margin: 0 auto;
}

.page-header {
  display: flex;
  align-items: center;
  gap: var(--spacing-md);
  margin-bottom: var(--spacing-lg);
}

.page-header h1 {
  font-size: 24px;
  color: var(--text-primary);
}

.form-card {
  padding: var(--spacing-lg);
}
</style>

<template>
  <div class="page-container">
    <!-- 顶部用户栏：显示当前登录用户 + 退出按钮 -->
    <div class="top-bar">
      <span class="current-user">
        <el-icon><User /></el-icon>
        {{ currentUser }}
        <el-tag
          size="small"
          :type="isAdmin ? 'danger' : 'info'"
          style="margin-left: 4px"
        >
          {{ isAdmin ? '管理员' : '普通用户' }}
        </el-tag>
      </span>
      <el-button
        id="btn-logout"
        type="danger"
        plain
        size="small"
        @click="handleLogout"
      >
        退出登录
      </el-button>
    </div>

    <div class="page-header">
      <h1>{{ isAdmin ? '用户管理' : '我的信息' }}</h1>
      <p>{{ isAdmin ? '管理系统中的所有用户信息' : '查看和编辑您的个人信息' }}</p>
    </div>

    <!-- 搜索栏与操作按钮 -->
    <el-card class="search-card">
      <div class="toolbar">
        <el-input
          v-if="isAdmin"
          v-model="keyword"
          placeholder="搜索用户名、姓名、邮箱、手机号..."
          prefix-icon="Search"
          clearable
          style="width: 320px"
          @keyup.enter="fetchUsers"
          @clear="fetchUsers"
        />
        <span v-else />
        <el-button
          v-if="isAdmin"
          type="primary"
          icon="Plus"
          @click="$router.push('/users/create')"
        >
          新增用户
        </el-button>
      </div>
    </el-card>

    <!-- 用户表格 -->
    <el-card class="table-card">
      <el-table
        v-loading="loading"
        :data="userList"
        stripe
        style="width: 100%"
        @row-click="handleRowClick"
      >
        <el-table-column
          prop="id"
          label="ID"
          width="80"
        />
        <el-table-column
          prop="username"
          label="用户名"
          width="120"
        />
        <el-table-column
          prop="realName"
          label="姓名"
          width="120"
        />
        <el-table-column
          prop="email"
          label="邮箱"
        />
        <el-table-column
          prop="phone"
          label="手机号"
          width="140"
        />
        <el-table-column
          prop="status"
          label="状态"
          width="100"
        >
          <template #default="{ row }">
            <el-tag
              :type="row.status === 1 ? 'success' : 'danger'"
              size="small"
            >
              {{ row.status === 1 ? '启用' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          width="200"
          fixed="right"
        >
          <template #default="{ row }">
            <!-- 管理员可编辑所有；普通用户仅可编辑自己 -->
            <el-button
              v-if="isAdmin || row.username === currentUsername"
              size="small"
              type="primary"
              link
              @click.stop="handleEdit(row)"
            >
              编辑
            </el-button>
            <!-- 仅管理员可见删除按钮 -->
            <el-button
              v-if="isAdmin"
              size="small"
              type="danger"
              link
              @click.stop="handleDelete(row)"
            >
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页（仅管理员展示完整分页） -->
      <div
        v-if="isAdmin"
        class="pagination-wrapper"
      >
        <el-pagination
          v-model:current-page="pagination.current"
          v-model:page-size="pagination.size"
          :total="pagination.total"
          :page-sizes="[5, 10, 20, 50]"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="fetchUsers"
          @current-change="fetchUsers"
        />
      </div>
    </el-card>

    <!-- 用户详情抽屉 -->
    <UserDetailDrawer
      v-model="drawerVisible"
      :user-id="selectedUserId"
      @close="drawerVisible = false"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { User } from '@element-plus/icons-vue'
import { getUsers, deleteUser } from '@/api/user'
import UserDetailDrawer from '@/components/UserDetailDrawer.vue'

const router = useRouter()
const loading = ref(false)
const keyword = ref('')
const userList = ref<any[]>([])
const drawerVisible = ref(false)
const selectedUserId = ref<number | null>(null)

/** 是否管理员 */
const isAdmin = computed(() => localStorage.getItem('role') === 'ADMIN')

/** 当前登录用户名 */
const currentUsername = computed(() => localStorage.getItem('username') || '')

/** 当前登录用户显示名 */
const currentUser = computed(() => {
  return localStorage.getItem('realName') || localStorage.getItem('username') || '未知用户'
})

const pagination = reactive({
  current: 1,
  size: 10,
  total: 0
})

/** 获取用户列表 */
const fetchUsers = async () => {
  loading.value = true
  try {
    const res: any = await getUsers({
      current: pagination.current,
      size: pagination.size,
      keyword: keyword.value || undefined
    })
    userList.value = res.data.records
    pagination.total = res.data.total
  } catch {
    // 错误已在拦截器中处理
  } finally {
    loading.value = false
  }
}

/** 点击行查看详情 */
const handleRowClick = (row: any) => {
  // 普通用户只能查看自己的详情
  if (!isAdmin.value && row.username !== currentUsername.value) return
  selectedUserId.value = row.id
  drawerVisible.value = true
}

/** 编辑用户 */
const handleEdit = (row: any) => {
  router.push(`/users/${row.id}/edit`)
}

/** 删除用户 */
const handleDelete = async (row: any) => {
  try {
    await ElMessageBox.confirm(`确定要删除用户「${row.realName || row.username}」吗？`, '删除确认', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await deleteUser(row.id)
    ElMessage.success('删除成功')
    fetchUsers()
  } catch {
    // 用户取消操作
  }
}

/** 退出登录（T001 + T002） */
const handleLogout = () => {
  localStorage.removeItem('token')
  localStorage.removeItem('username')
  localStorage.removeItem('realName')
  localStorage.removeItem('role')
  localStorage.removeItem('userId')
  ElMessage.success('已退出登录')
  router.push('/login')
}

onMounted(() => {
  fetchUsers()
})
</script>

<style scoped>
.page-container {
  padding: var(--spacing-lg);
  max-width: 1200px;
  margin: 0 auto;
}

.top-bar {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  gap: var(--spacing-md);
  margin-bottom: var(--spacing-md);
  padding: var(--spacing-sm) var(--spacing-md);
  background: var(--bg-surface);
  border-radius: var(--radius-md);
  border: 1px solid var(--border-color);
  box-shadow: var(--shadow-sm);
}

.current-user {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 14px;
  color: var(--text-primary);
  font-weight: 500;
}

.page-header {
  margin-bottom: var(--spacing-lg);
}

.page-header h1 {
  font-size: 28px;
  color: var(--text-primary);
  margin-bottom: 4px;
}

.page-header p {
  font-size: 14px;
  color: var(--text-secondary);
}

.search-card {
  margin-bottom: var(--spacing-md);
}

.toolbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.table-card {
  margin-bottom: var(--spacing-md);
}

.pagination-wrapper {
  display: flex;
  justify-content: flex-end;
  margin-top: var(--spacing-md);
}
</style>

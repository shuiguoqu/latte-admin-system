<template>
  <el-drawer
    :model-value="modelValue"
    title="用户详情"
    size="480px"
    @close="emit('close')"
  >
    <template v-if="loading">
      <div class="loading-wrapper">
        <el-skeleton
          :rows="6"
          animated
        />
      </div>
    </template>
    <template v-else-if="userData">
      <!-- 用户基本信息 -->
      <el-descriptions
        :column="1"
        border
        class="detail-section"
      >
        <el-descriptions-item label="ID">
          {{ userData.user?.id }}
        </el-descriptions-item>
        <el-descriptions-item label="用户名">
          {{ userData.user?.username }}
        </el-descriptions-item>
        <el-descriptions-item label="姓名">
          {{ userData.user?.realName }}
        </el-descriptions-item>
        <el-descriptions-item label="邮箱">
          {{ userData.user?.email }}
        </el-descriptions-item>
        <el-descriptions-item label="手机号">
          {{ userData.user?.phone }}
        </el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag
            :type="userData.user?.status === 1 ? 'success' : 'danger'"
            size="small"
          >
            {{ userData.user?.status === 1 ? '启用' : '禁用' }}
          </el-tag>
        </el-descriptions-item>
      </el-descriptions>

      <!-- 关联订单 -->
      <div class="orders-section">
        <h3>关联订单（{{ userData.orders?.length || 0 }}）</h3>
        <el-table
          v-if="userData.orders?.length"
          :data="userData.orders"
          stripe
          size="small"
        >
          <el-table-column
            prop="orderNo"
            label="订单号"
            width="130"
          />
          <el-table-column
            prop="productName"
            label="商品"
          />
          <el-table-column
            prop="amount"
            label="金额"
            width="90"
          >
            <template #default="{ row }">
              ¥{{ row.amount }}
            </template>
          </el-table-column>
          <el-table-column
            prop="status"
            label="状态"
            width="80"
          >
            <template #default="{ row }">
              <el-tag
                :type="orderStatusType(row.status)"
                size="small"
              >
                {{ orderStatusText(row.status) }}
              </el-tag>
            </template>
          </el-table-column>
        </el-table>
        <el-empty
          v-else
          description="暂无订单"
          :image-size="60"
        />
      </div>
    </template>
  </el-drawer>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import { getUserWithOrders } from '@/api/user'

const props = defineProps<{
  modelValue: boolean
  userId: number | null
}>()

const emit = defineEmits<{
  (e: 'close'): void
  (e: 'update:modelValue', value: boolean): void
}>()

const loading = ref(false)
const userData = ref<any>(null)

/** 订单状态映射 */
const orderStatusText = (status: number) => {
  const map: Record<number, string> = { 0: '待支付', 1: '已支付', 2: '已发货', 3: '已完成', 4: '已取消' }
  return map[status] || '未知'
}

const orderStatusType = (status: number) => {
  const map: Record<number, string> = { 0: 'warning', 1: 'primary', 2: 'info', 3: 'success', 4: 'danger' }
  return map[status] || 'info'
}

/** 监听 userId 变化加载数据 */
watch(() => props.userId, async (newId) => {
  if (newId && props.modelValue) {
    loading.value = true
    try {
      const res: any = await getUserWithOrders(newId)
      userData.value = res.data
    } catch {
      userData.value = null
    } finally {
      loading.value = false
    }
  }
}, { immediate: true })
</script>

<style scoped>
.loading-wrapper {
  padding: var(--spacing-lg);
}

.detail-section {
  margin-bottom: var(--spacing-lg);
}

.orders-section {
  margin-top: var(--spacing-lg);
}

.orders-section h3 {
  font-size: 16px;
  color: var(--text-primary);
  margin-bottom: var(--spacing-sm);
}
</style>

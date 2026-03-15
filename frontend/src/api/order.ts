import request from './request'

/** 订单分页查询 */
export function getOrders(params: { current: number; size: number; keyword?: string; minAmount?: number; maxAmount?: number }) {
  return request.get('/orders', { params })
}

/** 根据用户ID查询订单 */
export function getOrdersByUserId(userId: number) {
  return request.get(`/orders/user/${userId}`)
}

/** 新增订单 */
export function createOrder(data: any) {
  return request.post('/orders', data)
}

/** 更新订单 */
export function updateOrder(id: number, data: any) {
  return request.put(`/orders/${id}`, data)
}

/** 删除订单 */
export function deleteOrder(id: number) {
  return request.delete(`/orders/${id}`)
}

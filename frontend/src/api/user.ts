import request from './request'

/** 用户分页查询 */
export function getUsers(params: { current: number; size: number; keyword?: string }) {
  return request.get('/users', { params })
}

/** 根据ID查询用户 */
export function getUserById(id: number) {
  return request.get(`/users/${id}`)
}

/** 新增用户 */
export function createUser(data: any) {
  return request.post('/users', data)
}

/** 更新用户 */
export function updateUser(id: number, data: any) {
  return request.put(`/users/${id}`, data)
}

/** 删除用户 */
export function deleteUser(id: number) {
  return request.delete(`/users/${id}`)
}

/** 查询用户及关联订单 */
export function getUserWithOrders(id: number) {
  return request.get(`/users/${id}/orders`)
}

/** 登录 */
export function login(data: { username: string; password: string }) {
  return request.post('/auth/login', data)
}

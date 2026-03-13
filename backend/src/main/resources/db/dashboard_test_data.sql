-- ============================================================
-- 数据看板测试数据
-- 覆盖场景：
-- 1. 用户注册：今日、本周、本月、近12个月每月都有注册
-- 2. 订单状态：各状态都有数据
-- 3. 金额TOP10：有明显的金额差异
-- 4. 系统总览：足够的用户和订单数量
-- ============================================================

USE `user_management`;

-- 清理旧测试数据（保留admin等基础账号）
DELETE FROM t_order WHERE id > 10;
DELETE FROM t_user WHERE id > 5;

-- ============================================================
-- 用户测试数据 - 分布在近12个月各时间段
-- 密码统一为: user123 -> $2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6
-- ============================================================

-- 今日注册用户 (3人)
INSERT INTO t_user (username, password, real_name, email, phone, role, status, create_time) VALUES
('user_today_1', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '今日用户一', 'today1@test.com', '13900000001', 'USER', 1, NOW()),
('user_today_2', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '今日用户二', 'today2@test.com', '13900000002', 'USER', 1, NOW()),
('user_today_3', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '今日用户三', 'today3@test.com', '13900000003', 'USER', 1, NOW());

-- 本周注册用户（非今日，共4人）
INSERT INTO t_user (username, password, real_name, email, phone, role, status, create_time) VALUES
('user_week_1', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '周用户一', 'week1@test.com', '13900000004', 'USER', 1, DATE_SUB(NOW(), INTERVAL 2 DAY)),
('user_week_2', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '周用户二', 'week2@test.com', '13900000005', 'USER', 1, DATE_SUB(NOW(), INTERVAL 3 DAY)),
('user_week_3', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '周用户三', 'week3@test.com', '13900000006', 'USER', 1, DATE_SUB(NOW(), INTERVAL 4 DAY)),
('user_week_4', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '周用户四', 'week4@test.com', '13900000007', 'USER', 1, DATE_SUB(NOW(), INTERVAL 5 DAY));

-- 本月注册用户（非本周，共5人）
INSERT INTO t_user (username, password, real_name, email, phone, role, status, create_time) VALUES
('user_month_1', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '月用户一', 'month1@test.com', '13900000008', 'USER', 1, DATE_SUB(NOW(), INTERVAL 10 DAY)),
('user_month_2', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '月用户二', 'month2@test.com', '13900000009', 'USER', 1, DATE_SUB(NOW(), INTERVAL 12 DAY)),
('user_month_3', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '月用户三', 'month3@test.com', '13900000010', 'USER', 1, DATE_SUB(NOW(), INTERVAL 15 DAY)),
('user_month_4', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '月用户四', 'month4@test.com', '13900000011', 'USER', 1, DATE_SUB(NOW(), INTERVAL 18 DAY)),
('user_month_5', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '月用户五', 'month5@test.com', '13900000012', 'USER', 1, DATE_SUB(NOW(), INTERVAL 20 DAY));

-- 近12个月历史用户（每月若干人，模拟增长趋势）
INSERT INTO t_user (username, password, real_name, email, phone, role, status, create_time) VALUES
-- 11个月前 (2人)
('user_202404_1', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户A1', 'hist_a1@test.com', '13900000013', 'USER', 1, DATE_SUB(NOW(), INTERVAL 11 MONTH)),
('user_202404_2', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户A2', 'hist_a2@test.com', '13900000014', 'USER', 1, DATE_SUB(NOW(), INTERVAL 11 MONTH)),
-- 10个月前 (3人)
('user_202405_1', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户B1', 'hist_b1@test.com', '13900000015', 'USER', 1, DATE_SUB(NOW(), INTERVAL 10 MONTH)),
('user_202405_2', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户B2', 'hist_b2@test.com', '13900000016', 'USER', 1, DATE_SUB(NOW(), INTERVAL 10 MONTH)),
('user_202405_3', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户B3', 'hist_b3@test.com', '13900000017', 'USER', 1, DATE_SUB(NOW(), INTERVAL 10 MONTH)),
-- 9个月前 (2人)
('user_202406_1', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户C1', 'hist_c1@test.com', '13900000018', 'USER', 1, DATE_SUB(NOW(), INTERVAL 9 MONTH)),
('user_202406_2', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户C2', 'hist_c2@test.com', '13900000019', 'USER', 1, DATE_SUB(NOW(), INTERVAL 9 MONTH)),
-- 8个月前 (4人)
('user_202407_1', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户D1', 'hist_d1@test.com', '13900000020', 'USER', 1, DATE_SUB(NOW(), INTERVAL 8 MONTH)),
('user_202407_2', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户D2', 'hist_d2@test.com', '13900000021', 'USER', 1, DATE_SUB(NOW(), INTERVAL 8 MONTH)),
('user_202407_3', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户D3', 'hist_d3@test.com', '13900000022', 'USER', 1, DATE_SUB(NOW(), INTERVAL 8 MONTH)),
('user_202407_4', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户D4', 'hist_d4@test.com', '13900000023', 'USER', 1, DATE_SUB(NOW(), INTERVAL 8 MONTH)),
-- 7个月前 (3人)
('user_202408_1', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户E1', 'hist_e1@test.com', '13900000024', 'USER', 1, DATE_SUB(NOW(), INTERVAL 7 MONTH)),
('user_202408_2', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户E2', 'hist_e2@test.com', '13900000025', 'USER', 1, DATE_SUB(NOW(), INTERVAL 7 MONTH)),
('user_202408_3', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户E3', 'hist_e3@test.com', '13900000026', 'USER', 1, DATE_SUB(NOW(), INTERVAL 7 MONTH)),
-- 6个月前 (5人)
('user_202409_1', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户F1', 'hist_f1@test.com', '13900000027', 'USER', 1, DATE_SUB(NOW(), INTERVAL 6 MONTH)),
('user_202409_2', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户F2', 'hist_f2@test.com', '13900000028', 'USER', 1, DATE_SUB(NOW(), INTERVAL 6 MONTH)),
('user_202409_3', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户F3', 'hist_f3@test.com', '13900000029', 'USER', 1, DATE_SUB(NOW(), INTERVAL 6 MONTH)),
('user_202409_4', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户F4', 'hist_f4@test.com', '13900000030', 'USER', 1, DATE_SUB(NOW(), INTERVAL 6 MONTH)),
('user_202409_5', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户F5', 'hist_f5@test.com', '13900000031', 'USER', 1, DATE_SUB(NOW(), INTERVAL 6 MONTH)),
-- 5个月前 (4人)
('user_202410_1', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户G1', 'hist_g1@test.com', '13900000032', 'USER', 1, DATE_SUB(NOW(), INTERVAL 5 MONTH)),
('user_202410_2', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户G2', 'hist_g2@test.com', '13900000033', 'USER', 1, DATE_SUB(NOW(), INTERVAL 5 MONTH)),
('user_202410_3', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户G3', 'hist_g3@test.com', '13900000034', 'USER', 1, DATE_SUB(NOW(), INTERVAL 5 MONTH)),
('user_202410_4', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户G4', 'hist_g4@test.com', '13900000035', 'USER', 1, DATE_SUB(NOW(), INTERVAL 5 MONTH)),
-- 4个月前 (6人)
('user_202411_1', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户H1', 'hist_h1@test.com', '13900000036', 'USER', 1, DATE_SUB(NOW(), INTERVAL 4 MONTH)),
('user_202411_2', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户H2', 'hist_h2@test.com', '13900000037', 'USER', 1, DATE_SUB(NOW(), INTERVAL 4 MONTH)),
('user_202411_3', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户H3', 'hist_h3@test.com', '13900000038', 'USER', 1, DATE_SUB(NOW(), INTERVAL 4 MONTH)),
('user_202411_4', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户H4', 'hist_h4@test.com', '13900000039', 'USER', 1, DATE_SUB(NOW(), INTERVAL 4 MONTH)),
('user_202411_5', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户H5', 'hist_h5@test.com', '13900000040', 'USER', 1, DATE_SUB(NOW(), INTERVAL 4 MONTH)),
('user_202411_6', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户H6', 'hist_h6@test.com', '13900000041', 'USER', 1, DATE_SUB(NOW(), INTERVAL 4 MONTH)),
-- 3个月前 (5人)
('user_202412_1', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户I1', 'hist_i1@test.com', '13900000042', 'USER', 1, DATE_SUB(NOW(), INTERVAL 3 MONTH)),
('user_202412_2', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户I2', 'hist_i2@test.com', '13900000043', 'USER', 1, DATE_SUB(NOW(), INTERVAL 3 MONTH)),
('user_202412_3', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户I3', 'hist_i3@test.com', '13900000044', 'USER', 1, DATE_SUB(NOW(), INTERVAL 3 MONTH)),
('user_202412_4', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户I4', 'hist_i4@test.com', '13900000045', 'USER', 1, DATE_SUB(NOW(), INTERVAL 3 MONTH)),
('user_202412_5', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户I5', 'hist_i5@test.com', '13900000046', 'USER', 1, DATE_SUB(NOW(), INTERVAL 3 MONTH)),
-- 2个月前 (7人)
('user_202501_1', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户J1', 'hist_j1@test.com', '13900000047', 'USER', 1, DATE_SUB(NOW(), INTERVAL 2 MONTH)),
('user_202501_2', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户J2', 'hist_j2@test.com', '13900000048', 'USER', 1, DATE_SUB(NOW(), INTERVAL 2 MONTH)),
('user_202501_3', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户J3', 'hist_j3@test.com', '13900000049', 'USER', 1, DATE_SUB(NOW(), INTERVAL 2 MONTH)),
('user_202501_4', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户J4', 'hist_j4@test.com', '13900000050', 'USER', 1, DATE_SUB(NOW(), INTERVAL 2 MONTH)),
('user_202501_5', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户J5', 'hist_j5@test.com', '13900000051', 'USER', 1, DATE_SUB(NOW(), INTERVAL 2 MONTH)),
('user_202501_6', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户J6', 'hist_j6@test.com', '13900000052', 'USER', 1, DATE_SUB(NOW(), INTERVAL 2 MONTH)),
('user_202501_7', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户J7', 'hist_j7@test.com', '13900000053', 'USER', 1, DATE_SUB(NOW(), INTERVAL 2 MONTH)),
-- 1个月前 (6人)
('user_202502_1', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户K1', 'hist_k1@test.com', '13900000054', 'USER', 1, DATE_SUB(NOW(), INTERVAL 1 MONTH)),
('user_202502_2', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户K2', 'hist_k2@test.com', '13900000055', 'USER', 1, DATE_SUB(NOW(), INTERVAL 1 MONTH)),
('user_202502_3', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户K3', 'hist_k3@test.com', '13900000056', 'USER', 1, DATE_SUB(NOW(), INTERVAL 1 MONTH)),
('user_202502_4', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户K4', 'hist_k4@test.com', '13900000057', 'USER', 1, DATE_SUB(NOW(), INTERVAL 1 MONTH)),
('user_202502_5', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户K5', 'hist_k5@test.com', '13900000058', 'USER', 1, DATE_SUB(NOW(), INTERVAL 1 MONTH)),
('user_202502_6', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '历史用户K6', 'hist_k6@test.com', '13900000059', 'USER', 1, DATE_SUB(NOW(), INTERVAL 1 MONTH));

-- ============================================================
-- 订单测试数据
-- 状态: 0-待支付, 1-已支付, 2-已发货, 3-已完成, 4-已取消
-- ============================================================

-- 金额TOP10订单（按金额降序，覆盖不同状态）
INSERT INTO t_order (order_no, user_id, product_name, amount, status, create_time) VALUES
('ORD20261001', 1, 'MacBook Pro 16寸 M3 Max', 29999.00, 3, DATE_SUB(NOW(), INTERVAL 30 DAY)),
('ORD20261002', 2, 'iPhone 15 Pro Max 1TB', 15999.00, 3, DATE_SUB(NOW(), INTERVAL 25 DAY)),
('ORD20261003', 3, '戴森V15吸尘器套装', 6999.00, 2, DATE_SUB(NOW(), INTERVAL 20 DAY)),
('ORD20261004', 4, '索尼A7M4全画幅相机', 16800.00, 1, DATE_SUB(NOW(), INTERVAL 15 DAY)),
('ORD20261005', 5, 'Apple Watch Ultra 2', 6499.00, 3, DATE_SUB(NOW(), INTERVAL 12 DAY)),
('ORD20261006', 6, 'iPad Pro 12.9寸 M2', 10999.00, 0, DATE_SUB(NOW(), INTERVAL 10 DAY)),
('ORD20261007', 7, 'AirPods Max头戴式耳机', 4399.00, 4, DATE_SUB(NOW(), INTERVAL 8 DAY)),
('ORD20261008', 8, '三星Galaxy S24 Ultra', 9999.00, 3, DATE_SUB(NOW(), INTERVAL 5 DAY)),
('ORD20261009', 9, '任天堂Switch OLED限定版', 2899.00, 2, DATE_SUB(NOW(), INTERVAL 3 DAY)),
('ORD20261010', 10, 'Bose QC消噪耳机', 2699.00, 1, DATE_SUB(NOW(), INTERVAL 2 DAY));

-- 待支付订单 (status=0) - 共8笔
INSERT INTO t_order (order_no, user_id, product_name, amount, status, create_time) VALUES
('ORD20262001', 11, '小米14 Ultra', 5999.00, 0, NOW()),
('ORD20262002', 12, '华为Mate 60 Pro', 6999.00, 0, NOW()),
('ORD20262003', 13, 'OPPO Find X7 Ultra', 5999.00, 0, DATE_SUB(NOW(), INTERVAL 1 DAY)),
('ORD20262004', 14, 'vivo X100 Pro', 4999.00, 0, DATE_SUB(NOW(), INTERVAL 2 DAY)),
('ORD20262005', 15, '荣耀Magic6 Pro', 5399.00, 0, DATE_SUB(NOW(), INTERVAL 3 DAY)),
('ORD20262006', 16, '一加12', 4299.00, 0, DATE_SUB(NOW(), INTERVAL 4 DAY)),
('ORD20262007', 17, 'Redmi K70 Pro', 3299.00, 0, DATE_SUB(NOW(), INTERVAL 5 DAY)),
('ORD20262008', 18, 'realme GT5 Pro', 3399.00, 0, DATE_SUB(NOW(), INTERVAL 6 DAY));

-- 已支付订单 (status=1) - 共10笔
INSERT INTO t_order (order_no, user_id, product_name, amount, status, create_time) VALUES
('ORD20263001', 19, '索尼WH-1000XM5耳机', 2499.00, 1, NOW()),
('ORD20263002', 20, 'JBL蓝牙音箱', 1299.00, 1, DATE_SUB(NOW(), INTERVAL 1 DAY)),
('ORD20263003', 21, '漫步者TWS耳机', 399.00, 1, DATE_SUB(NOW(), INTERVAL 2 DAY)),
('ORD20263004', 22, '哈曼卡顿琉璃音箱', 1899.00, 1, DATE_SUB(NOW(), INTERVAL 3 DAY)),
('ORD20263005', 23, '马歇尔便携音箱', 1599.00, 1, DATE_SUB(NOW(), INTERVAL 4 DAY)),
('ORD20263006', 24, 'B&O耳机', 2299.00, 1, DATE_SUB(NOW(), INTERVAL 5 DAY)),
('ORD20263007', 25, '森海塞尔耳机', 1999.00, 1, DATE_SUB(NOW(), INTERVAL 6 DAY)),
('ORD20263008', 26, '铁三角耳机', 1699.00, 1, DATE_SUB(NOW(), INTERVAL 7 DAY)),
('ORD20263009', 27, 'AKG耳机', 1499.00, 1, DATE_SUB(NOW(), INTERVAL 8 DAY)),
('ORD20263010', 28, '拜雅耳机', 1799.00, 1, DATE_SUB(NOW(), INTERVAL 9 DAY));

-- 已发货订单 (status=2) - 共7笔
INSERT INTO t_order (order_no, user_id, product_name, amount, status, create_time) VALUES
('ORD20264001', 29, '戴森吹风机', 3199.00, 2, NOW()),
('ORD20264002', 30, '飞利浦电动牙刷', 399.00, 2, DATE_SUB(NOW(), INTERVAL 1 DAY)),
('ORD20264003', 31, '博朗剃须刀', 899.00, 2, DATE_SUB(NOW(), INTERVAL 2 DAY)),
('ORD20264004', 32, '松下电饭煲', 1299.00, 2, DATE_SUB(NOW(), INTERVAL 3 DAY)),
('ORD20264005', 33, '美的空气净化器', 1999.00, 2, DATE_SUB(NOW(), INTERVAL 4 DAY)),
('ORD20264006', 34, '格力空调扇', 699.00, 2, DATE_SUB(NOW(), INTERVAL 5 DAY)),
('ORD20264007', 35, '小米扫地机器人', 2499.00, 2, DATE_SUB(NOW(), INTERVAL 6 DAY));

-- 已完成订单 (status=3) - 共15笔
INSERT INTO t_order (order_no, user_id, product_name, amount, status, create_time) VALUES
('ORD20265001', 36, '耐克Air Jordan限量款', 1899.00, 3, NOW()),
('ORD20265002', 37, '阿迪达斯Yeezy', 2299.00, 3, DATE_SUB(NOW(), INTERVAL 1 DAY)),
('ORD20265003', 38, '新百伦990v5', 1299.00, 3, DATE_SUB(NOW(), INTERVAL 2 DAY)),
('ORD20265004', 39, '亚瑟士GEL-KAYANO', 1099.00, 3, DATE_SUB(NOW(), INTERVAL 3 DAY)),
('ORD20265005', 40, '匡威联名款', 699.00, 3, DATE_SUB(NOW(), INTERVAL 4 DAY)),
('ORD20265006', 41, 'Vans经典款', 499.00, 3, DATE_SUB(NOW(), INTERVAL 5 DAY)),
('ORD20265007', 42, 'Puma RS-X', 799.00, 3, DATE_SUB(NOW(), INTERVAL 6 DAY)),
('ORD20265008', 43, 'Reebok经典款', 599.00, 3, DATE_SUB(NOW(), INTERVAL 7 DAY)),
('ORD20265009', 44, 'Saucony跑鞋', 899.00, 3, DATE_SUB(NOW(), INTERVAL 8 DAY)),
('ORD20265010', 45, 'Hoka One One跑鞋', 1399.00, 3, DATE_SUB(NOW(), INTERVAL 9 DAY)),
('ORD20265011', 46, 'On Running跑鞋', 1599.00, 3, DATE_SUB(NOW(), INTERVAL 10 DAY)),
('ORD20265012', 47, 'Salomon户外鞋', 1199.00, 3, DATE_SUB(NOW(), INTERVAL 11 DAY)),
('ORD20265013', 48, 'Timberland靴子', 1399.00, 3, DATE_SUB(NOW(), INTERVAL 12 DAY)),
('ORD20265014', 49, 'Dr. Martens马丁靴', 1099.00, 3, DATE_SUB(NOW(), INTERVAL 13 DAY)),
('ORD20265015', 50, 'Clarks沙漠靴', 899.00, 3, DATE_SUB(NOW(), INTERVAL 14 DAY));

-- 已取消订单 (status=4) - 共5笔
INSERT INTO t_order (order_no, user_id, product_name, amount, status, create_time) VALUES
('ORD20266001', 51, '乐高星球大战套装', 3999.00, 4, NOW()),
('ORD20266002', 52, '高达模型PG版', 1599.00, 4, DATE_SUB(NOW(), INTERVAL 1 DAY)),
('ORD20266003', 53, '变形金刚MPM系列', 899.00, 4, DATE_SUB(NOW(), INTERVAL 2 DAY)),
('ORD20266004', 54, '海贼王手办', 599.00, 4, DATE_SUB(NOW(), INTERVAL 3 DAY)),
('ORD20266005', 55, '火影忍者手办', 499.00, 4, DATE_SUB(NOW(), INTERVAL 4 DAY));

-- 历史订单（分布在近12个月，模拟业务增长趋势）
INSERT INTO t_order (order_no, user_id, product_name, amount, status, create_time) VALUES
-- 11个月前
('ORD20260011', 1, '咖啡机入门款', 899.00, 3, DATE_SUB(NOW(), INTERVAL 11 MONTH)),
('ORD20260012', 2, '手冲咖啡套装', 299.00, 3, DATE_SUB(NOW(), INTERVAL 11 MONTH)),
-- 10个月前
('ORD20260021', 3, '咖啡豆礼盒', 199.00, 3, DATE_SUB(NOW(), INTERVAL 10 MONTH)),
('ORD20260022', 4, '磨豆器手动款', 159.00, 3, DATE_SUB(NOW(), INTERVAL 10 MONTH)),
('ORD20260023', 5, '咖啡杯套装', 99.00, 3, DATE_SUB(NOW(), INTERVAL 10 MONTH)),
-- 9个月前
('ORD20260031', 6, '意式咖啡机', 1299.00, 3, DATE_SUB(NOW(), INTERVAL 9 MONTH)),
('ORD20260032', 7, '奶泡机', 199.00, 3, DATE_SUB(NOW(), INTERVAL 9 MONTH)),
-- 8个月前
('ORD20260041', 8, '胶囊咖啡机', 699.00, 3, DATE_SUB(NOW(), INTERVAL 8 MONTH)),
('ORD20260042', 9, '咖啡胶囊套装', 299.00, 3, DATE_SUB(NOW(), INTERVAL 8 MONTH)),
('ORD20260043', 10, '咖啡糖浆', 79.00, 3, DATE_SUB(NOW(), INTERVAL 8 MONTH)),
('ORD20260044', 11, '咖啡搅拌棒', 29.00, 3, DATE_SUB(NOW(), INTERVAL 8 MONTH)),
-- 7个月前
('ORD20260051', 12, '冷萃咖啡壶', 259.00, 3, DATE_SUB(NOW(), INTERVAL 7 MONTH)),
('ORD20260052', 13, '冰滴咖啡器', 399.00, 3, DATE_SUB(NOW(), INTERVAL 7 MONTH)),
('ORD20260053', 14, '咖啡滤纸', 39.00, 3, DATE_SUB(NOW(), INTERVAL 7 MONTH)),
-- 6个月前
('ORD20260061', 15, '摩卡壶', 459.00, 3, DATE_SUB(NOW(), INTERVAL 6 MONTH)),
('ORD20260062', 16, '咖啡量勺', 29.00, 3, DATE_SUB(NOW(), INTERVAL 6 MONTH)),
('ORD20260063', 17, '咖啡称重秤', 129.00, 3, DATE_SUB(NOW(), INTERVAL 6 MONTH)),
('ORD20260064', 18, '咖啡温度计', 59.00, 3, DATE_SUB(NOW(), INTERVAL 6 MONTH)),
('ORD20260065', 19, '咖啡壶保温套', 49.00, 3, DATE_SUB(NOW(), INTERVAL 6 MONTH)),
-- 5个月前
('ORD20260071', 20, '法压壶', 159.00, 3, DATE_SUB(NOW(), INTERVAL 5 MONTH)),
('ORD20260072', 21, '爱乐压', 299.00, 3, DATE_SUB(NOW(), INTERVAL 5 MONTH)),
('ORD20260073', 22, '聪明杯', 179.00, 3, DATE_SUB(NOW(), INTERVAL 5 MONTH)),
('ORD20260074', 23, 'V60滤杯', 89.00, 3, DATE_SUB(NOW(), INTERVAL 5 MONTH)),
-- 4个月前
('ORD20260081', 24, '手冲壶温控款', 399.00, 3, DATE_SUB(NOW(), INTERVAL 4 MONTH)),
('ORD20260082', 25, '咖啡分享壶', 129.00, 3, DATE_SUB(NOW(), INTERVAL 4 MONTH)),
('ORD20260083', 26, '咖啡木托盘', 79.00, 3, DATE_SUB(NOW(), INTERVAL 4 MONTH)),
('ORD20260084', 27, '咖啡豆储存罐', 99.00, 3, DATE_SUB(NOW(), INTERVAL 4 MONTH)),
('ORD20260085', 28, '咖啡研磨机电动款', 599.00, 3, DATE_SUB(NOW(), INTERVAL 4 MONTH)),
('ORD20260086', 29, '咖啡压粉器', 149.00, 3, DATE_SUB(NOW(), INTERVAL 4 MONTH)),
-- 3个月前
('ORD20260091', 30, '咖啡拉花缸', 89.00, 3, DATE_SUB(NOW(), INTERVAL 3 MONTH)),
('ORD20260092', 31, '咖啡师围裙', 129.00, 3, DATE_SUB(NOW(), INTERVAL 3 MONTH)),
('ORD20260093', 32, '咖啡杯垫套装', 49.00, 3, DATE_SUB(NOW(), INTERVAL 3 MONTH)),
('ORD20260094', 33, '咖啡勺套装', 39.00, 3, DATE_SUB(NOW(), INTERVAL 3 MONTH)),
('ORD20260095', 34, '咖啡清洁刷', 29.00, 3, DATE_SUB(NOW(), INTERVAL 3 MONTH)),
-- 2个月前
('ORD20260101', 35, '咖啡萃取机', 899.00, 3, DATE_SUB(NOW(), INTERVAL 2 MONTH)),
('ORD20260102', 36, '咖啡保温杯', 159.00, 3, DATE_SUB(NOW(), INTERVAL 2 MONTH)),
('ORD20260103', 37, '咖啡随行杯', 129.00, 3, DATE_SUB(NOW(), INTERVAL 2 MONTH)),
('ORD20260104', 38, '咖啡玻璃杯', 79.00, 3, DATE_SUB(NOW(), INTERVAL 2 MONTH)),
('ORD20260105', 39, '咖啡陶瓷杯', 59.00, 3, DATE_SUB(NOW(), INTERVAL 2 MONTH)),
('ORD20260106', 40, '咖啡马克杯', 49.00, 3, DATE_SUB(NOW(), INTERVAL 2 MONTH)),
('ORD20260107', 41, '咖啡礼盒套装', 399.00, 3, DATE_SUB(NOW(), INTERVAL 2 MONTH)),
-- 1个月前
('ORD20260111', 42, '咖啡机清洁片', 59.00, 3, DATE_SUB(NOW(), INTERVAL 1 MONTH)),
('ORD20260112', 43, '咖啡除垢剂', 39.00, 3, DATE_SUB(NOW(), INTERVAL 1 MONTH)),
('ORD20260113', 44, '咖啡机滤网', 79.00, 3, DATE_SUB(NOW(), INTERVAL 1 MONTH)),
('ORD20260114', 45, '咖啡机密封圈', 29.00, 3, DATE_SUB(NOW(), INTERVAL 1 MONTH)),
('ORD20260115', 46, '咖啡机水箱', 99.00, 3, DATE_SUB(NOW(), INTERVAL 1 MONTH)),
('ORD20260116', 47, '咖啡机托盘', 49.00, 3, DATE_SUB(NOW(), INTERVAL 1 MONTH));

-- ============================================================
-- 数据统计验证
-- ============================================================
SELECT '=== 用户统计 ===' AS info;
SELECT 
    '今日注册' AS period, COUNT(*) AS count FROM t_user WHERE deleted = 0 AND DATE(create_time) = CURDATE()
UNION ALL
SELECT 
    '本周注册' AS period, COUNT(*) AS count FROM t_user WHERE deleted = 0 AND YEARWEEK(create_time, 1) = YEARWEEK(CURDATE(), 1)
UNION ALL
SELECT 
    '本月注册' AS period, COUNT(*) AS count FROM t_user WHERE deleted = 0 AND YEAR(create_time) = YEAR(CURDATE()) AND MONTH(create_time) = MONTH(CURDATE())
UNION ALL
SELECT 
    '总用户数' AS period, COUNT(*) AS count FROM t_user WHERE deleted = 0;

SELECT '=== 订单状态统计 ===' AS info;
SELECT 
    status,
    CASE status
        WHEN 0 THEN '待支付'
        WHEN 1 THEN '已支付'
        WHEN 2 THEN '已发货'
        WHEN 3 THEN '已完成'
        WHEN 4 THEN '已取消'
    END AS status_name,
    COUNT(*) AS count,
    SUM(amount) AS total_amount
FROM t_order 
WHERE deleted = 0
GROUP BY status
ORDER BY status;

SELECT '=== 金额TOP10订单 ===' AS info;
SELECT 
    o.order_no,
    u.real_name,
    o.product_name,
    o.amount,
    CASE o.status
        WHEN 0 THEN '待支付'
        WHEN 1 THEN '已支付'
        WHEN 2 THEN '已发货'
        WHEN 3 THEN '已完成'
        WHEN 4 THEN '已取消'
    END AS status_name
FROM t_order o
LEFT JOIN t_user u ON o.user_id = u.id
WHERE o.deleted = 0
ORDER BY o.amount DESC
LIMIT 10;

SELECT '=== 系统总览 ===' AS info;
SELECT 
    (SELECT COUNT(*) FROM t_user WHERE deleted = 0) AS total_users,
    (SELECT COUNT(*) FROM t_order WHERE deleted = 0) AS total_orders,
    (SELECT COALESCE(SUM(amount), 0) FROM t_order WHERE deleted = 0) AS total_amount,
    (SELECT COUNT(*) FROM t_order WHERE deleted = 0 AND status = 3) AS completed_orders,
    (SELECT COALESCE(SUM(amount), 0) FROM t_order WHERE deleted = 0 AND status = 3) AS completed_amount;

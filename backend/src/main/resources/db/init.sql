-- ============================================================
-- 用户管理系统 - 数据库初始化脚本
-- 字符集: utf8mb4 | 排序规则: utf8mb4_unicode_ci
-- ============================================================

CREATE DATABASE IF NOT EXISTS `user_management`
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE `user_management`;

-- ============================================================
-- 用户表
-- ============================================================
CREATE TABLE IF NOT EXISTS `t_user` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `username`    VARCHAR(50)  NOT NULL COMMENT '用户名（登录用）',
    `password`    VARCHAR(255) NOT NULL COMMENT '密码',
    `real_name`   VARCHAR(100) DEFAULT NULL COMMENT '真实姓名',
    `email`       VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    `phone`       VARCHAR(20)  DEFAULT NULL COMMENT '手机号',
    `role`        VARCHAR(20)  NOT NULL DEFAULT 'USER' COMMENT '角色：ADMIN-管理员，USER-普通用户',
    `status`      TINYINT      NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    `deleted`     TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-正常，1-已删除',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- ============================================================
-- 订单表
-- ============================================================
CREATE TABLE IF NOT EXISTS `t_order` (
    `id`           BIGINT         NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `order_no`     VARCHAR(64)    NOT NULL COMMENT '订单编号',
    `user_id`      BIGINT         NOT NULL COMMENT '关联用户ID',
    `product_name` VARCHAR(200)   NOT NULL COMMENT '商品名称',
    `amount`       DECIMAL(10, 2) NOT NULL DEFAULT 0.00 COMMENT '订单金额',
    `status`       TINYINT        NOT NULL DEFAULT 0 COMMENT '状态：0-待支付，1-已支付，2-已发货，3-已完成，4-已取消',
    `deleted`      TINYINT        NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-正常，1-已删除',
    `create_time`  DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_order_no` (`order_no`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';

-- ============================================================
-- 密码已采用 BCrypt 哈希存储
-- admin123 -> $2a$10$Qm4.QozGlzkmy.7cmwJ8xuMal22vlBR92NvyOMYsI49FERDTzXys.
-- user123  -> $2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6
-- ============================================================
INSERT INTO `t_user` (`username`, `password`, `real_name`, `email`, `phone`, `role`, `status`) VALUES
('admin',    '$2a$10$Qm4.QozGlzkmy.7cmwJ8xuMal22vlBR92NvyOMYsI49FERDTzXys.', '系统管理员', 'admin@example.com',    '13800000001', 'ADMIN', 1),
('zhangsan', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '张三',       'zhangsan@example.com', '13800000002', 'USER',  1),
('lisi',     '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '李四',       'lisi@example.com',     '13800000003', 'USER',  1),
('wangwu',   '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '王五',       'wangwu@example.com',   '13800000004', 'USER',  1),
('zhaoliu',  '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '赵六',       'zhaoliu@example.com',  '13800000005', 'USER',  0)
ON DUPLICATE KEY UPDATE `username` = VALUES(`username`);

INSERT INTO `t_order` (`order_no`, `user_id`, `product_name`, `amount`, `status`) VALUES
('ORD20260001', 1, '燕麦拿铁咖啡套餐',     128.00, 3),
('ORD20260002', 1, '办公桌面收纳套装',     259.90, 1),
('ORD20260003', 2, '无线蓝牙降噪耳机',     899.00, 2),
('ORD20260004', 2, '手工陶瓷马克杯',        68.50, 3),
('ORD20260005', 3, '北欧风格台灯',         199.00, 0),
('ORD20260006', 3, '有机棉质四件套',       459.00, 1),
('ORD20260007', 4, '智能体脂秤',           149.00, 3),
('ORD20260008', 4, '原木书架落地款',       1280.00, 4),
('ORD20260009', 5, '便携式咖啡研磨器',      329.00, 0),
('ORD20260010', 1, '天然乳胶枕头',         268.00, 2)
ON DUPLICATE KEY UPDATE `order_no` = VALUES(`order_no`);

INSERT INTO `t_user` (`username`, `password`, `real_name`, `email`, `phone`, `role`, `status`, `create_time`) VALUES
('today_user1', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '今日用户1', 'today1@test.com', '13800000006', 'USER', 1, NOW()),
('today_user2', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '今日用户2', 'today2@test.com', '13800000007', 'USER', 1, NOW()),
('today_user3', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '今日用户3', 'today3@test.com', '13800000008', 'USER', 1, NOW()),
('week_user1',  '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '本周用户1', 'week1@test.com',  '13800000009', 'USER', 1, DATE_SUB(NOW(), INTERVAL 3 DAY)),
('week_user2',  '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '本周用户2', 'week2@test.com',  '13800000010', 'USER', 1, DATE_SUB(NOW(), INTERVAL 5 DAY)),
('month_user1', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '本月用户1', 'month1@test.com', '13800000011', 'USER', 1, DATE_SUB(NOW(), INTERVAL 10 DAY)),
('month_user2', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '本月用户2', 'month2@test.com', '13800000012', 'USER', 1, DATE_SUB(NOW(), INTERVAL 15 DAY))
ON DUPLICATE KEY UPDATE `username` = VALUES(`username`);

INSERT INTO `t_user` (`username`, `password`, `real_name`, `email`, `phone`, `role`, `status`, `create_time`) VALUES
('m2_user1', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '上月用户1', 'm2u1@test.com', '13800001001', 'USER', 1, DATE_SUB(NOW(), INTERVAL 1 MONTH)),
('m2_user2', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '上月用户2', 'm2u2@test.com', '13800001002', 'USER', 1, DATE_SUB(NOW(), INTERVAL 1 MONTH)),
('m3_user1', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '前两月用户1', 'm3u1@test.com', '13800001003', 'USER', 1, DATE_SUB(NOW(), INTERVAL 2 MONTH)),
('m3_user2', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '前两月用户2', 'm3u2@test.com', '13800001004', 'USER', 1, DATE_SUB(NOW(), INTERVAL 2 MONTH)),
('m3_user3', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '前两月用户3', 'm3u3@test.com', '13800001005', 'USER', 1, DATE_SUB(NOW(), INTERVAL 2 MONTH)),
('m4_user1', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '前三用户1', 'm4u1@test.com', '13800001006', 'USER', 1, DATE_SUB(NOW(), INTERVAL 3 MONTH)),
('m4_user2', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '前三用户2', 'm4u2@test.com', '13800001007', 'USER', 1, DATE_SUB(NOW(), INTERVAL 3 MONTH)),
('m5_user1', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '前四用户1', 'm5u1@test.com', '13800001008', 'USER', 1, DATE_SUB(NOW(), INTERVAL 4 MONTH)),
('m6_user1', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '前五用户1', 'm6u1@test.com', '13800001009', 'USER', 1, DATE_SUB(NOW(), INTERVAL 5 MONTH)),
('m7_user1', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '前六用户1', 'm7u1@test.com', '13800001010', 'USER', 1, DATE_SUB(NOW(), INTERVAL 6 MONTH)),
('m8_user1', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '前七用户1', 'm8u1@test.com', '13800001011', 'USER', 1, DATE_SUB(NOW(), INTERVAL 7 MONTH)),
('m9_user1', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '前八用户1', 'm9u1@test.com', '13800001012', 'USER', 1, DATE_SUB(NOW(), INTERVAL 8 MONTH)),
('m10_user1', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '前九用户1', 'm10u1@test.com', '13800001013', 'USER', 1, DATE_SUB(NOW(), INTERVAL 9 MONTH)),
('m11_user1', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '前十用户1', 'm11u1@test.com', '13800001014', 'USER', 1, DATE_SUB(NOW(), INTERVAL 10 MONTH)),
('m12_user1', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '前十一用户1', 'm12u1@test.com', '13800001015', 'USER', 1, DATE_SUB(NOW(), INTERVAL 11 MONTH))
ON DUPLICATE KEY UPDATE `username` = VALUES(`username`);

INSERT INTO `t_order` (`order_no`, `user_id`, `product_name`, `amount`, `status`, `create_time`) VALUES
('TOP0001', 1, 'MacBook Pro 16寸', 19999.00, 3, DATE_SUB(NOW(), INTERVAL 1 DAY)),
('TOP0002', 2, 'iPhone 15 Pro Max', 9999.00, 3, DATE_SUB(NOW(), INTERVAL 2 DAY)),
('TOP0003', 3, '索尼A7M4相机', 16999.00, 2, DATE_SUB(NOW(), INTERVAL 3 DAY)),
('TOP0004', 4, '大疆御3无人机', 13888.00, 1, DATE_SUB(NOW(), INTERVAL 4 DAY)),
('TOP0005', 5, '华为Mate 60 Pro', 6999.00, 0, DATE_SUB(NOW(), INTERVAL 5 DAY)),
('TOP0006', 1, '联想拯救者Y9000X', 8999.00, 3, DATE_SUB(NOW(), INTERVAL 6 DAY)),
('TOP0007', 2, '三星S24 Ultra', 7999.00, 4, DATE_SUB(NOW(), INTERVAL 7 DAY)),
('TOP0008', 3, '戴森V15吸尘器', 5499.00, 3, DATE_SUB(NOW(), INTERVAL 8 DAY)),
('TOP0009', 4, '索尼WH-1000XM5', 2999.00, 2, DATE_SUB(NOW(), INTERVAL 9 DAY)),
('TOP0010', 5, '任天堂Switch OLED', 2299.00, 1, DATE_SUB(NOW(), INTERVAL 10 DAY)),
('TOP0011', 1, 'iPad Pro 12.9', 8499.00, 3, DATE_SUB(NOW(), INTERVAL 11 DAY)),
('STAT_PAY01', 1, '待支付订单1', 99.00, 0, NOW()),
('STAT_PAY02', 2, '待支付订单2', 199.00, 0, NOW()),
('STAT_PAY03', 3, '已支付订单1', 299.00, 1, NOW()),
('STAT_PAY04', 4, '已支付订单2', 399.00, 1, NOW()),
('STAT_PAY05', 5, '已支付订单3', 499.00, 1, NOW()),
('STAT_PAY06', 1, '已发货订单1', 599.00, 2, NOW()),
('STAT_PAY07', 2, '已发货订单2', 699.00, 2, NOW()),
('STAT_PAY08', 3, '已完成订单1', 799.00, 3, NOW()),
('STAT_PAY09', 4, '已完成订单2', 899.00, 3, NOW()),
('STAT_PAY10', 5, '已完成订单3', 999.00, 3, NOW()),
('STAT_PAY11', 1, '已完成订单4', 1099.00, 3, NOW()),
('STAT_PAY12', 2, '已取消订单1', 1199.00, 4, NOW()),
('STAT_PAY13', 3, '已取消订单2', 1299.00, 4, NOW())
ON DUPLICATE KEY UPDATE `order_no` = VALUES(`order_no`);

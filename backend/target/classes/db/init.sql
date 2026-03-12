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
-- ============================================================
INSERT INTO `t_user` (`username`, `password`, `real_name`, `email`, `phone`, `role`, `status`) VALUES
('admin',    '$2a$10$Qm4.QozGlzkmy.7cmwJ8xuMal22vlBR92NvyOMYsI49FERDTzXys.', '系统管理员', 'admin@example.com',    '13800000001', 'ADMIN', 1),
('zhangsan', '$2a$10$Qm4.QozGlzkmy.7cmwJ8xuMal22vlBR92NvyOMYsI49FERDTzXys.', '张三',       'zhangsan@example.com', '13800000002', 'USER',  1),
('lisi',     '$2a$10$Qm4.QozGlzkmy.7cmwJ8xuMal22vlBR92NvyOMYsI49FERDTzXys.', '李四',       'lisi@example.com',     '13800000003', 'USER',  1),
('wangwu',   '$2a$10$Qm4.QozGlzkmy.7cmwJ8xuMal22vlBR92NvyOMYsI49FERDTzXys.', '王五',       'wangwu@example.com',   '13800000004', 'USER',  1),
('zhaoliu',  '$2a$10$Qm4.QozGlzkmy.7cmwJ8xuMal22vlBR92NvyOMYsI49FERDTzXys.', '赵六',       'zhaoliu@example.com',  '13800000005', 'USER',  0)
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

-- ============================================================
-- 数据看板 - 丰富测试数据脚本
-- 特点：数据量大、分布真实、覆盖各种业务场景
-- ============================================================

USE `user_management`;

-- ============================================================
-- 0. 清理数据（可选）
-- ============================================================
-- 如需重置数据，取消下面注释
-- DELETE FROM t_order WHERE id > 10;
-- DELETE FROM t_user WHERE id > 5;
-- ALTER TABLE t_order AUTO_INCREMENT = 11;
-- ALTER TABLE t_user AUTO_INCREMENT = 6;

-- ============================================================
-- 1. 创建用户数据（500+用户，模拟真实增长曲线）
-- ============================================================

-- 存储过程：批量生成用户
DELIMITER $$

DROP PROCEDURE IF EXISTS GenerateUsers$$
CREATE PROCEDURE GenerateUsers()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE month_offset INT DEFAULT 0;
    DECLARE users_per_month INT DEFAULT 0;
    DECLARE base_date DATETIME;
    DECLARE user_name VARCHAR(50);
    DECLARE phone_num VARCHAR(20);
    
    -- 生成2024年4月到2025年3月的用户（12个月）
    WHILE month_offset < 12 DO
        -- 模拟真实业务场景：不同月份有不同增长量
        -- 618（6月）、双11（11月）、春节（2月）有峰值
        SET month_offset = month_offset + 1;
        
        CASE month_offset
            WHEN 1 THEN SET users_per_month = 25;  -- 2024年4月：平稳
            WHEN 2 THEN SET users_per_month = 35;  -- 2024年5月：增长
            WHEN 3 THEN SET users_per_month = 85;  -- 2024年6月：618大促峰值
            WHEN 4 THEN SET users_per_month = 30;  -- 2024年7月：回落
            WHEN 5 THEN SET users_per_month = 40;  -- 2024年8月：暑期
            WHEN 6 THEN SET users_per_month = 45;  -- 2024年9月：开学季
            WHEN 7 THEN SET users_per_month = 55;  -- 2024年10月：国庆
            WHEN 8 THEN SET users_per_month = 95;  -- 2024年11月：双11峰值
            WHEN 9 THEN SET users_per_month = 50;  -- 2024年12月：年终
            WHEN 10 THEN SET users_per_month = 35; -- 2025年1月：元旦
            WHEN 11 THEN SET users_per_month = 20; -- 2025年2月：春节低谷
            WHEN 12 THEN SET users_per_month = 45; -- 2025年3月：回升
        END CASE;
        
        SET i = 1;
        WHILE i <= users_per_month DO
            SET base_date = DATE_SUB(CURDATE(), INTERVAL (12 - month_offset) MONTH);
            SET user_name = CONCAT('user_', DATE_FORMAT(base_date, '%Y%m'), '_', LPAD(i, 4, '0'));
            SET phone_num = CONCAT('1', FLOOR(30 + RAND() * 69), LPAD(FLOOR(RAND() * 100000000), 8, '0'));
            
            INSERT INTO `t_user` (`username`, `password`, `real_name`, `email`, `phone`, `role`, `status`, `create_time`) 
            VALUES (
                user_name,
                '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6',
                CONCAT('用户', DATE_FORMAT(base_date, '%Y%m'), '-', i),
                CONCAT(user_name, '@example.com'),
                phone_num,
                'USER',
                IF(RAND() > 0.1, 1, 0), -- 90%用户启用
                DATE_ADD(base_date, INTERVAL FLOOR(RAND() * 28) DAY)
            );
            
            SET i = i + 1;
        END WHILE;
    END WHILE;
    
    -- 添加今天的用户（用于验证今日统计）
    SET i = 1;
    WHILE i <= 5 DO
        INSERT INTO `t_user` (`username`, `password`, `real_name`, `email`, `phone`, `role`, `status`, `create_time`) 
        VALUES (
            CONCAT('today_user_', LPAD(i, 3, '0')),
            '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6',
            CONCAT('今日用户', i),
            CONCAT('today', i, '@example.com'),
            CONCAT('13900000', LPAD(100 + i, 3, '0')),
            'USER',
            1,
            NOW()
        );
        SET i = i + 1;
    END WHILE;
END$$

DELIMITER ;

-- 执行生成用户
CALL GenerateUsers();
DROP PROCEDURE GenerateUsers;

-- ============================================================
-- 2. 创建订单数据（2000+订单，模拟真实交易分布）
-- ============================================================

DELIMITER $$

DROP PROCEDURE IF EXISTS GenerateOrders$$
CREATE PROCEDURE GenerateOrders()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE total_users INT DEFAULT 0;
    DECLARE random_user_id INT DEFAULT 0;
    DECLARE random_status INT DEFAULT 0;
    DECLARE random_amount DECIMAL(10,2) DEFAULT 0;
    DECLARE product_category INT DEFAULT 0;
    DECLARE product_name VARCHAR(200);
    DECLARE base_date DATETIME;
    DECLARE order_date DATETIME;
    
    -- 商品池
    -- 1: 数码电子 (高价值)
    -- 2: 家居家电 (中高价值)
    -- 3: 日用百货 (中价值)
    -- 4: 服装鞋包 (中低价值)
    -- 5: 食品饮料 (低价值)
    
    SET total_users = (SELECT COUNT(*) FROM t_user WHERE deleted = 0);
    
    -- 生成历史订单（2024年4月到2025年3月）
    WHILE i <= 2000 DO
        -- 随机用户
        SET random_user_id = FLOOR(1 + RAND() * total_users);
        
        -- 随机日期（近一年内）
        SET base_date = DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 365) DAY);
        SET order_date = DATE_ADD(base_date, INTERVAL FLOOR(RAND() * 24) HOUR);
        
        -- 随机状态（模拟真实分布：已完成最多，已取消最少）
        SET random_status = CASE 
            WHEN RAND() < 0.40 THEN 3  -- 40% 已完成
            WHEN RAND() < 0.65 THEN 1  -- 25% 已支付
            WHEN RAND() < 0.80 THEN 2  -- 15% 已发货
            WHEN RAND() < 0.92 THEN 0  -- 12% 待支付
            ELSE 4                      -- 8% 已取消
        END;
        
        -- 随机商品类别和金额
        SET product_category = CASE 
            WHEN RAND() < 0.20 THEN 1  -- 20% 数码电子
            WHEN RAND() < 0.45 THEN 2  -- 25% 家居家电
            WHEN RAND() < 0.70 THEN 3  -- 25% 日用百货
            WHEN RAND() < 0.90 THEN 4  -- 20% 服装鞋包
            ELSE 5                      -- 10% 食品饮料
        END;
        
        -- 根据类别生成商品名和金额
        SET product_name = CASE product_category
            WHEN 1 THEN ELT(1 + FLOOR(RAND() * 10), 
                'iPhone 15 Pro Max', 'MacBook Pro 16寸', '索尼A7M4相机', '大疆Mavic 3 Pro',
                'iPad Pro 12.9寸', '华为Mate60 Pro+', '小米14 Ultra', '三星S24 Ultra',
                '戴尔XPS 15笔记本', '索尼WH-1000XM5耳机')
            WHEN 2 THEN ELT(1 + FLOOR(RAND() * 10),
                '海尔智能冰箱', '西门子洗碗机', '戴森V15吸尘器', '小米空气净化器',
                '美的中央空调', '科沃斯扫地机器人', '石头洗地机', '松下智能马桶',
                'TOTO智能卫浴套装', '慕思智能床垫')
            WHEN 3 THEN ELT(1 + FLOOR(RAND() * 10),
                '无印良品收纳套装', '宜家全屋家具', '乐扣保鲜盒套装', '膳魔师保温杯',
                '象印电饭煲', '飞利浦电动牙刷', '欧乐B牙刷牙膏套装', '花王洗衣液套装',
                '维达纸巾整箱', '蓝月亮清洁套装')
            WHEN 4 THEN ELT(1 + FLOOR(RAND() * 10),
                '耐克运动鞋', '阿迪达斯运动套装', '优衣库羽绒服', 'ZARA大衣',
                'H&M春装套装', 'Coach手提包', 'MK斜挎包', '新秀丽行李箱',
                '北面冲锋衣', '匡威帆布鞋')
            ELSE ELT(1 + FLOOR(RAND() * 10),
                '三只松鼠零食大礼包', '良品铺子坚果礼盒', '蒙牛牛奶整箱', '伊利酸奶套装',
                '农夫山泉矿泉水', '可口可乐整箱', '星巴克咖啡豆', '茶叶礼盒',
                '进口红酒套装', '费列罗巧克力')
        END;
        
        SET random_amount = CASE product_category
            WHEN 1 THEN ROUND(3000 + RAND() * 27000, 2)  -- 3000-30000
            WHEN 2 THEN ROUND(1000 + RAND() * 9000, 2)   -- 1000-10000
            WHEN 3 THEN ROUND(100 + RAND() * 900, 2)     -- 100-1000
            WHEN 4 THEN ROUND(200 + RAND() * 1800, 2)    -- 200-2000
            ELSE ROUND(50 + RAND() * 450, 2)             -- 50-500
        END;
        
        INSERT INTO `t_order` (`order_no`, `user_id`, `product_name`, `amount`, `status`, `create_time`)
        VALUES (
            CONCAT('ORD', DATE_FORMAT(order_date, '%Y%m%d'), LPAD(i, 6, '0')),
            random_user_id,
            product_name,
            random_amount,
            random_status,
            order_date
        );
        
        SET i = i + 1;
    END WHILE;
    
    -- 添加今日订单（用于验证今日统计）
    SET i = 1;
    WHILE i <= 20 DO
        INSERT INTO `t_order` (`order_no`, `user_id`, `product_name`, `amount`, `status`, `create_time`)
        VALUES (
            CONCAT('ORD', DATE_FORMAT(NOW(), '%Y%m%d'), 'TODAY', LPAD(i, 3, '0')),
            FLOOR(1 + RAND() * total_users),
            ELT(1 + FLOOR(RAND() * 5), 'iPhone 15', '戴森吸尘器', '茅台飞天', 'SK-II神仙水', '乐高积木'),
            ROUND(1000 + RAND() * 9000, 2),
            FLOOR(RAND() * 5),
            DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 12) HOUR)
        );
        SET i = i + 1;
    END WHILE;
    
    -- 添加超高额订单（TOP展示用）
    INSERT INTO `t_order` (`order_no`, `user_id`, `product_name`, `amount`, `status`, `create_time`) VALUES
    ('ORD20250001VIP', 1, '保时捷911 Carrera', 1458000.00, 3, '2025-02-14 10:00:00'),
    ('ORD20250002VIP', 2, '特斯拉Model S Plaid', 1059999.00, 3, '2025-02-10 14:30:00'),
    ('ORD20250003VIP', 3, '劳力士潜航者型', 850000.00, 3, '2025-02-05 09:00:00'),
    ('ORD20250004VIP', 4, '爱马仕Birkin包', 680000.00, 3, '2025-01-28 11:00:00'),
    ('ORD20250005VIP', 5, '路易威登全套定制', 520000.00, 3, '2025-01-20 15:30:00'),
    ('ORD20250006VIP', 6, '意大利进口全屋定制', 458000.00, 3, '2025-01-15 10:00:00'),
    ('ORD20250007VIP', 7, '德国柏丽整体厨房', 388000.00, 3, '2025-01-10 13:30:00'),
    ('ORD20250008VIP', 8, '瑞士劳芬卫浴全套', 328000.00, 3, '2025-01-05 16:00:00'),
    ('ORD20250009VIP', 9, '美国席梦思智能床垫', 288000.00, 3, '2024-12-28 09:30:00'),
    ('ORD20250010VIP', 10, '丹麦BoConcept家具', 258000.00, 3, '2024-12-20 12:00:00'),
    ('ORD20250011VIP', 11, '日本骊住家装套餐', 228000.00, 3, '2024-12-15 14:30:00'),
    ('ORD20250012VIP', 12, '瑞典宜家高端定制', 198000.00, 3, '2024-12-10 10:00:00'),
    ('ORD20250013VIP', 13, '法国罗奇堡艺术家具', 168000.00, 3, '2024-12-05 15:00:00'),
    ('ORD20250014VIP', 14, '西班牙宝路萨陶瓷', 138000.00, 3, '2024-11-28 11:30:00'),
    ('ORD20250015VIP', 15, '意大利Poliform家具', 128000.00, 3, '2024-11-20 09:00:00');
END$$

DELIMITER ;

-- 执行生成订单
CALL GenerateOrders();
DROP PROCEDURE GenerateOrders;

-- ============================================================
-- 3. 数据验证查询（用于对比看板数据）
-- ============================================================

-- 验证1：用户统计
SELECT 
    '用户统计' as check_item,
    COUNT(*) as total_users,
    SUM(CASE WHEN DATE(create_time) = CURDATE() THEN 1 ELSE 0 END) as today_new,
    SUM(CASE WHEN YEARWEEK(create_time) = YEARWEEK(CURDATE()) THEN 1 ELSE 0 END) as week_new,
    SUM(CASE WHEN YEAR(create_time) = YEAR(CURDATE()) AND MONTH(create_time) = MONTH(CURDATE()) THEN 1 ELSE 0 END) as month_new
FROM t_user WHERE deleted = 0;

-- 验证2：订单统计
SELECT 
    '订单统计' as check_item,
    COUNT(*) as total_orders,
    SUM(amount) as total_amount,
    SUM(CASE WHEN status = 0 THEN 1 ELSE 0 END) as pending_count,
    SUM(CASE WHEN status = 1 THEN 1 ELSE 0 END) as paid_count,
    SUM(CASE WHEN status = 2 THEN 1 ELSE 0 END) as shipped_count,
    SUM(CASE WHEN status = 3 THEN 1 ELSE 0 END) as completed_count,
    SUM(CASE WHEN status = 4 THEN 1 ELSE 0 END) as cancelled_count
FROM t_order WHERE deleted = 0;

-- 验证3：订单状态金额统计
SELECT 
    status,
    COUNT(*) as count,
    SUM(amount) as amount,
    AVG(amount) as avg_amount,
    MIN(amount) as min_amount,
    MAX(amount) as max_amount
FROM t_order 
WHERE deleted = 0 
GROUP BY status 
ORDER BY status;

-- 验证4：TOP 15订单
SELECT 
    o.id, o.order_no, u.username, o.product_name, o.amount, o.status, o.create_time
FROM t_order o
LEFT JOIN t_user u ON o.user_id = u.id
WHERE o.deleted = 0
ORDER BY o.amount DESC
LIMIT 15;

-- 验证5：近12个月用户注册趋势
SELECT 
    DATE_FORMAT(create_time, '%Y-%m') as month,
    COUNT(*) as user_count
FROM t_user 
WHERE deleted = 0 AND create_time >= DATE_SUB(CURDATE(), INTERVAL 12 MONTH)
GROUP BY DATE_FORMAT(create_time, '%Y-%m')
ORDER BY month;

-- 验证6：近30天每日订单趋势
SELECT 
    DATE(create_time) as date,
    COUNT(*) as order_count,
    SUM(amount) as daily_amount
FROM t_order 
WHERE deleted = 0 AND create_time >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)
GROUP BY DATE(create_time)
ORDER BY date DESC
LIMIT 30;

-- ============================================================
-- 数据生成完成
-- ============================================================
-- 用户总数：约 520 人
-- 订单总数：约 2035 个
-- 总金额：约 500-800 万元
-- 时间跨度：2024年4月 - 2025年3月
-- ============================================================

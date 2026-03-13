-- ============================================================
-- 数据看板测试数据脚本
-- 包含：用户注册趋势数据、订单状态分布、高额订单等
-- ============================================================

USE `user_management`;

-- ============================================================
-- 清理现有测试数据（保留原始5个用户和10个订单）
-- ============================================================
-- 注意：此脚本会追加数据，如需重置请手动清理

-- ============================================================
-- 1. 创建更多用户（模拟近一年的注册趋势）
-- ============================================================
-- 2024年4月-2025年3月的用户注册数据，用于展示趋势图

-- 2024年4月注册用户 (5人)
INSERT INTO `t_user` (`username`, `password`, `real_name`, `email`, `phone`, `role`, `status`, `create_time`) VALUES
('user_2024_04_01', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '陈明', 'chenming@example.com', '13900000001', 'USER', 1, '2024-04-05 10:30:00'),
('user_2024_04_02', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '林小红', 'linxiaohong@example.com', '13900000002', 'USER', 1, '2024-04-12 14:20:00'),
('user_2024_04_03', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '周建国', 'zhoujianguo@example.com', '13900000003', 'USER', 1, '2024-04-18 09:15:00'),
('user_2024_04_04', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '吴美丽', 'wumeili@example.com', '13900000004', 'USER', 1, '2024-04-25 16:45:00'),
('user_2024_04_05', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '郑强', 'zhengqiang@example.com', '13900000005', 'USER', 1, '2024-04-28 11:00:00');

-- 2024年5月注册用户 (8人)
INSERT INTO `t_user` (`username`, `password`, `real_name`, `email`, `phone`, `role`, `status`, `create_time`) VALUES
('user_2024_05_01', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '孙丽华', 'sunlihua@example.com', '13900000006', 'USER', 1, '2024-05-03 08:30:00'),
('user_2024_05_02', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '杨志远', 'yangzhiyuan@example.com', '13900000007', 'USER', 1, '2024-05-08 13:20:00'),
('user_2024_05_03', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '朱丽', 'zhuli@example.com', '13900000008', 'USER', 1, '2024-05-12 10:00:00'),
('user_2024_05_04', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '何伟', 'hewei@example.com', '13900000009', 'USER', 1, '2024-05-15 15:30:00'),
('user_2024_05_05', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '谢婷婷', 'xietingting@example.com', '13900000010', 'USER', 1, '2024-05-20 09:45:00'),
('user_2024_05_06', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '罗军', 'luojun@example.com', '13900000011', 'USER', 1, '2024-05-22 14:00:00'),
('user_2024_05_07', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '高敏', 'gaomin@example.com', '13900000012', 'USER', 1, '2024-05-25 11:30:00'),
('user_2024_05_08', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '马晓东', 'maxiaodong@example.com', '13900000013', 'USER', 1, '2024-05-28 16:00:00');

-- 2024年6月注册用户 (12人) - 618活动高峰期
INSERT INTO `t_user` (`username`, `password`, `real_name`, `email`, `phone`, `role`, `status`, `create_time`) VALUES
('user_2024_06_01', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '梁静', 'liangjing@example.com', '13800000011', 'USER', 1, '2024-06-01 08:00:00'),
('user_2024_06_02', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '宋凯', 'songkai@example.com', '13800000012', 'USER', 1, '2024-06-03 10:30:00'),
('user_2024_06_03', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '郭芳', 'guofang@example.com', '13800000013', 'USER', 1, '2024-06-05 14:20:00'),
('user_2024_06_04', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '林峰', 'linfeng@example.com', '13800000014', 'USER', 1, '2024-06-08 09:15:00'),
('user_2024_06_05', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '徐雪', 'xuxue@example.com', '13800000015', 'USER', 1, '2024-06-10 11:45:00'),
('user_2024_06_06', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '胡波', 'hubo@example.com', '13800000016', 'USER', 1, '2024-06-12 15:30:00'),
('user_2024_06_07', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '黄雅', 'huangya@example.com', '13800000017', 'USER', 1, '2024-06-15 08:45:00'),
('user_2024_06_08', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '曾磊', 'cenglei@example.com', '13800000018', 'USER', 1, '2024-06-16 13:00:00'),
('user_2024_06_09', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '彭丽', 'pengli@example.com', '13800000019', 'USER', 1, '2024-06-18 10:20:00'),
('user_2024_06_10', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '邓辉', 'denghui@example.com', '13800000020', 'USER', 1, '2024-06-20 16:15:00'),
('user_2024_06_11', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '萧然', 'xiaoran@example.com', '13800000021', 'USER', 1, '2024-06-22 09:30:00'),
('user_2024_06_12', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '冯雨', 'fengyu@example.com', '13800000022', 'USER', 1, '2024-06-25 14:45:00');

-- 2024年7月注册用户 (6人)
INSERT INTO `t_user` (`username`, `password`, `real_name`, `email`, `phone`, `role`, `status`, `create_time`) VALUES
('user_2024_07_01', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '潘虹', 'panhong@example.com', '13800000023', 'USER', 1, '2024-07-05 10:00:00'),
('user_2024_07_02', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '蒋文', 'jiangwen@example.com', '13800000024', 'USER', 1, '2024-07-10 11:30:00'),
('user_2024_07_03', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '蔡明', 'caiming@example.com', '13800000025', 'USER', 1, '2024-07-15 15:00:00'),
('user_2024_07_04', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '贾玲', 'jialing@example.com', '13800000026', 'USER', 1, '2024-07-18 09:20:00'),
('user_2024_07_05', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '魏东', 'weidong@example.com', '13800000027', 'USER', 1, '2024-07-22 13:45:00'),
('user_2024_07_06', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '薛梅', 'xuemei@example.com', '13800000028', 'USER', 1, '2024-07-28 16:30:00');

-- 2024年8月注册用户 (9人)
INSERT INTO `t_user` (`username`, `password`, `real_name`, `email`, `phone`, `role`, `status`, `create_time`) VALUES
('user_2024_08_01', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '叶凡', 'yefan@example.com', '13800000029', 'USER', 1, '2024-08-02 08:15:00'),
('user_2024_08_02', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '阎丽', 'yanli@example.com', '13800000030', 'USER', 1, '2024-08-06 10:45:00'),
('user_2024_08_03', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '余洋', 'yuyang@example.com', '13800000031', 'USER', 1, '2024-08-10 14:00:00'),
('user_2024_08_04', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '潘安', 'panan@example.com', '13800000032', 'USER', 1, '2024-08-12 09:30:00'),
('user_2024_08_05', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '杜娟', 'dujuan@example.com', '13800000033', 'USER', 1, '2024-08-15 11:00:00'),
('user_2024_08_06', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '戴军', 'daijun@example.com', '13800000034', 'USER', 1, '2024-08-18 15:20:00'),
('user_2024_08_07', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '夏冰', 'xiabing@example.com', '13800000035', 'USER', 1, '2024-08-20 10:00:00'),
('user_2024_08_08', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '钟山', 'zhongshan@example.com', '13800000036', 'USER', 1, '2024-08-25 13:30:00'),
('user_2024_08_09', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '汪峰', 'wangfeng@example.com', '13800000037', 'USER', 1, '2024-08-28 16:45:00');

-- 2024年9月注册用户 (7人)
INSERT INTO `t_user` (`username`, `password`, `real_name`, `email`, `phone`, `role`, `status`, `create_time`) VALUES
('user_2024_09_01', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '田甜', 'tiantian@example.com', '13800000038', 'USER', 1, '2024-09-03 09:00:00'),
('user_2024_09_02', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '任泉', 'renquan@example.com', '13800000039', 'USER', 1, '2024-09-08 11:30:00'),
('user_2024_09_03', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '姜武', 'jiangwu@example.com', '13800000040', 'USER', 1, '2024-09-12 14:15:00'),
('user_2024_09_04', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '范伟', 'fanwei@example.com', '13800000041', 'USER', 1, '2024-09-15 10:45:00'),
('user_2024_09_05', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '方程', 'fangcheng@example.com', '13800000042', 'USER', 1, '2024-09-18 15:00:00'),
('user_2024_09_06', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '石磊', 'shilei@example.com', '13800000043', 'USER', 1, '2024-09-22 08:30:00'),
('user_2024_09_07', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '廖凡', 'liaofan@example.com', '13800000044', 'USER', 1, '2024-09-25 12:00:00');

-- 2024年10月注册用户 (11人) - 国庆活动
INSERT INTO `t_user` (`username`, `password`, `real_name`, `email`, `phone`, `role`, `status`, `create_time`) VALUES
('user_2024_10_01', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '谭晶', 'tanjing@example.com', '13800000045', 'USER', 1, '2024-10-01 10:00:00'),
('user_2024_10_02', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '陆毅', 'luyi@example.com', '13800000046', 'USER', 1, '2024-10-03 11:30:00'),
('user_2024_10_03', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '金铭', 'jinming@example.com', '13800000047', 'USER', 1, '2024-10-05 14:00:00'),
('user_2024_10_04', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '韦唯', 'weiwei@example.com', '13800000048', 'USER', 1, '2024-10-07 09:15:00'),
('user_2024_10_05', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '贾岛', 'jiadao@example.com', '13800000049', 'USER', 1, '2024-10-10 15:30:00'),
('user_2024_10_06', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '孟非', 'mengfei@example.com', '13800000050', 'USER', 1, '2024-10-12 10:45:00'),
('user_2024_10_07', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '龙丹', 'longdan@example.com', '13800000051', 'USER', 1, '2024-10-15 13:00:00'),
('user_2024_10_08', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '万茜', 'wanqian@example.com', '13800000052', 'USER', 1, '2024-10-18 08:30:00'),
('user_2024_10_09', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '段誉', 'duanyu@example.com', '13800000053', 'USER', 1, '2024-10-20 11:00:00'),
('user_2024_10_10', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '雷佳音', 'leijiayin@example.com', '13800000054', 'USER', 1, '2024-10-22 14:30:00'),
('user_2024_10_11', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '钱江', 'qianjiang@example.com', '13800000055', 'USER', 1, '2024-10-25 16:00:00');

-- 2024年11月注册用户 (8人) - 双11活动
INSERT INTO `t_user` (`username`, `password`, `real_name`, `email`, `phone`, `role`, `status`, `create_time`) VALUES
('user_2024_11_01', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '汤唯', 'tangwei@example.com', '13800000056', 'USER', 1, '2024-11-01 09:00:00'),
('user_2024_11_02', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '殷桃', 'yintao@example.com', '13800000057', 'USER', 1, '2024-11-05 10:30:00'),
('user_2024_11_03', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '常远', 'changyuan@example.com', '13800000058', 'USER', 1, '2024-11-08 14:00:00'),
('user_2024_11_04', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '伍佰', 'wubai@example.com', '13800000059', 'USER', 1, '2024-11-10 11:15:00'),
('user_2024_11_05', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '余男', 'yunan@example.com', '13800000060', 'USER', 1, '2024-11-11 08:00:00'),
('user_2024_11_06', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '元华', 'yuanhua@example.com', '13800000061', 'USER', 1, '2024-11-15 15:30:00'),
('user_2024_11_07', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '顾长卫', 'guchangwei@example.com', '13800000062', 'USER', 1, '2024-11-18 10:00:00'),
('user_2024_11_08', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '平采娜', 'pingcaina@example.com', '13800000063', 'USER', 1, '2024-11-22 13:45:00');

-- 2024年12月注册用户 (10人) - 年终活动
INSERT INTO `t_user` (`username`, `password`, `real_name`, `email`, `phone`, `role`, `status`, `create_time`) VALUES
('user_2024_12_01', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '安以轩', 'anyixuan@example.com', '13800000064', 'USER', 1, '2024-12-01 10:00:00'),
('user_2024_12_02', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '齐秦', 'qiqin@example.com', '13800000065', 'USER', 1, '2024-12-05 11:30:00'),
('user_2024_12_03', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '易峰', 'yifeng@example.com', '13800000066', 'USER', 1, '2024-12-08 14:00:00'),
('user_2024_12_04', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '康辉', 'kanghui@example.com', '13800000067', 'USER', 1, '2024-12-10 09:30:00'),
('user_2024_12_05', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '伍思凯', 'wusikai@example.com', '13800000068', 'USER', 1, '2024-12-12 15:00:00'),
('user_2024_12_06', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '施瓦辛格', 'shiwaixinge@example.com', '13800000069', 'USER', 1, '2024-12-15 10:45:00'),
('user_2024_12_07', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '牛莉', 'niuli@example.com', '13800000070', 'USER', 1, '2024-12-18 13:00:00'),
('user_2024_12_08', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '毛不易', 'maobuyi@example.com', '13800000071', 'USER', 1, '2024-12-20 08:15:00'),
('user_2024_12_09', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '洪金宝', 'hongjinbao@example.com', '13800000072', 'USER', 1, '2024-12-22 11:30:00'),
('user_2024_12_10', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '葛平', 'geping@example.com', '13800000073', 'USER', 1, '2024-12-25 14:45:00');

-- 2025年1月注册用户 (6人)
INSERT INTO `t_user` (`username`, `password`, `real_name`, `email`, `phone`, `role`, `status`, `create_time`) VALUES
('user_2025_01_01', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '柳云龙', 'liuyunlong@example.com', '13800000074', 'USER', 1, '2025-01-05 10:00:00'),
('user_2025_01_02', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '管虎', 'guanhu@example.com', '13800000075', 'USER', 1, '2025-01-08 11:30:00'),
('user_2025_01_03', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '祝无双', 'zhuwushuang@example.com', '13800000076', 'USER', 1, '2025-01-12 14:00:00'),
('user_2025_01_04', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '贺涵', 'hehan@example.com', '13800000077', 'USER', 1, '2025-01-15 09:30:00'),
('user_2025_01_05', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '秦海璐', 'qinhailu@example.com', '13800000078', 'USER', 1, '2025-01-18 15:00:00'),
('user_2025_01_06', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '江一燕', 'jiangyiyan@example.com', '13800000079', 'USER', 1, '2025-01-22 10:45:00');

-- 2025年2月注册用户 (4人)
INSERT INTO `t_user` (`username`, `password`, `real_name`, `email`, `phone`, `role`, `status`, `create_time`) VALUES
('user_2025_02_01', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '白百何', 'baibaihe@example.com', '13800000080', 'USER', 1, '2025-02-05 10:00:00'),
('user_2025_02_02', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '柯震东', 'kezhendong@example.com', '13800000081', 'USER', 1, '2025-02-10 11:30:00'),
('user_2025_02_03', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '蓝盈莹', 'lanyingying@example.com', '13800000082', 'USER', 1, '2025-02-14 14:00:00'),
('user_2025_02_04', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '文咏珊', 'wenyongshan@example.com', '13800000083', 'USER', 1, '2025-02-20 09:30:00');

-- 2025年3月注册用户 (3人) - 今天注册的
INSERT INTO `t_user` (`username`, `password`, `real_name`, `email`, `phone`, `role`, `status`, `create_time`) VALUES
('user_2025_03_01', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '章若楠', 'zhangruonan@example.com', '13800000084', 'USER', 1, '2025-03-10 10:00:00'),
('user_2025_03_02', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '张晚意', 'zhangwanyi@example.com', '13800000085', 'USER', 1, '2025-03-11 14:30:00'),
('user_2025_03_today', '$2a$10$uJYYxbN5mMsu.5fms0KTS./3D1dZxldCfy2RTHNLbuzZ8jPq7UgU6', '今日用户', 'todayuser@example.com', '13800000086', 'USER', 1, NOW());

-- ============================================================
-- 2. 创建更多订单（各种状态、不同金额）
-- ============================================================

-- 待支付订单 (status = 0)
INSERT INTO `t_order` (`order_no`, `user_id`, `product_name`, `amount`, `status`, `create_time`) VALUES
('ORD20240011', 6, 'iPhone 15 Pro Max 256GB', 9999.00, 0, '2025-03-12 10:30:00'),
('ORD20240012', 7, 'MacBook Air M3 16GB', 11999.00, 0, '2025-03-12 11:00:00'),
('ORD20240013', 8, '索尼WH-1000XM5降噪耳机', 2499.00, 0, '2025-03-11 15:20:00'),
('ORD20240014', 9, '戴森V15吸尘器', 4999.00, 0, '2025-03-10 09:45:00'),
('ORD20240015', 10, '小米14 Ultra 16GB+1TB', 6999.00, 0, '2025-03-09 14:00:00'),
('ORD20240016', 11, 'iPad Pro 12.9英寸 M2', 8999.00, 0, '2025-03-08 10:15:00'),
('ORD20240017', 12, '佳能EOS R6 Mark II', 15999.00, 0, '2025-03-07 16:30:00'),
('ORD20240018', 13, '大疆Mavic 3 Pro无人机', 13888.00, 0, '2025-03-06 11:00:00');

-- 已支付订单 (status = 1)
INSERT INTO `t_order` (`order_no`, `user_id`, `product_name`, `amount`, `status`, `create_time`) VALUES
('ORD20240019', 14, 'LG 48英寸OLED电竞显示器', 8999.00, 1, '2025-03-05 09:00:00'),
('ORD20240020', 15, '罗技G Pro X机械键盘套装', 1299.00, 1, '2025-03-04 13:30:00'),
('ORD20240021', 16, 'Herman Miller人体工学椅', 12800.00, 1, '2025-03-03 10:45:00'),
('ORD20240022', 17, '三星990 Pro 4TB固态硬盘', 2899.00, 1, '2025-03-02 15:00:00'),
('ORD20240023', 18, 'NVIDIA RTX 4090显卡', 15999.00, 1, '2025-03-01 11:20:00'),
('ORD20240024', 19, 'Apple Watch Ultra 2', 6499.00, 1, '2025-02-28 14:30:00'),
('ORD20240025', 20, '索尼A7M4全画幅微单', 16999.00, 1, '2025-02-27 09:45:00'),
('ORD20240026', 21, 'Bose家庭影院音响套装', 8999.00, 1, '2025-02-26 16:00:00'),
('ORD20240027', 22, '飞利浦智能电动牙刷套装', 899.00, 1, '2025-02-25 10:30:00'),
('ORD20240028', 23, '科沃斯扫地机器人X2', 4599.00, 1, '2025-02-24 13:00:00');

-- 已发货订单 (status = 2)
INSERT INTO `t_order` (`order_no`, `user_id`, `product_name`, `amount`, `status`, `create_time`) VALUES
('ORD20240029', 24, '海尔双开门智能冰箱', 7999.00, 2, '2025-02-23 08:30:00'),
('ORD20240030', 25, '西门子洗碗机全能舱', 6899.00, 2, '2025-02-22 11:45:00'),
('ORD20240031', 26, '美的中央空调一拖四', 18999.00, 2, '2025-02-21 15:20:00'),
('ORD20240032', 27, '方太集成烹饪中心', 12999.00, 2, '2025-02-20 10:00:00'),
('ORD20240033', 28, '小米电视ES Pro 86英寸', 7999.00, 2, '2025-02-19 14:15:00'),
('ORD20240034', 29, '石头自清洁扫拖机器人G20', 4999.00, 2, '2025-02-18 09:30:00'),
('ORD20240035', 30, '添可智能洗地机芙万3.0', 3499.00, 2, '2025-02-17 16:45:00'),
('ORD20240036', 31, '松下智能马桶盖旗舰款', 3299.00, 2, '2025-02-16 11:00:00');

-- 已完成订单 (status = 3) - 包含一些高额订单
INSERT INTO `t_order` (`order_no`, `user_id`, `product_name`, `amount`, `status`, `create_time`) VALUES
('ORD20240037', 32, '特斯拉Model Y充电桩套装', 12999.00, 3, '2025-02-15 10:30:00'),
('ORD20240038', 33, '索尼85英寸Mini LED电视', 29999.00, 3, '2025-02-14 13:00:00'),
('ORD20240039', 34, '卡萨帝双子滚筒洗衣机', 15999.00, 3, '2025-02-13 15:30:00'),
('ORD20240040', 35, '老板电器厨房全套套装', 25888.00, 3, '2025-02-12 09:00:00'),
('ORD20240041', 36, 'A.O.史密斯全屋净水系统', 18999.00, 3, '2025-02-11 11:20:00'),
('ORD20240042', 37, '慕思智能床垫旗舰款', 32999.00, 3, '2025-02-10 14:45:00'),
('ORD20240043', 38, '芝华仕头等舱沙发组合', 19999.00, 3, '2025-02-09 10:00:00'),
('ORD20240044', 39, '顾家家居全屋定制套餐', 58888.00, 3, '2025-02-08 16:30:00'),
('ORD20240045', 40, '欧派整体橱柜定制', 45800.00, 3, '2025-02-07 08:45:00'),
('ORD20240046', 41, '索菲亚全屋衣柜定制', 38800.00, 3, '2025-02-06 12:00:00'),
('ORD20240047', 42, '尚品宅配全屋家具套餐', 52800.00, 3, '2025-02-05 15:15:00'),
('ORD20240048', 43, 'TOTO智能卫浴全套', 28999.00, 3, '2025-02-04 09:30:00'),
('ORD20240049', 44, '汉斯格雅花洒龙头套装', 18999.00, 3, '2025-02-03 11:45:00'),
('ORD20240050', 45, '科勒智能马桶旗舰款', 12999.00, 3, '2025-02-02 14:00:00'),
('ORD20240051', 46, '高仪恒温花洒系统', 15999.00, 3, '2025-02-01 10:30:00');

-- 已取消订单 (status = 4)
INSERT INTO `t_order` (`order_no`, `user_id`, `product_name`, `amount`, `status`, `create_time`) VALUES
('ORD20240052', 47, '戴森吹风机HD15', 3299.00, 4, '2025-01-31 13:00:00'),
('ORD20240053', 48, '徕卡Q3全画幅相机', 50800.00, 4, '2025-01-30 16:30:00'),
('ORD20240054', 49, '哈苏X2D中画幅相机', 89999.00, 4, '2025-01-29 09:00:00'),
('ORD20240055', 50, '富士GFX100S中画幅', 69800.00, 4, '2025-01-28 11:15:00'),
('ORD20240056', 51, 'Red Komodo电影机', 45800.00, 4, '2025-01-27 15:45:00');

-- 更多历史订单（用于丰富数据）
-- 2024年订单
INSERT INTO `t_order` (`order_no`, `user_id`, `product_name`, `amount`, `status`, `create_time`) VALUES
('ORD20240057', 6, '华为Mate60 Pro+', 8999.00, 3, '2024-12-15 10:00:00'),
('ORD20240058', 7, '荣耀Magic6至臻版', 6999.00, 3, '2024-12-10 14:30:00'),
('ORD20240059', 8, 'OPPO Find X7 Ultra', 5999.00, 3, '2024-11-20 09:15:00'),
('ORD20240060', 9, 'vivo X100 Pro+', 5499.00, 3, '2024-11-15 11:00:00'),
('ORD20240061', 10, '一加12 24GB+1TB', 5299.00, 3, '2024-10-25 15:30:00'),
('ORD20240062', 11, '真我GT5 Pro', 4299.00, 3, '2024-10-10 10:45:00'),
('ORD20240063', 12, 'iQOO 12 Pro', 4999.00, 3, '2024-09-18 13:00:00'),
('ORD20240064', 13, '红魔9 Pro+', 5999.00, 3, '2024-09-05 16:15:00'),
('ORD20240065', 14, 'ROG游戏手机8', 7999.00, 3, '2024-08-20 09:30:00'),
('ORD20240066', 15, '黑鲨5 Pro', 3999.00, 3, '2024-08-10 11:45:00'),
('ORD20240067', 16, '联想拯救者Y90', 4999.00, 3, '2024-07-15 14:00:00'),
('ORD20240068', 17, '华硕Zenfone 10', 4599.00, 3, '2024-07-05 10:00:00'),
('ORD20240069', 18, '谷歌Pixel 8 Pro', 6999.00, 3, '2024-06-20 15:30:00'),
('ORD20240070', 19, '三星S24 Ultra', 9699.00, 3, '2024-06-10 09:00:00'),
('ORD20240071', 20, '索尼Xperia 1 VI', 7999.00, 3, '2024-05-25 12:30:00'),
('ORD20240072', 21, '夏普AQUOS R9', 5999.00, 3, '2024-05-15 16:00:00'),
('ORD20240073', 22, '京瓷DuraForce Pro', 3999.00, 3, '2024-04-20 10:30:00'),
('ORD20240074', 23, '卡特彼勒CAT S75', 4599.00, 3, '2024-04-10 14:00:00'),
('ORD20240075', 24, '诺基亚XR21', 3299.00, 3, '2024-03-25 08:45:00'),
('ORD20240076', 25, '摩托罗拉Edge 40 Pro', 4299.00, 3, '2024-03-15 11:30:00');

-- 超高额订单（TOP 10展示用）
INSERT INTO `t_order` (`order_no`, `user_id`, `product_name`, `amount`, `status`, `create_time`) VALUES
('ORD20240077', 1, '保时捷设计全套家居定制', 288888.00, 3, '2025-01-15 10:00:00'),
('ORD20240078', 2, '意大利进口全屋定制家具', 168888.00, 3, '2025-01-10 14:30:00'),
('ORD20240079', 3, '德国柏丽整体厨房定制', 128888.00, 3, '2025-01-05 09:00:00'),
('ORD20240080', 4, '瑞士劳芬全套卫浴定制', 98888.00, 3, '2024-12-20 11:00:00'),
('ORD20240081', 5, '美国席梦思智能床垫系统', 86888.00, 3, '2024-12-15 15:30:00'),
('ORD20240082', 6, '丹麦BoConcept全屋家具', 76888.00, 3, '2024-12-10 10:00:00'),
('ORD20240083', 7, '日本骊住整体家装套餐', 65888.00, 3, '2024-11-25 13:30:00'),
('ORD20240084', 8, '瑞典宜家高端定制系列', 55888.00, 3, '2024-11-20 16:00:00'),
('ORD20240085', 9, '法国罗奇堡艺术家具套装', 48888.00, 3, '2024-11-15 09:30:00'),
('ORD20240086', 10, '西班牙宝路萨陶瓷全套', 42888.00, 3, '2024-11-10 12:00:00');

-- ============================================================
-- 数据插入完成
-- ============================================================
-- 用户总数：约 100 人（含原始5人）
-- 订单总数：约 86 个（含原始10个）
-- 覆盖状态：待支付、已支付、已发货、已完成、已取消
-- 时间跨度：2024年3月 - 2025年3月
-- ============================================================

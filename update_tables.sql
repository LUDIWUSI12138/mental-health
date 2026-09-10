-- 在 mental_health 数据库中执行此脚本，创建新增功能的表

-- 6. 心理知识文章表
CREATE TABLE IF NOT EXISTS `article` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `title` VARCHAR(200) NOT NULL,
    `summary` VARCHAR(500),
    `content` TEXT NOT NULL,
    `category` VARCHAR(50),
    `cover_url` VARCHAR(500),
    `view_count` INT DEFAULT 0,
    `status` INT DEFAULT 1,
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 7. 心情日记表
CREATE TABLE IF NOT EXISTS `mood_diary` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `mood_score` INT NOT NULL,
    `mood_emoji` VARCHAR(10),
    `content` TEXT,
    `tags` VARCHAR(200),
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 8. 咨询预约表
CREATE TABLE IF NOT EXISTS `appointment` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `counselor_name` VARCHAR(50) NOT NULL,
    `appointment_date` DATE NOT NULL,
    `appointment_time` VARCHAR(20) NOT NULL,
    `reason` TEXT,
    `status` VARCHAR(20) DEFAULT 'pending',
    `admin_reply` TEXT,
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 9. 系统公告表
CREATE TABLE IF NOT EXISTS `announcement` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `title` VARCHAR(200) NOT NULL,
    `content` TEXT NOT NULL,
    `type` VARCHAR(20) DEFAULT 'info',
    `status` INT DEFAULT 1,
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 插入初始数据
INSERT INTO `article` (`title`, `summary`, `content`, `category`, `view_count`) VALUES
('如何有效管理情绪？', '学会识别和调节自己的情绪是心理健康的基础。', '情绪管理是心理健康的重要组成部分。\n\n1. 觉察情绪：当情绪出现时，先停下来感受它。\n2. 接纳情绪：所有情绪都是正常的。\n3. 合理表达：通过写日记、倾诉等方式表达。\n4. 调节技巧：深呼吸、冥想、运动。\n5. 寻求帮助：必要时寻求专业帮助。', '情绪管理', 128),
('大学生常见压力及应对策略', '学业、就业、人际关系是大学生的主要压力来源。', '常见压力：\n1. 学业压力\n2. 就业压力\n3. 人际关系\n4. 经济压力\n5. 家庭压力\n\n应对策略：合理规划时间、学会说不、培养兴趣、建立支持系统。', '压力应对', 256),
('建立健康的人际关系', '良好的人际关系是心理健康的重要保障。', '建议：真诚沟通、学会倾听、尊重差异、设定边界、处理冲突、维护关系。', '人际关系', 89),
('认识自我，接纳自我', '自我认知是个人成长的第一步。', '包括：了解优缺点、明确价值观、认识情绪模式、理解行为动机。\n方法：写日记、接受反馈、尝试新事物、反思经历。', '自我认知', 167),
('改善睡眠质量的实用方法', '良好的睡眠是身心健康的基石。', '建议：建立规律作息、睡前1小时避免电子设备、营造舒适环境、避免咖啡因、适度运动。', '睡眠健康', 203),
('正念冥想入门指南', '正念冥想是缓解压力和焦虑的有效工具。', '步骤：找安静地方、关注呼吸、思绪飘走时温和带回、从5分钟开始。好处：减压、改善注意力、提高睡眠。', '情绪管理', 312);

INSERT INTO `mood_diary` (`user_id`, `mood_score`, `mood_emoji`, `content`, `tags`) VALUES
(2, 4, '😊', '今天和室友一起去了图书馆，学习效率很高！', '["学习","运动"]'),
(2, 3, '😐', '今天有点累，课程比较多。', '["学习"]'),
(2, 5, '😄', '期中考试成绩出来了，比预期的好很多！', '["学习","美食"]'),
(3, 2, '😢', '和男朋友吵架了，心情很低落。', '["恋爱","情绪"]'),
(3, 4, '😊', '今天天气很好，和闺蜜出去逛街了。', '["社交"]');

INSERT INTO `appointment` (`user_id`, `counselor_name`, `appointment_date`, `appointment_time`, `reason`, `status`) VALUES
(2, '李老师', '2026-07-28', '14:00-15:00', '最近学习压力比较大', 'pending'),
(3, '王老师', '2026-07-25', '10:00-11:00', '感情方面有些困扰', 'approved');

INSERT INTO `announcement` (`title`, `content`, `type`) VALUES
('2026年秋季学期心理健康测评通知', '请各位同学在8月30日前完成在线测评。', 'info'),
('心理咨询服务开放预约', '学校心理咨询中心现已开放线上预约。', 'activity'),
('关于调整心理援助热线的通知', '心理援助热线调整为 12320-5，24小时服务。', 'warning');

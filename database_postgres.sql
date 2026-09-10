-- ============================================
-- PostgreSQL 版：高校大学生心理健康测评与预警系统
-- 表名 user 改为 users，避免与 PG 保留字冲突
-- ============================================

DROP TABLE IF EXISTS announcement CASCADE;
DROP TABLE IF EXISTS appointment CASCADE;
DROP TABLE IF EXISTS mood_diary CASCADE;
DROP TABLE IF EXISTS article CASCADE;
DROP TABLE IF EXISTS warning CASCADE;
DROP TABLE IF EXISTS assessment CASCADE;
DROP TABLE IF EXISTS question CASCADE;
DROP TABLE IF EXISTS questionnaire CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- 1. 用户表
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    real_name VARCHAR(50),
    gender VARCHAR(10),
    age INT,
    college VARCHAR(100),
    major VARCHAR(100),
    phone VARCHAR(20),
    email VARCHAR(100),
    role VARCHAR(20) DEFAULT 'student',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. 问卷表
CREATE TABLE questionnaire (
    id SERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    total_score INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. 题目表
CREATE TABLE question (
    id SERIAL PRIMARY KEY,
    questionnaire_id INT NOT NULL REFERENCES questionnaire(id),
    content TEXT NOT NULL,
    type VARCHAR(20) DEFAULT 'single',
    options TEXT,
    sort_order INT DEFAULT 0
);

-- 4. 测评记录表（level 为 PG 保留字，需加双引号）
CREATE TABLE assessment (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(id),
    questionnaire_id INT NOT NULL REFERENCES questionnaire(id),
    total_score INT DEFAULT 0,
    "level" VARCHAR(20),
    is_warning INT DEFAULT 0,
    answers TEXT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 5. 预警记录表
CREATE TABLE warning (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(id),
    assessment_id INT NOT NULL REFERENCES assessment(id),
    warning_level VARCHAR(20),
    score INT,
    message TEXT,
    status VARCHAR(20) DEFAULT 'pending',
    handler_id INT,
    handle_time TIMESTAMP,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 6. 心理知识文章表
CREATE TABLE article (
    id SERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    summary VARCHAR(500),
    content TEXT NOT NULL,
    category VARCHAR(50),
    cover_url VARCHAR(500),
    view_count INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 7. 心情日记表
CREATE TABLE mood_diary (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(id),
    mood_score INT NOT NULL,
    mood_emoji VARCHAR(10),
    content TEXT,
    tags VARCHAR(200),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 8. 咨询预约表
CREATE TABLE appointment (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(id),
    counselor_name VARCHAR(50) NOT NULL,
    appointment_date DATE NOT NULL,
    appointment_time VARCHAR(20) NOT NULL,
    reason TEXT,
    status VARCHAR(20) DEFAULT 'pending',
    admin_reply TEXT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 9. 系统公告表
CREATE TABLE announcement (
    id SERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    type VARCHAR(20) DEFAULT 'info',
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- 初始数据
-- ============================================

-- 管理员账号 (密码: admin123)
INSERT INTO users (username, password, real_name, role)
VALUES ('admin', 'admin123', '系统管理员', 'admin');

-- 测试学生账号 (密码: 123456)
INSERT INTO users (username, password, real_name, gender, age, college, major, role)
VALUES
('2021001', '123456', '张三', '男', 20, '计算机学院', '软件工程', 'student'),
('2021002', '123456', '李四', '女', 19, '计算机学院', '计算机科学', 'student'),
('2021003', '123456', '王五', '男', 21, '文学院', '汉语言文学', 'student');

-- SCL-90心理健康自评量表（简化版10题）
INSERT INTO questionnaire (title, description, total_score, status)
VALUES ('SCL-90心理健康自评量表', '本量表用于评估您的心理健康状况，请根据近一周的实际感受作答。每题从"没有"到"严重"分别计1-5分。', 50, 1);

INSERT INTO question (questionnaire_id, content, type, options, sort_order) VALUES
(1, '1. 头痛', 'single', '[{"text":"没有","score":1},{"text":"很轻","score":2},{"text":"中等","score":3},{"text":"偏重","score":4},{"text":"严重","score":5}]', 1),
(1, '2. 神经衰弱，无法放松', 'single', '[{"text":"没有","score":1},{"text":"很轻","score":2},{"text":"中等","score":3},{"text":"偏重","score":4},{"text":"严重","score":5}]', 2),
(1, '3. 对事物不感兴趣', 'single', '[{"text":"没有","score":1},{"text":"很轻","score":2},{"text":"中等","score":3},{"text":"偏重","score":4},{"text":"严重","score":5}]', 3),
(1, '4. 感到孤独', 'single', '[{"text":"没有","score":1},{"text":"很轻","score":2},{"text":"中等","score":3},{"text":"偏重","score":4},{"text":"严重","score":5}]', 4),
(1, '5. 焦虑、紧张不安', 'single', '[{"text":"没有","score":1},{"text":"很轻","score":2},{"text":"中等","score":3},{"text":"偏重","score":4},{"text":"严重","score":5}]', 5),
(1, '6. 感到前途没有希望', 'single', '[{"text":"没有","score":1},{"text":"很轻","score":2},{"text":"中等","score":3},{"text":"偏重","score":4},{"text":"严重","score":5}]', 6),
(1, '7. 自责或感到无价值', 'single', '[{"text":"没有","score":1},{"text":"很轻","score":2},{"text":"中等","score":3},{"text":"偏重","score":4},{"text":"严重","score":5}]', 7),
(1, '8. 入睡困难或睡眠不好', 'single', '[{"text":"没有","score":1},{"text":"很轻","score":2},{"text":"中等","score":3},{"text":"偏重","score":4},{"text":"严重","score":5}]', 8),
(1, '9. 感到被他人监视或议论', 'single', '[{"text":"没有","score":1},{"text":"很轻","score":2},{"text":"中等","score":3},{"text":"偏重","score":4},{"text":"严重","score":5}]', 9),
(1, '10. 有想伤害自己的念头', 'single', '[{"text":"没有","score":1},{"text":"很轻","score":2},{"text":"中等","score":3},{"text":"偏重","score":4},{"text":"严重","score":5}]', 10);

-- 第二份问卷：大学生压力源量表
INSERT INTO questionnaire (title, description, total_score, status)
VALUES ('大学生压力源量表', '本量表用于评估您近期的主要压力来源，请根据实际情况选择。', 0, 1);

INSERT INTO question (questionnaire_id, content, type, options, sort_order) VALUES
(2, '1. 学业压力（课程难度、考试、论文等）', 'single', '[{"text":"没有","score":1},{"text":"偶尔","score":2},{"text":"有时","score":3},{"text":"经常","score":4},{"text":"总是","score":5}]', 1),
(2, '2. 就业压力（求职、实习、职业规划等）', 'single', '[{"text":"没有","score":1},{"text":"偶尔","score":2},{"text":"有时","score":3},{"text":"经常","score":4},{"text":"总是","score":5}]', 2),
(2, '3. 人际关系压力（室友、同学、恋爱等）', 'single', '[{"text":"没有","score":1},{"text":"偶尔","score":2},{"text":"有时","score":3},{"text":"经常","score":4},{"text":"总是","score":5}]', 3),
(2, '4. 经济压力（生活费、学费等）', 'single', '[{"text":"没有","score":1},{"text":"偶尔","score":2},{"text":"有时","score":3},{"text":"经常","score":4},{"text":"总是","score":5}]', 4),
(2, '5. 家庭压力（家庭关系、父母期望等）', 'single', '[{"text":"没有","score":1},{"text":"偶尔","score":2},{"text":"有时","score":3},{"text":"经常","score":4},{"text":"总是","score":5}]', 5),
(2, '6. 对未来的迷茫和不确定感', 'single', '[{"text":"没有","score":1},{"text":"偶尔","score":2},{"text":"有时","score":3},{"text":"经常","score":4},{"text":"总是","score":5}]', 6);

-- 心理知识文章
INSERT INTO article (title, summary, content, category, view_count) VALUES
('如何有效管理情绪？', '学会识别和调节自己的情绪是心理健康的基础。', '情绪管理是心理健康的重要组成部分。以下是几个实用技巧：

1. 觉察情绪：当情绪出现时，先停下来感受它，给它命名。
2. 接纳情绪：所有情绪都是正常的，不要压抑或否认。
3. 合理表达：通过写日记、与朋友倾诉等方式表达情绪。
4. 调节技巧：深呼吸、冥想、运动都是有效的情绪调节方式。
5. 寻求帮助：如果情绪持续困扰你，不要犹豫寻求专业帮助。', '情绪管理', 128),
('大学生常见压力及应对策略', '学业、就业、人际关系是大学生的主要压力来源。', '大学生活中常见的压力包括：

1. 学业压力：课程繁重、考试焦虑、论文deadline
2. 就业压力：求职竞争、实习选择、职业规划迷茫
3. 人际关系：室友矛盾、社交焦虑、恋爱困扰
4. 经济压力：生活费紧张、学费负担
5. 家庭压力：父母期望、家庭关系

应对策略：
- 合理规划时间，避免拖延
- 学会说"不"，设定合理界限
- 培养兴趣爱好，丰富生活
- 建立支持系统，与朋友家人保持联系
- 必要时寻求专业心理咨询', '压力应对', 256),
('建立健康的人际关系', '良好的人际关系是心理健康的重要保障。', '人际关系对心理健康有着深远影响。以下是一些健康人际关系的建议：

1. 真诚沟通：坦诚表达自己的想法和感受
2. 学会倾听：认真聆听他人，不急于评判
3. 尊重差异：每个人都有不同的观点和生活方式
4. 设定边界：保护自己的隐私和个人空间
5. 处理冲突：用平和的方式解决分歧
6. 维护关系：定期联系朋友，关心身边的人', '人际关系', 89),
('认识自我，接纳自我', '自我认知是个人成长的第一步。', '自我认知是指对自己的了解和理解，包括：

1. 了解自己的优点和缺点
2. 明确自己的价值观和人生目标
3. 认识自己的情绪模式
4. 理解自己的行为动机

如何提升自我认知：
- 写日记记录自己的想法和感受
- 接受他人的反馈
- 尝试新事物，发现自己的潜能
- 反思过去的经历
- 必要时寻求心理咨询师的帮助', '自我认知', 167),
('改善睡眠质量的实用方法', '良好的睡眠是身心健康的基石。', '睡眠问题在大学生中非常普遍。以下是改善睡眠的建议：

1. 建立规律的作息时间
2. 睡前1小时避免使用电子设备
3. 营造舒适的睡眠环境
4. 避免睡前摄入咖啡因和酒精
5. 适度运动，但避免睡前剧烈运动
6. 学习放松技巧，如渐进性肌肉放松
7. 如果躺下20分钟仍无法入睡，起来做些轻松的事', '睡眠健康', 203),
('正念冥想入门指南', '正念冥想是缓解压力和焦虑的有效工具。', '正念冥想是一种训练注意力和觉察力的练习：

入门步骤：
1. 找一个安静的地方坐下
2. 闭上眼睛，关注呼吸
3. 当思绪飘走时，温和地将注意力带回呼吸
4. 从每天5分钟开始，逐渐增加

正念的好处：
- 减轻压力和焦虑
- 改善注意力和专注力
- 增强情绪调节能力
- 提高睡眠质量
- 促进自我认知', '情绪管理', 312);

-- 心情日记初始数据
INSERT INTO mood_diary (user_id, mood_score, mood_emoji, content, tags) VALUES
(2, 4, '😊', '今天和室友一起去了图书馆，学习效率很高，晚上还打了篮球，感觉很不错！', '["学习","运动","社交"]'),
(2, 3, '😐', '今天有点累，课程比较多，作业也赶不完。不过还好，晚上看了会儿剧放松了一下。', '["学习","休息"]'),
(2, 5, '😄', '期中考试成绩出来了，比预期的好很多！晚上和朋友去吃了火锅庆祝。', '["学习","美食","社交"]'),
(3, 2, '😢', '和男朋友吵架了，心情很低落。不知道该怎么处理这种关系。', '["恋爱","情绪"]'),
(3, 4, '😊', '今天天气很好，和闺蜜出去逛街了，买了新衣服，心情变好了。', '["社交","购物"]');

-- 咨询预约示例数据
INSERT INTO appointment (user_id, counselor_name, appointment_date, appointment_time, reason, status) VALUES
(2, '李老师', '2026-07-28', '14:00-15:00', '最近学习压力比较大，想聊聊如何调整心态', 'pending'),
(3, '王老师', '2026-07-25', '10:00-11:00', '感情方面有些困扰，想寻求一些建议', 'approved');

-- 系统公告
INSERT INTO announcement (title, content, type) VALUES
('2026年秋季学期心理健康测评通知', E'各位同学：

为了解大家的心理健康状况，学校将开展2026年秋季学期心理健康测评。请各位同学在8月30日前完成在线测评。

测评链接：登录系统后进入"测评中心"

如有疑问请联系心理中心。', 'info'),
('心理咨询服务开放预约', E'学校心理咨询中心现已开放线上预约。如需心理咨询，请通过系统"咨询预约"功能进行预约。

咨询时间：周一至周五 9:00-17:00
咨询地点：行政楼302室', 'activity'),
('关于调整心理援助热线的通知', E'即日起，心理援助热线调整为 12320-5，24小时服务。如有紧急情况请随时拨打。', 'warning');

/*
  云借阅-图书管理系统 数据库脚本
  数据库：cloudlibrary
  数据库账号：root / 123456
  管理员账号：root / 123456
  普通用户：  张三 / 123456
  执行方式： mysql -uroot -p123456 < cloudlibrary.sql
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE DATABASE IF NOT EXISTS `cloudlibrary`
    DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `cloudlibrary`;

-- ----------------------------
-- 图书表 book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `book_id`         int(32)     NOT NULL AUTO_INCREMENT COMMENT '图书编号',
  `book_name`       varchar(32) DEFAULT NULL COMMENT '图书名称',
  `book_isbn`       varchar(32) DEFAULT NULL COMMENT '图书标准ISBN编号',
  `book_press`      varchar(32) DEFAULT NULL COMMENT '图书出版社',
  `book_author`     varchar(32) DEFAULT NULL COMMENT '图书作者',
  `book_pagination` int(11)     DEFAULT NULL COMMENT '图书页数',
  `book_price`      double(10,0) DEFAULT NULL COMMENT '图书价格',
  `book_uploadtime` varchar(32) DEFAULT NULL COMMENT '图书上架时间',
  `book_status`     varchar(1)  DEFAULT '0' COMMENT '图书状态（0：可借阅，1：借阅中，2：归还中，3：已下架）',
  `book_borrower`   varchar(32) DEFAULT NULL COMMENT '图书借阅人',
  `book_borrowtime` varchar(32) DEFAULT NULL COMMENT '图书借阅时间',
  `book_returntime` varchar(32) DEFAULT NULL COMMENT '图书预计归还时间',
  PRIMARY KEY (`book_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- 图书测试数据（状态分别覆盖可借阅、借阅中）
INSERT INTO `book` VALUES (1, 'Java基础案例教程（第2版）', '9787115547477', '人民邮电出版社', '张三', 291, 59, '2020-12-11', '0', NULL, NULL, NULL);
INSERT INTO `book` VALUES (2, '挪威的森林', '9787546205618', '上海译文出版社', '村上春树', 380, 34, '2020-12-12', '0', NULL, NULL, NULL);
INSERT INTO `book` VALUES (4, 'JavaWeb程序设计任务教程', '9787115439369', '人民邮电出版社', '张三', 419, 56, '2020-12-14', '0', NULL, NULL, NULL);
INSERT INTO `book` VALUES (5, 'Java基础入门（第2版）', '9787302511410', '清华大学出版社', '张三', 413, 59, '2020-12-15', '0', NULL, NULL, NULL);
INSERT INTO `book` VALUES (6, 'SpringCloud微服务架构开发', '9787115529046', '人民邮电出版社', '张三', 196, 43, '2020-12-16', '0', NULL, NULL, NULL);
INSERT INTO `book` VALUES (7, 'SpringBoot企业级开发教程', '9787115512796', '人民邮电出版社', '张三', 270, 56, '2020-12-17', '0', NULL, NULL, NULL);
INSERT INTO `book` VALUES (8, 'Spark大数据分析与实战', '9787302534327', '清华大学出版社', '张三', 228, 49, '2020-12-18', '0', NULL, NULL, NULL);
INSERT INTO `book` VALUES (10, '边城', '9787543067028', '武汉出版社', '沈从文', 368, 26, '2020-12-20', '0', NULL, NULL, NULL);
INSERT INTO `book` VALUES (12, '自在独行', '9787535488473', '长江文艺出版社', '贾平凹', 320, 39, '2020-12-22', '0', NULL, NULL, NULL);
INSERT INTO `book` VALUES (13, '沉默的巡游', '9787544280662', '南海出版公司', '东野圭吾', 400, 59, '2020-12-23', '1', '张三', '2020-12-11', '2020-12-23');

-- ----------------------------
-- 借阅记录表 record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record` (
  `record_id`        int(32) NOT NULL AUTO_INCREMENT COMMENT '借阅记录id',
  `record_bookname`  varchar(32) DEFAULT NULL COMMENT '借阅的图书名称',
  `record_bookisbn`  varchar(32) DEFAULT NULL COMMENT '借阅的图书的ISBN编号',
  `record_borrower`  varchar(32) DEFAULT NULL COMMENT '图书借阅人',
  `record_borrowtime` varchar(32) DEFAULT NULL COMMENT '图书借阅时间',
  `record_remandtime` varchar(32) DEFAULT NULL COMMENT '图书归还时间',
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `record` VALUES (23, 'Java基础案例教程（第2版）', '9787115547477', '张三', '2020-12-21', '2020-12-21');
INSERT INTO `record` VALUES (24, '沉默的巡游', '9787544280662', '张三', '2020-12-11', '2021-01-06');

-- ----------------------------
-- 用户（员工）表 user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id`          int(32) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_name`        varchar(32) DEFAULT NULL COMMENT '用户名称',
  `user_password`    varchar(32) DEFAULT NULL COMMENT '用户密码',
  `user_email`       varchar(32) DEFAULT NULL COMMENT '用户邮箱（用户账号）',
  `user_role`        varchar(32) DEFAULT NULL COMMENT '用户角色（admin：管理员，user：普通用户）',
  `user_status`      varchar(1)  DEFAULT '0' COMMENT '用户状态（0：正常，1：离职）',
  `user_hiredate`    varchar(32) DEFAULT NULL COMMENT '入职时间',
  `user_departuredate` varchar(32) DEFAULT NULL COMMENT '离职时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- 管理员：root / 123456 ；普通用户：张三 / 123456
-- 登录时账号既可以用邮箱，也可以用用户名
INSERT INTO `user` VALUES (1, 'root', '123456', 'root@cloudlibrary.com', 'admin', '0', '2020-12-01', NULL);
INSERT INTO `user` VALUES (2, '张三', '123456', 'zhangsan@itcast.cn', 'user', '0', '2020-12-02', NULL);
INSERT INTO `user` VALUES (3, '李四', '123456', 'lisi@itcast.cn', 'user', '0', '2020-12-03', NULL);

SET FOREIGN_KEY_CHECKS = 1;

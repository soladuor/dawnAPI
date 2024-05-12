/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 80031 (8.0.31)
 Source Host           : localhost:3306
 Source Schema         : unlimitedapi

 Target Server Type    : MySQL
 Target Server Version : 80031 (8.0.31)
 File Encoding         : 65001

 Date: 12/05/2024 22:59:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for identifier
-- ----------------------------
DROP TABLE IF EXISTS `identifier`;
CREATE TABLE `identifier`
(
    `key`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '标识名称',
    `value`       varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '值',
    `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
    PRIMARY KEY (`key`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of identifier
-- ----------------------------
INSERT INTO
    `identifier`
VALUES
    ('partnerId', '', '辩证云合作方Id');
INSERT INTO
    `identifier`
VALUES
    ('partnerToken', '', '工作室面板 Token');
INSERT INTO
    `identifier`
VALUES
    ('rootSrc', 'https://t.zydsoft.cn', '辩证云接口根路径（生产c/沙箱t）');
INSERT INTO
    `identifier`
VALUES
    ('secret', '', '辩证云秘钥');

-- ----------------------------
-- Table structure for whitelist
-- ----------------------------
DROP TABLE IF EXISTS `whitelist`;
CREATE TABLE `whitelist`
(
    `id`          int                                                           NOT NULL AUTO_INCREMENT COMMENT '用于唯一标识每个IP',
    `ip_address`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '存储IP地址的字段',
    `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '描述字段',
    `city`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'ip归属地',
    `created_at`  timestamp                                                     NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'IP添加到表中的时间戳',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 10
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of whitelist
-- ----------------------------
INSERT INTO
    `whitelist`
VALUES
    (1, '0:0:0:0:0:0:0:1', 'localhost', ' 本机地址', '2023-04-16 14:27:20');
INSERT INTO
    `whitelist`
VALUES
    (2, '127.0.0.1', '本机', ' 本机地址', '2023-04-16 16:49:42');

SET FOREIGN_KEY_CHECKS = 1;

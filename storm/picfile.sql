/*
 Navicat Premium Data Transfer

 Source Server         : localhost_mysql5,7
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : localhost:3307
 Source Schema         : storm

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 16/09/2022 16:51:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for picfile
-- ----------------------------
DROP TABLE IF EXISTS `picfile`;
CREATE TABLE `picfile`  (
  `location` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '位置',
  `name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '文件夹名',
  `ext` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '扩展名',
  `size` bigint(20) NULL DEFAULT NULL COMMENT '大小'
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

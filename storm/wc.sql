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

 Date: 16/09/2022 20:53:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for wc
-- ----------------------------
DROP TABLE IF EXISTS `wc`;
CREATE TABLE `wc`  (
  `word` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `word_count` int(11) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

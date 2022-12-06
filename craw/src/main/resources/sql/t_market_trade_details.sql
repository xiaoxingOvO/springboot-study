/*
 Navicat Premium Data Transfer

 Source Server         : TD_mysql8.0
 Source Server Type    : MySQL
 Source Server Version : 80031 (8.0.31)
 Source Host           : 124.222.47.254:3308
 Source Schema         : carbon

 Target Server Type    : MySQL
 Target Server Version : 80031 (8.0.31)
 File Encoding         : 65001

 Date: 06/12/2022 14:56:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_market_trade_details
-- ----------------------------
DROP TABLE IF EXISTS `t_market_trade_details`;
CREATE TABLE `t_market_trade_details` (
  `id` varchar(40) NOT NULL COMMENT '单日市场交易详情id',
  `market_id` varchar(40) DEFAULT NULL COMMENT '市场交易主表id',
  `date` date DEFAULT NULL COMMENT '日期',
  `current_time` varchar(40) DEFAULT NULL COMMENT '当前时间',
  `latest_price` decimal(20,2) DEFAULT NULL COMMENT '最新价',
  `volume` decimal(20,0) DEFAULT NULL COMMENT '成交量',
  `turnover` decimal(20,2) DEFAULT NULL COMMENT '成交金额',
  `avg_trade_price` decimal(20,2) DEFAULT NULL COMMENT '成交均价',
  `quote_change` decimal(20,2) DEFAULT NULL COMMENT '涨跌幅',
  `created_by_id` varchar(40) DEFAULT NULL COMMENT '创建人id',
  `created_by` varchar(50) DEFAULT NULL COMMENT '创建人姓名',
  `created_date` datetime DEFAULT NULL COMMENT '创建时间',
  `last_modified_by_id` varchar(40) DEFAULT NULL COMMENT '最后一次修改人id',
  `last_modified_by` varchar(50) DEFAULT NULL COMMENT '最后一次修改人姓名',
  `last_modified_date` datetime DEFAULT NULL COMMENT '最后一次修改时间',
  `delete` tinyint DEFAULT '0' COMMENT '删除标记,0为未删除，1为删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='市场交易单日详情';

SET FOREIGN_KEY_CHECKS = 1;

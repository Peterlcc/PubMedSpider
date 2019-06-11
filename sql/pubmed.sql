/*
Navicat MySQL Data Transfer

Source Server         : MYSQL
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : pubmed

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2019-06-11 15:27:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for articles
-- ----------------------------
DROP TABLE IF EXISTS `articles`;
CREATE TABLE `articles` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(255) DEFAULT NULL COMMENT '文章标题',
  `summary` text COMMENT '文章摘要',
  `pmid` varchar(100) DEFAULT NULL COMMENT 'PMID号',
  `url` varchar(255) DEFAULT NULL COMMENT '详情页对应的连接',
  `keyword` varchar(100) DEFAULT NULL COMMENT '查找的关键字',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=177 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for article_url
-- ----------------------------
DROP TABLE IF EXISTS `article_url`;
CREATE TABLE `article_url` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `url` varchar(255) DEFAULT NULL COMMENT '详情页请求连接',
  `keyword` varchar(100) DEFAULT NULL COMMENT '查找的关键字',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=434 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for conditions
-- ----------------------------
DROP TABLE IF EXISTS `conditions`;
CREATE TABLE `conditions` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `item` varchar(100) DEFAULT NULL COMMENT '查询内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

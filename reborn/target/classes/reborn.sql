/*
Navicat MySQL Data Transfer

Source Server         : 112.74.167.175
Source Server Version : 50628
Source Host           : 112.74.167.175:3306
Source Database       : reborn

Target Server Type    : MYSQL
Target Server Version : 50628
File Encoding         : 65001

Date: 2016-12-01 11:55:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(50) NOT NULL,
  `parent_id` int(10) NOT NULL,
  `url` varchar(50) DEFAULT '',
  PRIMARY KEY (`menu_id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '系统管理', '0', '');
INSERT INTO `menu` VALUES ('2', '商品管理', '0', '');
INSERT INTO `menu` VALUES ('3', '订单管理', '0', '');
INSERT INTO `menu` VALUES ('4', '菜单管理', '1', 'menu');
INSERT INTO `menu` VALUES ('5', '角色管理', '1', '');
INSERT INTO `menu` VALUES ('6', '数据管理', '1', '');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', 'admin');

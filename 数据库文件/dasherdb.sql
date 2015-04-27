/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50530
Source Host           : localhost:3306
Source Database       : dasherdb

Target Server Type    : MYSQL
Target Server Version : 50530
File Encoding         : 65001

Date: 2015-04-27 11:45:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `complain`
-- ----------------------------
DROP TABLE IF EXISTS `complain`;
CREATE TABLE `complain` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comId` varchar(255) DEFAULT NULL,
  `uid` varchar(255) DEFAULT NULL,
  `mid` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `imageName` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of complain
-- ----------------------------

-- ----------------------------
-- Table structure for `complain_deal`
-- ----------------------------
DROP TABLE IF EXISTS `complain_deal`;
CREATE TABLE `complain_deal` (
  `comId` varchar(255) NOT NULL DEFAULT '',
  `comResult` int(11) DEFAULT NULL,
  `comContent` varchar(255) DEFAULT NULL,
  `returnMoney` float DEFAULT '0',
  `deductMoney` float DEFAULT '0',
  PRIMARY KEY (`comId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of complain_deal
-- ----------------------------

-- ----------------------------
-- Table structure for `manager`
-- ----------------------------
DROP TABLE IF EXISTS `manager`;
CREATE TABLE `manager` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `mobilePhone` varchar(255) DEFAULT NULL,
  `type` int(255) DEFAULT NULL,
  `createBy` int(11) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `updateBy` int(11) DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of manager
-- ----------------------------

-- ----------------------------
-- Table structure for `market_commodity`
-- ----------------------------
DROP TABLE IF EXISTS `market_commodity`;
CREATE TABLE `market_commodity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mcid` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `subscribe` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of market_commodity
-- ----------------------------

-- ----------------------------
-- Table structure for `market_info`
-- ----------------------------
DROP TABLE IF EXISTS `market_info`;
CREATE TABLE `market_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `smid` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `subscribe` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `serviceTime` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  `latitude` varchar(255) DEFAULT NULL,
  `goodEvaluate` int(11) DEFAULT NULL,
  `badEvaluate` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of market_info
-- ----------------------------

-- ----------------------------
-- Table structure for `market_menu`
-- ----------------------------
DROP TABLE IF EXISTS `market_menu`;
CREATE TABLE `market_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mid` varchar(255) DEFAULT NULL,
  `smid` varchar(255) DEFAULT NULL,
  `uid` varchar(255) DEFAULT NULL,
  `wid` varchar(255) DEFAULT NULL,
  `dishsMoney` float DEFAULT '0',
  `carriageMoney` float DEFAULT '0',
  `taxesMoney` float DEFAULT '0',
  `serviceMoney` float DEFAULT '0',
  `tipMoney` float DEFAULT '0',
  `menuCount` int(11) DEFAULT NULL,
  `payType` int(11) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  `latitude` varchar(255) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of market_menu
-- ----------------------------

-- ----------------------------
-- Table structure for `market_menu_record`
-- ----------------------------
DROP TABLE IF EXISTS `market_menu_record`;
CREATE TABLE `market_menu_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mid` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `subscribe` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of market_menu_record
-- ----------------------------

-- ----------------------------
-- Table structure for `menu`
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mid` varchar(255) DEFAULT NULL,
  `sid` varchar(255) DEFAULT NULL,
  `uid` varchar(255) DEFAULT NULL,
  `wid` varchar(255) DEFAULT NULL,
  `dishsMoney` float DEFAULT '0',
  `carriageMoney` float DEFAULT '0',
  `taxesMoney` float DEFAULT '0',
  `serviceMoney` float DEFAULT '0',
  `tipMoney` float DEFAULT '0',
  `menuCount` int(11) DEFAULT NULL,
  `payType` int(11) DEFAULT NULL,
  `mealStartDate` datetime DEFAULT NULL,
  `mealEndDate` datetime DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  `latitude` varchar(255) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------

-- ----------------------------
-- Table structure for `menu_dish`
-- ----------------------------
DROP TABLE IF EXISTS `menu_dish`;
CREATE TABLE `menu_dish` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mid` varchar(255) DEFAULT NULL,
  `did` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` float(11,0) DEFAULT '0',
  `count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu_dish
-- ----------------------------

-- ----------------------------
-- Table structure for `menu_evaluate`
-- ----------------------------
DROP TABLE IF EXISTS `menu_evaluate`;
CREATE TABLE `menu_evaluate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` varchar(255) DEFAULT NULL,
  `mid` varchar(255) DEFAULT NULL,
  `uid` varchar(255) DEFAULT NULL,
  `wid` varchar(255) DEFAULT NULL,
  `evalShop` int(11) DEFAULT NULL,
  `evalServer` int(11) DEFAULT NULL,
  `evalContent` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu_evaluate
-- ----------------------------

-- ----------------------------
-- Table structure for `server_settle`
-- ----------------------------
DROP TABLE IF EXISTS `server_settle`;
CREATE TABLE `server_settle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `oldBalance` float DEFAULT '0',
  `settleType` int(11) DEFAULT NULL,
  `settlePrice` float DEFAULT '0',
  `curBalance` float DEFAULT '0',
  `settleNumber` varchar(255) DEFAULT NULL,
  `settleDesc` varchar(255) DEFAULT NULL,
  `createBy` varchar(11) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of server_settle
-- ----------------------------

-- ----------------------------
-- Table structure for `shop_dish`
-- ----------------------------
DROP TABLE IF EXISTS `shop_dish`;
CREATE TABLE `shop_dish` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` varchar(255) DEFAULT NULL,
  `did` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` float DEFAULT '0',
  `typeId` int(11) DEFAULT NULL,
  `chilies` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shop_dish
-- ----------------------------

-- ----------------------------
-- Table structure for `shop_dish_type`
-- ----------------------------
DROP TABLE IF EXISTS `shop_dish_type`;
CREATE TABLE `shop_dish_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `sortNum` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shop_dish_type
-- ----------------------------

-- ----------------------------
-- Table structure for `shop_info`
-- ----------------------------
DROP TABLE IF EXISTS `shop_info`;
CREATE TABLE `shop_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `typeTab` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `subscribe` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `logo` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  `latitude` varchar(255) DEFAULT NULL,
  `goodEvaluate` int(11) DEFAULT NULL,
  `badEvaluate` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shop_info
-- ----------------------------

-- ----------------------------
-- Table structure for `shop_type`
-- ----------------------------
DROP TABLE IF EXISTS `shop_type`;
CREATE TABLE `shop_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shop_type
-- ----------------------------

-- ----------------------------
-- Table structure for `user_address`
-- ----------------------------
DROP TABLE IF EXISTS `user_address`;
CREATE TABLE `user_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  `latitude` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_address
-- ----------------------------

-- ----------------------------
-- Table structure for `user_info`
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(255) DEFAULT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `account` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `equmentNumber` varchar(255) DEFAULT NULL,
  `logo` varchar(255) DEFAULT NULL,
  `balance` float DEFAULT '0',
  `mobilePhone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  `latitude` varchar(255) DEFAULT NULL,
  `goodEvaluate` int(11) DEFAULT NULL,
  `badEvaluate` int(11) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_info
-- ----------------------------

-- ----------------------------
-- Table structure for `user_settle`
-- ----------------------------
DROP TABLE IF EXISTS `user_settle`;
CREATE TABLE `user_settle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(255) DEFAULT NULL,
  `oldBalance` float DEFAULT '0',
  `settleType` int(11) DEFAULT NULL,
  `settlePrice` float DEFAULT '0',
  `curBalance` float DEFAULT '0',
  `settleNumber` varchar(255) DEFAULT NULL,
  `settleDesc` varchar(255) DEFAULT NULL,
  `createBy` varchar(11) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_settle
-- ----------------------------

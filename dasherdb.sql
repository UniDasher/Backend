/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50530
Source Host           : localhost:3306
Source Database       : dasherdb

Target Server Type    : MYSQL
Target Server Version : 50530
File Encoding         : 65001

Date: 2015-05-13 17:31:08
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
-- Table structure for `login`
-- ----------------------------
DROP TABLE IF EXISTS `login`;
CREATE TABLE `login` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loginId` varchar(255) DEFAULT NULL,
  `authCode` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `loginTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of login
-- ----------------------------
INSERT INTO `login` VALUES ('1', '2', '444dc519608648c0a63acc101fb28cb0', '0', '2015-05-13 15:58:01');
INSERT INTO `login` VALUES ('15', '6', '26fcffacaf44485c9668bd56b3b47dcd', '0', '2015-05-13 13:36:57');
INSERT INTO `login` VALUES ('16', '1', '1c3eb055af2740ddbedec4de9625ab64', '0', '2015-05-13 17:00:57');
INSERT INTO `login` VALUES ('17', '20150428145032', 'c61c24ae50f8445a932ceab3439eb8ca', '1', '2015-05-06 10:57:49');
INSERT INTO `login` VALUES ('18', '20150428144843', 'd7c15ef77f234132a51542aec77d5149', '0', '2015-05-12 15:27:00');

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
  `email` varchar(255) DEFAULT NULL,
  `type` int(255) DEFAULT NULL,
  `createBy` int(11) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `updateBy` int(11) DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of manager
-- ----------------------------
INSERT INTO `manager` VALUES ('1', 'sa', '946E554A715E2DCECA744EEEC54759AAAF76795700F581451BF81BC8', 'b7e45d0c65244a9f9d58fd7c36639023', 'sa', 'me', '137719049', '1', '0', '2015-04-27 14:15:06', '1', '2015-04-27 15:14:06', '1');
INSERT INTO `manager` VALUES ('2', 'admin', '946E554A715E2DCECA744EEEC54759AAAF76795700F581451BF81BC8', '6ac603e8ea7e49949ca9617be163d6d1', '', '', '', '0', '0', '2015-04-27 15:34:06', '2', '2015-04-30 11:20:49', '1');
INSERT INTO `manager` VALUES ('3', 'sa2', '946E554A715E2DCECA744EEEC54759AAAF76795700F581451BF81BC8', '6923e0922885487d8f1ae547ba5eb02f', 'a', 'm', '13771904', '0', '0', '2015-04-27 15:55:44', '3', '2015-04-29 13:40:45', '1');
INSERT INTO `manager` VALUES ('4', 'sa3', '946E554A715E2DCECA744EEEC54759AAAF76795700F581451BF81BC8', 'eb52ceeeed1d4cafa04c828b80c3668a', 's', 'ds', '123', '0', '0', '2015-04-29 14:29:42', null, null, '1');
INSERT INTO `manager` VALUES ('5', 'sal', '5726B3D7D2CB43DEAEA3B713771A6C73D4B713C19F1843418376FD16', '18ba96f253ee4c959cc39e0bafc0c878', 'a', 'm', '13771904@qq.com', '0', '2', '2015-05-04 11:06:34', null, null, '1');
INSERT INTO `manager` VALUES ('6', 'li', '5224D2F0C218344A98B8924325970D0881C7C41B63B5FA30CDB3DA8B', 'c4c4eae5cfac43dd8ba5ff4f37f7c190', 'a', 'm', '13771904@qq.com', '0', '2', '2015-05-04 11:11:40', null, null, '1');
INSERT INTO `manager` VALUES ('7', 'adminstrice', '7C64C6443C0A8D60A6E864B46A87F4D42C8BC16786B8FC1C932857E9', '85609ffda67240e0b9ec61b6505938fc', 'a', 'm', '13771904@163.com', '0', '6', '2015-05-04 11:15:25', null, null, '1');

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
  `createBy` varchar(255) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `updateBy` varchar(255) DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
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
  `chilies` varchar(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `createBy` int(11) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `updateBy` int(11) DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shop_dish
-- ----------------------------
INSERT INTO `shop_dish` VALUES ('1', '20150506153039', '20150511133812676', '扬州炒饭', '8', '1', '炒饭', '特色小吃', '2', '2015-05-11 12:55:59', null, null, '1');
INSERT INTO `shop_dish` VALUES ('2', '20150506153039', '20150511133842653', '麻辣米线', '10', '2', '很辣', '推荐菜', '2', '2015-05-11 13:08:04', null, null, '1');
INSERT INTO `shop_dish` VALUES ('3', '20150506155823', '20150511133842676', '牛肉拉面', '15', '1', '微辣', '特色菜', '2', '2015-05-11 13:38:42', null, null, '1');
INSERT INTO `shop_dish` VALUES ('4', '20150506153039', '20150511133844243', '麻辣烫', '15', '7', '很辣', '特色', '2', '2015-05-11 13:38:44', '2', '2015-05-11 13:43:51', '1');
INSERT INTO `shop_dish` VALUES ('5', '20150506160130', '20150511142743600', '鸭血粉丝', '10', '1', '很辣', '地道', '2', '2015-05-11 14:27:43', null, null, '1');
INSERT INTO `shop_dish` VALUES ('6', '20150506153039', '20150511142834926', '麻辣香锅', '15', '9', '很辣', '正宗', '2', '2015-05-11 14:28:34', null, null, '1');

-- ----------------------------
-- Table structure for `shop_dish_type`
-- ----------------------------
DROP TABLE IF EXISTS `shop_dish_type`;
CREATE TABLE `shop_dish_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `sortNum` int(11) DEFAULT NULL,
  `createBy` int(11) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `updateBy` int(11) DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shop_dish_type
-- ----------------------------
INSERT INTO `shop_dish_type` VALUES ('1', '苏州江浙', '1', '6', '2015-05-07 15:29:17', '2', '2015-05-08 16:25:45', '1');
INSERT INTO `shop_dish_type` VALUES ('2', '日韩料理', '2', '2', '2015-05-07 15:30:24', null, null, '1');
INSERT INTO `shop_dish_type` VALUES ('3', '火锅', '3', '2', '2015-05-07 15:43:12', '1', '2015-05-07 17:07:47', '1');
INSERT INTO `shop_dish_type` VALUES ('4', '小吃快餐', '4', '6', '2015-05-07 15:44:32', null, null, '1');
INSERT INTO `shop_dish_type` VALUES ('5', '江浙小吃', '6', '2', '2015-05-07 16:29:56', null, null, '1');
INSERT INTO `shop_dish_type` VALUES ('6', '江浙', '9', '1', '2015-05-07 16:30:56', null, null, '1');
INSERT INTO `shop_dish_type` VALUES ('7', '烧烤', '7', '2', '2015-05-08 11:22:20', null, null, '1');
INSERT INTO `shop_dish_type` VALUES ('8', '烧烤2', '5', '6', '2015-05-08 11:33:40', null, null, '1');
INSERT INTO `shop_dish_type` VALUES ('9', '烧烤3', '11', '1', '2015-05-08 11:34:43', null, null, '1');
INSERT INTO `shop_dish_type` VALUES ('10', '热菜', '8', '6', '2015-05-08 11:35:34', null, null, '1');
INSERT INTO `shop_dish_type` VALUES ('11', '冷菜', '10', '6', '2015-05-08 11:45:44', '2', '2015-05-08 16:25:45', '1');

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
  `createBy` int(11) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `updateBy` int(11) DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shop_info
-- ----------------------------
INSERT INTO `shop_info` VALUES ('1', '20150506153039', '麻辣米线', '特色', '江苏', '', '1371904649@qq.com', '13711321123', 'logo', '12', '12', null, null, '1', '2015-05-06 15:30:39', '2', '2015-05-13 15:41:09', '1');
INSERT INTO `shop_info` VALUES ('2', '20150506155823', '老妈米线', '特色', '江苏省苏州市姑苏区三香路', '', '137@qq.com', '13711321123', 'logo', '123', '54', null, null, '2', '2015-05-06 15:58:23', '2', '2015-05-13 15:23:47', '1');
INSERT INTO `shop_info` VALUES ('3', '20150506160130', '四海游龙', '特色', '江苏', '特色小吃', '137@qq.com', '13711321123', 'logo', '145', '62', null, null, '6', '2015-05-06 16:01:30', '2', '2015-05-06 16:14:55', '1');
INSERT INTO `shop_info` VALUES ('4', '20150513110935241', '豪客来', '美味', '中国', '美食', '13719222@qq.com', '13711321123', '/image/default.jpg', '112', '12', null, null, '6', '2015-05-13 11:09:35', null, null, '1');
INSERT INTO `shop_info` VALUES ('5', '20150513133331452', '美食街', '特色', '平江2', '特色小吃2', '1372@qq.com', '13711321123', '/image/default.jpg', '12', '12', null, null, '6', '2015-05-13 13:33:31', '1', '2015-05-13 13:37:35', '1');
INSERT INTO `shop_info` VALUES ('6', '20150513154226627', 'xf', 'sd', '江苏省苏州市姑苏区中街路200', '', '13@qq.com', '13723431345', '/image/default.jpg', '120.620898', '31.322418', null, null, '2', '2015-05-13 15:42:26', '2', '2015-05-13 15:58:13', '0');

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
  `createBy` varchar(255) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `updateBy` varchar(255) DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_address
-- ----------------------------
INSERT INTO `user_address` VALUES ('1', '20150508152851965', '徐州', '102', '30', '20150428145032', '2015-05-12 14:07:39', '', null, '1');
INSERT INTO `user_address` VALUES ('2', '20150508152851965', '徐州', '102', '30', '20150428145032', '2015-05-12 14:08:15', '20150428144843', '2015-05-12 15:25:10', '1');
INSERT INTO `user_address` VALUES ('3', '20150508152851965', '苏州', '102', '30', '20150428145032', '2015-05-12 14:09:07', null, null, '1');
INSERT INTO `user_address` VALUES ('4', '20150508152851965', '苏州', '102', '30', '20150428145032', '2015-05-12 14:10:09', '20150428144843', '2015-05-12 15:26:00', '1');
INSERT INTO `user_address` VALUES ('5', '20150428144843', '江苏省', '32', '12', '20150428144843', '2015-05-12 15:09:19', '20150428144843', '2015-05-12 15:25:10', '1');
INSERT INTO `user_address` VALUES ('6', '20150428144843', '国际科技园', '32', '12', '20150428144843', '2015-05-12 15:10:03', null, null, '1');
INSERT INTO `user_address` VALUES ('7', '20150428144843', '科技园', '42', '112', '20150428144843', '2015-05-12 15:10:38', '20150428144843', '2015-05-12 15:26:00', '2');

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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('1', '20150428144843', 'sdf', 'sd', 'liuqing', '946E554A715E2DCECA744EEEC54759AAAF76795700F581451BF81BC8', '53100d82c556461eb0b3afc1761a5c59', '1377649', null, '0', '1355523', '4821362', '苏州', '112', '23', null, null, null, '1');
INSERT INTO `user_info` VALUES ('2', '20150428145032', 'lsd', '4ds', 'liu', '946E554A715E2DCECA744EEEC54759AAAF76795700F581451BF81BC8', '89bbd7fc108549ec95a198d67239f2cf', '137719', 'logo', '0', '13771904', '133482', '平江区', '111', '111', null, null, null, '1');
INSERT INTO `user_info` VALUES ('3', '20150429152537', 'v', 'cxv', 'liu3', '1CE0E635F5B44C7810C1489FFA9141A8BF40359F2CCF47ADDCF5096C', '47becd2910fa474dae0847c58360df1d', '149', 'logo', '0', '1355523', '1334821362', '苏州', '112', '23', null, null, null, '2');
INSERT INTO `user_info` VALUES ('4', '20150505131241', 'et4', 'we', 'me', '7223D0248312822C9E9EF9500EDDD795AB8A20C989F74B1483ECC8DF', 'c2b97345aff64dffb82c650fd62473fb', '13771904649', 'logo', '0', '13711911043', '13362@163.com', '江苏', '112', '23', null, null, null, '1');
INSERT INTO `user_info` VALUES ('5', '20150505131254', '34', 'sd', 'mee', '8B4CD0A74C67BE890A4000D73B3FE998A9B189A65DD5110B43EB0CD7', '7c10106288ee448293530fcb33dd081f', '1649', 'logo', '0', '13711911043', '13362@163.com', '江苏', '112', '23', null, null, null, '2');
INSERT INTO `user_info` VALUES ('6', '20150505131348', 'mg', 'ds', 'tt', 'CE1D2F1AB71DC75032111AAD9E1790AB178A41CBB9033BAEADEAD3A2', '252d963133ed478b910808040abccec5', '137719', 'logo', '0', '13711911043', '13362@163.com', '徐州', '112', '23', null, null, null, '2');
INSERT INTO `user_info` VALUES ('7', '20150508152851965', 'liu', 'qing', 'sa', '2C2571F07529A51BBDEC3540B30B76916A2DEE411137858950A7D803', '1db013542f3b4825914e506424369c86', '13774504649', 'logo', '0', '137719049', '121362@qq.com', '苏州', '112', '23', null, null, null, '1');

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

/*
Navicat MySQL Data Transfer

Source Server         : mysql56
Source Server Version : 50634
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50634
File Encoding         : 65001

Date: 2017-08-22 18:21:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for moviesubjects
-- ----------------------------
DROP TABLE IF EXISTS `moviesubjects`;
CREATE TABLE `moviesubjects` (
  `movieId` int(11) NOT NULL AUTO_INCREMENT,
  `doubanMovieId` int(11) DEFAULT NULL,
  `name` text,
  `director` text,
  `actors` text,
  `country` text,
  `language` text,
  `releaseData` text,
  `runTime` text,
  `ratingNum` float DEFAULT NULL,
  `tags` text,
  `type` text,
  `url` varchar(255) DEFAULT '',
  `urlMd5` varchar(255) DEFAULT '',
  PRIMARY KEY (`movieId`),
  UNIQUE KEY `un_ix_url_md5` (`urlMd5`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*
Navicat MySQL Data Transfer

Source Server         : mysql56
Source Server Version : 50634
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50634
File Encoding         : 65001

Date: 2017-08-11 18:05:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments` (
  `commentsId` int(11) NOT NULL AUTO_INCREMENT,
  `doubanMovieId` int(11) DEFAULT NULL,
  `movieName` text,
  `commentInfo` longtext,
  `commentAuthor` varchar(100) NOT NULL DEFAULT '',
  `commentVote` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`commentsId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
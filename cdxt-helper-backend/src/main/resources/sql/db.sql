/*
SQLyog Enterprise v12.08 (64 bit)
MySQL - 5.7.29-log : Database - cdxt
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`cdxt` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;

USE `cdxt`;

/*Table structure for table `tb_deploy_post` */

DROP TABLE IF EXISTS `tb_deploy_post`;

CREATE TABLE `tb_deploy_post` (
  `id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `hospital_id` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '医院id',
  `hospital_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '医院名称',
  `remark` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `modification_src` varchar(3000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新路径',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `post_level` int(2) DEFAULT NULL,
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `state` int(2) NOT NULL DEFAULT '0' COMMENT '0 未处理 1 已处理',
  `uid` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `tb_deploy_post` */

insert  into `tb_deploy_post`(`id`,`hospital_id`,`hospital_name`,`remark`,`modification_src`,`gmt_create`,`post_level`,`gmt_modified`,`state`,`uid`) values ('1265649089584836608','1','测试医院',' 测试','/sdfsdf','2020-05-27 22:24:25',NULL,'2020-05-27 22:24:20',1,'1265647429739417600'),('1267076412582445056','1',NULL,NULL,NULL,'2020-05-31 20:52:12',NULL,'2020-05-31 20:52:12',1,'1265647429739417600'),('1267077347064987648','1',NULL,NULL,NULL,'2020-05-31 20:55:55',NULL,'2020-05-31 20:55:55',1,'1265647429739417600'),('1267077581073596416','1',NULL,NULL,NULL,'2020-05-31 20:56:51',NULL,'2020-05-31 20:56:51',1,'1265647429739417600'),('1267077848196235264','1',NULL,NULL,NULL,'2020-05-31 20:57:54',NULL,'2020-05-31 20:57:54',1,'1265647429739417600'),('1267094082375712768',NULL,NULL,NULL,NULL,'2020-05-31 22:03:01',NULL,'2020-05-31 22:03:01',1,'1265647429739417600'),('1267095550923120640','1',NULL,'是打发斯蒂芬','水电费','2020-05-31 22:08:32',0,'2020-05-31 22:08:32',1,'1265647429739417600');

/*Table structure for table `tb_hospital` */

DROP TABLE IF EXISTS `tb_hospital`;

CREATE TABLE `tb_hospital` (
  `id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '医院名称',
  `branch` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '对应分支名称',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `tb_hospital` */

insert  into `tb_hospital`(`id`,`name`,`branch`,`gmt_create`,`gmt_modified`) values ('1','测试医院','master','2020-05-27 22:25:33','2020-05-27 22:25:29');

/*Table structure for table `tb_user` */

DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '密码',
  `role` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '角色',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `tb_user` */

insert  into `tb_user`(`id`,`username`,`password`,`role`,`gmt_create`,`gmt_modified`) values ('1265647429739417600','user','123456','user','2020-05-27 22:13:56','2020-05-27 22:13:56');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

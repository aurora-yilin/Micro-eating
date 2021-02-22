/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.7.29 : Database - Micro-eating
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`Micro-eating` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `Micro-eating`;

/*Table structure for table `OrderInfo` */

DROP TABLE IF EXISTS `OrderInfo`;

CREATE TABLE `OrderInfo` (
  `order_id` varchar(128) NOT NULL COMMENT '标志一个订单的唯一编号',
  `user_name` varchar(64) NOT NULL COMMENT '订单所属的用户',
  `order_subject` varchar(128) DEFAULT NULL COMMENT '订单的subject',
  `order_body` text COMMENT '订单的描述信息',
  `order_create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单的创建时间',
  `order_statu` int(11) NOT NULL COMMENT '标志订单的当前状态（1为支付成功2为未支付3为退款）',
  `order_pay_mode` int(11) NOT NULL COMMENT '标志订单的支付方式，（1为支付宝，2为微信支付）',
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `OrderInfo_order_id_uindex` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='存放订单信息的表';

/*Data for the table `OrderInfo` */

insert  into `OrderInfo`(`order_id`,`user_name`,`order_subject`,`order_body`,`order_create_time`,`order_statu`,`order_pay_mode`) values ('0041fa72b4c448da83ca793d93bad203','admin','【微膳食】','测试用户','2020-12-15 17:29:45',3,1),('019f0cb930eb4069a62d72d6ac24f246','admin','【微膳食】','测试订单数据库','2020-12-14 18:07:10',2,1),('61f91617c2d644d084827bce99794aba','admin','【微膳食】','testtest','2020-12-15 11:37:53',2,1),('8f696bd7903e42d5ae4212f0ddcff952','admin','【微膳食】','测试用户','2020-12-15 17:56:40',3,1),('9467c2a1ff214c2a9925fc907a8824c6','admin','【微膳食】','测试用户1234','2020-12-15 11:35:06',2,1),('f35d92b50e7c4732ad6b7e571af8a1cb','admin','【微膳食】','测试订单数据库','2020-12-08 16:56:41',3,1);

/*Table structure for table `authority` */

DROP TABLE IF EXISTS `authority`;

CREATE TABLE `authority` (
  `authority_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `authority_name` varchar(10) NOT NULL COMMENT '权限名',
  PRIMARY KEY (`authority_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

/*Data for the table `authority` */

insert  into `authority`(`authority_id`,`authority_name`) values (1,'admin'),(2,'guest');

/*Table structure for table `commodity_info` */

DROP TABLE IF EXISTS `commodity_info`;

CREATE TABLE `commodity_info` (
  `commodity_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品的唯一标识id',
  `commodity_name` varchar(256) NOT NULL COMMENT '商品的名字',
  `commodity_Details` text COMMENT '商品的详情',
  `commodity_price` double NOT NULL COMMENT '商品价格',
  PRIMARY KEY (`commodity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

/*Data for the table `commodity_info` */

insert  into `commodity_info`(`commodity_id`,`commodity_name`,`commodity_Details`,`commodity_price`) values (1,'鱼香肉丝','鱼香肉丝是一道常见川菜，上河帮川菜的代表菜色之一。 鱼香是川菜主要传统味型之一。 成菜具有鱼香味，但其味并不直接来自鱼，而是由醋、泡红辣椒、薑等调味品调制出的。',33),(2,'红烧肉','红烧肉，一道著名的大众菜肴，各大菜系都有自己特色的红烧肉。其以五花肉为制作主料，最好选用肥瘦相间的三层肉（五花肉）来做，锅具以砂锅为主，做出来的肉肥瘦相间，香甜松软，营养丰富，入口即化。\r\n红烧肉在我国各地流传甚广，做法多达二三十种，具有一定的营养价值',99),(3,'开水白菜','开水白菜以北方的大白菜心来制作，配以用鸡，鸭，排骨熬煮，并用鸡肉蓉，猪肉蓉澄澈的高汤调味，最后浇汤时在汤里淋一些鸡油。 成菜后，清鲜淡雅，香味浓醇，汤味浓厚，不油不腻，却清香爽口。 ... 这菜听似朴实无华，然则尽显上乘的制汤功夫。',999),(4,'佛跳墙','又名满坛香、福寿全，是福建福州的当地名菜，属闽菜系。 相传，它是在清道光年间由福州聚春园菜馆老板郑春发研制出来的。 佛跳墙富含营养，可促进发育，美容，延缓衰老，增强免疫力，乃进补佳品',999);

/*Table structure for table `evaluation` */

DROP TABLE IF EXISTS `evaluation`;

CREATE TABLE `evaluation` (
  `evaluation_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评价id',
  `evaluation_content` text COMMENT '评价内容',
  `evaluation_belong_user` varchar(128) NOT NULL COMMENT '评价所属用户',
  `evaluation_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评价时间',
  `evaluation_belong_commodity_id` int(11) NOT NULL,
  PRIMARY KEY (`evaluation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COMMENT='评价表';

/*Data for the table `evaluation` */

insert  into `evaluation`(`evaluation_id`,`evaluation_content`,`evaluation_belong_user`,`evaluation_date`,`evaluation_belong_commodity_id`) values (1,'鱼香肉丝美味','test','2020-12-14 16:08:24',1),(2,'鱼香肉丝太好吃了','123','2020-12-14 16:08:45',1),(3,'红烧肉肥而不腻','test','2020-12-15 09:35:23',2),(4,'红烧肉健康美味','123','2020-12-15 09:35:23',2),(5,'红烧肉香','name','2020-12-15 09:47:54',2),(6,'红烧肉无敌','name','2020-12-15 10:21:40',2),(7,'开水煮白菜真好吃','test','2020-12-15 16:30:38',3),(9,'佛跳墙真好吃','test','2020-12-15 17:02:12',4),(10,'这家的佛跳墙绝了','test','2020-12-15 17:04:12',4),(11,'这家的佛跳墙简直无敌','name','2020-12-15 17:05:30',4),(14,'这家的佛跳墙简直太好吃了','123','2020-12-15 17:29:09',4);

/*Table structure for table `image` */

DROP TABLE IF EXISTS `image`;

CREATE TABLE `image` (
  `image_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '图片id',
  `image_path` varchar(256) NOT NULL COMMENT '图片的路径',
  `image_belong_user` varchar(128) DEFAULT NULL COMMENT '图片所属的用户',
  `image_belong_commodity_id` int(11) NOT NULL COMMENT '图片所属商品的id',
  `image_upload_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '图片的上传时间',
  PRIMARY KEY (`image_id`),
  UNIQUE KEY `image_image_path_uindex` (`image_path`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4;

/*Data for the table `image` */

insert  into `image`(`image_id`,`image_path`,`image_belong_user`,`image_belong_commodity_id`,`image_upload_date`) values (1,'a93f1ad93e6d4c54aaf3a112e11a1603.jpg','admin',1,'2020-12-14 16:05:44'),(2,'2cb013b9b0d6450a962f325ef518ef1c.jpg','admin',2,'2020-12-14 16:06:32'),(3,'banner-slider1.png','test',1,'2020-12-14 16:10:22'),(4,'banner-slider2.png','123',1,'2020-12-14 16:10:41'),(6,'banner-slider3.png','123',1,'2020-12-14 17:50:14'),(7,'123.png','test',2,'2020-12-15 09:12:29'),(8,'456.png','123',2,'2020-12-15 09:12:47'),(9,'789.png','123',2,'2020-12-15 09:13:04'),(10,'123456.png','admin',1,'2020-12-15 09:14:30'),(11,'456789.png','admin',2,'2020-12-15 09:14:30'),(13,'123fjasldf.png','admin',3,'2020-12-15 15:29:56'),(14,'123fjafsdfaxcvxc.png','admin',3,'2020-12-15 15:30:40'),(15,'fotiaoqiang1.jpg','admin',4,'2020-12-15 16:10:03'),(16,'fotiaoqiang2.png','admin',4,'2020-12-15 16:10:03'),(18,'fotiaoqiang3.jpg','test',4,'2020-12-15 17:02:12'),(19,'fotiaoqiang4.png','test',4,'2020-12-15 17:02:12'),(20,'fotiaoqiang6.jpg','test',4,'2020-12-15 17:04:12'),(21,'fotiaoqiang8.png','test',4,'2020-12-15 17:04:12'),(22,'fotiaoqiang15.jpg','name',4,'2020-12-15 17:05:30'),(23,'fotiaoqiang19.png','name',4,'2020-12-15 17:05:30'),(26,'fotiaoqiang25.jpg','123',4,'2020-12-15 17:29:09'),(27,'fotiaoqiang37.png','123',4,'2020-12-15 17:29:09');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_name` varchar(10) NOT NULL,
  `password` varchar(128) NOT NULL,
  `email` varchar(64) DEFAULT NULL,
  `head_image` varchar(256) NOT NULL DEFAULT '/image/default.jpg',
  `phone_num` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`user_name`),
  UNIQUE KEY `mail` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `user` */

insert  into `user`(`user_name`,`password`,`email`,`head_image`,`phone_num`) values ('admin','$2a$10$avh.uIcqfDiO3wVixSk0RO6u3yoFMhAoDjnvUxPma9ry4duZXQF82','957197536@qq.com','/image/default.jpg','18538765325'),('test','$2a$10$avh.uIcqfDiO3wVixSk0RO6u3yoFMhAoDjnvUxPma9ry4duZXQF82','1342992351@qq.com','/image/default.jpg','15238651809');

/*Table structure for table `user_authority` */

DROP TABLE IF EXISTS `user_authority`;

CREATE TABLE `user_authority` (
  `user_name` varchar(10) NOT NULL,
  `authority_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `user_authority` */

insert  into `user_authority`(`user_name`,`authority_id`) values ('admin',1),('admin',2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

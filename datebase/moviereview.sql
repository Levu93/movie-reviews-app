/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.11 : Database - moviereview
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`moviereview` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;

USE `moviereview`;

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` text COLLATE utf8_unicode_ci NOT NULL,
  `movie` int(11) NOT NULL,
  `user` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `movie` (`movie`),
  KEY `user` (`user`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`movie`) REFERENCES `movie` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`user`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `comment` */

insert  into `comment`(`id`,`text`,`movie`,`user`) values (1,'Amazing movie for real.',1,'admin'),(9,'It just failed overall to make a decent movie, and I am tired about giving movie justice just because \'at least the effects were good\' or \'at least the sound was great\'. If the film can\'t stand up with its own story, then it is a bad film. And as sorry as I am to say, this is a bad film.',7,'user'),(11,'Dead Men Tell No Tales does\'t worry about building a trilogy of films (there is no cliffhanger) and takes the time to tell its own story on its own terms. The story is easy to follow (which is saying something for this series), the action isn\'t confusing, and the situations are often fun and funny. You can\'t say any of that about the previous sequel.',5,'user'),(12,'Perhaps the most compelling thing about seeing The Circle, is the glow of people checking their phones as soon as the credits rolled. This is going to be a film people claim they understand when they don\'t even understand the controlled lives they are living. In all a genuinely creepy film carried by Emma Watson, not something for your average Marvel fan though.',6,'user'),(16,'Very nice movie. :D',5,'admin'),(17,'Disappointment :(',7,'admin');

/*Table structure for table `movie` */

DROP TABLE IF EXISTS `movie`;

CREATE TABLE `movie` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `released` date NOT NULL,
  `description` text COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `movie` */

insert  into `movie`(`id`,`name`,`released`,`description`) values (1,'Split','2017-01-20','Three girls are kidnapped by a man with a diagnosed 23 distinct personalities. They must try to escape before the apparent emergence of a frightful new 24th.'),(5,'Pirates of the Caribbean: Dead Men Tell No Tales','2017-05-26','Captain Jack Sparrow searches for the trident of Poseidon.'),(6,'The Circle','2017-04-28','A woman lands a dream job at a powerful tech company called the Circle, only to uncover an agenda that will affect the lives of all of humanity.'),(7,'Alien: Covenant','2017-05-19','The crew of a colony ship, bound for a remote planet, discover an uncharted paradise with a threat beyond their imagination, and must attempt a harrowing escape.');

/*Table structure for table `rating` */

DROP TABLE IF EXISTS `rating`;

CREATE TABLE `rating` (
  `movie` int(11) NOT NULL,
  `user` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `rating` int(11) NOT NULL,
  PRIMARY KEY (`movie`,`user`),
  KEY `user` (`user`),
  CONSTRAINT `rating_ibfk_1` FOREIGN KEY (`movie`) REFERENCES `movie` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `rating_ibfk_2` FOREIGN KEY (`user`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `rating` */

insert  into `rating`(`movie`,`user`,`rating`) values (1,'admin',9),(5,'admin',10),(5,'user',8),(6,'admin',4),(6,'user',10),(7,'admin',5),(7,'user',2);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `username` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `surname` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `role` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `user` */

insert  into `user`(`username`,`password`,`name`,`surname`,`role`) values ('admin','admin','admin','admin','admin'),('user','user','User','Uzerovic','user');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

CREATE DATABASE  IF NOT EXISTS `jtutor` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `jtutor`;
-- MySQL dump 10.13  Distrib 5.7.12, for osx10.9 (x86_64)
--
-- Host: localhost    Database: jtutor
-- ------------------------------------------------------
-- Server version	5.7.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `answer`
--

DROP TABLE IF EXISTS `answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `answer_valid` bit(1) NOT NULL,
  `answer_value` varchar(255) NOT NULL,
  `question_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8frr4bcabmmeyyu60qt7iiblo` (`question_id`),
  CONSTRAINT `FK8frr4bcabmmeyyu60qt7iiblo` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answer`
--

LOCK TABLES `answer` WRITE;
/*!40000 ALTER TABLE `answer` DISABLE KEYS */;
INSERT INTO `answer` VALUES (3,'\0','Inner class.',3),(4,'\0','Outer class.',3),(5,'','Sub-class.',3),(6,'\0','Super class.',3),(7,'\0','Public',4),(8,'\0','Protected',4),(9,'\0','Default',4),(10,'','Private',4),(11,'\0','8',5),(12,'','4',5),(13,'\0','2',5),(14,'\0','16',5),(15,'\0','output=0',6),(16,'\0','output=6',6),(17,'\0','output=5',6),(18,'','output=4',6),(19,'','NO',7),(20,'\0','YES',7),(21,'\0','YES',8),(22,'','NO',8),(23,'\0','4',9),(24,'\0','3.7',9),(25,'','3',9),(26,'\0','0',9),(27,'','NO',10),(28,'\0','YES',10),(29,'\0','YES',11),(30,'','NO',11),(31,'','NO',12),(32,'\0','YES',12),(33,'\0','JDK',13),(34,'\0','SDK',13),(35,'','JRE',13),(36,'\0','CVS',13),(37,'\0','NO',14),(38,'','YES',14),(39,'','NO',15),(40,'\0','YES',15),(41,'\0','YES',16),(42,'','NO',16),(43,'','Run()',17),(44,'\0','Start()',17),(45,'\0','Sleep()',17),(46,'\0','Wait()',17),(47,'\0','super()',18),(48,'\0','constant()',18),(49,'','this()',18),(50,'\0','YES',19),(51,'','NO',19),(52,'\0','YES',20),(53,'','NO',20),(54,'\0','Java.Throwable',21),(55,'','Java.Lang.Throwable',21),(56,'\0','Java.Lang.Exception',21),(57,'\0','Java.Lang.Throwables',21),(58,'','PATH',22),(59,'\0','CLASSPATH',22),(60,'\0','PATHS',22),(61,'\0','BIN',22);
/*!40000 ALTER TABLE `answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quest`
--

DROP TABLE IF EXISTS `quest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quest` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quest_description` varchar(255) NOT NULL,
  `quest_name` varchar(32) NOT NULL,
  `quest_price` int(11) NOT NULL,
  `quest_prize` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_h5wciroswvcse32y1hurwxt4s` (`quest_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quest`
--

LOCK TABLES `quest` WRITE;
/*!40000 ALTER TABLE `quest` DISABLE KEYS */;
INSERT INTO `quest` VALUES (2,'Questions and Tests','Java Quiz',100,100);
/*!40000 ALTER TABLE `quest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question_answers_count` int(11) NOT NULL,
  `question_value` varchar(255) NOT NULL,
  `quest_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK77jha3opawivtb5nx7xwbqbrs` (`quest_id`),
  CONSTRAINT `FK77jha3opawivtb5nx7xwbqbrs` FOREIGN KEY (`quest_id`) REFERENCES `quest` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (3,1,'Which class can access all public and protected methods and fields of its super class?',2),(4,1,'Method\'s field can be accessed from the same class to which they belong.',2),(5,1,'In java, float takes _________ bytes in memory.',2),(6,1,'What will be the output of following piece of code? ? public class operatorExample { public static void main(String args[]) { int x=4; System.out.println(x++); } }',2),(7,1,'Can variables be used in Java without initialization?',2),(8,1,'Can a class in java be inherited from more than one class?',2),(9,1,'What will be the output of Round(3.7)?',2),(10,1,'Does Java supports goto?',2),(11,1,'In JAVA can a dead thread be started again?',2),(12,1,'Is JDK required on each machine to run a Java program?',2),(13,1,'Which tool is required on each machine to run a Java program?',2),(14,1,'Is it possible to define a method in Java class but provide it’s implementation in the code of another language like C?',2),(15,1,'Can a variable be local and static at the same time?',2),(16,1,'Can we have any other return type than void for main method in java class?',2),(17,1,'Which method of the Runnable interface that must be implemented by all threads?',2),(18,1,'Which Keyword is used If a class has multiple constructors defined,it\'s possible to call a constructor from another constructor’s body?',2),(19,1,'Can we compare string and stringBuffer in Java?',2),(20,1,'Can we cast any other type to boolean type with type casting?',2),(21,1,'Base class for all exceptions',2),(22,1,'Environment variable that stores the location of bin folder',2);
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_login` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(100) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `username` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (2,'admin@localhost',NULL,'2016-10-31 09:56:18',NULL,'$2a$10$FuvU7rf.fHZPLZzzkgi6f.zJ.FRmHwHRHu/KB7y5xMadb7SXxFX26','ROLE_ADMIN','8a625d3be28f69d867fdf6755ab7e708','admin');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-22 12:36:06

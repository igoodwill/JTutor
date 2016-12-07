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
-- Table structure for table `answer_dto`
--

DROP TABLE IF EXISTS `answer_dto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `answer_dto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quest_id` int(11) DEFAULT NULL,
  `question_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=316 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answer_dto`
--

LOCK TABLES `answer_dto` WRITE;
/*!40000 ALTER TABLE `answer_dto` DISABLE KEYS */;
/*!40000 ALTER TABLE `answer_dto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `answer_dto_answers`
--

DROP TABLE IF EXISTS `answer_dto_answers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `answer_dto_answers` (
  `answerdto_id` int(11) NOT NULL,
  `answers_id` int(11) NOT NULL,
  UNIQUE KEY `UK_l6g1ssf8kh3qfirafguutpm2v` (`answers_id`),
  KEY `FK51vf9b05yy0cxmc1oh62xv1nk` (`answerdto_id`),
  CONSTRAINT `FK51vf9b05yy0cxmc1oh62xv1nk` FOREIGN KEY (`answerdto_id`) REFERENCES `answer_dto` (`id`),
  CONSTRAINT `FKdd8143qmntlb2j7rwen4f3wyp` FOREIGN KEY (`answers_id`) REFERENCES `user_answer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answer_dto_answers`
--

LOCK TABLES `answer_dto_answers` WRITE;
/*!40000 ALTER TABLE `answer_dto_answers` DISABLE KEYS */;
/*!40000 ALTER TABLE `answer_dto_answers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lecture`
--

DROP TABLE IF EXISTS `lecture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lecture` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lecture_description` varchar(255) NOT NULL,
  `lecture_name` varchar(32) NOT NULL,
  `lecture_value` varchar(8191) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_hogrg1fr6ld1n8iver5o1956d` (`lecture_name`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lecture`
--

LOCK TABLES `lecture` WRITE;
/*!40000 ALTER TABLE `lecture` DISABLE KEYS */;
INSERT INTO `lecture` VALUES (12,'Introduction in Java.','Java Lecture','Java is a high-level programming language originally developed by Sun Microsystems and released in 1995. Java runs on a variety of platforms, such as Windows, Mac OS, and the various versions of UNIX. This tutorial gives a complete understanding of Java.\r\n\r\nThis reference will take you through simple and practical approaches while learning Java Programming language.'),(14,'Java Basics. First Steps.','Java Basics','When we consider a Java program, it can be defined as a collection of objects that communicate via invoking each other\'s methods. Let us now briefly look into what do class, object, methods, and instance variables mean.\r\n\r\n    Object - Objects have states and behaviors. Example: A dog has states - color, name, breed as well as behavior such as wagging their tail, barking, eating. An object is an instance of a class.\r\n\r\n    Class - A class can be defined as a template/blueprint that describes the behavior/state that the object of its type supports.\r\n\r\n    Methods - A method is basically a behavior. A class can contain many methods. It is in methods where the logics are written, data is manipulated and all the actions are executed.\r\n\r\n    Instance Variables - Each object has its unique set of instance variables. An object\'s state is created by the values assigned to these instance variables.\r\n\r\nFirst Java Program\r\n\r\nLet us look at a simple code that will print the words Hello World.\r\nExample\r\n\r\npublic class MyFirstJavaProgram {\r\n\r\n   /* This is my first java program.\r\n    * This will print \'Hello World\' as the output\r\n    */\r\n\r\n   public static void main(String []args) {\r\n      System.out.println(\"Hello World\"); // prints Hello World\r\n   }\r\n}\r\n\r\nLet\'s look at how to save the file, compile, and run the program. Please follow the subsequent steps:\r\n\r\n    Open notepad and add the code as above.\r\n\r\n    Save the file as: MyFirstJavaProgram.java.\r\n\r\n    Open a command prompt window and go to the directory where you saved the class. Assume it\'s C:\\.\r\n\r\n    Type \'javac MyFirstJavaProgram.java\' and press enter to compile your code. If there are no errors in your code, the command prompt will take you to the next line (Assumption : The path variable is set).\r\n\r\n    Now, type \' java MyFirstJavaProgram \' to run your program.\r\n\r\n    You will be able to see \' Hello World \' printed on the window.\r\n\r\nOutput\r\n\r\nC:\\> javac MyFirstJavaProgram.java\r\nC:\\> java MyFirstJavaProgram \r\nHello World\r\n\r\nBasic Syntax\r\n\r\nAbout Java programs, it is very important to keep in mind the following points.\r\n\r\n    Case Sensitivity - Java is case sensitive, which means identifier Hello and hello would have different meaning in Java.\r\n\r\n    Class Names - For all class names the first letter should be in Upper Case. If several words are used to form a name of the class, each inner word\'s first letter should be in Upper Case.\r\n\r\n    Example: class MyFirstJavaClass\r\n\r\n    Method Names - All method names should start with a Lower Case letter. If several words are used to form the name of the method, then each inner word\'s first letter should be in Upper Case.\r\n\r\n    Example: public void myMethodName()\r\n\r\n    Program File Name - Name of the program file should exactly match the class name.\r\n\r\n    When saving the file, you should save it using the class name (Remember Java is case sensitive) and append \'.java\' to the end of the name (if the file name and the class name do not match, your program will not compile).\r\n\r\n    Example: Assume \'MyFirstJavaProgram\' is the class name. Then the file should be saved as \'MyFirstJavaProgram.java\'\r\n\r\n    public static void main(String args[]) - Java program processing starts from the main() method which is a mandatory part of every Java program.\r\n\r\nJava Identifiers\r\n\r\nAll Java components require names. Names used for classes, variables, and methods are called identifiers.\r\n\r\nIn Java, there are several points to remember about identifiers. They are as follows:\r\n\r\n    All identifiers should begin with a letter (A to Z or a to z), currency character ($) or an underscore (_).\r\n\r\n    After the first character, identifiers can have any combination of characters.\r\n\r\n    A key word cannot be used as an identifier.\r\n\r\n    Most importantly, identifiers are case sensitive.\r\n\r\n    Examples of legal identifiers: age, $salary, _value, __1_value.\r\n\r\n    Examples of illegal identifiers: 123abc, -salary.\r\n\r\nJava Modifiers\r\n\r\nLike other languages, it is possible to modify classes, methods, etc., by using modifiers. There are two categories of modifiers:\r\n\r\n    Access Modifiers - default, public , protected, private\r\n\r\n    Non-access Modifiers - final, abstract, strictfp\r\n\r\nWe will be looking into more details about modifiers in the next section.\r\n\r\nJava Variables\r\n\r\nFollowing are the types of variables in Java:\r\n\r\n    Local Variables\r\n    Class Variables (Static Variables)\r\n    Instance Variables (Non-static Variables)\r\n\r\nJava Arrays\r\n\r\nArrays are objects that store multiple variables of the same type. However, an array itself is an object on the heap. We will look into how to declare, construct, and initialize in the upcoming chapters.\r\nJava Enums\r\n\r\nEnums were introduced in Java 5.0. Enums restrict a variable to have one of only a few predefined values. The values in this enumerated list are called enums.\r\n\r\nWith the use of enums it is possible to reduce the number of bugs in your code.\r\n\r\nFor example, if we consider an application for a fresh juice shop, it would be possible to restrict the glass size to small, medium, and large. This would make sure that it would not allow anyone to order any size other than small, medium, or large.\r\nExample\r\n\r\nclass FreshJuice {\r\n   enum FreshJuiceSize{ SMALL, MEDIUM, LARGE }\r\n   FreshJuiceSize size;\r\n}\r\n\r\npublic class FreshJuiceTest {\r\n\r\n   public static void main(String args[]) {\r\n      FreshJuice juice = new FreshJuice();\r\n      juice.size = FreshJuice.FreshJuiceSize.MEDIUM ;\r\n      System.out.println(\"Size: \" + juice.size);\r\n   }\r\n}\r\n\r\nThe above example will produce the following result:\r\nOutput\r\n\r\nSize: MEDIUM\r\n\r\nNote  Enums can be declared as their own or inside a class. Methods, variables, constructors can be defined inside enums as well.\r\nJava Keywords\r\n\r\nThe following list shows the reserved words in Java. These reserved words may not be used as constant or variable or any other identifier names.\r\nabstract 	assert 	boolean 	break\r\nbyte 	case 	catch 	char\r\nclass 	const 	continue 	default\r\ndo 	double 	else 	enum\r\nextends 	final 	finally 	float\r\nfor 	goto 	if 	implements\r\nimport 	instanceof 	int 	interface\r\nlong 	native 	new 	package\r\nprivate 	protected 	public 	return\r\nshort 	static 	strictfp 	super\r\nswitch 	synchronized 	this 	throw\r\nthrows 	transient 	try 	void\r\nvolatile 	while 		\r\n\r\nComments in Java\r\n\r\nJava supports single-line and multi-line comments very similar to C and C++. All characters available inside any comment are ignored by Java compiler.\r\nExample\r\n\r\npublic class MyFirstJavaProgram {\r\n\r\n   /* This is my first java program.\r\n    * This will print \'Hello World\' as the output\r\n    * This is an example of multi-line comments.\r\n    */\r\n\r\n   public static void main(String []args) {\r\n      // This is an example of single line comment\r\n      /* This is also an example of single line comment. */\r\n      System.out.println(\"Hello World\");\r\n   }\r\n}\r\n\r\nOutput\r\n\r\nHello World\r\n\r\nUsing Blank Lines\r\n\r\nA line containing only white space, possibly with a comment, is known as a blank line, and Java totally ignores it.\r\n\r\nInheritance\r\n\r\nIn Java, classes can be derived from classes. Basically, if you need to create a new class and here is already a class that has some of the code you require, then it is possible to derive your new class from the already existing code.\r\n\r\nThis concept allows you to reuse the fields and methods of the existing class without having to rewrite the code in a new class. In this scenario, the existing class is called the superclass and the derived class is called the subclass.\r\nInterfaces\r\n\r\nIn Java language, an interface can be defined as a contract between objects on how to communicate with each other. Interfaces play a vital role when it comes to the concept of inheritance.\r\n\r\nAn interface defines the methods, a deriving class (subclass) should use. But the implementation of the methods is totally up to the subclass.');
/*!40000 ALTER TABLE `lecture` ENABLE KEYS */;
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_h5wciroswvcse32y1hurwxt4s` (`quest_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quest`
--

LOCK TABLES `quest` WRITE;
/*!40000 ALTER TABLE `quest` DISABLE KEYS */;
INSERT INTO `quest` VALUES (2,'Questions and Tests','Java Quiz');
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
INSERT INTO `question` VALUES (3,1,'Which class can access all public and protected methods and fields of its super class?',2),(4,1,'Method\'s field can be accessed from the same class to which they belong.',2),(5,1,'In java, float takes _________ bytes in memory.',2),(6,1,'What will be the output of following piece of code?\r\n    public class OperatorExample {\r\n        public static void main(String args[]) {\r\n            int x=4;\r\n            System.out.println(x++); \r\n        }\r\n}',2),(7,1,'Can variables be used in Java without initialization?',2),(8,1,'Can a class in java be inherited from more than one class?',2),(9,1,'What will be the output of Round(3.7)?',2),(10,1,'Does Java supports goto?',2),(11,1,'In JAVA can a dead thread be started again?',2),(12,1,'Is JDK required on each machine to run a Java program?',2),(13,1,'Which tool is required on each machine to run a Java program?',2),(14,1,'Is it possible to define a method in Java class but provide it’s implementation in the code of another language like C?',2),(15,1,'Can a variable be local and static at the same time?',2),(16,1,'Can we have any other return type than void for main method in java class?',2),(17,1,'Which method of the Runnable interface that must be implemented by all threads?',2),(18,1,'Which Keyword is used If a class has multiple constructors defined,it\'s possible to call a constructor from another constructor’s body?',2),(19,1,'Can we compare string and stringBuffer in Java?',2),(20,1,'Can we cast any other type to boolean type with type casting?',2),(21,1,'Base class for all exceptions',2),(22,1,'Environment variable that stores the location of bin folder',2);
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_answer`
--

DROP TABLE IF EXISTS `user_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `state` bit(1) NOT NULL,
  `value` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1067 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_answer`
--

LOCK TABLES `user_answer` WRITE;
/*!40000 ALTER TABLE `user_answer` DISABLE KEYS */;
INSERT INTO `user_answer` VALUES (622,'','Public'),(623,'\0','Protected'),(624,'\0','Default'),(625,'\0','Private'),(634,'\0','8'),(635,'\0','4'),(636,'','2'),(637,'\0','16'),(638,'\0','Public'),(639,'\0','Protected'),(640,'\0','Default'),(641,'','Private'),(662,'\0','8'),(663,'','4'),(664,'\0','2'),(665,'\0','16'),(674,'\0','Public'),(675,'\0','Protected'),(676,'\0','Default'),(677,'','Private'),(682,'\0','8'),(683,'\0','4'),(684,'\0','2'),(685,'','16'),(686,'\0','Public'),(687,'\0','Protected'),(688,'\0','Default'),(689,'','Private'),(694,'\0','Inner class.'),(695,'\0','Outer class.'),(696,'','Sub-class.'),(697,'\0','Super class.'),(702,'\0','Inner class.'),(703,'\0','Outer class.'),(704,'','Sub-class.'),(705,'\0','Super class.'),(710,'\0','Inner class.'),(711,'\0','Outer class.'),(712,'','Sub-class.'),(713,'\0','Super class.'),(718,'\0','Inner class.'),(719,'\0','Outer class.'),(720,'','Sub-class.'),(721,'\0','Super class.'),(726,'\0','Inner class.'),(727,'\0','Outer class.'),(728,'','Sub-class.'),(729,'\0','Super class.'),(734,'\0','Inner class.'),(735,'\0','Outer class.'),(736,'','Sub-class.'),(737,'\0','Super class.'),(742,'\0','Inner class.'),(743,'\0','Outer class.'),(744,'\0','Sub-class.'),(745,'','Super class.'),(750,'\0','Inner class.'),(751,'','Outer class.'),(752,'\0','Sub-class.'),(753,'\0','Super class.'),(758,'','Inner class.'),(759,'\0','Outer class.'),(760,'','Sub-class.'),(761,'\0','Super class.'),(766,'\0','Inner class.'),(767,'\0','Outer class.'),(768,'','Sub-class.'),(769,'\0','Super class.'),(778,'','Inner class.'),(779,'\0','Outer class.'),(780,'','Sub-class.'),(781,'\0','Super class.'),(786,'\0','Inner class.'),(787,'\0','Outer class.'),(788,'','Sub-class.'),(789,'\0','Super class.'),(798,'\0','Public'),(799,'','Protected'),(800,'\0','Default'),(801,'\0','Private'),(818,'\0','YES'),(819,'','NO'),(877,'\0','8'),(878,'','4'),(879,'\0','2'),(880,'\0','16'),(1003,'\0','Inner class.'),(1004,'\0','Outer class.'),(1005,'\0','Sub-class.'),(1006,'','Super class.');
/*!40000 ALTER TABLE `user_answer` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (2,'admin@localhost',NULL,'2016-12-07 19:33:14',NULL,'$2a$10$FuvU7rf.fHZPLZzzkgi6f.zJ.FRmHwHRHu/KB7y5xMadb7SXxFX26','ROLE_ADMIN','8a625d3be28f69d867fdf6755ab7e708','admin'),(3,'igor.goodwill1@gmail.com',NULL,'2016-12-07 10:35:07',NULL,'$2a$10$bE.tCzRAPTR0ALZEtAUsC.I.bpJcbnGg4LFlvFPyDoBuhrwiGGrI.','ROLE_USER','1f03592d1c8ed62772454927d8ef6df9','igoodwill');
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

-- Dump completed on 2016-12-07 19:44:13

-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: project
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `project`
--

/*!40000 DROP DATABASE IF EXISTS `project`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `project` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `project`;

--
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accounts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `phone_no` varchar(10) DEFAULT NULL,
  `access_level` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES (1,'admin','admin',NULL,NULL,NULL,1),(2,'manager','manager','Jacob','John','85677989',2),(17,'employee1','employee1','Jack','Ryan','99623435',3),(19,'employee2','employee2','fname','lname','7845678899',2),(22,'a','a','abc','def','9823455555',2),(23,'b','b','ghi','jkl','6578234510',3);
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ftransfer`
--

DROP TABLE IF EXISTS `ftransfer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ftransfer` (
  `id` int NOT NULL,
  `fromid` int DEFAULT NULL,
  `toid` int DEFAULT NULL,
  `amount` int DEFAULT NULL,
  `frem` int DEFAULT NULL,
  `trem` int DEFAULT NULL,
  `dt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ftransfer`
--

LOCK TABLES `ftransfer` WRITE;
/*!40000 ALTER TABLE `ftransfer` DISABLE KEYS */;
INSERT INTO `ftransfer` VALUES (1,1,2,20,180,170,'2023-01-09 01:00:59'),(2,2,1,100,70,280,'2023-01-09 01:18:16'),(3,3,4,5,10260,45,'2023-02-07 17:34:23');
/*!40000 ALTER TABLE `ftransfer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `items`
--

DROP TABLE IF EXISTS `items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `items` (
  `id` int NOT NULL,
  `itemname` varchar(30) DEFAULT NULL,
  `barcode` varchar(30) DEFAULT NULL,
  `pprice` int DEFAULT NULL,
  `sprice` int DEFAULT NULL,
  `qntytype` varchar(6) DEFAULT NULL,
  `stock` int DEFAULT NULL,
  `gst` int DEFAULT NULL,
  `mrp` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `itemname` (`itemname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `items`
--

LOCK TABLES `items` WRITE;
/*!40000 ALTER TABLE `items` DISABLE KEYS */;
INSERT INTO `items` VALUES (1,'RAM','90001',1750,1850,'Unit',70,18,1900),(3,'Monitor','90002',25000,27000,'Unit',5,12,28000),(4,'SSD - 1TB','90003',8500,8800,'Unit',1000,18,9500),(5,'SSD - 512GB','90004',5600,5700,'Unit',650,18,5900),(6,'item1','90006',1000,1100,'Unit',500,5,1200),(7,'Item2','90007',300,400,'Unit',210,5,500),(8,'item3','90009',2500,2550,'Unit',10,0,2560),(9,'item4','90010',4300,4300,'Unit',50,5,4400);
/*!40000 ALTER TABLE `items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ledger`
--

DROP TABLE IF EXISTS `ledger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ledger` (
  `id` int NOT NULL,
  `ledgername` varchar(30) DEFAULT NULL,
  `type` varchar(19) DEFAULT NULL,
  `amount` int DEFAULT NULL,
  `phone` varchar(13) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ledgername` (`ledgername`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ledger`
--

LOCK TABLES `ledger` WRITE;
/*!40000 ALTER TABLE `ledger` DISABLE KEYS */;
INSERT INTO `ledger` VALUES (1,'user1','Sundry Debtor',300,'9876454545','AddressOfUser1'),(2,'user2','Sundry Debtor',440,'6578565775','AddressOfUser2'),(3,'user3','Sundry Debtor',10260,'7878565990','AddressOfUser3'),(4,'user4','Sundry Creditor',30,'8970545555','AddressOfUser4'),(5,'user5','Sundry Creditor',-130,'5656789090','AddressOfUser5'),(6,'CASH ACCOUNT','Sundry Debtor',0,'','');
/*!40000 ALTER TABLE `ledger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login_log`
--

DROP TABLE IF EXISTS `login_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `login_log` (
  `id` int NOT NULL AUTO_INCREMENT,
  `time` timestamp NOT NULL,
  `date` date NOT NULL,
  `user` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=177 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login_log`
--

LOCK TABLES `login_log` WRITE;
/*!40000 ALTER TABLE `login_log` DISABLE KEYS */;
INSERT INTO `login_log` VALUES (1,'2023-01-06 19:15:36','2023-01-07','admin'),(2,'2023-01-06 19:16:26','2023-01-07','manager'),(3,'2023-01-06 20:25:25','2023-01-07','employee2'),(4,'2023-01-07 03:52:51','2023-01-07','manager'),(5,'2023-01-07 04:05:49','2023-01-07','admin'),(6,'2023-01-07 05:05:18','2023-01-07','admin'),(7,'2023-01-07 05:16:57','2023-01-07','manager'),(8,'2023-01-07 06:33:48','2023-01-07','emp'),(9,'2023-01-07 08:57:14','2023-01-07','admin'),(10,'2023-01-07 09:04:02','2023-01-07','admin'),(11,'2023-01-07 14:21:21','2023-01-07','manager'),(12,'2023-01-07 16:56:10','2023-01-07','admin'),(13,'2023-01-07 17:01:20','2023-01-07','admin'),(14,'2023-01-07 17:02:34','2023-01-07','admin'),(15,'2023-01-07 19:25:30','2023-01-08','employee1'),(16,'2023-01-07 19:33:20','2023-01-08','admin'),(17,'2023-01-07 19:37:27','2023-01-08','admin'),(18,'2023-01-08 04:28:29','2023-01-08','admin'),(19,'2023-01-08 04:40:06','2023-01-08','manager'),(20,'2023-01-08 04:57:05','2023-01-08','admin'),(21,'2023-01-08 04:58:47','2023-01-08','admin'),(22,'2023-01-08 05:07:01','2023-01-08','admin'),(23,'2023-01-08 05:08:18','2023-01-08','admin'),(24,'2023-01-08 05:09:44','2023-01-08','employee1'),(25,'2023-01-08 05:21:50','2023-01-08','manager'),(26,'2023-01-08 06:09:00','2023-01-08','admin'),(27,'2023-01-08 06:53:04','2023-01-08','admin'),(28,'2023-01-08 06:57:27','2023-01-08','admin'),(29,'2023-01-08 07:01:43','2023-01-08','admin'),(30,'2023-01-08 07:06:26','2023-01-08','admin'),(31,'2023-01-08 07:07:06','2023-01-08','admin'),(32,'2023-01-08 07:10:50','2023-01-08','admin'),(33,'2023-01-08 07:17:02','2023-01-08','admin'),(34,'2023-01-08 07:20:39','2023-01-08','employee1'),(35,'2023-01-08 08:33:00','2023-01-08','admin'),(36,'2023-01-08 08:35:36','2023-01-08','manager'),(37,'2023-01-08 09:18:38','2023-01-08','admin'),(38,'2023-01-08 09:29:03','2023-01-08','manager'),(39,'2023-01-08 10:35:58','2023-01-08','admin'),(40,'2023-01-08 10:45:08','2023-01-08','admin'),(41,'2023-01-08 10:46:32','2023-01-08','admin'),(42,'2023-01-08 10:49:23','2023-01-08','manager'),(43,'2023-01-08 10:52:09','2023-01-08','admin'),(44,'2023-01-08 10:56:29','2023-01-08','admin'),(45,'2023-01-08 10:57:01','2023-01-08','admin'),(46,'2023-01-08 10:57:32','2023-01-08','admin'),(47,'2023-01-08 10:58:31','2023-01-08','admin'),(48,'2023-01-08 11:34:08','2023-01-08','manager'),(49,'2023-01-08 11:35:47','2023-01-08','admin'),(50,'2023-01-08 11:51:48','2023-01-08','admin'),(51,'2023-01-08 11:54:17','2023-01-08','admin'),(52,'2023-01-08 12:55:34','2023-01-08','admin'),(53,'2023-01-08 12:57:36','2023-01-08','admin'),(54,'2023-01-08 13:08:06','2023-01-08','admin'),(55,'2023-01-08 13:11:25','2023-01-08','admin'),(56,'2023-01-08 13:12:57','2023-01-08','admin'),(57,'2023-01-08 13:13:50','2023-01-08','admin'),(58,'2023-01-08 13:16:42','2023-01-08','admin'),(59,'2023-01-08 13:37:45','2023-01-08','admin'),(60,'2023-01-08 13:39:27','2023-01-08','admin'),(61,'2023-01-08 13:59:30','2023-01-08','admin'),(62,'2023-01-08 14:04:04','2023-01-08','admin'),(63,'2023-01-08 14:05:27','2023-01-08','admin'),(64,'2023-01-08 14:08:26','2023-01-08','admin'),(65,'2023-01-08 14:09:51','2023-01-08','admin'),(66,'2023-01-08 14:25:19','2023-01-08','a'),(67,'2023-01-08 14:53:39','2023-01-08','a'),(68,'2023-01-08 15:20:45','2023-01-08','admin'),(69,'2023-01-08 15:21:46','2023-01-08','admin'),(70,'2023-01-08 15:26:27','2023-01-08','a'),(71,'2023-01-08 17:18:59','2023-01-08','admin'),(72,'2023-01-08 17:19:51','2023-01-08','a'),(73,'2023-01-08 17:20:10','2023-01-08','a'),(74,'2023-01-08 17:27:28','2023-01-08','a'),(75,'2023-01-08 17:33:55','2023-01-08','a'),(76,'2023-01-08 17:34:54','2023-01-08','a'),(77,'2023-01-08 18:20:09','2023-01-08','a'),(78,'2023-01-08 18:21:56','2023-01-08','a'),(79,'2023-01-08 18:26:22','2023-01-08','a'),(80,'2023-01-08 18:33:38','2023-01-09','a'),(81,'2023-01-08 18:34:40','2023-01-09','a'),(82,'2023-01-08 18:35:48','2023-01-09','a'),(83,'2023-01-08 18:36:56','2023-01-09','a'),(84,'2023-01-08 18:37:56','2023-01-09','a'),(85,'2023-01-08 18:39:13','2023-01-09','a'),(86,'2023-01-08 18:39:40','2023-01-09','a'),(87,'2023-01-08 18:40:41','2023-01-09','a'),(88,'2023-01-08 19:21:42','2023-01-09','a'),(89,'2023-01-08 19:24:19','2023-01-09','a'),(90,'2023-01-08 19:28:34','2023-01-09','a'),(91,'2023-01-08 19:32:45','2023-01-09','a'),(92,'2023-01-08 19:33:38','2023-01-09','a'),(93,'2023-01-08 19:35:56','2023-01-09','a'),(94,'2023-01-08 19:37:37','2023-01-09','a'),(95,'2023-01-08 19:40:53','2023-01-09','a'),(96,'2023-01-08 19:41:38','2023-01-09','admin'),(97,'2023-01-08 19:46:52','2023-01-09','a'),(98,'2023-01-08 19:48:01','2023-01-09','a'),(99,'2023-01-08 20:10:26','2023-01-09','a'),(100,'2023-01-08 20:19:54','2023-01-09','a'),(101,'2023-01-08 20:31:41','2023-01-09','a'),(102,'2023-01-08 20:32:28','2023-01-09','a'),(103,'2023-01-08 20:36:46','2023-01-09','a'),(104,'2023-01-08 21:02:55','2023-01-09','a'),(105,'2023-01-08 21:04:07','2023-01-09','a'),(106,'2023-01-08 21:25:42','2023-01-09','a'),(107,'2023-01-08 21:26:21','2023-01-09','a'),(108,'2023-01-08 21:28:58','2023-01-09','a'),(109,'2023-01-08 21:30:14','2023-01-09','a'),(110,'2023-01-08 21:33:05','2023-01-09','a'),(111,'2023-01-08 21:36:10','2023-01-09','a'),(112,'2023-01-08 21:43:59','2023-01-09','a'),(113,'2023-01-08 21:57:06','2023-01-09','a'),(114,'2023-01-08 22:00:42','2023-01-09','admin'),(115,'2023-01-08 22:02:04','2023-01-09','a'),(116,'2023-01-08 22:02:42','2023-01-09','a'),(117,'2023-01-08 23:00:13','2023-01-09','a'),(118,'2023-01-08 23:02:30','2023-01-09','a'),(119,'2023-01-08 23:06:09','2023-01-09','a'),(120,'2023-01-08 23:10:16','2023-01-09','a'),(121,'2023-01-08 23:11:36','2023-01-09','a'),(122,'2023-01-09 00:08:33','2023-01-09','admin'),(123,'2023-01-09 00:11:33','2023-01-09','a'),(124,'2023-01-09 00:12:16','2023-01-09','a'),(125,'2023-01-09 00:13:13','2023-01-09','a'),(126,'2023-01-09 00:14:43','2023-01-09','a'),(127,'2023-01-09 00:16:46','2023-01-09','a'),(128,'2023-01-09 00:19:18','2023-01-09','a'),(129,'2023-01-09 00:23:19','2023-01-09','admin'),(130,'2023-01-09 00:24:04','2023-01-09','a'),(131,'2023-01-09 00:25:06','2023-01-09','a'),(132,'2023-01-09 03:14:02','2023-01-09','a'),(133,'2023-01-09 04:01:34','2023-01-09','a'),(134,'2023-01-09 04:02:33','2023-01-09','a'),(135,'2023-01-09 04:14:41','2023-01-09','a'),(136,'2023-01-09 05:05:12','2023-01-09','a'),(137,'2023-01-09 05:08:27','2023-01-09','a'),(138,'2023-01-09 05:10:54','2023-01-09','a'),(139,'2023-01-09 05:12:58','2023-01-09','a'),(140,'2023-01-09 05:13:32','2023-01-09','a'),(141,'2023-01-09 05:16:40','2023-01-09','a'),(142,'2023-01-09 05:20:34','2023-01-09','a'),(143,'2023-01-09 05:22:16','2023-01-09','a'),(144,'2023-01-09 05:25:42','2023-01-09','a'),(145,'2023-01-09 05:36:56','2023-01-09','a'),(146,'2023-01-09 05:39:35','2023-01-09','a'),(147,'2023-01-09 05:41:20','2023-01-09','a'),(148,'2023-01-09 14:53:04','2023-01-09','a'),(149,'2023-01-09 14:53:59','2023-01-09','manager'),(150,'2023-01-09 17:10:13','2023-01-09','admin'),(151,'2023-01-09 17:22:18','2023-01-09','admin'),(152,'2023-01-09 17:31:29','2023-01-09','employee1'),(153,'2023-01-09 17:33:27','2023-01-09','admin'),(154,'2023-01-09 17:38:38','2023-01-09','admin'),(155,'2023-01-09 17:41:19','2023-01-09','a'),(156,'2023-01-09 17:41:38','2023-01-09','a'),(157,'2023-01-09 17:43:04','2023-01-09','a'),(158,'2023-01-09 17:52:35','2023-01-09','a'),(159,'2023-01-09 17:52:58','2023-01-09','admin'),(160,'2023-01-09 17:53:57','2023-01-09','a'),(161,'2023-01-09 18:03:35','2023-01-09','a'),(162,'2023-01-09 18:06:04','2023-01-09','employee1'),(163,'2023-01-09 18:07:41','2023-01-09','b'),(164,'2023-01-09 18:08:18','2023-01-09','a'),(165,'2023-01-09 18:11:57','2023-01-09','a'),(166,'2023-01-09 18:17:01','2023-01-09','a'),(167,'2023-01-09 18:18:31','2023-01-09','a'),(168,'2023-01-09 18:18:58','2023-01-09','a'),(169,'2023-01-11 07:22:45','2023-01-11','manager'),(170,'2023-01-11 07:26:36','2023-01-11','admin'),(171,'2023-02-06 13:11:50','2023-02-06','manager'),(172,'2023-02-06 13:14:05','2023-02-06','a'),(173,'2023-02-06 13:18:21','2023-02-06','a'),(174,'2023-02-06 13:25:47','2023-02-06','a'),(175,'2023-02-07 12:00:19','2023-02-07','a'),(176,'2023-02-07 12:14:50','2023-02-07','a');
/*!40000 ALTER TABLE `login_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `id` int NOT NULL,
  `ledgerid` int DEFAULT NULL,
  `amount` int DEFAULT NULL,
  `updated` int DEFAULT NULL,
  `dt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES (1,2,20,45,'2023-01-09 04:42:01'),(2,4,5,50,'2023-02-07 17:40:29'),(3,2,35,490,'2023-02-07 17:45:06');
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase`
--

DROP TABLE IF EXISTS `purchase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase` (
  `id` int NOT NULL,
  `ledgerid` int DEFAULT NULL,
  `discount` int DEFAULT NULL,
  `total` int DEFAULT NULL,
  `payed` int DEFAULT NULL,
  `updated` int DEFAULT NULL,
  `prdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase`
--

LOCK TABLES `purchase` WRITE;
/*!40000 ALTER TABLE `purchase` DISABLE KEYS */;
INSERT INTO `purchase` VALUES (1,6,0,26646,10000,-8160,'2023-01-09 10:56:19'),(2,2,0,21100,1100,-19510,'2023-02-07 17:51:20'),(3,2,50,487450,487400,440,'2023-02-07 17:58:00');
/*!40000 ALTER TABLE `purchase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchasedetails`
--

DROP TABLE IF EXISTS `purchasedetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchasedetails` (
  `prid` int DEFAULT NULL,
  `itemid` int DEFAULT NULL,
  `qnty` int DEFAULT NULL,
  `pprice` int DEFAULT NULL,
  `sprice` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchasedetails`
--

LOCK TABLES `purchasedetails` WRITE;
/*!40000 ALTER TABLE `purchasedetails` DISABLE KEYS */;
INSERT INTO `purchasedetails` VALUES (1,4,2,13323,4243),(2,9,2,4300,4300),(2,8,5,2500,2550),(3,8,5,2500,2550),(3,3,19,25000,27000);
/*!40000 ALTER TABLE `purchasedetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receipt`
--

DROP TABLE IF EXISTS `receipt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receipt` (
  `id` int NOT NULL,
  `ledgerid` int DEFAULT NULL,
  `amount` int DEFAULT NULL,
  `updated` int DEFAULT NULL,
  `dt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receipt`
--

LOCK TABLES `receipt` WRITE;
/*!40000 ALTER TABLE `receipt` DISABLE KEYS */;
INSERT INTO `receipt` VALUES (1,2,40,25,'2023-01-09 04:36:56'),(2,5,50,-110,'2023-01-09 10:35:44'),(3,5,20,-130,'2023-02-07 18:11:51');
/*!40000 ALTER TABLE `receipt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `saledetails`
--

DROP TABLE IF EXISTS `saledetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `saledetails` (
  `saleid` int DEFAULT NULL,
  `itemid` int DEFAULT NULL,
  `qnty` int DEFAULT NULL,
  `rate` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `saledetails`
--

LOCK TABLES `saledetails` WRITE;
/*!40000 ALTER TABLE `saledetails` DISABLE KEYS */;
INSERT INTO `saledetails` VALUES (1,4,2,4243),(2,4,2,4243),(3,4,5,4243),(4,8,5,2550),(4,5,2,5700),(4,7,10,400);
/*!40000 ALTER TABLE `saledetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales`
--

DROP TABLE IF EXISTS `sales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sales` (
  `id` int NOT NULL,
  `ledgerid` int DEFAULT NULL,
  `discount` int DEFAULT NULL,
  `total` int DEFAULT NULL,
  `recieved` int DEFAULT NULL,
  `updated` int DEFAULT NULL,
  `sldate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales`
--

LOCK TABLES `sales` WRITE;
/*!40000 ALTER TABLE `sales` DISABLE KEYS */;
INSERT INTO `sales` VALUES (1,6,0,8486,0,8486,'2023-01-09 10:52:32'),(2,6,0,8486,8160,-7834,'2023-01-11 12:54:13'),(3,3,1000,20215,10000,10265,'2023-01-11 12:56:14'),(4,4,50,28100,28120,30,'2023-02-07 18:09:34');
/*!40000 ALTER TABLE `sales` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-07 18:19:04

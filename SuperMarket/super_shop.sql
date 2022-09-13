-- MariaDB dump 10.19  Distrib 10.7.3-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: super_shop
-- ------------------------------------------------------
-- Server version	10.7.3-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin_tbl`
--

DROP TABLE IF EXISTS `admin_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `admin_email` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `admin_pass` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_tbl`
--

LOCK TABLES `admin_tbl` WRITE;
/*!40000 ALTER TABLE `admin_tbl` DISABLE KEYS */;
INSERT INTO `admin_tbl` VALUES
(1,'Helal Uddin','helal@gmail.com','123');
/*!40000 ALTER TABLE `admin_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `billing_tbl`
--

DROP TABLE IF EXISTS `billing_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `billing_tbl` (
  `bill_id` int(11) NOT NULL AUTO_INCREMENT,
  `invoice_id` int(11) DEFAULT 1,
  `customer_name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone` varchar(13) COLLATE utf8mb4_unicode_ci NOT NULL,
  `product_name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `weight` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '100 g',
  `category` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `quantity` int(11) NOT NULL,
  `unit_price` double NOT NULL,
  `total_price` double NOT NULL,
  `date` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`bill_id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `billing_tbl`
--

LOCK TABLES `billing_tbl` WRITE;
/*!40000 ALTER TABLE `billing_tbl` DISABLE KEYS */;
INSERT INTO `billing_tbl` VALUES
(1,1,'Helal','1565555565','Vanila','100 g','Ice  Cream',1,200,200,'2022/05/02 10:04:24'),
(2,1,'helal','166666666','Snsilk Shampoo','100 g','Shampoo',1,200,200,'2022/05/02 10:06:08'),
(3,1,'demo','122354444','Snsilk Shampoo','100 g','Shampoo',1,200,200,'2022/05/02 10:09:34'),
(4,1,'demo','122354444','Vanila','100 g','Ice  Cream',1,200,200,'2022/05/02 10:09:34'),
(5,1,'demo','122354444','Vanila','100 g','Ice  Cream',1,200,200,'2022/05/02 10:09:34'),
(6,1,'dem','1213432423','Snsilk Shampoo','100 g','Shampoo',1,200,200,'2022/05/02 10:14:34'),
(7,1,'dem','1213432423','Vanila','100 g','Ice  Cream',1,200,200,'2022/05/02 10:14:34'),
(8,1,'dem','1213432423','Snsilk Shampoo','100 g','Shampoo',1,200,200,'2022/05/02 10:14:34'),
(9,1,'demo1','1558944645','Snsilk Shampoo','100 g','Shampoo',1,200,200,'2022/05/02 10:24:58'),
(10,1,'demo1','1558944645','dove','100 g','Soap',1,45.54,45.54,'2022/05/02 10:24:58'),
(11,1,'demo1','1558944645','Vanila','100 g','Ice  Cream',1,200,200,'2022/05/02 10:24:58'),
(12,1,'rtet','33433','Vanila','100 g','Ice  Cream',1,200,200,'2022/05/02 10:28:05'),
(13,1,'rtet','33433','Snsilk Shampoo','100 g','Shampoo',1,200,200,'2022/05/02 10:28:05'),
(14,1,'test','1141124','Vanila','100 g','Ice  Cream',1,200,200,'2022/05/02 10:30:49'),
(15,1,'w345','435','Snsilk Shampoo','100 g','Shampoo',1,200,200,'2022/05/02 10:33:38'),
(16,1,'65ruu','45','dove','100 g','Soap',1,45.54,45.54,'2022/05/02 10:35:40'),
(17,1,'65ruu','45','Vanila','100 g','Ice  Cream',1,200,200,'2022/05/02 10:35:40'),
(18,1,'Helal Uddin','155555555','dove','100 g','Soap',1,45.54,45.54,'2022/05/02 10:41:44'),
(19,1,'Helal Uddin','155555555','Vanila','100 g','Ice  Cream',1,200,200,'2022/05/02 10:41:44'),
(20,1,'Helal Uddin','155555555','Snsilk Shampoo','100 g','Shampoo',1,200,200,'2022/05/02 10:41:44'),
(21,1,'hell','1555555','dove','100 g','Soap',1,45.54,45.54,'2022/05/02 12:51:20'),
(22,1,'jen','112213113','Vanila','100 g','Ice  Cream',1,200,200,'2022/05/02 13:03:48'),
(23,1,'jen','0155555555','Snsilk Shampoo','100 g','Shampoo',1,200,200,'2022/05/02 13:06:30'),
(24,1,'Helal','01212121212','Vanila','100 g','Ice  Cream',5,200,1000,'2022/05/07 12:01:21'),
(25,1,'helal','012114134324','Dove Soap','100 g','Soap',3,30,90,'2022/05/07 13:01:25'),
(26,2,'jon','0155555555','Dove Soap','100 g','Soap',1,30,30,'2022/05/09'),
(27,3,'jon','0155555555','Vanila','100 g','Ice  Cream',1,200,200,'2022/05/09'),
(28,4,'sara','0155555555','Vanila','100 g','Ice  Cream',2,200,400,'2022/05/09'),
(29,5,'sara','112143254','Dove Soap','100 g','Soap',3,30,90,'2022/05/09'),
(30,6,'jhon','214314325','Vanila','100 g','Ice  Cream',2,200,400,'2022/05/09'),
(31,7,'alex','1141241241','Dove Soap','100 g','Soap',1,30,30,'2022/05/09'),
(32,8,'alex','1141241241','Vanila','100 g','Ice  Cream',1,200,200,'2022/05/09'),
(33,9,'jessi','0123432555','Vanila','100 g','Ice  Cream',2,200,400,'2022/05/09'),
(34,1,'jessi','0123432555','Dove Soap','100 g','Soap',2,30,60,'2022/05/09'),
(35,1,'jessi','0123432555','Vanila','100 g','Ice  Cream',2,200,400,'2022/05/09'),
(36,10,'jimi','0155555555','Vanila','100 g','Ice  Cream',1,200,200,'2022/05/09'),
(37,10,'jimi','0155555555','Dove Soap','100 g','Soap',1,30,30,'2022/05/09'),
(38,10,'jimi','0155555555','Dove Soap','100 g','Soap',1,30,30,'2022/05/09'),
(39,11,'joy','04555554545','Snsilk Shampoo','100 g','Shampoo',1,200,200,'2022/05/09'),
(40,11,'joy','04555554545','dove','100 g','Soap',1,45.54,45.54,'2022/05/09'),
(41,11,'joy','04555554545','Vanila','100 g','Ice  Cream',1,200,200,'2022/05/09'),
(42,11,'joy','04555554545','Dove Soap','100 g','Soap',1,30,30,'2022/05/09'),
(43,12,'yuvi','06656556656','Vanila','100 g','Ice  Cream',1,200,200,'2022/05/09'),
(44,12,'yuvi','06656556656','Dove Soap','100 g','Soap',1,30,30,'2022/05/09'),
(45,13,'Helal','456546546','Dove Soap','50g','Ice  Cream',1,200,200,'2022/05/10'),
(46,13,'Helal','56546546','Dove Soap','75g','Ice  Cream',1,200,200,'2022/05/10'),
(47,14,'hell','534656','Vanila','500.0kg','Ice  Cream',1,200,200,'2022/05/10'),
(48,14,'hell','534656','Vanila','500.0kg','Ice  Cream',1,200,200,'2022/05/10');
/*!40000 ALTER TABLE `billing_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category_tbl`
--

DROP TABLE IF EXISTS `category_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category_tbl` (
  `cat_id` int(11) NOT NULL AUTO_INCREMENT,
  `cat_name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `cat_desc` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`cat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category_tbl`
--

LOCK TABLES `category_tbl` WRITE;
/*!40000 ALTER TABLE `category_tbl` DISABLE KEYS */;
INSERT INTO `category_tbl` VALUES
(1,'Ice  Cream','descriptions here...............'),
(2,'Soap','The lorem ipsum is a placeholder text used in publishing and graphic design. This filler text is a short paragraph that contains all the letters of the alphabet.'),
(3,'Shampoo','The lorem ipsum is a placeholder text used in publishing and graphic design. This filler text is a short paragraph that contains all the letters of the alphabet. The characters are spread out evenly so that the readers attention is focused on the layout of the text instead of its content.');
/*!40000 ALTER TABLE `category_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_tbl`
--

DROP TABLE IF EXISTS `product_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_tbl` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `weight` double NOT NULL,
  `unit` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `price` double NOT NULL,
  `category` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `stock` int(11) NOT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_tbl`
--

LOCK TABLES `product_tbl` WRITE;
/*!40000 ALTER TABLE `product_tbl` DISABLE KEYS */;
INSERT INTO `product_tbl` VALUES
(1,'dove',80,'Gram',45.54,'Soap',0),
(2,'Snsilk Shampoo',1,'Litter',200,'Shampoo',1),
(3,'Dove Soap',50,'kg',30,'Soap',36),
(4,'Vanila',500,'kg',200,'Ice  Cream',29);
/*!40000 ALTER TABLE `product_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seller_tbl`
--

DROP TABLE IF EXISTS `seller_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `seller_tbl` (
  `seller_id` int(11) NOT NULL AUTO_INCREMENT,
  `seller_name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `seller_email` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `seller_password` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`seller_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seller_tbl`
--

LOCK TABLES `seller_tbl` WRITE;
/*!40000 ALTER TABLE `seller_tbl` DISABLE KEYS */;
INSERT INTO `seller_tbl` VALUES
(1,'Helal','helel@gmail.com','123456'),
(2,'Misty','misty@gmail.com','1234'),
(3,'Asif Awlad','asif@gmail.com','123'),
(4,'Demo','demo@mail.com','1234');
/*!40000 ALTER TABLE `seller_tbl` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-10 10:56:54

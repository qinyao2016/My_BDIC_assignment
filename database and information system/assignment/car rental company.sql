-- MySQL dump 10.13  Distrib 5.7.18, for macos10.12 (x86_64)
--
-- Host: localhost    Database: assignment
-- ------------------------------------------------------
-- Server version	5.7.18

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
-- Table structure for table `car`
--

DROP TABLE IF EXISTS `car`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `car` (
  `RegistrationNumber` int(10) NOT NULL,
  `ProductDate` date DEFAULT NULL,
  `PricePerDay` int(5) DEFAULT NULL,
  `MileageInTotal` decimal(10,3) DEFAULT NULL,
  `ModelName` char(20) DEFAULT NULL,
  `BuyExpenditure` int(8) DEFAULT NULL,
  `BuyDate` date DEFAULT NULL,
  PRIMARY KEY (`RegistrationNumber`),
  UNIQUE KEY `RegistrationNumber` (`RegistrationNumber`),
  KEY `ModelName` (`ModelName`),
  CONSTRAINT `car_ibfk_1` FOREIGN KEY (`ModelName`) REFERENCES `model` (`ModelName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car`
--

LOCK TABLES `car` WRITE;
/*!40000 ALTER TABLE `car` DISABLE KEYS */;
INSERT INTO `car` VALUES (2102031995,'2007-04-06',300,2948.840,'SANTANA',200000,'2015-04-02'),(2102031996,'2005-03-06',250,2944.400,'QQ',50000,'2012-07-14'),(2102031997,'2010-12-13',500,3920.250,'focus',150000,'2015-08-14'),(2102031998,'2009-06-30',450,3920.250,'focus',NULL,NULL),(2102031999,'2009-06-30',600,873.280,'Mini Cooper',NULL,NULL),(2102032000,'2013-08-25',700,823.980,'Audi A6',NULL,NULL),(2102032002,'2013-08-25',700,854.230,'Audi A6',NULL,NULL),(2102032003,'2015-08-25',650,1131.870,'Mini Cooper',NULL,NULL),(2102032004,'2016-08-25',300,573.240,'QQ',NULL,NULL);
/*!40000 ALTER TABLE `car` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `model`
--

DROP TABLE IF EXISTS `model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `model` (
  `ModelName` char(20) NOT NULL,
  `ManufactureName` char(15) DEFAULT NULL,
  `NumberSeats` int(3) DEFAULT NULL,
  PRIMARY KEY (`ModelName`),
  UNIQUE KEY `ModelName` (`ModelName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `model`
--

LOCK TABLES `model` WRITE;
/*!40000 ALTER TABLE `model` DISABLE KEYS */;
INSERT INTO `model` VALUES ('Audi A6','Audi',4),('focus','ford',4),('Mini Cooper','BMW',4),('QQ','qirui',4),('SANTANA','Volkswagen',4);
/*!40000 ALTER TABLE `model` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mumbers`
--

DROP TABLE IF EXISTS `mumbers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mumbers` (
  `MumbershipNo` int(8) NOT NULL,
  `Address` char(30) DEFAULT NULL,
  `FName` char(15) DEFAULT NULL,
  `LName` char(15) DEFAULT NULL,
  `TelNumber` char(11) DEFAULT NULL,
  `JoinDate` date DEFAULT NULL,
  PRIMARY KEY (`MumbershipNo`),
  UNIQUE KEY `MumbershipNo` (`MumbershipNo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mumbers`
--

LOCK TABLES `mumbers` WRITE;
/*!40000 ALTER TABLE `mumbers` DISABLE KEYS */;
INSERT INTO `mumbers` VALUES (16372301,'f508','Zixuan','Yang','12345678901','2018-09-03'),(16372313,'f506','Wenqi','Zhao','15011289266','1997-05-01'),(16372317,'f506','Qihui','Liu','15801651933','1998-08-03'),(16372318,'f506','Yuxuan','Wang','15011365378','1998-02-09'),(16372324,'f506','Ying','Wu','17801117385','1998-02-19'),(16372329,'f507','Qingqiong','Yang','15011365378','1997-02-07'),(16372331,'f507','Daofei','Wang','18385894701','1997-01-06');
/*!40000 ALTER TABLE `mumbers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rental`
--

DROP TABLE IF EXISTS `rental`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rental` (
  `RentID` int(10) NOT NULL,
  `RentalTime` int(3) DEFAULT NULL,
  `Mileage` decimal(10,3) DEFAULT NULL,
  `RentalExpenditure` int(7) DEFAULT NULL,
  `RentDate` date DEFAULT NULL,
  `RegistrationNumber` int(10) DEFAULT NULL,
  `MumbershipNo` int(8) DEFAULT NULL,
  PRIMARY KEY (`RentID`),
  UNIQUE KEY `RentID` (`RentID`),
  KEY `RegistrationNumber` (`RegistrationNumber`),
  KEY `MumbershipNo` (`MumbershipNo`),
  CONSTRAINT `rental_ibfk_1` FOREIGN KEY (`RegistrationNumber`) REFERENCES `car` (`RegistrationNumber`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `rental_ibfk_2` FOREIGN KEY (`MumbershipNo`) REFERENCES `mumbers` (`MumbershipNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rental`
--

LOCK TABLES `rental` WRITE;
/*!40000 ALTER TABLE `rental` DISABLE KEYS */;
INSERT INTO `rental` VALUES (1623100203,4,70.200,2000,'2018-02-08',2102032000,16372318),(1623100204,7,186.300,3500,'2016-07-19',2102032002,16372317),(1623100205,12,246.200,3000,'2010-01-02',2102031998,16372317),(1623100206,4,23.100,5000,'2018-02-07',2102032004,16372331);
/*!40000 ALTER TABLE `rental` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status`
--

DROP TABLE IF EXISTS `status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `status` (
  `RegistrationNumber` int(10) NOT NULL,
  `Status` enum('available_rent','rented','available_sell','sold') NOT NULL,
  PRIMARY KEY (`RegistrationNumber`,`Status`),
  CONSTRAINT `status_ibfk_1` FOREIGN KEY (`RegistrationNumber`) REFERENCES `car` (`RegistrationNumber`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status`
--

LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
INSERT INTO `status` VALUES (2102031995,'sold'),(2102031996,'sold'),(2102031997,'sold'),(2102031998,'available_sell'),(2102031999,'rented'),(2102032000,'available_rent'),(2102032000,'available_sell'),(2102032002,'available_rent'),(2102032003,'available_rent'),(2102032004,'available_sell');
/*!40000 ALTER TABLE `status` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-21 23:22:06

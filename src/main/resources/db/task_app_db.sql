-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: task_app_db
-- ------------------------------------------------------
-- Server version	8.0.36

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
-- Table structure for table `task_spaces`
--

DROP TABLE IF EXISTS `task_spaces`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_spaces` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `creation_dt` datetime NOT NULL,
  `deletion_dt` datetime DEFAULT NULL,
  `last_modified_dt` timestamp NULL DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `description` text,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_task_spaces_user` (`user_id`),
  CONSTRAINT `fk_task_spaces_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_spaces`
--

LOCK TABLES `task_spaces` WRITE;
/*!40000 ALTER TABLE `task_spaces` DISABLE KEYS */;
INSERT INTO `task_spaces` VALUES (38,'2025-01-10 05:25:20',NULL,NULL,'Jakarta (Jakarta EE)','',24),(39,'2025-01-10 05:25:41',NULL,NULL,'.NET','',24),(40,'2025-01-10 05:25:54',NULL,NULL,'Symfony','',24),(43,'2025-01-10 05:49:32',NULL,NULL,'myTaskSpace','',28),(44,'2025-01-10 05:52:50',NULL,NULL,'this workspace','',29),(45,'2025-01-10 05:54:59',NULL,NULL,'Hello Taskspace','',30),(46,'2025-01-10 20:53:44',NULL,NULL,'Mathematique','',24);
/*!40000 ALTER TABLE `task_spaces` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tasks`
--

DROP TABLE IF EXISTS `tasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tasks` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `creation_dt` datetime NOT NULL,
  `deletion_dt` datetime DEFAULT NULL,
  `last_modified_dt` datetime DEFAULT NULL,
  `completion_dt` datetime DEFAULT NULL,
  `content` text NOT NULL,
  `priority` int NOT NULL,
  `task_space_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tasks_task_space` (`task_space_id`),
  CONSTRAINT `fk_tasks_task_space` FOREIGN KEY (`task_space_id`) REFERENCES `task_spaces` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tasks`
--

LOCK TABLES `tasks` WRITE;
/*!40000 ALTER TABLE `tasks` DISABLE KEYS */;
INSERT INTO `tasks` VALUES (22,'2025-01-10 05:26:18',NULL,NULL,'2025-01-10 05:56:12','Set up Jakarta EE Project',0,38),(23,'2025-01-10 05:26:29',NULL,NULL,NULL,'Implement REST API',1,38),(24,'2025-01-10 05:26:43',NULL,NULL,NULL,'Database Connection',2,38),(25,'2025-01-10 05:26:50',NULL,NULL,NULL,'Security Configuration',3,38),(26,'2025-01-10 05:26:57',NULL,NULL,NULL,'Dependency Injection',4,38),(27,'2025-01-10 05:27:12',NULL,NULL,NULL,'Set up .NET Core Web API',0,39),(28,'2025-01-10 05:27:18',NULL,NULL,NULL,'Database Integration',1,39),(29,'2025-01-10 05:27:26',NULL,NULL,NULL,'Authentication',2,39),(30,'2025-01-10 05:27:33',NULL,NULL,NULL,'SignalR',3,39),(31,'2025-01-10 05:27:38',NULL,NULL,NULL,'Unit Testing',4,39),(32,'2025-01-10 05:27:49',NULL,NULL,NULL,'Set up Symfony Project',0,40),(33,'2025-01-10 05:27:56',NULL,NULL,NULL,'Doctrine ORM Setup',1,40),(34,'2025-01-10 05:28:02',NULL,NULL,NULL,'Twig Template Integration',2,40),(35,'2025-01-10 05:28:09',NULL,NULL,NULL,'Security Setup',3,40),(36,'2025-01-10 05:28:15',NULL,NULL,NULL,'Create a Form',4,40),(37,'2025-01-10 05:28:24',NULL,NULL,NULL,'API with API Platform',5,40),(41,'2025-01-10 05:49:49',NULL,NULL,'2025-01-10 05:50:23','Learn a new Language',0,43),(42,'2025-01-10 05:50:04',NULL,NULL,'2025-01-10 05:50:27','Learn DFS in Graphs',1,43),(43,'2025-01-10 05:50:19',NULL,NULL,NULL,'Touch some Grass',2,43),(44,'2025-01-10 05:53:06',NULL,NULL,'2025-01-10 05:53:35','Learn a new Language',0,44),(45,'2025-01-10 05:53:26',NULL,NULL,'2025-01-10 05:53:38','Learn how to implement a LLU Cache',1,44),(46,'2025-01-10 05:53:33',NULL,NULL,NULL,'Touch some grass',2,44),(47,'2025-01-10 05:55:09',NULL,NULL,'2025-01-10 05:55:30','Learn a new Language',0,45),(48,'2025-01-10 05:55:21',NULL,NULL,'2025-01-10 05:55:32','Try to understand request actions',1,45),(49,'2025-01-10 05:55:28',NULL,NULL,NULL,'Touch some grass',2,45),(50,'2025-01-10 20:54:01',NULL,NULL,'2025-01-10 20:54:35','Learn equations',0,46),(51,'2025-01-10 20:54:16',NULL,NULL,'2025-01-10 20:54:39','understand functions',1,46),(52,'2025-01-10 20:54:31',NULL,NULL,'2025-01-10 20:54:42','go out',2,46);
/*!40000 ALTER TABLE `tasks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `deletion_dt` datetime DEFAULT NULL,
  `creation_dt` datetime NOT NULL,
  `last_conn_dt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (24,'Ilyas','example@mail.com','$2a$10$O2EF6ZaK0ik7kxQzzuIcVebTFagy7HuyLUlBsLxsZXv4sQ5cfYdrS',NULL,'2025-01-10 05:23:38','2025-01-10 20:53:15'),(28,'someName','mai@mail.com','$2a$10$OTVRrlhMnMfwVu9MY4Zw1u2w/JDjJeZ3xXVtOuusVEs8lf0yJH.Yy',NULL,'2025-01-10 05:49:17','2025-01-10 05:49:17'),(29,'newuser','user@gmail.com','$2a$10$kabK81.zIZ.NHOxXNbbNWeGsCQAKrPIrPpl1cU.uRm9AB1Ys01Ani',NULL,'2025-01-10 05:52:37','2025-01-10 05:52:37'),(30,'HelloWorld','helllo@gmail.com','$2a$10$1RNGEH5IjASDgaEUYMJ9m.LJg0Y70yBhTHZmkWHAmijyj7xwzLWGq',NULL,'2025-01-10 05:54:48','2025-01-10 05:54:48');
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

-- Dump completed on 2025-01-10 21:01:19

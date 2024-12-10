-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               9.1.0 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Dumping data for table bank.notifications: ~4 rows (approximately)
INSERT INTO `notifications` (`id`, `date`, `message`, `user_id`, `type`, `amount`, `isdeleted`, `is_read`) VALUES
	(1, '2024-12-10 00:00:01', 'Chuyển tiền đến tài khoản Do Viet HUng chuyen tien', 1, 'transaction', -500, 0, 1),
	(2, '2024-12-10 21:20:38', 'Nhận tiền từ tài khoản Do Viet HUng chuyen tien', 1234567899, 'transaction', 500, 0, 0),
	(3, '2024-12-10 00:00:01', 'Chuyển tiền đến tài khoản do Viet hung chuyen tien', 1, 'transaction', -50, 0, 0),
	(4, '2024-12-10 22:57:50', 'Nhận tiền từ tài khoản do Viet hung chuyen tien', 1234567899, 'transaction', 50, 0, 0);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;

-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 29, 2025 at 03:07 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `joymarketdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `idProduct` varchar(100) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `stock` int(11) NOT NULL,
  `category` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`idProduct`, `name`, `price`, `stock`, `category`) VALUES
('P001', 'Chocolate Milk', 5000, 10, 'Drink'),
('P002', 'Bread', 10000, 10, 'Food');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `idUser` varchar(5) NOT NULL,
  `fullName` varchar(100) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `role` enum('admin','customer','courier') NOT NULL,
  `balance` double DEFAULT NULL,
  `emergencyContact` varchar(50) DEFAULT NULL,
  `vehicleType` varchar(255) DEFAULT NULL,
  `vehiclePlate` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`idUser`, `fullName`, `email`, `password`, `phone`, `address`, `role`, `balance`, `emergencyContact`, `vehicleType`, `vehiclePlate`) VALUES
('AD001', 'Auryn', 'auryn@gmail.com', 'admin123', '081234567890', 'Jakarta', 'admin', NULL, '021-9999', NULL, NULL),
('AD002', 'Alicia', 'alicia@gmail.com', 'admin321', '081234567891', 'Jakarta', 'admin', NULL, '021-9998', NULL, NULL),
('CR001', 'Nicholas', 'nicholas@gmail.com', 'nic123', '08123123', 'kebun jeruk', 'courier', NULL, NULL, 'cars', 'B 1010 ASD'),
('CU001', 'Rosamond', 'rosa@gmail.com', 'rosa123', '082345678901', 'Bumi', 'customer', 310000, NULL, NULL, NULL),
('CU002', 'Selina', 'selina@gmail.com', 'selsel123', '083456789012', 'Indonesia', 'customer', 10000, NULL, NULL, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`idProduct`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`idUser`),
  ADD UNIQUE KEY `email` (`email`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

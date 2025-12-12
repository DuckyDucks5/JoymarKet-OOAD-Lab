-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 12, 2025 at 04:45 AM
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
-- Table structure for table `cart_item`
--

CREATE TABLE `cart_item` (
  `idCustomer` varchar(255) NOT NULL,
  `idProduct` varchar(255) NOT NULL,
  `count` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cart_item`
--

INSERT INTO `cart_item` (`idCustomer`, `idProduct`, `count`) VALUES
('CR001', 'P002', 10),
('CU001', 'P002', 4);

-- --------------------------------------------------------

--
-- Table structure for table `delivery`
--

CREATE TABLE `delivery` (
  `idOrder` varchar(20) NOT NULL,
  `idCourier` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `delivery`
--

INSERT INTO `delivery` (`idOrder`, `idCourier`) VALUES
('ODR006', 'CR001'),
('ODR007', 'CR002'),
('ODR008', 'CR001'),
('ODR009', 'CR002'),
('ODR011', 'CR002');

-- --------------------------------------------------------

--
-- Table structure for table `order_detail`
--

CREATE TABLE `order_detail` (
  `idOrder` varchar(50) NOT NULL,
  `idProduct` varchar(50) NOT NULL,
  `qty` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `order_detail`
--

INSERT INTO `order_detail` (`idOrder`, `idProduct`, `qty`) VALUES
('ODR006', 'P001', 2),
('ODR007', 'P001', 10),
('ODR007', 'P002', 1),
('ODR008', 'P001', 1),
('ODR009', 'P002', 1),
('ODR010', 'P001', 2),
('ODR011', 'P001', 5),
('ODR011', 'P002', 10);

-- --------------------------------------------------------

--
-- Table structure for table `order_header`
--

CREATE TABLE `order_header` (
  `idOrder` varchar(50) NOT NULL,
  `idCustomer` varchar(50) NOT NULL,
  `idPromo` varchar(50) DEFAULT NULL,
  `status` varchar(30) NOT NULL,
  `ordered_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `total_amount` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `order_header`
--

INSERT INTO `order_header` (`idOrder`, `idCustomer`, `idPromo`, `status`, `ordered_at`, `total_amount`) VALUES
('ODR006', 'CR001', NULL, 'Waiting for Delivery', '2025-12-04 08:57:51', 10000),
('ODR007', 'CR001', NULL, 'Delivered', '2025-12-04 16:16:52', 60000),
('ODR008', 'CR001', NULL, 'Waiting for Delivery', '2025-12-04 17:00:19', 5000),
('ODR009', 'CR001', NULL, 'In Progress', '2025-12-04 17:02:28', 10000),
('ODR010', 'CU001', NULL, 'Pending', '2025-12-09 17:07:06', 10000),
('ODR011', 'CU003', 'PRM002', 'Delivered', '2025-12-10 15:05:34', 125000);

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
('P001', 'Chocolate Milk', 5000, 100, 'Drink'),
('P002', 'Bread', 10000, 184, 'Food');

-- --------------------------------------------------------

--
-- Table structure for table `promo`
--

CREATE TABLE `promo` (
  `idPromo` varchar(100) NOT NULL,
  `code` varchar(50) NOT NULL,
  `headline` varchar(255) NOT NULL,
  `discountPercentage` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `promo`
--

INSERT INTO `promo` (`idPromo`, `code`, `headline`, `discountPercentage`) VALUES
('PRM001', 'DISC10', 'Diskon Spesial 10%', 0.1),
('PRM002', 'HEMAT50', 'Potongan Setengah Harga!', 0.5),
('PRM003', 'NEWUSER', 'Promo Pengguna Baru 20%', 0.2),
('PRM004', 'FLASH5', 'Flash Sale 5%', 0.05);

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
  `vehiclePlate` varchar(255) DEFAULT NULL,
  `gender` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`idUser`, `fullName`, `email`, `password`, `phone`, `address`, `role`, `balance`, `emergencyContact`, `vehicleType`, `vehiclePlate`, `gender`) VALUES
('AD001', 'Auryn', 'auryn@gmail.com', 'admin123', '081234567890', 'Jakarta', 'admin', NULL, '021-9999', NULL, NULL, ''),
('AD002', 'Alicia', 'alicia@gmail.com', 'admin321', '081234567891', 'Jakarta', 'admin', NULL, '021-9998', NULL, NULL, ''),
('CR001', 'Nicholas', 'nicholas@gmail.com', 'nic123', '081231232383', 'Kebon Jeruk', 'courier', 1000015000, NULL, 'Car', 'B 1010 ASD', ''),
('CR002', 'Kevin ', 'kevin@gmail.com', 'kevin123', '0812345', 'kemanggisan', 'courier', NULL, NULL, 'Motorcycle', 'B 4325 BS', ''),
('CU001', 'Rosamond', 'rosa@gmail.com', 'rosa123', '082345678901', 'Bum', 'customer', 325000, NULL, NULL, NULL, 'Female'),
('CU002', 'Selina', 'selina@gmail.com', 'selsel123', '083456789012', 'Indonesia', 'customer', 10000, NULL, NULL, NULL, 'Female'),
('CU003', 'ryn', 'ryn@gmail.com', 'rynryn', '1234567890', 'jakut', 'customer', 97500, NULL, NULL, NULL, 'Female'),
('CU004', 'fab', 'fab@gmail.com', 'fabfab', '12345678910', 'jakarta', 'customer', 0, NULL, NULL, NULL, 'Male'),
('CU005', 'van', 'van@gmail.com', 'vanvan', '09123456789', 'america', 'customer', 0, NULL, NULL, NULL, 'Female');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cart_item`
--
ALTER TABLE `cart_item`
  ADD PRIMARY KEY (`idCustomer`,`idProduct`),
  ADD KEY `idProduct` (`idProduct`);

--
-- Indexes for table `delivery`
--
ALTER TABLE `delivery`
  ADD PRIMARY KEY (`idOrder`,`idCourier`),
  ADD KEY `fk_delivery_courier` (`idCourier`);

--
-- Indexes for table `order_detail`
--
ALTER TABLE `order_detail`
  ADD PRIMARY KEY (`idOrder`,`idProduct`),
  ADD KEY `idProduct` (`idProduct`);

--
-- Indexes for table `order_header`
--
ALTER TABLE `order_header`
  ADD PRIMARY KEY (`idOrder`),
  ADD KEY `idCustomer` (`idCustomer`),
  ADD KEY `idPromo` (`idPromo`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`idProduct`);

--
-- Indexes for table `promo`
--
ALTER TABLE `promo`
  ADD PRIMARY KEY (`idPromo`),
  ADD UNIQUE KEY `code` (`code`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`idUser`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cart_item`
--
ALTER TABLE `cart_item`
  ADD CONSTRAINT `cart_item_ibfk_1` FOREIGN KEY (`idCustomer`) REFERENCES `users` (`idUser`),
  ADD CONSTRAINT `cart_item_ibfk_2` FOREIGN KEY (`idProduct`) REFERENCES `product` (`idProduct`);

--
-- Constraints for table `delivery`
--
ALTER TABLE `delivery`
  ADD CONSTRAINT `fk_delivery_courier` FOREIGN KEY (`idCourier`) REFERENCES `users` (`idUser`),
  ADD CONSTRAINT `fk_delivery_order` FOREIGN KEY (`idOrder`) REFERENCES `order_header` (`idOrder`);

--
-- Constraints for table `order_detail`
--
ALTER TABLE `order_detail`
  ADD CONSTRAINT `order_detail_ibfk_1` FOREIGN KEY (`idOrder`) REFERENCES `order_header` (`idOrder`) ON DELETE CASCADE,
  ADD CONSTRAINT `order_detail_ibfk_2` FOREIGN KEY (`idProduct`) REFERENCES `product` (`idProduct`) ON DELETE CASCADE;

--
-- Constraints for table `order_header`
--
ALTER TABLE `order_header`
  ADD CONSTRAINT `order_header_ibfk_1` FOREIGN KEY (`idCustomer`) REFERENCES `users` (`idUser`),
  ADD CONSTRAINT `order_header_ibfk_2` FOREIGN KEY (`idPromo`) REFERENCES `promo` (`idPromo`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

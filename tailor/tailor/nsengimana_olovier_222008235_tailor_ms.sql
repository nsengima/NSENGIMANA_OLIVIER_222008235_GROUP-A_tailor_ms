-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 19, 2024 at 09:39 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `nsengimana_olovier_222008235_tailor_ms`
--

-- --------------------------------------------------------

--
-- Table structure for table `appointment`
--

CREATE TABLE `appointment` (
  `appointment_id` int(34) NOT NULL,
  `customer_id` int(21) NOT NULL,
  `appointment_date` varchar(30) NOT NULL,
  `purpose` varchar(23) NOT NULL,
  `supplier_id` int(12) NOT NULL,
  `status` varchar(41) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `appointment`
--

INSERT INTO `appointment` (`appointment_id`, `customer_id`, `appointment_date`, `purpose`, `supplier_id`, `status`) VALUES
(4, 2, '2.3.2023', 'dealing', 4, 'pending'),
(5, 7, '4.4.2021', 'BASE DRESS', 9, 'PEND');

-- --------------------------------------------------------

--
-- Table structure for table `appointment_view`
--

CREATE TABLE `appointment_view` (
  `appointment_id` int(34) DEFAULT NULL,
  `customer_id` int(21) DEFAULT NULL,
  `appointment_date` date DEFAULT NULL,
  `purpose` varchar(23) DEFAULT NULL,
  `supplier_id` int(12) DEFAULT NULL,
  `status` varchar(41) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `billinginvoice_view`
--

CREATE TABLE `billinginvoice_view` (
  `invoice_id` int(12) DEFAULT NULL,
  `order_id` int(12) DEFAULT NULL,
  `invoice_issued_date` date DEFAULT NULL,
  `total_amount` int(14) DEFAULT NULL,
  `payment_status` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `billing_and_invoice`
--

CREATE TABLE `billing_and_invoice` (
  `invoice_id` int(12) NOT NULL,
  `order_id` int(12) NOT NULL,
  `invoice_issued_date` varchar(30) NOT NULL,
  `total_amount` int(14) NOT NULL,
  `payment_status` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `billing_and_invoice`
--

INSERT INTO `billing_and_invoice` (`invoice_id`, `order_id`, `invoice_issued_date`, `total_amount`, `payment_status`) VALUES
(1, 2, '1998-07-09', 20, 'equity bank');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `customer_id` int(34) NOT NULL,
  `names` varchar(50) NOT NULL,
  `contact` int(10) NOT NULL,
  `order_history` varchar(100) NOT NULL,
  `preferred_fabric_choice` varchar(45) NOT NULL,
  `payment_information` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`customer_id`, `names`, `contact`, `order_history`, `preferred_fabric_choice`, `payment_information`) VALUES
(5, 'jefg', 234, 'dej', 'cotton', 'wenfo'),
(7, 'Kamana', 786543213, 'old', 'silver', 'bpr'),
(8, 'samuel', 785423123, ' first', 'cottle', 'momo'),
(9, 'rwema', 97654321, '12q', 'blue', 'ecobank');

-- --------------------------------------------------------

--
-- Table structure for table `customerordersummary`
--

CREATE TABLE `customerordersummary` (
  `customer_id` int(34) DEFAULT NULL,
  `names` varchar(50) DEFAULT NULL,
  `contact` int(10) DEFAULT NULL,
  `order_history` varchar(100) DEFAULT NULL,
  `preferred_fabric_choice` varchar(45) DEFAULT NULL,
  `payment_information` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `customer_view`
--

CREATE TABLE `customer_view` (
  `customer_id` int(34) DEFAULT NULL,
  `names` varchar(50) DEFAULT NULL,
  `contact` int(10) DEFAULT NULL,
  `order_history` varchar(100) DEFAULT NULL,
  `preferred_fabric_choice` varchar(45) DEFAULT NULL,
  `payment_information` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `deletecustomer`
--

CREATE TABLE `deletecustomer` (
  `customer_id` int(34) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `fabric`
--

CREATE TABLE `fabric` (
  `fabric_id` int(23) NOT NULL,
  `fabric_type` varchar(34) NOT NULL,
  `fabric_color` varchar(23) NOT NULL,
  `fabric_design` varchar(45) NOT NULL,
  `price_per_meter` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `fabric`
--

INSERT INTO `fabric` (`fabric_id`, `fabric_type`, `fabric_color`, `fabric_design`, `price_per_meter`) VALUES
(1, 'fitting', 'red', 'fitting inch', 150);

-- --------------------------------------------------------

--
-- Table structure for table `fabric_view`
--

CREATE TABLE `fabric_view` (
  `fabric_id` int(23) DEFAULT NULL,
  `fabric_type` varchar(34) DEFAULT NULL,
  `fabric_color` varchar(23) DEFAULT NULL,
  `fabric_design` varchar(45) DEFAULT NULL,
  `price_per_meter` int(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `inventory_item`
--

CREATE TABLE `inventory_item` (
  `item_id` int(6) NOT NULL,
  `item_name` varchar(23) NOT NULL,
  `description` varchar(32) NOT NULL,
  `quantity_in_stock` int(12) NOT NULL,
  `supplier_id` int(21) NOT NULL,
  `purchasing_price` int(32) NOT NULL,
  `selling_price` int(23) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `inventory_item`
--

INSERT INTO `inventory_item` (`item_id`, `item_name`, `description`, `quantity_in_stock`, `supplier_id`, `purchasing_price`, `selling_price`) VALUES
(1, 'cotton', 'smoth cotton', 12, 1, 300, 500);

-- --------------------------------------------------------

--
-- Table structure for table `inventory_item_view`
--

CREATE TABLE `inventory_item_view` (
  `item_id` int(6) DEFAULT NULL,
  `item_name` varchar(23) DEFAULT NULL,
  `description` varchar(32) DEFAULT NULL,
  `quantity_in_stock` int(12) DEFAULT NULL,
  `supplier_id` int(21) DEFAULT NULL,
  `purchasing_price` int(32) DEFAULT NULL,
  `selling_price` int(23) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `order1`
--

CREATE TABLE `order1` (
  `order_id` int(20) NOT NULL,
  `customer_id` int(34) DEFAULT NULL,
  `order_date` date NOT NULL,
  `delivery_date` date NOT NULL,
  `status` varchar(21) NOT NULL,
  `total_amount` int(15) NOT NULL,
  `fabric_id` int(34) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `order_view`
--

CREATE TABLE `order_view` (
  `order_id` int(20) DEFAULT NULL,
  `customer_id` int(34) DEFAULT NULL,
  `order_date` date DEFAULT NULL,
  `delivery_date` date DEFAULT NULL,
  `status` varchar(21) DEFAULT NULL,
  `total_amount` int(15) DEFAULT NULL,
  `fabric_id` int(34) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `staff_id` int(15) NOT NULL,
  `first_name` varchar(16) NOT NULL,
  `last_name` varchar(15) NOT NULL,
  `role` varchar(34) NOT NULL,
  `contact_information` int(34) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`staff_id`, `first_name`, `last_name`, `role`, `contact_information`) VALUES
(1, 'Nshuti', 'Elie', 'tailor', 783832520);

-- --------------------------------------------------------

--
-- Table structure for table `staff_view`
--

CREATE TABLE `staff_view` (
  `staff_id` int(15) DEFAULT NULL,
  `first_name` varchar(16) DEFAULT NULL,
  `last_name` varchar(15) DEFAULT NULL,
  `role` varchar(34) DEFAULT NULL,
  `contact_information` int(34) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `supplier_id` int(12) NOT NULL,
  `supplier_name` varchar(34) NOT NULL,
  `contact_information` int(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`supplier_id`, `supplier_name`, `contact_information`) VALUES
(1, 'Hirwa', 783832520);

-- --------------------------------------------------------

--
-- Table structure for table `supplier_view`
--

CREATE TABLE `supplier_view` (
  `supplier_id` int(12) DEFAULT NULL,
  `supplier_name` varchar(34) DEFAULT NULL,
  `contact_information` int(12) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(128) NOT NULL,
  `password` varchar(128) NOT NULL,
  `Firstname` varchar(35) NOT NULL,
  `Secondname` varchar(25) NOT NULL,
  `Email` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `Firstname`, `Secondname`, `Email`) VALUES
(1, '222008235', '222008235', '', '', 0),
(2, 'admin', 'admin123', '', '', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `appointment`
--
ALTER TABLE `appointment`
  ADD PRIMARY KEY (`appointment_id`);

--
-- Indexes for table `billing_and_invoice`
--
ALTER TABLE `billing_and_invoice`
  ADD PRIMARY KEY (`invoice_id`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`customer_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `appointment`
--
ALTER TABLE `appointment`
  MODIFY `appointment_id` int(34) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `billing_and_invoice`
--
ALTER TABLE `billing_and_invoice`
  MODIFY `invoice_id` int(12) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `customer_id` int(34) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

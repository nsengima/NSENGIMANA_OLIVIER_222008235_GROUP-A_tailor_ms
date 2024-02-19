
--
-- Database: `online tailoring management system`
--



-- --------------------------------------------------------

--
-- Table structure for table `appointment`
--

CREATE TABLE `appointment` (
  `appointment_id` int(34) NOT NULL PRIMARY KEY AUTO_INCREMENT,
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
(1, 1, '0000-00-00', 'buy', 1, 'pending'),
(2, 2, '0000-00-00', 'pay', 3, 'finished');

-- --------------------------------------------------------

--
-- Stand-in structure for view `appointment_view`
-- (See below for the actual view)
--
CREATE TABLE `appointment_view` (
`appointment_id` int(34)
,`customer_id` int(21)
,`appointment_date` date
,`purpose` varchar(23)
,`supplier_id` int(12)
,`status` varchar(41)
);

-- --------------------------------------------------------

--
-- Table structure for table `biling and invoice`
--

CREATE TABLE `billing_and_invoice` (
  `invoice_id` int(12) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `order_id` int(12) NOT NULL,
  `invoice_issued_date` varchar(30) NOT NULL,
  `total_amount` int(14) NOT NULL,
  `payment_status` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `biling and invoice`
--

INSERT INTO `billing_and_invoice` (`invoice_id`, `order_id`, `invoice_issued_date`, `total_amount`, `payment_status`) VALUES
(1, 2, '1998-07-09', 20, 'equity bank');

-- --------------------------------------------------------

--
-- Stand-in structure for view `billinginvoice_view`
-- (See below for the actual view)
--
CREATE TABLE `billinginvoice_view` (
`invoice_id` int(12)
,`order_id` int(12)
,`invoice_issued_date` date
,`total_amount` int(14)
,`payment_status` varchar(30)
);

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `customer_id` int(34) NOT NULL AUTO_INCREMENT,
  `names` varchar(50) NOT NULL,
  `contact` int(10) NOT NULL,
  `order_history` varchar(100) NOT NULL,
  `preferred_fabric_choice` varchar(45) NOT NULL,
  `payment_information` varchar(100) NOT NULL,
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`customer_id`, `names`, `contact`, `order_history`, `preferred_fabric_choice`, `payment_information`) VALUES
(1, 'MANZI', 786543212, 'scheduled', 'cotton', 'bk');

-- --------------------------------------------------------

--
-- Stand-in structure for view `customerordersummary`
-- (See below for the actual view)
--
CREATE TABLE `customerordersummary` (
`customer_id` int(34)
,`names` varchar(50)
,`contact` int(10)
,`order_history` varchar(100)
,`preferred_fabric_choice` varchar(45)
,`payment_information` varchar(100)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `customer_view`
-- (See below for the actual view)
--
CREATE TABLE `customer_view` (
`customer_id` int(34)
,`names` varchar(50)
,`contact` int(10)
,`order_history` varchar(100)
,`preferred_fabric_choice` varchar(45)
,`payment_information` varchar(100)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `deletecustomer`
-- (See below for the actual view)
--
CREATE TABLE `deletecustomer` (
`customer_id` int(34)
);

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
-- Stand-in structure for view `fabric_view`
-- (See below for the actual view)
--
CREATE TABLE `fabric_view` (
`fabric_id` int(23)
,`fabric_type` varchar(34)
,`fabric_color` varchar(23)
,`fabric_design` varchar(45)
,`price_per_meter` int(5)
);

-- --------------------------------------------------------

--
-- Table structure for table `inventory item`
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
-- Dumping data for table `inventory item`
--



-- --------------------------------------------------------

--
-- Stand-in structure for view `inventory_item_view`
-- (See below for the actual view)
--
CREATE TABLE `inventory_item_view` (
`item_id` int(6)
,`item_name` varchar(23)
,`description` varchar(32)
,`quantity_in_stock` int(12)
,`supplier_id` int(21)
,`purchasing_price` int(32)
,`selling_price` int(23)
);

INSERT INTO `inventory_item` (`item_id`, `item_name`, `description`, `quantity_in_stock`, `supplier_id`, `purchasing_price`, `selling_price`) VALUES
(1, 'cotton', 'smoth cotton', 12, 1, 300, 500);
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

--
-- Dumping data for table `order1`
--

INSERT INTO `order1` (`order_id`, `customer_id`, `order_date`, `delivery_date`, `status`, `total_amount`, `fabric_id`) VALUES
(3, 1, '2022-03-03', '2023-01-12', 'pending', 2300, 1);

-- --------------------------------------------------------

--
-- Stand-in structure for view `order_view`
-- (See below for the actual view)
--
CREATE TABLE `order_view` (
`order_id` int(20)
,`customer_id` int(34)
,`order_date` date
,`delivery_date` date
,`status` varchar(21)
,`total_amount` int(15)
,`fabric_id` int(34)
);

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
-- Stand-in structure for view `staff_view`
-- (See below for the actual view)
--
CREATE TABLE `staff_view` (
`staff_id` int(15)
,`first_name` varchar(16)
,`last_name` varchar(15)
,`role` varchar(34)
,`contact_information` int(34)
);

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

CREATE TABLE `supplier_view` (
`supplier_id` int(12)
,`supplier_name` varchar(34)
,`contact_information` int(12)
);

-- --------------------------------------------------------
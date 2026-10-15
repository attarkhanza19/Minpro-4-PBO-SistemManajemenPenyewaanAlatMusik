-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Oct 15, 2026 at 04:15 AM
-- Server version: 8.0.30
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_penyewaan_alat_musik`
--

-- --------------------------------------------------------

--
-- Table structure for table `alat_musik`
--

CREATE TABLE `alat_musik` (
  `id_alat` varchar(20) NOT NULL,
  `nama_alat` varchar(100) NOT NULL,
  `jenis_alat` varchar(50) NOT NULL,
  `harga_sewa` double NOT NULL,
  `status` varchar(20) NOT NULL,
  `bahan` varchar(50) DEFAULT NULL,
  `daya` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `alat_musik`
--

INSERT INTO `alat_musik` (`id_alat`, `nama_alat`, `jenis_alat`, `harga_sewa`, `status`, `bahan`, `daya`) VALUES
('DRM-001', 'Drum Elektrik Besar Banar', 'Drum', 150000, 'Ready', NULL, 220),
('GTR-001', 'Gitar Akustik', 'Gitar', 100000, 'Ready', 'Kayu', NULL),
('KBD-001', 'Keyboard', 'Keyboard', 150000, 'Ready', NULL, 220);

-- --------------------------------------------------------

--
-- Table structure for table `pelanggan`
--

CREATE TABLE `pelanggan` (
  `id_pelanggan` varchar(20) NOT NULL,
  `nama_pelanggan` varchar(100) NOT NULL,
  `no_telepon` varchar(20) NOT NULL,
  `alamat` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `pelanggan`
--

INSERT INTO `pelanggan` (`id_pelanggan`, `nama_pelanggan`, `no_telepon`, `alamat`) VALUES
('PLN-001', 'Dilan', '08882293918', 'Samarinda'),
('PLN-002', 'Ibrah', '+628123456789', 'Samarinda'),
('PLN-003', 'Budi', '081234567890', 'Samarinda');

-- --------------------------------------------------------

--
-- Table structure for table `penyewaan`
--

CREATE TABLE `penyewaan` (
  `id_penyewaan` varchar(20) NOT NULL,
  `id_pelanggan` varchar(20) NOT NULL,
  `id_alat` varchar(20) NOT NULL,
  `lama_sewa` int NOT NULL,
  `diskon` double DEFAULT '0',
  `total_harga` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `penyewaan`
--

INSERT INTO `penyewaan` (`id_penyewaan`, `id_pelanggan`, `id_alat`, `lama_sewa`, `diskon`, `total_harga`) VALUES
('SW-001', 'PLN-001', 'GTR-001', 2, 0, 200000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `alat_musik`
--
ALTER TABLE `alat_musik`
  ADD PRIMARY KEY (`id_alat`);

--
-- Indexes for table `pelanggan`
--
ALTER TABLE `pelanggan`
  ADD PRIMARY KEY (`id_pelanggan`);

--
-- Indexes for table `penyewaan`
--
ALTER TABLE `penyewaan`
  ADD PRIMARY KEY (`id_penyewaan`),
  ADD KEY `id_pelanggan` (`id_pelanggan`),
  ADD KEY `id_alat` (`id_alat`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `penyewaan`
--
ALTER TABLE `penyewaan`
  ADD CONSTRAINT `penyewaan_ibfk_1` FOREIGN KEY (`id_pelanggan`) REFERENCES `pelanggan` (`id_pelanggan`),
  ADD CONSTRAINT `penyewaan_ibfk_2` FOREIGN KEY (`id_alat`) REFERENCES `alat_musik` (`id_alat`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 22, 2020 at 05:48 PM
-- Server version: 10.1.37-MariaDB
-- PHP Version: 7.3.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `facial_recognition`
--

-- --------------------------------------------------------

--
-- Table structure for table `akun`
--

CREATE TABLE `akun` (
  `ID_AKUN` int(11) NOT NULL,
  `USENAME_AKUN` varchar(100) NOT NULL,
  `PASSWORD_AKUN` varchar(255) NOT NULL,
  `NAMA_AKUN` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `dataset_register`
--

CREATE TABLE `dataset_register` (
  `auto_id` int(11) NOT NULL,
  `id_regis` int(11) NOT NULL,
  `nama_lengkap` varchar(255) NOT NULL,
  `kamera` varchar(255) NOT NULL,
  `jarak_layar` int(11) NOT NULL,
  `ekspresi` varchar(255) NOT NULL,
  `tgl_regis` varchar(20) NOT NULL,
  `keterangan` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `dataset_register`
--

INSERT INTO `dataset_register` (`auto_id`, `id_regis`, `nama_lengkap`, `kamera`, `jarak_layar`, `ekspresi`, `tgl_regis`, `keterangan`) VALUES
(4, 1, 'Hilnanda Ardiansyah', 'Webcam', 30, 'Bahagia', '16/04/2020', 'menggunakan lampu kamar'),
(5, 2, 'Hilnanda Ardiansyah', 'Webcam', 30, 'Waiting for Expression..', '16/04/2020', 'Waiting'),
(6, 3, 'Hilnanda Ardiansyah', 'Webcam', 30, 'Marah', '16/04/2020', 'menggunakan lampu kamar'),
(7, 4, 'Hilnanda Ardiansyah', 'Webcam', 30, 'Sedih', '16/04/2020', 'menggunakan lampu kamar'),
(8, 5, 'Hilnanda Ardiansyah', 'Webcam', 30, 'Jijik', '16/04/2020', 'menggunakan lampu kamar'),
(10, 6, 'Hilnanda Ardiansyah', 'Webcam', 30, 'Terkejut', '16/04/2020', 'menggunakan kamera kamar');

-- --------------------------------------------------------

--
-- Table structure for table `stimulus`
--

CREATE TABLE `stimulus` (
  `ID_STIMULUS` int(11) NOT NULL,
  `NAMA_STIMULUS` varchar(255) NOT NULL,
  `SOURCE_STIMULUS` varchar(255) NOT NULL,
  `EKSPRESI_STIMULUS` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `userdata`
--

CREATE TABLE `userdata` (
  `ID_USER` int(11) NOT NULL,
  `ID_STIMULUS` int(11) DEFAULT NULL,
  `NAMA_USER` varchar(255) NOT NULL,
  `JENIS_KELAMIN` varchar(255) NOT NULL,
  `HAPPY_USER` int(11) DEFAULT NULL,
  `SAD_USER` int(11) DEFAULT NULL,
  `DISGUST_USER` int(11) DEFAULT NULL,
  `SURPRISED_USER` int(11) DEFAULT NULL,
  `ANGRY_USER` int(11) DEFAULT NULL,
  `KETERANGAN_USER` varchar(500) DEFAULT NULL,
  `VIDEO_USER` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `akun`
--
ALTER TABLE `akun`
  ADD PRIMARY KEY (`ID_AKUN`),
  ADD UNIQUE KEY `AKUN_PK` (`ID_AKUN`);

--
-- Indexes for table `dataset_register`
--
ALTER TABLE `dataset_register`
  ADD PRIMARY KEY (`auto_id`);

--
-- Indexes for table `stimulus`
--
ALTER TABLE `stimulus`
  ADD PRIMARY KEY (`ID_STIMULUS`),
  ADD UNIQUE KEY `STIMULUS_PK` (`ID_STIMULUS`);

--
-- Indexes for table `userdata`
--
ALTER TABLE `userdata`
  ADD PRIMARY KEY (`ID_USER`),
  ADD UNIQUE KEY `USERDATA_PK` (`ID_USER`),
  ADD KEY `MENONTON_FK` (`ID_STIMULUS`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `dataset_register`
--
ALTER TABLE `dataset_register`
  MODIFY `auto_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `userdata`
--
ALTER TABLE `userdata`
  ADD CONSTRAINT `FK_USERDATA_MENONTON_STIMULUS` FOREIGN KEY (`ID_STIMULUS`) REFERENCES `stimulus` (`ID_STIMULUS`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

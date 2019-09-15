-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 25 Agu 2019 pada 15.33
-- Versi Server: 10.1.28-MariaDB
-- PHP Version: 7.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_sawlection`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `admin`
--

CREATE TABLE `admin` (
  `id_admin` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `jabatan` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `admin`
--

INSERT INTO `admin` (`id_admin`, `id_user`, `jabatan`) VALUES
(1, 1, 'Laboran FRI');

-- --------------------------------------------------------

--
-- Struktur dari tabel `keanggotaan`
--

CREATE TABLE `keanggotaan` (
  `id_user` int(11) DEFAULT NULL,
  `id_lab` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `keanggotaan`
--

INSERT INTO `keanggotaan` (`id_user`, `id_lab`) VALUES
(2, 1),
(3, 1),
(4, 2),
(21, 2);

-- --------------------------------------------------------

--
-- Struktur dari tabel `kriteria`
--

CREATE TABLE `kriteria` (
  `id_kriteria` int(11) NOT NULL,
  `id_seleksi` int(5) DEFAULT NULL,
  `nama_kriteria` varchar(255) DEFAULT NULL,
  `tipe_kriteria` varchar(10) DEFAULT NULL,
  `bobot_kriteria` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `kriteria`
--

INSERT INTO `kriteria` (`id_kriteria`, `id_seleksi`, `nama_kriteria`, `tipe_kriteria`, `bobot_kriteria`) VALUES
(1, 1, 'IPK', 'Benefit', 15),
(2, 1, 'Nilai Dasjar', 'Benefit', 25),
(3, 1, 'Hasil Tes Tulis', 'Benefit', 20),
(4, 1, 'Hasil Tes Praktik', 'Benefit', 25),
(5, 1, 'Hasil Tes IQ', 'Benefit', 10),
(6, 1, 'Semester', 'Cost', 5),
(7, 2, 'Nilai ERP', 'Benefit', 40),
(8, 2, 'Nilai RPB', 'Benefit', 60);

-- --------------------------------------------------------

--
-- Struktur dari tabel `laboratorium`
--

CREATE TABLE `laboratorium` (
  `id_lab` int(11) NOT NULL,
  `nama_lab` varchar(50) NOT NULL,
  `lokasi` varchar(50) NOT NULL,
  `prodi` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `laboratorium`
--

INSERT INTO `laboratorium` (`id_lab`, `nama_lab`, `lokasi`, `prodi`) VALUES
(1, 'SISJAR', 'C105', 'Sistem Informasi'),
(2, 'BPAD', 'C101', 'Sistem Informasi'),
(3, 'DASPRO', 'C200', 'Sistem Informasi'),
(4, 'ENSYS', 'C207', 'Teknik Industri'),
(5, 'SISPROMASI', 'C305', 'Teknik Industri');

-- --------------------------------------------------------

--
-- Struktur dari tabel `panitia`
--

CREATE TABLE `panitia` (
  `id_panitia` int(11) NOT NULL,
  `id_user` int(11) DEFAULT NULL,
  `id_seleksi` int(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `panitia`
--

INSERT INTO `panitia` (`id_panitia`, `id_user`, `id_seleksi`) VALUES
(4, 2, 1),
(5, 3, 1),
(6, 21, 2),
(7, 4, 2);

-- --------------------------------------------------------

--
-- Struktur dari tabel `pendaftar`
--

CREATE TABLE `pendaftar` (
  `id_pendaftar` int(11) NOT NULL,
  `id_seleksi` int(5) DEFAULT NULL,
  `id_user` int(11) DEFAULT NULL,
  `hasil` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `pendaftar`
--

INSERT INTO `pendaftar` (`id_pendaftar`, `id_seleksi`, `id_user`, `hasil`) VALUES
(1, 1, 5, 70.984),
(2, 1, 6, 88.796),
(3, 1, 7, 84.75),
(4, 1, 8, 85.212),
(5, 1, 9, 75.609),
(6, 1, 10, 85.504),
(7, 1, 11, 78.749),
(8, 1, 12, 80.949),
(9, 1, 13, 90.829),
(10, 1, 14, 86.76),
(11, 1, 15, 89.24),
(12, 1, 16, 91.605),
(13, 1, 17, 86.317),
(14, 1, 18, 84.019),
(15, 2, 20, 100);

-- --------------------------------------------------------

--
-- Struktur dari tabel `rangking`
--

CREATE TABLE `rangking` (
  `id_rangking` int(11) NOT NULL,
  `id_pendaftar` int(11) NOT NULL,
  `id_kriteria` int(11) NOT NULL,
  `nilai` double NOT NULL,
  `nilai_normalisasi` double NOT NULL,
  `bobot_normalisasi` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `rangking`
--

INSERT INTO `rangking` (`id_rangking`, `id_pendaftar`, `id_kriteria`, `nilai`, `nilai_normalisasi`, `bobot_normalisasi`) VALUES
(6, 1, 5, 3.05, 0.023, 0.226),
(7, 1, 2, 3, 0.75, 18.75),
(8, 1, 3, 85, 0.914, 18.28),
(9, 1, 4, 78, 0.918, 22.941),
(10, 1, 5, 95, 0.704, 7.037),
(11, 1, 6, 4, 0.75, 3.75),
(12, 2, 6, 3, 1, 5),
(13, 2, 1, 3.57, 0.937, 14.055),
(14, 2, 2, 3.5, 0.875, 21.875),
(15, 2, 3, 89, 0.957, 19.14),
(16, 2, 4, 75, 0.882, 22.059),
(17, 2, 5, 90, 0.667, 6.667),
(18, 3, 6, 5, 0.6, 3),
(19, 3, 1, 3.64, 0.955, 14.331),
(20, 3, 2, 3, 0.75, 18.75),
(21, 3, 3, 92, 0.989, 19.785),
(22, 3, 4, 70, 0.824, 20.588),
(23, 3, 5, 112, 0.83, 8.296),
(24, 4, 6, 3, 1, 5),
(25, 4, 1, 3.01, 0.79, 11.85),
(26, 4, 2, 3.5, 0.875, 21.875),
(27, 4, 3, 73, 0.785, 15.699),
(28, 4, 4, 80, 0.941, 23.529),
(29, 4, 5, 98, 0.726, 7.259),
(30, 5, 6, 6, 0.5, 2.5),
(31, 5, 1, 2.85, 0.748, 11.22),
(32, 5, 2, 3, 0.75, 18.75),
(33, 5, 3, 67, 0.72, 14.409),
(34, 5, 4, 73, 0.859, 21.471),
(35, 5, 5, 98, 0.726, 7.259),
(36, 6, 6, 3, 1, 5),
(37, 6, 1, 2.47, 0.648, 9.724),
(38, 6, 2, 4, 1, 25),
(39, 6, 3, 70, 0.753, 15.054),
(40, 6, 4, 75, 0.882, 22.059),
(41, 6, 5, 117, 0.867, 8.667),
(42, 7, 6, 4, 0.75, 3.75),
(43, 7, 1, 3.13, 0.822, 12.323),
(44, 7, 2, 2.5, 0.625, 15.625),
(45, 7, 3, 80, 0.86, 17.204),
(46, 7, 4, 70, 0.824, 20.588),
(47, 7, 5, 125, 0.926, 9.259),
(48, 8, 1, 2.54, 0.667, 10),
(49, 8, 6, 3, 1, 5),
(50, 8, 2, 3, 0.75, 18.75),
(51, 8, 3, 77, 0.828, 16.559),
(52, 8, 4, 80, 0.941, 23.529),
(53, 8, 5, 96, 0.711, 7.111),
(54, 9, 6, 5, 0.6, 3),
(55, 9, 1, 3.05, 0.801, 12.008),
(56, 9, 2, 4, 1, 25),
(57, 9, 3, 90, 0.968, 19.355),
(58, 9, 4, 75, 0.882, 22.059),
(59, 9, 5, 127, 0.941, 9.407),
(60, 10, 6, 3, 1, 5),
(61, 10, 1, 3.37, 0.885, 13.268),
(62, 10, 2, 3, 0.75, 18.75),
(63, 10, 3, 84, 0.903, 18.065),
(64, 10, 4, 80, 0.941, 23.529),
(65, 10, 5, 110, 0.815, 8.148),
(66, 11, 6, 3, 1, 5),
(67, 11, 1, 3.12, 0.819, 12.283),
(68, 11, 2, 4, 1, 25),
(69, 11, 3, 68, 0.731, 14.624),
(70, 11, 4, 85, 1, 25),
(71, 11, 5, 99, 0.733, 7.333),
(72, 12, 6, 3, 1, 5),
(73, 12, 1, 3.75, 0.984, 14.764),
(74, 12, 2, 3.5, 0.875, 21.875),
(75, 12, 3, 86, 0.925, 18.495),
(76, 12, 4, 73, 0.859, 21.471),
(77, 12, 5, 135, 1, 10),
(78, 13, 6, 3, 1, 5),
(79, 13, 1, 3.81, 1, 15),
(80, 13, 2, 3, 0.75, 18.75),
(81, 13, 3, 79, 0.849, 16.989),
(82, 13, 4, 75, 0.882, 22.059),
(83, 13, 5, 115, 0.852, 8.519),
(84, 14, 6, 3, 1, 5),
(85, 14, 1, 3.31, 0.869, 13.031),
(86, 14, 2, 2.5, 0.625, 15.625),
(87, 14, 3, 93, 1, 20),
(88, 14, 4, 71, 0.835, 20.882),
(89, 14, 5, 128, 0.948, 9.481),
(90, 15, 7, 90, 1, 40),
(91, 15, 8, 70, 1, 60);

-- --------------------------------------------------------

--
-- Struktur dari tabel `seleksi`
--

CREATE TABLE `seleksi` (
  `id_seleksi` int(5) NOT NULL,
  `nama_seleksi` varchar(50) DEFAULT NULL,
  `id_lab` int(11) DEFAULT NULL,
  `tahun_ajaran` varchar(50) DEFAULT NULL,
  `kuota` int(11) DEFAULT NULL,
  `Deskripsi` varchar(200) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `seleksi`
--

INSERT INTO `seleksi` (`id_seleksi`, `nama_seleksi`, `id_lab`, `tahun_ajaran`, `kuota`, `Deskripsi`, `status`) VALUES
(1, 'Seleksi Penerimaan Asisten Lab. Sisjar', 1, '2018/2019', 10, 'Untuk menyeleksi asisten praktikum Lab. Sisjar yang sesuai dengan kriteria yang diharapkan', NULL),
(2, 'Seleksi Penerimaan asisten', 2, '2018/2019', 10, 'Penerimaan asisten', NULL);

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `id_user` int(11) NOT NULL,
  `nim` int(11) DEFAULT NULL,
  `nama` varchar(50) NOT NULL,
  `kontak` varchar(15) DEFAULT NULL,
  `username` varchar(10) NOT NULL,
  `password` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`id_user`, `nim`, `nama`, `kontak`, `username`, `password`) VALUES
(1, 120900, 'BANGKIT RIAWAN', NULL, 'admin', 'admin'),
(2, 1106120129, 'MUHAMMAD UMAR', '082346417078', 'umar', 'umar'),
(3, 1106120125, 'SYACHRIR HIDAYATULLAH', '083456781233', 'syachrir', 'syachrir'),
(4, 1106120127, 'ERFANDI RAMBANG', '081612345199', 'fandi', 'fandi'),
(5, 1202160002, 'AULIA NURI IKHSANATI', NULL, 'aulia', 'aulia'),
(6, 1202160009, 'RAFLY ZULFI ROBBIANSYAH', NULL, 'rafly', 'rafly'),
(7, 1202160011, 'OKSYA AFIFAH', NULL, 'oksya', 'oksya'),
(8, 1202160013, 'CAHYANTO AJI NUGROHO', NULL, 'cahyanto', 'cahyanto'),
(9, 1202160015, 'AWANDA PUTRI PERDANA', NULL, 'awanda', 'awanda'),
(10, 1202160019, 'BRIAN GAMALIEL', NULL, 'brian', 'brian'),
(11, 1202160023, 'RIANE MANUHO', NULL, 'riane', 'riane'),
(12, 1202160024, 'AGUNG PRABOWO', NULL, 'agung', 'agung'),
(13, 1202160028, 'RAYDITTO A MAKALALAG', NULL, 'rayditto', 'rayditto'),
(14, 1202160030, 'CLAUDIO BERNADUS', NULL, 'claudio', 'claudio'),
(15, 1202160034, 'HANIF RAMADHAN ABDILLAH', NULL, 'hanif', 'hanif'),
(16, 1202160041, 'RICHARD SALENDAH', NULL, 'richard', 'richard'),
(17, 1202160043, 'SUZANNA E MANABUNG', NULL, 'suzanna', 'suzanna'),
(18, 1202160044, 'M. ASRI FADLURRAHMAN', NULL, 'asri', 'asri'),
(19, 3434, '23424', '23324', 'boleh', 'boleh'),
(20, 1102134135, 'BUDI', NULL, 'budi', 'budi'),
(21, 12123, 'panitia', NULL, 'panitia1', 'panitia1');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id_admin`),
  ADD KEY `id_user` (`id_user`);

--
-- Indexes for table `keanggotaan`
--
ALTER TABLE `keanggotaan`
  ADD KEY `id_user` (`id_user`),
  ADD KEY `id_lab` (`id_lab`);

--
-- Indexes for table `kriteria`
--
ALTER TABLE `kriteria`
  ADD PRIMARY KEY (`id_kriteria`),
  ADD KEY `id_seleksi` (`id_seleksi`);

--
-- Indexes for table `laboratorium`
--
ALTER TABLE `laboratorium`
  ADD PRIMARY KEY (`id_lab`);

--
-- Indexes for table `panitia`
--
ALTER TABLE `panitia`
  ADD PRIMARY KEY (`id_panitia`),
  ADD KEY `id_seleksi` (`id_seleksi`),
  ADD KEY `id_user` (`id_user`) USING BTREE;

--
-- Indexes for table `pendaftar`
--
ALTER TABLE `pendaftar`
  ADD PRIMARY KEY (`id_pendaftar`),
  ADD KEY `id_seleksi` (`id_seleksi`),
  ADD KEY `id_user` (`id_user`) USING BTREE;

--
-- Indexes for table `rangking`
--
ALTER TABLE `rangking`
  ADD PRIMARY KEY (`id_rangking`),
  ADD KEY `id_pendaftar` (`id_pendaftar`),
  ADD KEY `id_kriteria` (`id_kriteria`);

--
-- Indexes for table `seleksi`
--
ALTER TABLE `seleksi`
  ADD PRIMARY KEY (`id_seleksi`),
  ADD KEY `id_lab` (`id_lab`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`) USING BTREE,
  ADD UNIQUE KEY `username` (`username`) USING BTREE;

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id_admin` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `kriteria`
--
ALTER TABLE `kriteria`
  MODIFY `id_kriteria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `laboratorium`
--
ALTER TABLE `laboratorium`
  MODIFY `id_lab` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `panitia`
--
ALTER TABLE `panitia`
  MODIFY `id_panitia` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `pendaftar`
--
ALTER TABLE `pendaftar`
  MODIFY `id_pendaftar` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `rangking`
--
ALTER TABLE `rangking`
  MODIFY `id_rangking` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=92;

--
-- AUTO_INCREMENT for table `seleksi`
--
ALTER TABLE `seleksi`
  MODIFY `id_seleksi` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `admin`
--
ALTER TABLE `admin`
  ADD CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`);

--
-- Ketidakleluasaan untuk tabel `keanggotaan`
--
ALTER TABLE `keanggotaan`
  ADD CONSTRAINT `keanggotaan_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`),
  ADD CONSTRAINT `keanggotaan_ibfk_2` FOREIGN KEY (`id_lab`) REFERENCES `laboratorium` (`id_lab`);

--
-- Ketidakleluasaan untuk tabel `kriteria`
--
ALTER TABLE `kriteria`
  ADD CONSTRAINT `kriteria_ibfk_1` FOREIGN KEY (`id_seleksi`) REFERENCES `seleksi` (`id_seleksi`);

--
-- Ketidakleluasaan untuk tabel `panitia`
--
ALTER TABLE `panitia`
  ADD CONSTRAINT `panitia_ibfk_1` FOREIGN KEY (`id_seleksi`) REFERENCES `seleksi` (`id_seleksi`),
  ADD CONSTRAINT `panitia_ibfk_2` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`);

--
-- Ketidakleluasaan untuk tabel `pendaftar`
--
ALTER TABLE `pendaftar`
  ADD CONSTRAINT `pendaftar_ibfk_1` FOREIGN KEY (`id_seleksi`) REFERENCES `seleksi` (`id_seleksi`),
  ADD CONSTRAINT `pendaftar_ibfk_2` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`);

--
-- Ketidakleluasaan untuk tabel `rangking`
--
ALTER TABLE `rangking`
  ADD CONSTRAINT `rangking_ibfk_1` FOREIGN KEY (`id_pendaftar`) REFERENCES `pendaftar` (`id_pendaftar`),
  ADD CONSTRAINT `rangking_ibfk_2` FOREIGN KEY (`id_kriteria`) REFERENCES `kriteria` (`id_kriteria`);

--
-- Ketidakleluasaan untuk tabel `seleksi`
--
ALTER TABLE `seleksi`
  ADD CONSTRAINT `seleksi_ibfk_1` FOREIGN KEY (`id_lab`) REFERENCES `laboratorium` (`id_lab`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

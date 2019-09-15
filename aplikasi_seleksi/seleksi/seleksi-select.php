<?php 
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
	include "../koneksi.php";
	
	$query = mysqli_query($con, "SELECT id_seleksi, nama_seleksi, nama_lab, tahun_ajaran, kuota, deskripsi from seleksi a, laboratorium b where a.id_lab=b.id_lab order by id_seleksi asc");
	
	$json = array();
	
	while($row = mysqli_fetch_assoc($query)){
		$json[] = $row;
	}
	
	echo json_encode($json);
	
	mysqli_close($con);
	
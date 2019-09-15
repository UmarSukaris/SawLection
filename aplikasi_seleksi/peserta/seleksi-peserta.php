<?php 
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
	include "../koneksi.php";
	$id_user = $_POST['id_user'];
	$query = mysqli_query($con, "SELECT b.id_seleksi, nama_seleksi, nama_lab, tahun_ajaran, kuota, deskripsi from pendaftar a, seleksi b, laboratorium c where a.id_seleksi = b.id_seleksi and a.id_pendaftar = (SELECT id_pendaftar FROM pendaftar a, user b where a.id_user = b.id_user AND a.id_user = '".$id_user."') and b.id_lab=c.id_lab order by a.id_seleksi asc");
	
	$json = array();
	
	while($row = mysqli_fetch_assoc($query)){
		$json[] = $row;
	}
	
	echo json_encode($json);
	
	mysqli_close($con);
	
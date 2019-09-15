<?php 
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
	include "../koneksi.php";
	class usr{}
	
	$id_user = $_POST['id_user'];
	
	$query = mysqli_query($con, "SELECT a.id_seleksi, nama_seleksi, nama_lab, tahun_ajaran, kuota, deskripsi from seleksi a, laboratorium b, pendaftar c where a.id_lab = b.id_lab and a.id_seleksi=c.id_seleksi and c.id_user = '".$id_user."'");
	$row = mysqli_fetch_array($query);
	
	if(empty($row)){
	$query2 = mysqli_query($con, "SELECT a.id_seleksi, nama_seleksi, nama_lab, tahun_ajaran, kuota, deskripsi from seleksi a, laboratorium b where a.id_lab = b.id_lab order by id_seleksi");
	$json = array();
	
	while($row2 = mysqli_fetch_assoc($query2)){
		$json[] = $row2;
	}
	echo json_encode($json);
	
	}else {
		$response = new usr();
		$response->message = "Maaf anda sudah mengikuti seleksi";
		die(json_encode($response));
	}
	
	
	
	mysqli_close($con);
	
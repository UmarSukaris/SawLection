<?php
	ini_set('display_errors', 1);
	ini_set('display_startup_errors', 1);
	error_reporting(E_ALL);

	include "../koneksi.php";
	
	$nama_seleksi = $_POST['nama_seleksi'];
	$id_laboratorium = $_POST['id_laboratorium'];
	$tahun_ajaran = $_POST['tahun_ajaran'];
	$kuota = $_POST['kuota'];
	$deskripsi = $_POST['deskripsi'];
	
	class emp{}
	
	if (empty($nama_seleksi) || empty($id_laboratorium) || empty($tahun_ajaran) || empty($kuota) || empty($deskripsi)) { 
		$response = new emp();
		$response->success = 0;
		$response->message = "Kolom isian tidak boleh kosong"; 
		die(json_encode($response));
	} else {
		$query = mysqli_query($con, "INSERT INTO seleksi (id_seleksi, nama_seleksi, id_lab, tahun_ajaran, kuota, deskripsi) VALUES(0,'".$nama_seleksi."','".$id_laboratorium."','".$tahun_ajaran."','".$kuota."','".$deskripsi."')");
		
		if ($query) {
			$response = new emp();
			$response->success = 1;
			$response->message = "Data berhasil di simpan";
			die(json_encode($response));
		} else{ 
			$response = new emp();
			$response->success = 0;
			$response->message = "Error simpan Data";
			die(json_encode($response)); 
		}	
	}
?>
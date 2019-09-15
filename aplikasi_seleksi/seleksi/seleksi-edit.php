<?php
	ini_set('display_errors', 1);
	ini_set('display_startup_errors', 1);
	error_reporting(E_ALL);
	include "../koneksi.php";
	
	$id_seleksi	= $_POST['id_seleksi'];
	
	class emp{}
	
	if (empty($id_seleksi)) { 
		$response = new emp();
		$response->success = 0;
		$response->message = "Error Mengambil Data Seleksi"; 
		die(json_encode($response));
	} else {
		$query 	= mysqli_query($con, "SELECT * FROM seleksi WHERE id_seleksi='".$id_seleksi."'");
		$row 	= mysqli_fetch_array($query);
		
		if (!empty($row)) {
			$response = new emp();
			$response->success = 1;
			$response->id_seleksi = $row["id_seleksi"];
			$response->nama_seleksi = $row["nama_seleksi"];
			$response->id_lab = $row["id_lab"];
			$response->tahun_ajaran = $row["tahun_ajaran"];
			$response->kuota = $row["kuota"];
			$response->deskripsi = $row["deskripsi"];
			die(json_encode($response));
		} else{ 
			$response = new emp();
			$response->success = 0;
			$response->message = "Error Mengambil Data";
			die(json_encode($response)); 
		}	
	}
?>
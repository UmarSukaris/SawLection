<?php
	ini_set('display_errors', 1);
	ini_set('display_startup_errors', 1);
	error_reporting(E_ALL);

	include "../koneksi.php";
	$id_seleksi = $_POST['id_seleksi'];
	$nama_kriteria = $_POST['nama_kriteria'];
	$tipe_kriteria = $_POST['tipe_kriteria'];
	$bobot_kriteria = $_POST['bobot_kriteria'];
	
	class emp{}
	
	if (empty($id_seleksi) || empty($nama_kriteria) || empty($tipe_kriteria) || empty($bobot_kriteria)) { 
		$response = new emp();
		$response->success = 0;
		$response->message = "Kolom isian tidak boleh kosong"; 
		die(json_encode($response));
	} else {
		$query = mysqli_query($con, "INSERT INTO kriteria (id_kriteria, id_seleksi, nama_kriteria, tipe_kriteria, bobot_kriteria) VALUES(0,'".$id_seleksi."','".$nama_kriteria."','".$tipe_kriteria."','".$bobot_kriteria."')");
		
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
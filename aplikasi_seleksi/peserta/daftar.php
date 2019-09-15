<?php
	ini_set('display_errors', 1);
	ini_set('display_startup_errors', 1);
	error_reporting(E_ALL);

	include "../koneksi.php";
	
	$id_seleksi = $_POST['id_seleksi'];
	$id_user = $_POST['id_user'];
	
	class emp{}
	
	if (empty($id_seleksi) || empty($id_user)) { 
		$response = new emp();
		$response->success = 0;
		$response->message = "Kolom isian tidak boleh kosong"; 
		die(json_encode($response));
	} else {
		$query = mysqli_query($con, "INSERT INTO pendaftar (id_pendaftar, id_seleksi, id_user) VALUES(0,'".$id_seleksi."','".$id_user."')");
		
		if ($query) {
			$response = new emp();
			$response->success = 1;
			$response->message = "Pendaftaran Berhasil, silahkan ikuti prosedur selanjutnya";
			die(json_encode($response));
		} else{ 
			$response = new emp();
			$response->success = 0;
			$response->message = "Pendaftaran Gagal!";
			die(json_encode($response)); 
		}	
	}
?>
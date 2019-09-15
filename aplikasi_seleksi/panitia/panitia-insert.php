<?php
	ini_set('display_errors', 1);
	ini_set('display_startup_errors', 1);
	error_reporting(E_ALL);

	include "../koneksi.php";
	
	$id_user = $_POST['id_user'];
	$id_seleksi = $_POST['id_seleksi'];
	
	class emp{}
	
	if (empty($id_user) || empty($id_seleksi)) { 
		$response = new emp();
		$response->success = 0;
		$response->message = "Kolom isian tidak boleh kosong"; 
		die(json_encode($response));
	} else {
		$query = mysqli_query($con, "INSERT INTO panitia (id_panitia, id_user, id_seleksi) VALUES(0,'".$id_user."','".$id_seleksi."')");
		
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
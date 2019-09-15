<?php
	include "../koneksi.php";
	
	$id_seleksi = $_POST['id_seleksi'];
	
	class emp{}
	
	if (empty($id_seleksi)) { 
		$response = new emp();
		$response->success = 0;
		$response->message = "Error hapus Seleksi";
		die(json_encode($response));
	} else {
		$query = mysqli_query($con, "DELETE FROM seleksi WHERE id_seleksi='".$id_seleksi."'");
		
		if ($query) {
			$response = new emp();
			$response->success = 1;
			$response->message = "Seleksi berhasil di hapus";
			die(json_encode($response));
		} else{ 
			$response = new emp();
			$response->success = 0;
			$response->message = "Error hapus Seleksi";
			die(json_encode($response)); 
		}	
	}
?>
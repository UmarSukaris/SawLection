<?php
	include "../koneksi.php";
	
	$id_panitia = $_POST['id_panitia'];
	
	class emp{}
	
	if (empty($id_panitia)) { 
		$response = new emp();
		$response->success = 0;
		$response->message = "Error hapus panitia";
		die(json_encode($response));
	} else {
		$query = mysqli_query($con, "DELETE FROM panitia WHERE id_panitia='".$id_panitia."'");
		
		if ($query) {
			$response = new emp();
			$response->success = 1;
			$response->message = "Panitia telah di hapus";
			die(json_encode($response));
		} else{ 
			$response = new emp();
			$response->success = 0;
			$response->message = "Error hapus panitia";
			die(json_encode($response)); 
		}	
	}
?>
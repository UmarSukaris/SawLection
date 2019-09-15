<?php
	include "../koneksi.php";
	
	$id_kriteria 	= $_POST['id_kriteria'];
	
	class emp{}
	
	if (empty($id_kriteria)) { 
		$response = new emp();
		$response->success = 0;
		$response->message = "Error hapus Data"; 
		die(json_encode($response));
	} else {
		$query = mysqli_query($con, "DELETE FROM kriteria WHERE id_kriteria='".$id_kriteria."'");
		
		if ($query) {
			$response = new emp();
			$response->success = 1;
			$response->message = "Data berhasil di hapus";
			die(json_encode($response));
		} else{ 
			$response = new emp();
			$response->success = 0;
			$response->message = "Error hapus Data";
			die(json_encode($response)); 
		}	
	}
?>
<?php
	ini_set('display_errors', 1);
	ini_set('display_startup_errors', 1);
	error_reporting(E_ALL);
	include "../koneksi.php";
	
	$id_kriteria 	= $_POST['id_kriteria'];
	
	class emp{}
	
	if (empty($id_kriteria)) { 
		$response = new emp();
		$response->success = 0;
		$response->message = "Error Mengambil Data"; 
		die(json_encode($response));
	} else {
		$query 	= mysqli_query($con, "SELECT * FROM kriteria WHERE id_kriteria='".$id_kriteria."'");
		$row 	= mysqli_fetch_array($query);
		
		if (!empty($row)) {
			$response = new emp();
			$response->success = 1;
			$response->id_kriteria = $row["id_kriteria"];
			$response->nama_kriteria = $row["nama_kriteria"];
			$response->tipe_kriteria = $row["tipe_kriteria"];
			$response->bobot_kriteria = $row["bobot_kriteria"];
			die(json_encode($response));
		} else{ 
			$response = new emp();
			$response->success = 0;
			$response->message = "Error Mengambil Data";
			die(json_encode($response)); 
		}	
	}
?>
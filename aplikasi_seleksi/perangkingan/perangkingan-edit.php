<?php
	ini_set('display_errors', 1);
	ini_set('display_startup_errors', 1);
	error_reporting(E_ALL);
	include "../koneksi.php";
	
	$id_rangking 	= $_POST['id_rangking'];
	
	class emp{}
	
	if (empty($id_rangking)) { 
		$response = new emp();
		$response->success = 0;
		$response->message = "Error Mengambil Data"; 
		die(json_encode($response));
	} else {
		$query 	= mysqli_query($con, "SELECT * FROM rangking WHERE id_rangking='".$id_rangking."'");
		$row 	= mysqli_fetch_array($query);
		
		if (!empty($row)) {
			$response = new emp();
			$response->success = 1;
			$response->id_rangking = $row["id_rangking"];
			$response->id_pendaftar = $row["id_pendaftar"];
			$response->id_kriteria = $row["id_kriteria"];
			$response->nilai = $row["nilai"];
			die(json_encode($response));
		} else{ 
			$response = new emp();
			$response->success = 0;
			$response->message = "Error Mengambil Data";
			die(json_encode($response)); 
		}	
	}
?>
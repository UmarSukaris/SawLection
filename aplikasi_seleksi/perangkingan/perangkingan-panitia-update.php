<?php
	include "../koneksi.php";
	
	$id_rangking = $_POST['id_rangking'];
	$id_pendaftar = $_POST['id_pendaftar'];
	$id_kriteria = $_POST['id_kriteria'];
	$nilai = $_POST['nilai'];
	
	class emp{}
	
	if (empty($id_rangking) || empty($id_pendaftar) || empty($id_kriteria) || empty($nilai)) { 
		$response = new emp();
		$response->success = 0;
		$response->message = "Kolom isian tidak boleh kosong"; 
		die(json_encode($response));
	} else {
		$query = mysqli_query($con, "UPDATE rangking SET id_kriteria='".$id_kriteria."', nilai='".$nilai."' WHERE id_rangking='".$id_rangking."' and id_pendaftar='".$id_pendaftar."'");
		
		if ($query) {
			$response = new emp();
			$response->success = 1;
			$response->message = "Data berhasil di update";
			die(json_encode($response));
		} else{ 
			$response = new emp();
			$response->success = 0;
			$response->message = "Error update Data";
			die(json_encode($response)); 
		}	
	}
?>
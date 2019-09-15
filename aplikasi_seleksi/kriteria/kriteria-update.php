<?php
	include "../koneksi.php";
	
	$id_seleksi = $_POST['id_seleksi'];
	$id_kriteria = $_POST['id_kriteria'];
	$nama_kriteria = $_POST['nama_kriteria'];
	$tipe_kriteria = $_POST['tipe_kriteria'];
	$bobot_kriteria = $_POST['bobot_kriteria'];
	
	class emp{}
	
	if (empty($id_kriteria) || empty($id_seleksi) || empty($nama_kriteria) || empty($tipe_kriteria) || empty($bobot_kriteria)) { 
		$response = new emp();
		$response->success = 0;
		$response->message = "Kolom isian tidak boleh kosong"; 
		die(json_encode($response));
	} else {
		$query = mysqli_query($con, "UPDATE kriteria SET nama_kriteria='".$nama_kriteria."', tipe_kriteria='".$tipe_kriteria."', bobot_kriteria='".$bobot_kriteria."' WHERE id_kriteria='".$id_kriteria."' and id_seleksi='".$id_seleksi."'");
		
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
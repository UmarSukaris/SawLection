<?php
	include "../koneksi.php";
	
	$nama_seleksi = $_POST['nama_seleksi'];
	$id_laboratorium = $_POST['id_laboratorium'];
	$tahun_ajaran = $_POST['tahun_ajaran'];
	$kuota = $_POST['kuota'];
	$deskripsi = $_POST['deskripsi'];
	
	class emp{}
	
	if (empty($nama_seleksi) || empty($id_laboratorium) || empty($tahun_ajaran) || empty($kuota) || empty($deskripsi)) { 
		$response = new emp();
		$response->success = 0;
		$response->message = "Kolom isian tidak boleh kosong"; 
		die(json_encode($response));
	} else {
		$query = mysqli_query($con, "UPDATE seleksi SET nama_seleksi='".$nama_seleksi."', id_lab='".$id_laboratorium."', tahun_ajaran='".$tahun_ajaran."', kuota='".$kuota."', deskripsi='".$kuota."' WHERE id_seleksi='".$id_seleksi."'");
		
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
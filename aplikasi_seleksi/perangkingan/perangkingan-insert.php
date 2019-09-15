<?php
	ini_set('display_errors', 1);
	ini_set('display_startup_errors', 1);
	error_reporting(E_ALL);

	include "../koneksi.php";
        
	$id_pendaftar = $_POST['id_pendaftar'];
	$id_kriteria = $_POST['id_kriteria'];
	$nilai = $_POST['nilai'];
	
	class emp{}
	
	if (empty($id_pendaftar) || empty($id_kriteria) || empty($nilai)) { 
		$response = new emp();
		$response->success = 0;
		$response->message = "Kolom isian tidak boleh kosong"; 
		die(json_encode($response));
	} else {
		$query = mysqli_query($con, "INSERT INTO rangking (id_rangking, id_pendaftar, id_kriteria, nilai) VALUES(0, '".$id_pendaftar."','".$id_kriteria."','".$nilai."')");
		
            if ($query) {
            $response = new emp();                                  
			$response->success = 1;
			$response->message = "Data berhasil di simpan";
			die(json_encode($response));
		}else{ 
			$response = new emp();
			$response->success = 0;
			$response->message = "Error simpan Data";
			die(json_encode($response)); 
		}
    }
	
?>
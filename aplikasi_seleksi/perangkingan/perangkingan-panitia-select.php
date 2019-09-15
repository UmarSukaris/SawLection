<?php 
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
	include "../koneksi.php";
	
	$id_seleksi = $_POST['id_seleksi'];
	$id_pendaftar = $_POST['id_pendaftar'];
	
	$query = mysqli_query($con, "SELECT id_rangking, nama_kriteria, nilai FROM pendaftar a, kriteria b, rangking c where a.id_pendaftar=c.id_pendaftar and b.id_kriteria=c.id_kriteria and a.id_seleksi=b.id_seleksi=1 and a.id_seleksi='".$id_seleksi."' and a.id_pendaftar='".$id_pendaftar."' order by a.nama_pendaftar asc");
	
	$json = array();
	
	while($row = mysqli_fetch_assoc($query)){
		$json[] = $row;
	}
	
	echo json_encode($json);
	
	mysqli_close($con);
	
?>
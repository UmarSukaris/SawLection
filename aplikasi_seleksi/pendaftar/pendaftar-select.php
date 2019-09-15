<?php 
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
	include "../koneksi.php";
	
	$id_seleksi = $_POST['id_seleksi'];
	
	$query = mysqli_query($con, "SELECT id_pendaftar, b.nim, b.nama FROM pendaftar a, user b where a.id_user=b.id_user and id_seleksi = '".$id_seleksi."' ORDER BY id_pendaftar ASC");
	
	$json = array();
	
	while($row = mysqli_fetch_assoc($query)){
		$json[] = $row;
	}
	
	echo json_encode($json);
	
	mysqli_close($con);
	
?>
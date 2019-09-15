<?php 
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
	include "koneksi.php";
	$id_seleksi = $_POST['id_seleksi'];
	
	$query = mysqli_query($con, "SELECT * FROM user a, seleksi b, keanggotaan c where a.id_user=c.id_user and b.id_lab=c.id_lab and b.id_seleksi='".$id_seleksi."' ORDER BY a.id_user ASC");
	
	$json = array();
	
	while($row = mysqli_fetch_assoc($query)){
		$json[] = $row;
	}
	
	echo json_encode($json);
	
	mysqli_close($con);
	
?>
<?php

	include_once "koneksi.php";

	class usr{}
	
	$username = $_POST["username"];
	$password = $_POST["password"];
	
	if ((empty($username)) || (empty($password))) { 
		$response = new usr();
		$response->success = 0;
		$response->message = "Kolom tidak boleh kosong"; 
		die(json_encode($response));
	}
	
	$query = mysqli_query($con, "SELECT * FROM user WHERE username='".$username."' AND password='".$password."'");
	
	$row = mysqli_fetch_array($query);
	
	if (!empty($row)){
		$response = new usr();
		$query2 = mysqli_query($con, "SELECT * From user a, admin b where a.id_user=b.id_user and a.username='".$username."'");
		$row2 = mysqli_fetch_array($query2);
		
		$query3 = mysqli_query($con, "SELECT * From user a, panitia b where a.id_user=b.id_user and a.username='".$username."'");
		$row3 = mysqli_fetch_array($query3);
		
		$query4 = mysqli_query($con, "SELECT * From user where id_user NOT IN (SELECT id_user FROM panitia) and id_user NOT IN (SELECT id_user FROM admin) and username = '".$username."'");
		$row4 = mysqli_fetch_array($query4);
		
		if(!empty($row2)){
			$response->role = 0;
		}else if(!empty($row3)){	
			$response->role = 1;
		}else if(!empty($row4)) {
			$response->role = 2;
		}
		
		$response->success = 1;
		$response->message = "Selamat datang ".$row['username'];
		$response->id_user = $row['id_user'];
		$response->nim = $row['nim'];
		$response->nama = $row['nama'];
		die(json_encode($response));
		
	} else { 
		$response = new usr();
		$response->success = 0;
		$response->message = "Username atau password salah";
		die(json_encode($response));
	}
	
	mysqli_close($con);

?>
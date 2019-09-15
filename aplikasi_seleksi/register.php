<?php
	include_once "koneksi.php";

	class usr{}
	
	$nim = $_POST["nim"];
	$nama = $_POST["nama"];
	$username = $_POST["username"];
	$password = $_POST["password"];
	$confirm_password = $_POST["confirm_password"];
	
	if ((empty($nim))) { 
		$response = new usr();
		$response->success = 0;
		$response->message = "Kolom nim tidak boleh kosong"; 
		die(json_encode($response));
	} else if ((empty($nama))) { 
		$response = new usr();
		$response->success = 0;
		$response->message = "Kolom nama tidak boleh kosong"; 
		die(json_encode($response));
	}else if ((empty($username))) { 
		$response = new usr();
		$response->success = 0;
		$response->message = "Kolom username tidak boleh kosong"; 
		die(json_encode($response));
	}else if ((empty($password))) { 
		$response = new usr();
		$response->success = 0;
		$response->message = "Kolom password tidak boleh kosong"; 
		die(json_encode($response));
	} else if ((empty($confirm_password)) || $password != $confirm_password) { 
		$response = new usr();
		$response->success = 0;
		$response->message = "Konfirmasi password tidak sama"; 
		die(json_encode($response));
	} else {
		if (!empty($username) && $password == $confirm_password){
			$query = mysqli_query($con, "INSERT INTO user (id_user, nim, nama, username, password) VALUES(0, '".$nim."','".$nama."','".$username."','".$password."')");
			
			if ($query){
				$response = new usr();
				$response->success = 1;
				$response->message = "Register berhasil, silahkan login.";
				die(json_encode($response));
				
			} else { 
				$response = new usr();
				$response->success = 0;
				$response->message = "Username sudah ada";
				die(json_encode($response));
			}
		}	
	}

	mysqli_close($con);

?>	
<?php 
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
	include "koneksi.php";
	$id_seleksi = $_POST['id_seleksi'];
    class emp{}
	$response = new emp();
	$query = mysqli_query($con, "SELECT * FROM pendaftar a, kriteria b, rangking c where a.id_pendaftar=c.id_pendaftar and b.id_kriteria=c.id_kriteria and a.id_seleksi=b.id_seleksi and a.id_seleksi='".$id_seleksi."'");
                while($row = mysqli_fetch_assoc($query)){
                $tipe_kriteria = $row['tipe_kriteria'];
                $bobot_kriteria = $row['bobot_kriteria'];
                $id_rangking = $row['id_rangking'];
                $id_pendaftar = $row['id_pendaftar'];
				$id_kriteria = $row['id_kriteria'];
                
                if($tipe_kriteria=='Benefit'){
                        $querymax = mysqli_query($con, "SELECT max(nilai) as mnr1 FROM pendaftar a, kriteria b, rangking c where a.id_pendaftar=c.id_pendaftar and b.id_kriteria=c.id_kriteria and c.id_kriteria='".$id_kriteria."' LIMIT 0,1");    
		        
                        while ($row2 = mysqli_fetch_assoc($querymax)){
                            $nilai_normalisasi = $row['nilai']/$row2['mnr1'];
                        }
                        			
				} else{
                        $querymin = mysqli_query($con, "SELECT min(nilai) as mnr2 FROM pendaftar a, kriteria b, rangking c where a.id_pendaftar=c.id_pendaftar and b.id_kriteria=c.id_kriteria and c.id_kriteria='".$id_kriteria."' LIMIT 0,1");    
		        
						while ($row3 = mysqli_fetch_assoc($querymin)){
                            $nilai_normalisasi = $row3['mnr2']/$row['nilai'];
                        }
				}
                        $bobot_normalisasi = $bobot_kriteria*$nilai_normalisasi;
                        $insertnormalisasi = mysqli_query($con, "UPDATE rangking 
																		SET 
																			nilai_normalisasi = ROUND('".$nilai_normalisasi."', 3),
																			bobot_normalisasi = ROUND('".$bobot_normalisasi."', 3)
																		WHERE
																			id_rangking = '".$id_rangking."'");
																				
                $queryhasil = mysqli_query($con, "SELECT ROUND(SUM(bobot_normalisasi),3) AS bbn FROM rangking WHERE id_pendaftar='".$id_pendaftar."' LIMIT 0,1");
			while ($row4 = mysqli_fetch_assoc($queryhasil)){
                            $hasil=$row4['bbn'];
                            
                            $inserthasil = mysqli_query($con, "UPDATE 
					pendaftar
				SET 
					hasil = '".$hasil."'
				WHERE
					id_pendaftar = '".$id_pendaftar."'");
                            
                        }
                
	}
        if($query){
            $query2 = mysqli_query($con, "SELECT id_pendaftar, nama, hasil from pendaftar a, user b where a.id_user=b.id_user and id_seleksi='".$id_seleksi."' order by hasil desc");
            $json = array();
            while($row5 = mysqli_fetch_assoc($query2)){
			$json[] = $row5;
	}
            echo json_encode($json);
			$response->success = 1;
		}else{ 
			
			$response->success = 0;
			$response->message = "Error";
			die(json_encode($response)); 
		}
        
	mysqli_close($con);
	
?>
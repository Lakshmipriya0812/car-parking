<?php require_once "db_connection.php";?>
<?php

// Create connection

// json response array
$response = array("error" => false);

if (isset($_GET['user']) && isset($_GET['pid']) && isset($_GET['slot'])) {
global $connection;
    // receiving the post params
    $user = $_GET['user'];
    $pid = $_GET['pid'];
    $slot = $_GET['slot'];
	
		if($slot == "nul"){
		$slotsql="SELECT * from `booking` where `user`='{$user}'";
		$result = mysqli_query($connection, $slotsql);
		
		$mrow= mysqli_fetch_assoc($result);
		$cslot= $mrow["slot"];
		$cpid= $mrow["pid"];
		$sql = "UPDATE `slot` SET `{$cslot}` = '0' WHERE `pid` = '{$cpid}'";
	
	$result = mysqli_query($connection, $sql);
		
	}	
	else{
		$sql = "UPDATE `slot` SET `{$slot}` = '1' WHERE `pid` = '{$pid}'";
	
	$result = mysqli_query($connection, $sql);
	}
	
	$sql = "UPDATE `booking` SET `pid` = '{$pid}', `slot` = '{$slot}' WHERE `user` = '{$user}'";
	
	$result = mysqli_query($connection, $sql);
	


	$sql = "SELECT * FROM booking where user= '{$user}'";
	
	$result = mysqli_query($connection, $sql);

    $data = array();
    while ($row = mysqli_fetch_assoc($result)) {
        $data["data"][] = $row;

    }
	header('Content-Type:Application/json');

    echo json_encode($data);

}
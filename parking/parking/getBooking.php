<?php require_once "db_connection.php";?>
<?php

// Create connection

// json response array
$response = array("error" => false);

if (isset($_GET['user'])) {
global $connection;
    // receiving the post params
    $usermail = $_GET['user'];
	$sql = "SELECT * FROM booking where user= '{$usermail}'";
	
	$result = mysqli_query($connection, $sql);

    $data = array();
    while ($row = mysqli_fetch_assoc($result)) {
        $data["data"][] = $row;

    }
	header('Content-Type:Application/json');

    echo json_encode($data);
	
}

// echo $json;


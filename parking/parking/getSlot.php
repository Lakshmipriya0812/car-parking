<?php require_once "db_connection.php";?>
<?php
global $connection;
// $con = mysqli_connect("localhost", "root", "root", "social");

if (isset($_GET['pid'])) {

    // receiving the post params
    $pid = $_GET['pid'];

    $sql = "SELECT * FROM slot where pid='{$pid}'";
    $result = mysqli_query($connection, $sql);

    $data = array();
    while ($row = mysqli_fetch_assoc($result)) {
        $data["data"][] = $row;

    }

    header('Content-Type:Application/json');

    echo json_encode($data);

}

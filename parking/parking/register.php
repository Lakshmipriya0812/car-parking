<?php
require_once 'db_functions.php';

// $con=mysqli_connect("localhost","user","user","blood_bank");

// if (mysqli_connect_errno($con)) {
// echo "Failed to connect to MySQL: " . mysqli_connect_error();
// }
// else{
// echo "server connected";
// }

// $username = $_GET['username'];
// $password = $_GET['password'];
// $result = mysqli_query($con,"SELECT * FROM users");
// $row = mysqli_fetch_array($result);
// $data = $row[3];
// echo $data;

// if($data){
// echo $data;
// }
// else{
// echo "no data";
// }
// mysqli_close($con);

// json response array
$response = array("error" => false);

if (isset($_GET['username']) && isset($_GET['email']) && isset($_GET['password'])) {

    // receiving the post params
    $username = $_GET['username'];
    $email = $_GET['email'];
    $password = $_GET["password"];

    // check if user already exists with the same email
    if (emailExists($email)) {
        // email already exists
        $response["error"] = true;
        $response["error_msg"] = "Email already exists with " . $email;
        echo json_encode($response);
    } else {
        // create a new user
        $user = storeUser($username, $email, $password);
        if ($user) {
            // user stored successfully
            $response["error"] = false;
            $response["user"]["email"] = $user["email"];
            $response["user"]["name"] = $user["name"];
            $response["user"]["email"] = $user["email"];
            echo json_encode($response);
        } else {
            // user failed to store
            $response["error"] = true;
            $response["error_msg"] = "Unknown error occurred!";
            echo json_encode($response);
        }
    }
} else {
    $response["error"] = true;
    $response["error_msg"] = "Required parameters missing!";
    echo json_encode($response);
}

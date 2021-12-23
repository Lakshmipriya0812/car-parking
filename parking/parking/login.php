<?php
require_once 'db_functions.php';
 
// json response array
$response = array("error" => FALSE);
 
if (isset($_GET['email']) && isset($_GET['password'])) {
 
    // receiving the post params
    $email = $_GET['email'];
    $password = $_GET['password'];
 
    // get the user by email and password
    $user = getUserByEmailAndPassword($email, $password);
 
    if ($user != false) {
        // user is found
        $response["error"] = FALSE;
        $response["user"]["email"] = $user["email"];
        // $response["user"]["username"] = $user["username"];
		echo "1";
        //echo json_encode($response);
    } else {
        // user is not found with the credentials
        $response["error"] = TRUE;
        $response["error_msg"] = "Wrong email or password entered! Please try again!";
        echo json_encode($response);
    }
} else {
    //required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters missing :(!";
    echo json_encode($response);
}
?>
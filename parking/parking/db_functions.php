<?php require_once "db_connection.php";?>
<?php

function storeUser($username, $email, $password)
{
    global $connection;

    $query = "INSERT INTO users(";
    $query .= "name, email, password) ";
    $query .= "VALUES('{$username}', '{$email}','{$password}')";

    $result = mysqli_query($connection, $query);
	

    if ($result) {
        $user = "SELECT * FROM users WHERE email = '{$email}'";
        $res = mysqli_query($connection, $user);
		
		$query1 = "INSERT INTO booking(";
    $query1 .= "user, slot, pid) ";
    $query1 .= "VALUES('{$email}', 'nul','nul')";

    $result1 = mysqli_query($connection, $query1);

        while ($user = mysqli_fetch_assoc($res)) {
            return $user;
        }
    } else {
        return false;
    }

}

function getUserByEmailAndPassword($email, $password)
{
    global $connection;
    $query = "SELECT * from users where email = '{$email}' and password = '{$password}'";

    $user = mysqli_query($connection, $query);

    if ($user) {
        while ($res = mysqli_fetch_assoc($user)) {
            return $res;
        }
    } else {
        return false;
    }
}

function emailExists($email)
{
    global $connection;
    $query = "SELECT email from users where email = '{$email}'";

    $result = mysqli_query($connection, $query);

    if (mysqli_num_rows($result) > 0) {
        return true;
    } else {
        return false;
    }
}

// function updateUser($email,$bldgrp,$loc,$mobile){
//     global $connection;
//     $query = "UPDATE `users` SET `bloodGrp` = '{$bldgrp}', `location` = '{$loc}', `mobile` = '{$mobile}' WHERE `email` = '{$email}'";

//     $result = mysqli_query($connection, $query);
//     return true;
//     // if(mysqli_num_rows($result) > 0){
//         // return true;
//     // }else{
//         // return false;
//     // }
// }

function addnewpost($name, $post, $stat,$dt)
{
    global $connection;

    $query = "INSERT INTO post(";
    $query .= "user, post,stat,ptime) ";
    $query .= "VALUES('{$name}', '{$post}', '{$stat}', '{$dt}')";

    $result = mysqli_query($connection, $query);

    if ($result) {
        return "Post updated";
    } else {
        return false;
    }

}

function addnewcmt($name, $cmt, $stat, $postid)
{
    global $connection;
    $time1 = date('F d, Y, g:i a', time());

    $query = "INSERT INTO comments(";
    $query .= "name, comment,postid,time,stat) ";
    $query .= "VALUES('{$name}', '{$cmt}', '{$postid}', '{$time1}', '{$stat}')";

    $result = mysqli_query($connection, $query);

    if ($result) {
        return "Post updated";
    } else {
        return false;
    }

}


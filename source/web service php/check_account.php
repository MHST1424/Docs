<?php

/*
 * Following code will list all the products
 */

// array for JSON response
$response = array();


// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();
if(isset($_POST['user_name']) && isset($_POST['pass']))
{
	$user = $_POST['user_name'];
	$pass = $_POST['pass'];
	// get accont with user_name and password from database
	$result = mysql_query("SELECT *FROM Accounts where user_name = '$user' and password = '$pass'") or die(mysql_error());

	// check for empty result
	if (mysql_num_rows($result) == 1) {
		$response["accounts"] = array();
		
		while ($row = mysql_fetch_array($result)) {
			// temp user array
			$account = array();	
			$account["uid"] = $row['uid'];
			$account["user_name"] = $row['user_name'];
			$account["pass"] = $row['password'];
			$account["avata"] = $row['avata'];
			$account["name"] = $row['name'];
			$account["gender"] = $row['gender'];
			$account["birthday"] = $row['birthday'];
			$account["email"] = $row['email'];
			$account["cmnd"] = $row['cmnd'];
			$account["phone_number"] = $row['phone_number'];
			$account["number_cmnd"] = $row['number_cmnd'];
			$account["validate"] = $row['validate'];
			$account["visiable"] = $row['visiable'];
			
			// push single product into final response array
			array_push($response["accounts"], $account);
			}
		// success
		$response["success"] = 1;
		// echoing JSON response
		echo json_encode($response);
	} else {
		// no products found
		$response["success"] = 0;
		$response["message"] = "Không tìm thấy account!";
		// echo no users JSON
		echo json_encode($response);
	}
}
?>

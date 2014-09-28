<?php
	header("Content-type: text/html; charset=utf-8");
/*
 * Following code will list all the products
 */

// array for JSON response
$response = array();


// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();
if(isset($_POST['uid']))
{
	$uid = $_POST['uid'];
	// get all products from TripsInfo table have lc_start and lc_finish
	$result = mysql_query("SELECT *FROM Accounts WHERE uid = '$uid'") or die(mysql_error());
	
	// check for empty result
	if (mysql_num_rows($result) == 1) {
		// looping through all results
		// products node
		$response["accounts"] = array();
		
		while ($row = mysql_fetch_array($result)) {
			// temp user array
			$account = array();	
			$account["uid"] = $row['uid'];
			$account["user_name"] = $row['user_name'];
			$account["avata"] = $row['avata'];
			$account["name"] = $row['name'];
			$account["gender"] = $row['gender'];
			$account["birthday"] = $row['birthday'];
			
			// push single product into final response array
			array_push($response["accounts"], $account);
		}
		// success
		//$response["success"] = 1;

		// echoing JSON response
		echo json_encode($response);
	} else {
		// no products found
		$response["success"] = 0;
		$response["message"] = "No products found";

		// echo no users JSON
		echo json_encode($response);
	}
	}
?>

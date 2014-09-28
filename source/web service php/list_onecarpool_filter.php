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
if(isset($_POST['pid']))
{
	$pid = $_POST['pid'];
	// get all products from TripsInfo table have lc_start and lc_finish
	$result = mysql_query("SELECT pid, a.uid, lat_long, lc_start, lc_finish, time_start, time_back, owner, type_car, num_seat, cost, notes, name FROM TripsInfo AS a JOIN Accounts AS b ON a.uid = b.uid WHERE pid='$pid'") or die(mysql_error());
	
	// check for empty result
	if (mysql_num_rows($result) > 0) {
		// looping through all results
		// products node
		$response["tripsinfos"] = array();
		
		while ($row = mysql_fetch_array($result)) {
			// temp user array
			$tripsinfo = array();	
			$tripsinfo["pid"] = $row['pid'];
			$tripsinfo["uid"] = $row['uid'];
			$tripsinfo["name"] = $row['name'];
			$tripsinfo["lc_start"] = $row['lc_start'];
			$tripsinfo["lc_finish"] = $row['lc_finish'];
			$tripsinfo["time_start"] = $row['time_start'];
			$tripsinfo["time_back"] = $row['time_back'];
			$tripsinfo["owner"] = $row['owner'];
			$tripsinfo["type_car"] = $row['type_car'];
			$tripsinfo["num_seat"] = $row['num_seat'];
			$tripsinfo["cost"] = $row['cost'];
			


			// push single product into final response array
			array_push($response["tripsinfos"], $tripsinfo);
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

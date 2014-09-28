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
// get all products from friend table
$result = mysql_query("SELECT *FROM Accounts WHERE visiable='0'") or die(mysql_error());

// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["friends"] = array();
    
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $friend = array();		
		$friend["user_name"] = $row['user_name'];
		$friend["uid"] = $row['uid'];
		$friend["avata"] = $row['avata'];
		
        // push single product into final response array
        array_push($response["friends"], $friend);
    }
    // success
    //$response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No friend found";

    // echo no users JSON
    echo json_encode($response);
}
?>

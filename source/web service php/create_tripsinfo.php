<?php
	header("Content-type: text/html; charset=utf-8");
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['uid']) && isset($_POST['lat_long']) && isset($_POST['lc_start']) && isset($_POST['lc_finish']) && isset($_POST['time_start']) && isset($_POST['owner']) && isset($_POST['num_seat']) && isset($_POST['cost'])) {
    
	$uid = $_POST['uid'];
	$lat_long = $_POST['lat_long'];
    $lc_start = $_POST['lc_start'];
    $lc_finish = $_POST['lc_finish'];
    $time_start = $_POST['time_start'];
	$time_back = $_POST['time_back'];
	$owner = $_POST['owner'];
	$type_car = $_POST['type_car'];
	$num_seat = $_POST['num_seat'];
	$cost = $_POST['cost'];
	$note = $_POST['note'];

    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql inserting a new row
    $result = mysql_query("INSERT INTO TripsInfo(uid, lat_long, lc_start, lc_finish, time_start, time_back, owner, type_car, num_seat, cost, notes) VALUES('$uid', '$lat_long', N'$lc_start', N'$lc_finish', '$time_start', '$time_back', '$owner', '$type_car', '$num_seat', '$cost', '$note')");

    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Thông tin chuyến đi đã được tạo";

        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Lỗi !";
        
        // echoing JSON response
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>
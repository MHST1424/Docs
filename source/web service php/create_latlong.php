<?php
	header("Content-type: text/html; charset=utf-8");
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['lat_long']) || isset($_POST['lat_start']) && isset($_POST['long_start']) && isset($_POST['lat_end']) && isset($_POST['long_end'])) {

	$lat_long = $_POST['lat_long'];
    $lat_start = $_POST['lat_start'];
	$long_start = $_POST['long_start'];
    $lat_end = $_POST['lat_end'];
	$long_end = $_POST['long_end'];
    

    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql inserting a new row
    $result = mysql_query("INSERT INTO LatLong(lat_long, lat_start, long_start, lat_end, long_end) VALUES('$lat_long', '$lat_start', '$long_start', '$lat_end', '$long_end')");

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
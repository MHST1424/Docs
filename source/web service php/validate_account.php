<?php

/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */

// array for JSON response
$response = array();
//INSERT INTO accounts(user_name, password, name, birthday, gender, email) VALUES("alsfk", "jhfdaf", "kjlasfdl", true, 1992-04-03, "aljksd")  && isset($_POST['gender']) && isset($_POST['birthday'])
// check for required fields
if (isset($_POST['uid']) && isset($_POST['number_cmnd']) && isset($_POST['cmnd'])) {

	$uid = $_POST['uid'];
	$number_cmnd = $_POST['number_cmnd'];
    $cmnd = $_POST['cmnd'];

    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql inserting a new row
    $result = mysql_query("UPDATE Accounts SET number_cmnd='$number_cmnd', cmnd='$cmnd' WHERE uid='$uid'");

    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Xác thực thành công!";

        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Lỗi xác thực tài khoản!";
        
        // echoing JSON response
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Lỗi thiếu các giá trị mặc định!";

    // echoing JSON response
    echo json_encode($response);
}
?>
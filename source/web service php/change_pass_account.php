<?php

/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */

// array for JSON response
$response = array();
//INSERT INTO accounts(user_name, password, name, birthday, gender, email) VALUES("alsfk", "jhfdaf", "kjlasfdl", true, 1992-04-03, "aljksd")  && isset($_POST['gender']) && isset($_POST['birthday'])
// check for required fields
if (isset($_POST['uid']) && isset($_POST['passold']) && isset($_POST['passnew'])) {

	$uid = $_POST['uid'];
	$passold = $_POST['passold'];
    $passnew = $_POST['passnew'];

    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql inserting a new row
    $result = mysql_query("UPDATE Accounts SET password = '$passnew' WHERE uid = '$uid' AND password = '$passold'");

    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Tài khoản đã được thay đổi pass!";

        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Lỗi tài khoản or mật khẩu không chính xác!";
        
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
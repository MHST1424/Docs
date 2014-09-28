<?php
	header("Content-type: text/html; charset=utf-8");
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */

// array for JSON response
$response = array();
//INSERT INTO accounts(user_name, password, name, birthday, gender, email) VALUES("alsfk", "jhfdaf", "kjlasfdl", true, 1992-04-03, "aljksd")  && isset($_POST['gender']) && isset($_POST['birthday'])
// check for required fields
if (isset($_POST['user_name']) && isset($_POST['pass']) && isset($_POST['name'])) {

	$user_name = $_POST['user_name'];
	$pass = $_POST['pass'];
    $name = $_POST['name'];
    $gender = $_POST['gender'];
    $birthday = $_POST['birthday'];
	$email = $_POST['email'];
	$phone_number = $_POST['phone_number'];

    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql inserting a new row
    $result = mysql_query("INSERT INTO Accounts(user_name, password, name, birthday, gender, email, phone_number) VALUES('$user_name', '$pass', N'$name', '$birthday', '$gender', '$email', '$phone_number')");

    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Tài khoản đã được tạo!";

        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Lỗi tài khoản không được tạo!";
        
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
<?php
$link = mysql_connect("sql301.byethost7.com", "b7_15085510", "viettung0803");
if (!$link) {
    die('Could not connect: ' . mysql_error());
}
echo 'Connected successfully';
mysql_close($link);
?>
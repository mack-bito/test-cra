<?php

$conn = mysqli_connect("localhost", "root", "password", "test_db");
echo "Connected successfully!<br>";

$user_id = $_GET['id'];
$query = "SELECT * FROM users WHERE id = $user_id"; 
$result = mysqli_query($conn, $query);

$row = mysqli_fetch_assoc($result);

echo "Hello, " . $row['username'] . "!<br>";

<?php
// Buggy PHP code example

// Connect to MySQL database
$conn = mysqli_connect("localhost", "root", "password", "test_db");

// Missing error handling for failed connection
echo "Connected successfully!<br>";

// SQL injection risk (unsafe user input directly in query)
$user_id = $_GET['id'];
$query = "SELECT * FROM users WHERE id = $user_id"; 

$result = mysqli_query($conn, $query);

// Forgetting to check if query execution was successful
$row = mysqli_fetch_assoc($result);

// Using undefined index (possible PHP notice)
echo "Hello, " . $row['username'] . "!<br>";

// Forgetting to close the DB connection

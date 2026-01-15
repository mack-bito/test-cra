<?php
declare(strict_types=1);
session_start();
require __DIR__ . "/db.php";

if ($_SERVER["REQUEST_METHOD"] !== "POST") {
  http_response_code(405);
  echo "Use POST";
  exit;
}

$email = strtolower(trim($_POST["email"] ?? ""));
$password = $_POST["password"] ?? "";

if (!filter_var($email, FILTER_VALIDATE_EMAIL) || strlen($password) < 8) {
  http_response_code(400);
  echo "Invalid email or password too short (min 8)";
  exit;
}

$hash = password_hash($password, PASSWORD_DEFAULT);

$stmt = $pdo->prepare("INSERT INTO users (email, password_hash) VALUES (:email, :hash)");
try {
  $stmt->execute([":email" => $email, ":hash" => $hash]);
  echo "Registered";
} catch (PDOException $e) {
  // If email unique constraint fails
  if ((int)($e->errorInfo[1] ?? 0) === 1062) {
    http_response_code(409);
    echo "Email already exists";
  } else {
    http_response_code(500);
    echo "Server error";
  }
}


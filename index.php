<?php
declare(strict_types=1);
require __DIR__ . "/db.php";

header("Content-Type: application/json");

$method = $_SERVER["REQUEST_METHOD"];
$path = parse_url($_SERVER["REQUEST_URI"], PHP_URL_PATH);
$parts = array_values(array_filter(explode("/", $path)));

function respond($data, int $status = 200) {
    http_response_code($status);
    echo json_encode($data);
    exit;
}

function error($msg, int $status = 400) {
    respond(["error" => $msg], $status);
}

function body() {
    return json_decode(file_get_contents("php://input"), true);
}

if ($parts[0] !== "notes") {
    error("Not Found", 404);
}

/* GET /notes */
if ($method === "GET" && count($parts) === 1) {
    $stmt = $db->query("SELECT * FROM notes ORDER BY id DESC");
    respond($stmt->fetchAll(PDO::FETCH_ASSOC));
}

/* GET /notes/{id} */
if ($method === "GET" && count($parts) === 2) {
    $id = intval($parts[1]);

    $stmt = $db->prepare("SELECT * FROM notes WHERE id = ?");
    $stmt->execute([$id]);
    $note = $stmt->fetch(PDO::FETCH_ASSOC);

    if (!$note) error("Note not found", 404);
    respond($note);
}

/* POST /notes */
if ($method === "POST" && count($parts) === 1) {
    $data = body();

    if (!isset($data["title"]) || !isset($data["body"])) {
        error("title and body required");
    }

    $stmt = $db->prepare("INSERT INTO notes(title, body) VALUES (?,?)");
    $stmt->execute([$data["title"], $data["body"]]);

    respond(["id" => $db->lastInsertId()], 201);
}

/* DELETE /notes/{id} */
if ($method === "DELETE" && count($parts) === 2) {
    $id = intval($parts[1]);

    $stmt = $db->prepare("DELETE FROM notes WHERE id = ?");
    $stmt->execute([$id]);

    if ($stmt->rowCount() === 0) error("Not found", 404);
    respond(["deleted" => true]);
}

error("Invalid route", 404);


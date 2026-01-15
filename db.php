<?php
declare(strict_types=1);

$db = new PDO("sqlite:" . __DIR__ . "/notes.db");
$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);


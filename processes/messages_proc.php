<?php
require_once "../includes/DB_Connect.php";

if(isset($_POST["send_message"])){
    $sender_email = addslashes($_POST["sender_email"]);
    $receiver_email = addslashes($_POST["receiver_email"]);
    $subject = addslashes($_POST["subject"]);
    $message = addslashes($_POST["message"]);

    if(!filter_var($sender_email, FILTER_VALIDATE_EMAIL)){
        die("Invalid sender_email");
    }

    if(!filter_var($receiver_email, FILTER_VALIDATE_EMAIL)){
        die("Invalid receiver_email");
    }

    $stmt = $pdo->prepare("INSERT INTO messages (sender_email, receiver_email, subject, message) VALUES (?, ?, ?, ?)");

    $stmt->execute([$sender_email, $receiver_email, $subject, $message]);

    header("Location: ../view_messages.php");
    exit();
}
?>
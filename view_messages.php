<?php
require_once "includes/DB_Connect.php";
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home - DBT</title>
    <link rel="stylesheet" href="css/style.css" />
</head>
<body>
    <!-- top navigation starts here -->
    <?php require "includes/navigation.php"; ?>
    <!-- top navigation ends here -->
<div class="header">
    <h1>Header</h1>
</div>
<!-- the main content section starts here -->
<div class="row">
    <div class="content">
<h3>Messages List</h3>

<?php
$stmt = $pdo->query("SELECT * FROM messages");
$messages = $stmt->fetchAll(PDO::FETCH_ASSOC);
?>

<table class="table table-striped table-hover">
      <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">Sender</th>
      <th scope="col">Receiver</th>
      <th scope="col">subject</th>
      <th scope="col">Message</th>
      <th scope="col">Date Sent</th>
    </tr>
  </thead>
  <tbody>

  <?php 
  if($messages){
    $sn = 1;
  foreach($messages  AS $message){
  ?>

    <tr>
      <th scope="row"><?php print $sn; $sn++; ?></th>
      <td><?php print $message["sender_email"]; ?></td>
      <td><?php print $message["receiver_email"]; ?></td>
      <td><?php print $message["subject"]; ?></td>
      <td><?php 
       $shown_string = implode(' ', array_slice(str_word_count(addslashes($message["message"]), 2), 0, 10)) . ' ... ' ; //converting the full text into an array storing all words, then slicing the array at the maximum number of words determined by $max_words      
       print $shown_string ; ?></td>
      <td><?php print date("Y-M-d", strtotime($message["date_sent"])); ?></td>
    </tr>

    <?php }} ?>
  </tbody>

  </table>

  </div>
    <div class="sidebar">
<h3>Side Bar</h3>
<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
    </div>
</div>
<!-- the main content section ends here -->
<div class="footer">
copyright &copy; DBIT 2023
</div>
</body>
</html>
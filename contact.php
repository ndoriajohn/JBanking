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
    <h1>Talk to Us</h1>
</div>
<!-- the main content section starts here -->
<div class="row">
    <div class="content">
<h3>Main content</h3>

<form action="" method="POST">
    <label for="sender_email">Sender Email:</label><br>
    <input type="text" name="sender_email" id="sender_email" placeholder="Enter the sender Email" maxlength="60" required /><br><br>

    <label for="receiver_email">Receiver Email:</label><br>
    <input type="email" name="receiver_email" id="receiver_email" placeholder="Enter the receiver Email" maxlength="60" /><br><br>

    <label for="subject">Subject:</label><br>
    <input type="email" name="subject" id="subject" placeholder="Enter the subject" maxlength="160" required /><br><br>

    <label for="message">Message:</label><br>
    <textarea name="message" id="message" placeholder="Enter the message" rows="10" required></textarea><br><br>

    <input type="submit" name="send_message" value="Send Message" />

</form>



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
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
<h3>Register Here</h3>

<form action="" method="POST">
    <label for="fullname">Full Name:</label><br>
    <input type="text" name="fullname" id="fullname" placeholder="Enter your name" maxlength="60" required /><br><br>
    <label for="email_address">Email Address:</label><br>
    <input type="email" name="email_address" id="email_address" placeholder="Enter your email address" maxlength="60" /><br><br>
    <label for="phone_number">Phone Number:</label><br>
    <input type="text" name="phone_number" id="phone_number" placeholder="Enter your phone number" maxlength="13" /><br><br>
    <label for="username">Userame:</label><br>
    <input type="text" name="username" id="username" placeholder="Enter your username" maxlength="60" /><br><br>
    <label for="dob">Date of Birth:</label><br>
    <input type="date" name="dob" id="dob" /><br><br>
    <label for="password">Password:</label><br>
    <input type="password" name="password" id="password" placeholder="Enter your password" /><br><br>
    <label for="Conf_password">Password:</label><br>
    <input type="password" name="Conf_password" id="Conf_password" placeholder="Re-Enter your password" /><br><br>
    <label for="userrole">User Role:</label><br>
    <select name="userrole">
        <option value="">--Select Role--</option>
        <option value="1">Admin</option>
        <option value="2">Author</option>
        <option value="3">Editor</option>
    </select>
    <br><br>

    <input type="checkbox" name="tnc" id="tnc" value="1"/> <label for="tnc">Accept Terms & Conditions</label>
    <br><br>

   <input type="submit" name="sign_up" value="Sign Up" />
    <br><br>
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
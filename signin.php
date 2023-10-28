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
    <div class="topnav">
        <a href="./">Home</a>
        <a href="about.html">About</a>
        <a href="contact.html">Contact</a>
        <a href="product.html">Products</a>
        
        <div class="topnav-right">
            <a href="signup.html">Sign Up</a>
            <a href="signin.html">Sign In</a>
        </div>
    </div>
    <!-- top navigation ends here -->
<div class="header">
    <h1>Authentication</h1>
</div>
<!-- the main content section starts here -->
<div class="row">
    <div class="content">
<h3>Register Here</h3>

<?php

// Index arrays or Numeric arrays

$age = array(1,5,15,29,6,25,19);
$age_number = [78,54,12,6,98,82,45];
$colors = ["Black", "White", "Yellow", "Purple", "Red", "Green"];
$content = array(45, "Peter", 56.6, "Alex", 5.16);
print_r($age);
print '<br>';
print_r($age_number);
print '<br>';
print_r($colors);
print '<br>';
print '<pre>';
var_dump($content);
print '</pre>';


// Associative Arrays

$user_age = array( "Peter" => 12, "Alex" => 14, "Ken" => 24, "Jane" => 16, "Ken" => 12 );


print '<br>';
print '<pre>';
print_r($user_age);
print '</pre>';
print '<br>';

$employees = [
    "Director" => [
        "Fullanme" => "Alex Okama",
        "email" => "alex@yahoo.com",
        "Address" => [
            "Home" => "Langata",
            "Work" => "Mada"
        ]
        ],
    "Secretary" => [
        "Fullanme" => "Jane Okama",
        "email" => "jane@yahoo.com",
        "Address" => [
            "Home" => "Ngong",
            "Work" => "Westlands"
        ]
    ],
    "Chair Person" => [
        "Fullanme" => "Peter Okama",
        "email" => "peter@yahoo.com",
        "Address" => [
            "Home" => "Imara",
            "Work" => "Upper Hill"
        ]
    ]
        ];


print '<br>';
print '<pre>';
print_r($employees);
print '</pre>';
print '<br>';

print $employees["Secretary"]["Address"]["Work"];


print '<br>';
$var = "alex.okama@yahoo.com";

$var_arr = explode("@", $var);

print '<pre>';
print_r($var_arr);
print '</pre>';

print reset($var_arr);
print '<br>';

$counties = "Siaya,nairobi,meru,kisumu,nakuru,mombasa,kiambu";
$counties2 = "Marsabit, Machakos, Laikipia, Tharak-Nithi";

$count_arr = explode(",", $counties);
$count_arr2 = explode(",", $counties2);

$all_counties = array_merge($count_arr, $count_arr2);

asort($all_counties);
print '<br>';
?>

<select>
<option>-Select County-</option>
<?php
foreach($all_counties AS $county){
    print "<option>" . ucwords($county) . "</option>";
}
?>
</select>
<?php
print '<br>';
print_r($all_counties);
$count_String = implode("/", $all_counties);
print $count_String;

print '<br>';

print '<h1>While Loop</h1>';
$m = 0;
while($m <= 5){
    print $m . '<br>';
    $m++;
}

print '<h1>do-While Loop</h1>';
$n = 1;
do{
print $n . '<br>';
$n++;
}while($n <= 6);

print '<h1>For Loop</h1>';

for($l=3; $l<12; $l++){
    print $l . '<br>';
}

print date('Y-F-d');

?>
<br>
<select>
<?php
print '<option>' . date('d') . '</option>';
for($d=1; $d<=31; $d++){
    print '<option>' . $d . '</option>';
}
?>
</select>

<select>
<?php

$months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
print '<option>' . date('F') . '</option>';
foreach($months AS $month){
    print '<option>' . $month . '</option>';
}

?>
</select>

<select>
<?php
print '<option>' . date('Y') . '</option>';
for($y=2000; $y<=2023; $y++){
    print '<option>' . $y . '</option>';
}
?>
</select>


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
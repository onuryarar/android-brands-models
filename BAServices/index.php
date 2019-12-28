<?php
include_once 'inc/config.php';

$androidDb = new AndroidDb();

if (!$androidDb->connect()) {
    die('Hata: Veritabanına bağlanılamadı.' . $androidDb->error());
}

if (isset($_GET['users'])) {

    $query = "SELECT id,firstname,lastname,email FROM users";
    echo $androidDb->select($query);

} else if (isset($_POST['email']) && isset($_POST['password'])) {

    $email = trim($_POST["email"]);
    $password = md5(trim($_POST["password"]));
    $query = "SELECT id,firstname,lastname,email FROM users WHERE email='$email' AND password='$password'";
    echo $androidDb->select($query);

} else if (isset($_GET['brands'])) {

    $query = "SELECT * FROM brands";
    echo $androidDb->select($query);

} else if (isset($_GET['models'])) {

    $query = "SELECT * FROM brands b JOIN models m ON b.id = m.brand_id";
    echo $androidDb->select($query);

} else if (isset($_GET['getModels'])) {

    $query = "SELECT * FROM brands b JOIN models m ON b.id = m.brand_id WHERE m.brand_id = " . "'" . $_GET['getModels'] . "'";
    echo $androidDb->select($query);

} else {
    require 'requests.php';
}

?>

<style>
body {
    background-color: black;
    color: #e0e0e0;
    font-family: Roboto;
}
p {
    margin: 15px 0px 0px 15px ;
}
a {
    color: #d32f2f;
    text-decoration: none;
}
a:hover {
    color: #d32f2f;
    text-decoration: underline;
}
</style>

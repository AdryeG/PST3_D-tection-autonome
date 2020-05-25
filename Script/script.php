<?php

//require "fonctions.php";

$username = "";
$email = "";
$password= "";

$servername = null;
$port = null;
$dbname = 'capteurs';
$dbuser = 'root';  
$dbpass = '5qaJy6mHtxI70t1o'; 
$dbhost = '35.187.127.17'; 
$connect = new mysqli($servername, $dbuser, $dbpass, $dbname, $port, "/cloudsql/nimble-lead-277612:europe-west1:pst");

if(!$connect)
{
  echo "Error: " . mysqli_connect_error();
  exit();
}

$id = $_GET['id'];
/*Ajout d'activité de capteurs*/
if ($id == 0)
{
  $numSerie = $_GET['numSerie'];

  if($numSerie != 0)
  {
  
    $req = $connect->prepare("INSERT INTO capteur_info (numSerie) VALUES (".$numSerie.")");
    $req->execute();

    if ($req)
      echo "Insertion Success!<br>";

    else
      echo "Marche po";
  }
}
/*Création de compte*/
if ($id == 1)
{
  $add = '"';
  $username = $add.$_GET['username'].$add;
  $email = $add.$_GET['email'].$add;
  $password = $add.$_GET['password'].$add;

  $trimemail = trim($email, '"'); //Permet de supprimer les "" qui encadre notre variable

  if ($req = $connect->query("SELECT * FROM users WHERE email LIKE '".$trimemail."'"))
  {
    //printf("Compte similaire trouvé : %d", $req->num_rows); // Permet de voir le résultat de la requête
    if ($req->num_rows == 0)
    {
      $req->free();
      $register = $connect->prepare("INSERT INTO users(username, email, password) VALUES(".$username.", ".$email.", ".$password.")");
      $register->execute();
      echo "Successfully Registered";
      /*$register = $connect->prepare("INSERT INTO users(username, email, password) VALUES (?, ?, ?)");
      $register->execute(array($username, $email, $password));*/
      $register->free();
    }
    else
      echo "mail already used please change it";
  }
  else
    echo "marche pop";
}
/*Connexion a l'app*/
if ($id == 2)
{
  $email = $_GET['email'];
  $password = $_GET['password'];

  $req = $connect->query("SELECT * FROM users WHERE email LIKE '".$email."'");

  if($req->num_rows > 0)
  {
    $reqLogin = $connect->query("SELECT * FROM users WHERE email LIKE '".$email."' AND password LIKE '".$password."'");

    if($reqLogin->num_rows > 0)
    {
      echo "Login Success";

      $userinfo = $reqLogin->fetch();
      //Insertion en BDD

    }
    else
    {
      echo "Wrong Password";
    }
  }
  else
  {
    echo "This email is not registered";
  }
}
/*Fetch capteur list*/
if ($id == 3)
{

  $req = "SELECT typeCapteur, numSerie, nomCapteur FROM capteur_list";

  $result = mysqli_query($connect, $req);

  $json_array = array();

  while ($row = mysqli_fetch_assoc($result))
  {
    $json_array[] = $row;
  }

  echo json_encode($json_array);
}
?>
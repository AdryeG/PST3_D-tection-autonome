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


  $date = date_create();

  $req_date = $connect->query("SELECT timestamp FROM capteur_info WHERE numSerie = ".$numSerie." ORDER BY id_info DESC LIMIT 1");
  $req_date = $req_date->fetch_row();
  $req_date = $req_date[0];

  /*echo "1:";
  echo (date_timestamp_get($date) + 60);
  echo "2:";
  echo $req_date;*/

  if ((date_timestamp_get($date) - 60) > $req_date)
  {
    $req = $connect->prepare("INSERT INTO capteur_info (numSerie, timestamp) VALUES (".$numSerie.", ".date_timestamp_get($date).")");
    $req->execute();

    if ($req)
      echo "Insertion Success!<br>";

    else
      echo "Marche po";
  }
  else
    echo "Trop de requête"; //Permet de ne pas spamer la BDD
}
/*Création de compte*/
if ($id == 1)
{
  $add = '"';
  $username = $add.$_GET['username'].$add;
  $email = $add.$_GET['email'].$add;
  $password = $add.$_GET['password'].$add;

  $trimemail = trim($email, '"'); //Permet de supprimer les "" qui encadre notre variable

  if ($req = $connect->query("SELECT * FROM users WHERE email = '".$trimemail."'"))
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

  $req = $connect->query("SELECT * FROM users WHERE email = '".$email."'");

  if($req->num_rows > 0)
  {
    $reqLogin = $connect->query("SELECT * FROM users WHERE email = '".$email."' AND password = '".$password."'");

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

  $req = "SELECT typeCapteur, numSerie, nomCapteur, notification FROM capteur_list";

  $result = mysqli_query($connect, $req);

  $json_array = array();

  while ($row = mysqli_fetch_assoc($result))
  {
    $json_array[] = $row;
  }
  echo '{"Liste":';
  echo json_encode($json_array);
  echo "}";
}
/*Ajouter un capteur dans la base de données*/
if ($id == 4)
{

  $add = '"';
  $numSerie = $_GET['numSerie'];
  $nomCapteur = $add.$_GET['nomCapteur'].$add;
  $typeCapteur = $_GET['typeCapteur'];

  $req = $connect->query("SELECT * FROM capteur_list WHERE numSerie = '".$numSerie."'");

  if($req->num_rows == 0)
  {
    $sql_register = $connect->prepare("INSERT INTO capteur_list(nomCapteur, numSerie, typeCapteur) VALUES(".$nomCapteur.", ".$numSerie.", ".$typeCapteur.")");
    $sql_register->execute();
    echo "Successfully added";
  }
  else
  {
    echo "Numserie already used !";
  }
}
/*Fetch data list*/
if ($id == 5)
{

  $numSerie = $_GET['numSerie'];

  $req = "SELECT time FROM capteur_info WHERE numSerie = '".$numSerie."'";

  $result = mysqli_query($connect, $req);

  $json_array = array();

  while ($row = mysqli_fetch_assoc($result))
  {
    $json_array[] = $row;
  }
  echo '{"Liste":';
  echo json_encode($json_array);
  echo "}";
}
/*Supprimer un capteur*/
if ($id == 6)
{
  $numSerie = $_GET['numSerie'];
  $numSerie = trim($numSerie, '"');
  $numSerie = trim($numSerie, "'");

  $id = $connect->query("SELECT ID_list FROM capteur_list WHERE numSerie = '".$numSerie."'");

  if($id->num_rows != 0)
  {
    $req = $connect->prepare("DELETE FROM capteur_list WHERE numSerie = '".$numSerie."' ");
    $req->execute();
    echo "Successfully deleted";
  }
  else
    echo "Capteur deja supprime";
}
/*Activer/Désactiver les notifications*/
if ($id == 7)
{
  $numSerie = $_GET['numSerie'];
  $numSerie = trim($numSerie, '"');
  $numSerie = trim($numSerie, "'");

  $notif = $connect->query("SELECT notification FROM capteur_list WHERE numSerie = '".$numSerie."'");
  $result = $notif->fetch_row();
  $mode = $result[0];

  if ($mode == "0")
  {
    $reqON = $connect->prepare("UPDATE capteur_list SET notification = 1 WHERE numSerie = '".$numSerie."'");
    $reqON->execute();    
  }
  elseif ($mode == "1") 
  {
    $reqOFF = $connect->prepare("UPDATE capteur_list SET notification = 0 WHERE numSerie = '".$numSerie."'");
    $reqOFF->execute();
  }
  else
    echo "echec";
}
?>
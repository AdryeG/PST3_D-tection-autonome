#include <WiFi.h>
#include <HTTPClient.h>

#include "nRF24L01.h"
#include "RF24.h"

RF24 radio(4, 5); //Pin MESSO et MISSO
const byte address[6] = "node1";

const char* ssid     = "/*Nom de la WIFI*/";
const char* password = "/*Mdp de la WIFI*/";
const char* host = "/*Addresse ip*/";

void setup()
{
  //Initialisation du module radio
  Serial.begin(115200);
  Serial.println("Starting...");
  radio.begin();
  radio.setPALevel(RF24_PA_MIN); //Mode de consomation faible
  radio.openReadingPipe(0, address); //Pin de reception 0
  radio.startListening(); //Mode transmetteur

  // Connexion à la WIFI
  Serial.print("Connecting to ");
  Serial.println(ssid);
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) 
  {
      delay(500);
      Serial.print(".");
  }

  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}

void loop()
{ 
  //Si une donnée reçus par la radio alors
  if (radio.available()) 
  {
    char numSerie[32];
    while (radio.available())
    {
      //On lit la donnée reçus
      int len = radio.getDynamicPayloadSize();
      radio.read(&numSerie, len);
    }
    
    HTTPClient http;

    WiFiClient client;
    //On entre dans la BDD l'activation d'un capteur
    http.begin(String("https://nimble-lead-277612.ew.r.appspot.com/?id=0&numSerie=") + numSerie); //On prépare la connexion
    int httpCode = http.GET(); //On execute
    http.end(); //On ferme la connexion

  }
}



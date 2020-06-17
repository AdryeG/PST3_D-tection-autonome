#include <nRF24L01.h>
#include <RF24.h>
#include <RF24_config.h>

#define CE_PIN 3
#define CSN_PIN 4 

const int powerLatch = 4;

RF24 radio(CE_PIN, CSN_PIN);

//Addresse sur le quel les deux modules radio communique
const byte address[6] = "node1";

void setup() {

  // Définition des pins
  pinMode(powerLatch, OUTPUT);

  // Garde le circuit allumé
  digitalWrite(powerLatch, HIGH);

 //Initialisation du module radio 
  BegeningTransmision();
  
}

void BegeningTransmision(){
  radio.begin(); // Allume la radio
  
  radio.openWritingPipe(address); // Mode emmetteur
}

void loop(void){
  bool send = false;
  const char text[] = "/*Numéro de série*/";
  while (send == false) //S'assure que le message c'est bien envoyé
  {
    if (!radio.write(&text , sizeof(text))) //Envoie le message
    {
      send = false;
    }
    else
      send = true;
  }
  digitalWrite(powerLatch, LOW); //Permet d'éteindre le circuit
  exit(0);
}
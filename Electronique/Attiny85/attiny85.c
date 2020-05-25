#include <nRF24L01.h>
#include <RF24.h>
#include <RF24_config.h>

#define CE_PIN 3
#define CSN_PIN 4 

const int powerLatch = 4;

RF24 radio(CE_PIN, CSN_PIN);

//address through which two modules communicate.
const byte address[6] = "node1";

unsigned long payload = 0;

void setup() {

  // Définition des pins
  pinMode(powerLatch, OUTPUT);

  // Garde le circuit allumé
  digitalWrite(powerLatch, HIGH);
 
  BegeningTransmision();
  
  // Remettre le cicuit en off
  digitalWrite(powerLatch, LOW);
  
}

void BegeningTransmision(){
  radio.begin(); // Start up the radio
  
  radio.openWritingPipe(address); // Write to device address
  radio.stopListening();
}

void loop(void){
  bool send = false;
  const char text[] = "124578";
  while (send == false)
  {
    if (!radio.write(&text , sizeof(text)))
    {
      send = false;
    }
    else
      send = true;
  }
  digitalWrite(powerLatch, LOW);
  exit(0);
}
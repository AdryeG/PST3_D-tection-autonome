#include <WiFi.h>
#include <HTTPClient.h>

#include "nRF24L01.h"
#include "RF24.h"

RF24 radio(4, 5);
const byte address[6] = "node1";

const char* ssid     = "Bbox-58031F45";
const char* password = "DDD2E2FC6EF5F691454F629215A9DE";
const char* host = "35.187.127.17";

void setup()
{
  Serial.begin(115200);
  Serial.println("Starting...");
  radio.begin();
  radio.setPALevel(RF24_PA_MIN);
  radio.openReadingPipe(0, address);
  radio.startListening();

  // We start by connecting to a WiFi network
  Serial.println();
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) 
  {
      delay(500);
      Serial.print(".");
  }

  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}

void loop()
{ 
  if (radio.available()) 
  {
    char numSerie[32];
    while (radio.available())
    {
      int len = radio.getDynamicPayloadSize();
      radio.read(&numSerie, len);
      //Serial.println(numSerie);
    }
    
    HTTPClient http;

    WiFiClient client;

    http.begin(String("https://nimble-lead-277612.ew.r.appspot.com/?numSerie=") + numSerie);
    int httpCode = http.GET();
    http.end();

  }
}
#include <SoftwareSerial.h>
#include <OneWire.h> 
#include <DallasTemperature.h>
#include "DHT.h"
#include <ArduinoJson.h>
#include <FastLED.h>


const byte rxPin = 10; 
const byte txPin = 11; 
SoftwareSerial esp (rxPin, txPin);

#define ONE_WIRE_BUS 2 
#define DHTPIN 3
#define DHTTYPE DHT11
#define DATA_PIN_LED 8
#define NUM_LEDS 64
#define DATA_PIN_KIRMIZI_LED 9
#define motor_pin 4
#define motor_pin2 5
#define motor_pin3 6
#define motor_pin4 7

CRGB leds[NUM_LEDS];

OneWire oneWire(ONE_WIRE_BUS); 

DallasTemperature sensors(&oneWire);

DHT dht(DHTPIN, DHTTYPE);

char gonderilen[30];

const int analog_pin_toprak = A0;
String toprak_nem;

const int analog_pin_su = A1;
String su_seviye;


const int analog_pin_isik = A2; 
String isik_deger;


const int analog_pin_havaKalite = A3; 
String hava_kalitesi;

float toprak_sicaklik;
float hava_nem;
float hava_sicaklik;

void setup(void) 
{ 
 Serial.begin(115200); 
 sensors.begin(); 
 dht.begin();
 esp.begin(115200);
 pinMode(analog_pin_havaKalite,INPUT);
 pinMode(DATA_PIN_KIRMIZI_LED,OUTPUT);
 FastLED.addLeds<WS2812B, DATA_PIN_LED, GRB>(leds, NUM_LEDS);
 FastLED.setBrightness(50);
 pinMode(motor_pin,OUTPUT);
 pinMode(motor_pin2,OUTPUT);
 pinMode(motor_pin3,OUTPUT);
 pinMode(motor_pin4,OUTPUT);
 delay(1000); 
} 
void loop(void) 
{ 
  
  Serial.println("----------------------------------------");
  su_seviye = analogRead(analog_pin_su);
  Serial.println("Su seviyesi:");
  Serial.println(su_seviye);
  
  hava_nem = dht.readHumidity();
  Serial.println("hava nemi:");
  Serial.println(hava_nem);
  hava_sicaklik = dht.readTemperature();
  Serial.println("hava sicakligi:");
  Serial.println(hava_sicaklik);
  
  toprak_nem = analogRead(analog_pin_toprak);
  Serial.println("Toprak nemi:");
  Serial.println(toprak_nem);

  sensors.requestTemperatures(); 
  toprak_sicaklik = sensors.getTempCByIndex(0);
  Serial.println("Toprak sicakligi:"); 
  Serial.println(toprak_sicaklik); 

  hava_kalitesi = analogRead(analog_pin_havaKalite);
  Serial.println("hava kalitesi değeri: ");
  Serial.println(hava_kalitesi);

  isik_deger = analogRead(analog_pin_isik);
  Serial.println("Isik değeri: ");
  Serial.println(isik_deger);
  Serial.println("----------------------------------------");
 
  String sensor_degerleri=toprak_nem+','+su_seviye+','+toprak_sicaklik+','+hava_sicaklik+','+hava_nem+','+hava_kalitesi+','+isik_deger+'|*';
  
  sensor_degerleri.toCharArray(gonderilen,33); 
  for (int i=0;i<sensor_degerleri.length();i++)
  {
    esp.write(gonderilen[i]);
  } 
  delay(1000);
  if(toprak_nem.toFloat() > 512 && su_seviye.toFloat() > 10){
    digitalWrite(motor_pin,HIGH);
    digitalWrite(motor_pin2,LOW);
  }else{
    digitalWrite(motor_pin,LOW);
    digitalWrite(motor_pin2,LOW);
  }
  if(hava_kalitesi.toFloat() < 400){
    digitalWrite(motor_pin3,LOW);
    digitalWrite(motor_pin4,LOW);
    Serial.print("Hava kalitesi iyi");
    Serial.print("\n");
  }
  else{
    digitalWrite(motor_pin3,HIGH);
    digitalWrite(motor_pin4,LOW);
    Serial.print("Hava kalitesi kötü");
    Serial.print("\n");
  }

  if(hava_sicaklik < 30){
    digitalWrite(8,HIGH);
  }else{
    digitalWrite(8,LOW);
  }
  
  if(isik_deger.toFloat() > 600){
    for(int i = 0;i<64;i++){
        leds[i].setRGB(255, 128, 0);
        
    }
    FastLED.show();
  }else{
     for(int i = 0;i<64;i++){
        leds[i].setRGB(0, 0, 0);
        
    }
    FastLED.show();
  }
  delay(2000);   
} 

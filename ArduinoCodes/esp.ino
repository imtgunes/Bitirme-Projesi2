#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>
#include <ArduinoJson.h>


#define FIREBASE_HOST "example.firebaseio.com"
#define FIREBASE_AUTH "token_or_secret"
#define WIFI_SSID "SSID"
#define WIFI_PASSWORD "PASSWORD"


String values;
char dizi[34];
void setup() {
  Serial.begin(115200);
  
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("connecting");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  Serial.println();
  Serial.print("connected: ");
  Serial.println(WiFi.localIP());
  
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  
 
  delay(1000); 
}



void loop() {
  delay(3000); 
  Firebase.setString("connected", "connected");
  String gelen="";
  while(Serial.available()>0)
  {
    for (int i=0;i<35;i++)
    {  
   
    dizi[i] = Serial.read();
    if (dizi[i]=='|*')
     {   break; }
     gelen+= dizi[i];
     
     
    }
    
    int i;
    char ayirici[] = ",";
    char *p;
    char string[128];
    String degerler[7];

    gelen.toCharArray(string, sizeof(string));
    i = 0;
    p = strtok(string, ayirici);
    while(p && i < 7)
    {
      degerler[i] = p;
      p = strtok(NULL, ayirici);
      ++i;
    }
    
    delay(10);
    String toprak_nem = degerler[0];
    String su_seviye = degerler[1];
    String toprak_sicaklik = degerler[2];
    String hava_sicaklik = degerler[3];
    String hava_nem = degerler[4];
    String hava_kalitesi = degerler[5];
    String isik_deger = degerler[6];
    delay(10);
    Firebase.setString("toprak_nem", toprak_nem);
    delay(10);
    Firebase.setString("su_seviye", su_seviye);
    delay(10);
    Firebase.setString("toprak_sicaklik", toprak_sicaklik);
    delay(10);
    Firebase.setString("hava_sicaklik", hava_sicaklik);
    delay(10);
    Firebase.setString("hava_nem", hava_nem);
    delay(10);
    Firebase.setString("hava_kalitesi", hava_kalitesi);
    delay(10);
    Firebase.setString("isik_deger", isik_deger);
    delay(10);

    
  }
  
}

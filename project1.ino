// Authors: Katie Hummel, Tho Le


// include the library code:
#include <LiquidCrystal.h>
#include <dht.h>
dht DHT;
#define DHT11_PIN 6
int lightPin = 0;  //pin for Photoresistor
int TranslateAnalog = 0;

int redpin = 4; // select the pin for the red LED
int greenpin = 3; // select the pin for the green LED
int val;


//// initialize the library by associating any needed LCD interface pin
//// with the arduino pin number it is connected to
const int rs = 7, en = 8, d4 = 9, d5 = 10, d6 = 11, d7 = 12; //7, 8, 9, 10, 11, 12
LiquidCrystal lcd(rs, en, d4, d5, d6, d7);

void setup() {
  // set up the LCD's number of columns and rows:
  lcd.begin(16, 2);
  Serial.begin(9600);
  pinMode (7, OUTPUT);
  pinMode (redpin, OUTPUT);
  pinMode (greenpin, OUTPUT);
}

void loop() {
  // LCD Display
  int chk = DHT.read11(DHT11_PIN);
  lcd.setCursor(0,0); 
  lcd.print("Temp: ");
  lcd.print((DHT.temperature*9/5)+32);
  lcd.print((char)223);
  lcd.print("F");
  lcd.setCursor(13, 0);
  lcd.print("   ");
  lcd.setCursor(0,1);
  lcd.print("Humidity: ");
  lcd.print(DHT.humidity);
  lcd.print("%");

  // Serial Monitor Display
  Serial.print("Temp: ");
  Serial.print((DHT.temperature*9/5)+32);
  Serial.print("F  ");
  Serial.print("Humidity: ");
  Serial.print(DHT.humidity);
  Serial.print("%  ");
//  delay(1000);

  Serial.print("Photo resistor value: ");
  Serial.println(analogRead(lightPin)); //Write the value of the photoresistor to the serial monitor.
  TranslateAnalog = analogRead(lightPin); //Read the Photoresistor value
//  If Photoresistor value is above 355 it will disable LED
  if(TranslateAnalog>355) {
     digitalWrite(7, LOW);
     analogWrite(greenpin, 0);
     delay (15);
  }
//  If Photoreistor value is below 355 it will turn LED ON
  if(TranslateAnalog<355) {
     lcd.clear();
     digitalWrite(7, HIGH);
     analogWrite (greenpin, 255);
     lcd.setCursor(0,0); 
     lcd.print("Hey it got dark!");
     lcd.setCursor(0,1); 
     lcd.print("Here's some ");
     delay(2000);
     lcd.clear();
     lcd.setCursor(0,0);
     lcd.print("light to help ");
     lcd.setCursor(0,1);
     lcd.print("you out!");
     delay (2000); // delay the next line of words to print to the LCD dispaly

//        analogWrite (greenpin, val);
//        analogWrite (redpin, 255-val);
//        delay (15);
       
   }
   delay(1000); // update the temp and humidity values on the LCD display every second

}

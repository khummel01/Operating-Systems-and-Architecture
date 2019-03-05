#include <Servo.h> 
int servoPin = 9;
Servo myServo; // create servo object to control servo  
int servo_position = 0;   // initial angle for servo

void setup() {
  // put your setup code here, to run once:
  myServo.attach(9); // attaches the servo on pin 9 to the servo object
  Serial.begin(9600); // initialize the serial port
}

void loop() {
  // put your main code here, to run repeatedly:
    for (servo_position = 0; servo_position <=180; servo_position +=1){
 
    myServo.write(servo_position);
    delay(10);
  }
 
  for (servo_position=180; servo_position >= 0; servo_position -=1){
 
    myServo.write(servo_position);
    delay(10);
  }
}
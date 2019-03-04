/*
Adafruit Arduino - Lesson 16. Stepper
*/

#include <Servo.h> 
int servoPin = 9;
Servo myservo; // create servo object to control servo  
int angle = 0;   // initial angle for servo
int angleStep = 10;

#include <Stepper.h>
int in1Pin = 5;
int in2Pin = 6;
int in3Pin = 3;
int in4Pin = 4;

// Distance Sensor
const int trigPin = 8;
const int echoPin = 7;
long duration;
int distance;
 
//Stepper motor(512, in1Pin, in2Pin, in3Pin, in4Pin);  
const int stepsPerRevolution = 360;  // change this to fit the number of steps per revolution
// for your motor

// initialize the stepper library on pins 8 through 11:
Stepper myStepper(stepsPerRevolution, in1Pin, in2Pin, in3Pin, in4Pin);

void setup() {
  myStepper.setSpeed(10); // set the speed at 60 rpm
  myservo.attach(9); // attaches the servo on pin 9 to the servo object
  pinMode(echoPin, INPUT);
  pinMode(trigPin, OUTPUT);
//  pinMode(2, INPUT_PULLUP);
  Serial.begin(9600); // initialize the serial port

}

void loop() {
  // STEPPER
//  delay(500);

//  myservo.write(10);
//  delay(1000);
//  myStepper.setSpeed(20);
//  myStepper.step(stepsPerRevolution);
//  delay(2000);
//  myStepper.setSpeed(100);
//  myStepper.step(stepsPerRevolution);
//  myservo.write(170);
//  delay(2000);

  // Clears the trigPin
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);
  // Sets the trigPin on HIGH state for 10 micro seconds
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);
  // Reads the echoPin, returns the sound wave travel time in microseconds
  duration = pulseIn(echoPin, HIGH);
  // Calculating the distance
  distance= duration*0.034/2;
  // Prints the distance on the Serial Monitor
  if (distance < 3) {
    myStepper.setSpeed(48);
    myStepper.step(stepsPerRevolution);
  } else if (distance < 10) {
    myStepper.setSpeed(30);
    myStepper.step(stepsPerRevolution);
  } else if (distance < 15) {
    myStepper.setSpeed(28);
    myStepper.step(stepsPerRevolution);
  } else if (distance < 20){
    myStepper.setSpeed(18);
    myStepper.step(stepsPerRevolution);
  } else if (distance < 20){
    myStepper.setSpeed(10);
    myStepper.step(stepsPerRevolution);
  } else if (distance < 5){
    myStepper.setSpeed(16);
    myStepper.step(stepsPerRevolution);
  } else {
    myStepper.setSpeed(5);
    myStepper.step(stepsPerRevolution);
  }
  Serial.print("Distance: ");
  Serial.println(distance);
}

// TODO: barking sounds

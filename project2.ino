/*
Project 2
*/

#include <Servo.h> 
int servoPin = 9;
Servo myServo; // create servo object to control servo  
int servo_position = 0; // initial angle for servo # TODO: might not need
boolean forward = true; // we want to rotate between moving back and forward 180 degrees
int num_rounds = 0;

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
 
// Stepper motor(512, in1Pin, in2Pin, in3Pin, in4Pin);  
const int stepsPerRevolution = 360;  // change this to fit the number of steps per revolution for your motor

// initialize the stepper library on pins 8 through 11:
Stepper myStepper(stepsPerRevolution, in1Pin, in2Pin, in3Pin, in4Pin);

void setup() {
  myStepper.setSpeed(10); // set the speed at 60 rpm
  myServo.attach(9); // attaches the servo on pin 9 to the servo object
  pinMode(echoPin, INPUT);
  pinMode(trigPin, OUTPUT);
//  pinMode(2, INPUT_PULLUP);
  Serial.begin(9600); // initialize the serial port
}

void loop() {
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
  distance = duration*0.034/2;
  if (distance < 10) {
      if (forward) {
        servo_position = 0;
        for (servo_position = 0; servo_position <=180; servo_position +=1){
          myServo.write(servo_position);
          delay(10);
        forward = false; 
        }
      } else {
          for (servo_position = 0; servo_position <=180; servo_position +=1){
            myServo.write(servo_position);
            delay(10);
          }
          forward = true;
      }
  } else if (distance < 20) {
    if (num_rounds == 0) {
      servo_position = 0;
    }
    if (num_rounds < 2) {
       if (servo_position < 180) {
          servo_position = servo_position + 90;
                        Serial.print(servo_position);
          myServo.write(servo_position);
          delay(900);
        } else {
            servo_position = servo_position - 90;
                          Serial.print(servo_position);
            myServo.write(servo_position);
            delay(900);
          }
       num_rounds++;
     } else {
        myStepper.setSpeed(15);
        myStepper.step(stepsPerRevolution);
        num_rounds = 0;
      }
  } else {
     myStepper.setSpeed(40);
     myStepper.step(stepsPerRevolution);
  }
  // Prints the distance on the Serial Monitor
  Serial.print("Distance: ");
  Serial.println(distance);
}

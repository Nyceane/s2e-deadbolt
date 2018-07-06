#include <SoftwareSerial.h>

const int relayPin =  6;      // the number of the Relay
SoftwareSerial mySerial(0, 1); // RX, TX

void setup() {
  // initialize the LED pin as an output:
  pinMode(relayPin, OUTPUT);
  Serial.begin(9600);     // opens serial port, sets data rate to 9600 bps
  while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }
  //S2E is using 115200 from previous steps
  mySerial.begin(115200);
}

void loop() {
  if (mySerial.available()) {
    if(mySerial.read() == 49)
    {
      digitalWrite(relayPin, HIGH);
      Serial.println("on");
      delay(5000);
      digitalWrite(relayPin, LOW);
      Serial.println("off");
    }
  }
  delay(100);
}
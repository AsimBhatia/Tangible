#include <Mouse.h>
#include <Keyboard.h>
#include <MouseTo.h>

// Scrolling variables
int scrollVal = 0;

int state = 0;
float prevPos;
float lastPos = -1;
int loopCounter = 0;

// Sensor variables
int n = 6; // Number of sensors
int gridRows = 4; // Number of touch screen rows
float fixedScreenHeight = 27.50;

// Input mode
char toggleChar; // Holds the user mode input
int touchMode = 0; // Mode value
int cursorSpeed = 5; // Speed for cursor

// Define Trig and Echo pin:
byte trigPins[] = {2, 4, 6, 8, 10, 12};
byte echoPins[] = {3, 5, 7, 9, 11, 13};

float positions[] = {0, 0, 0, 0, 0, 0}; // Raw positions of each sensors
int absCoords[] = {0, 0}; // Current finger position as a coordinate pair

// Define variables:
long duration;
float distance;
int screenPos;
float screenHeight;
int alreadyHasRightInput = 0, alreadyHasLeftInput = 0; // Prevent undesired Presentation Mode behaviour

bool isClicked = false; // Used for drawing mode

// Grid mapping variables
int touchMap[5][6] = {{0, 0, 0, 0, 0, 0},
                      {0, 0, 0, 0, 0, 0},
                      {0, 0, 0, 0, 0, 0},
                      {0, 0, 0, 0, 0, 0},
                      {0, 0, 0, 0, 0, 0}}; // This row will be used to indicate extraneous positions (negative or greater than screenHeight)
float colPos[6]; // Holds relative position for each column
int colIndex[6]; // Holds index value for each column


void setup() {
    MouseTo.setCorrectionFactor(1);
    
  // Define inputs and outputs:
  for (int i = 0; i < sizeof(trigPins); i++) {
    pinMode(trigPins[i], OUTPUT);
    pinMode(echoPins[i], INPUT);

    // Clear the trigPin by setting it LOW:
    digitalWrite(trigPins[i], LOW);
    delayMicroseconds(5);
    
    // Trigger the sensor by setting the trigPin high for 10 microseconds:
    digitalWrite(trigPins[i], HIGH);
    delayMicroseconds(10);
    digitalWrite(trigPins[i], LOW);
    
    // Read the echoPin, pulseIn() returns the duration (length of the pulse) in microseconds:
    duration = pulseIn(echoPins[i], HIGH);
    
    // Calculate the distance:
    distance= duration*0.034/2;

    screenHeight = fixedScreenHeight ? fixedScreenHeight : distance; // Calibrate to bottom of screen position on start
  }

  //Begin Serial communication at a baudrate of 9600:
  Serial.begin(9600);
  
  Mouse.begin();
  Keyboard.begin();
}

void loop() {

  if (loopCounter == 10) {
    loopCounter = 0;
    if (lastPos >= 0) {
      prevPos = lastPos;
    } else {
      prevPos = -1;
    }
  }



  for (int i = 0; i < sizeof(trigPins); i++) {
    // Clear the trigPin by setting it LOW:
    digitalWrite(trigPins[i], LOW);
    delayMicroseconds(5);
    
    // Trigger the sensor by setting the trigPin high for 10 microseconds:
    digitalWrite(trigPins[i], HIGH);
    delayMicroseconds(10);
    digitalWrite(trigPins[i], LOW);
    
    // Read the echoPin, pulseIn() returns the duration (length of the pulse) in microseconds:
    duration = pulseIn(echoPins[i], HIGH);
    
    // Calculate the distance:
    distance = duration*0.034/2;

    positions[i] = distance;
    delay(0);
  }

  // Calculate the relative position on the screen
  for (int i = 0; i < n; i++) {
    colPos[i] = positions[i] / screenHeight;
  }
  
  // Translate column position distances into indexes in the touch grid
  absCoords[1] = 4;
  for (int i = 0; i < n; i++) {
    if (colPos[i] > 0.1 && colPos[i] <= 0.25) { // Use > 0.1 as a threshold from the base of the screen
      colIndex[i] = 0;
      absCoords[0] = i;
      absCoords[1] = 0;
    } else if (colPos[i] > 0.25 && colPos[i] <= 0.5) {
      colIndex[i] = 1;
      absCoords[0] = i;
      absCoords[1] = 1;
    } else if (colPos[i] > 0.5 && colPos[i] <= 0.75) {
      colIndex[i] = 2;
      absCoords[0] = i;
      absCoords[1] = 2;
    } else if (colPos[i] > 0.75 && colPos[i] <= 0.9) {
      colIndex[i] = 3;
      absCoords[0] = i;
      absCoords[1] = 3;
    } else {
      colIndex[i] = 4;
    }
  }

  // Reset touchMap to 0s
  for (int i = 0; i < gridRows; i++) {
    for (int j = 0; j < n; j++) {
      touchMap[i][j] = 0;
    }
  }

  // Apply colIndex values to touchMap
  for (int i = 0; i < n; i++) {
    touchMap[colIndex[i]][i] = 1;
  }

  toggleChar = Serial.read();
  switch(toggleChar) {
    case '0': touchMode = 0; break; // Safe mode
    case '1': touchMode = 1; break; // Scroll Mode
    case '2': touchMode = 2; break; // UP DOWN SPACE Mode
    case '3': touchMode = 3; break; // Draw Mode
    case '4': touchMode = 4; break; // Cursor Mode
    case '5': touchMode = 5; break; // Guitar Mode
    case '6': touchMode = 6; break; // New Scroll Mode
    case '7': touchMode = 7; break; // Presentation Mode
    case '8': touchMode = 8; break; // Asteroid Mode
    case '9': touchMode = 9; break; // Asteroid Mode
    case 'a': touchMode = 10; break; // Asteroid Mode
    case 'b': touchMode = 11; break; // Asteroid Mode
    default: break;  
  }

  if (touchMode == 0) {
    // Do nothing
  } else if (touchMode == 1) {
    if (absCoords[1] == 2 || absCoords[1] == 3) {
      Keyboard.release(KEY_DOWN_ARROW);
      Keyboard.press(KEY_UP_ARROW);
    } else if (absCoords[1] == 0 || absCoords[1] == 1) {
      Keyboard.release(KEY_UP_ARROW);
      Keyboard.press(KEY_DOWN_ARROW);
    } else {
      Keyboard.release(KEY_UP_ARROW);
      Keyboard.release(KEY_DOWN_ARROW);
    }
  } else if (touchMode == 2) { 
    switch(touchMap[3][5]) {
      // Map to arrow keys
      case 1: Keyboard.press(KEY_UP_ARROW); break;
      case 0: Keyboard.release(KEY_UP_ARROW); break;
      default: Keyboard.release(KEY_UP_ARROW); break;
    }
    switch(touchMap[3][0]) {
      case 1: Keyboard.press(KEY_DOWN_ARROW); break;
      case 0: Keyboard.release(KEY_DOWN_ARROW); break;
      default: Keyboard.release(KEY_DOWN_ARROW); break;
    }
    switch(touchMap[1][2] || touchMap[1][3]) {
      case 1: Keyboard.press((char) 32); break;
      case 0: Keyboard.release((char) 32); break;
      default: Keyboard.release((char) 32); break;
    }
  } else if (touchMode == 3) {
    // Map to mouse
    if (isClicked == true) {
      Mouse.press();
    } else {
      Mouse.release();
    }
    if (touchMap[0][1] == 1) {
      Mouse.move(-cursorSpeed,-cursorSpeed);
      isClicked = true;
    } else if (touchMap[0][2] == 1 || touchMap[0][3] == 1) {
      Mouse.move(0,-cursorSpeed);
      isClicked = true;
    } else if (touchMap[0][4] == 1) {
      Mouse.move(cursorSpeed,-cursorSpeed);
      isClicked = true;
    } else if (touchMap[1][4] == 1 || touchMap[2][4] == 1) {
      Mouse.move(cursorSpeed,0);
      isClicked = true;
    } else if (touchMap[3][4] == 1) {
      Mouse.move(cursorSpeed,cursorSpeed);
      isClicked = true;
    } else if (touchMap[3][2] == 1 || touchMap[3][3] == 1) {
      Mouse.move(0,cursorSpeed);
      isClicked = true;
    } else if (touchMap[3][1] == 1) {
      Mouse.move(-cursorSpeed,cursorSpeed);
      isClicked = true;
    } else if (touchMap[1][1] == 1 || touchMap[2][1] == 1) {
      Mouse.move(-cursorSpeed,0);
      isClicked = true;
    } else {
      Mouse.move(0,0);
      isClicked = false;
    }
  } else if (touchMode == 4) {
    // Map to mouse
      MouseTo.setTarget((absCoords[0]+1)*(1800/6)-100, (absCoords[1]+1)*(960/4)-100, false);
      while (MouseTo.move() == false) {}
  } else if (touchMode == 5) {
      if (colPos[3] >= 0 && colPos[3] <= 1) {
        MouseTo.setTarget(1800/2, floor(960 * colPos[3]), false);
        while (MouseTo.move() == false) {}
      }
  } else if (touchMode == 6) {
      if (colPos[3] >= 0 && colPos[3] <= 1) {
        Serial.println("HI");
        Mouse.move(0, 0, floor(5*(0.5-colPos[3])) );
      }
  } else if (touchMode == 7) {
    int hasRightInput = 0, hasLeftInput = 0;
    for(int i=0; i<4; ++i) {
      if (touchMap[i][4] || touchMap[i][5]) hasRightInput = 1;
      if (touchMap[i][0] || touchMap[i][1]) hasLeftInput = 1;
    }
    if (hasRightInput && !alreadyHasRightInput) {
      Keyboard.press(KEY_RIGHT_ARROW);
      Keyboard.release(KEY_RIGHT_ARROW);
      delay(1000);
      alreadyHasRightInput = 1;
    } else if(!hasRightInput) {
      Keyboard.release(KEY_RIGHT_ARROW);
      alreadyHasRightInput = 0;
    } else {
      Keyboard.release(KEY_RIGHT_ARROW);
    }
    if (hasLeftInput && !alreadyHasLeftInput) {
      Keyboard.press(KEY_LEFT_ARROW);
      Keyboard.release(KEY_LEFT_ARROW);
      delay(1000);
      alreadyHasLeftInput = 1;
    } else if(!hasLeftInput) {
       Keyboard.release(KEY_LEFT_ARROW);
       alreadyHasLeftInput = 0;
    } else {
       Keyboard.release(KEY_LEFT_ARROW);
    }

  } else if (touchMode == 8) {
    switch(touchMap[3][5]) {
      // Map to arrow keys
      case 1: Keyboard.press(KEY_RIGHT_ARROW); break;
      case 0: Keyboard.release(KEY_RIGHT_ARROW); break;
      default: Keyboard.release(KEY_RIGHT_ARROW); break;
    }
    switch(touchMap[3][4]) {
      case 1: Keyboard.press(KEY_LEFT_ARROW); break;
      case 0: Keyboard.release(KEY_LEFT_ARROW); break;
      default: Keyboard.release(KEY_LEFT_ARROW); break;
    }
    switch(touchMap[3][0]) {
      case 1: Keyboard.press(KEY_UP_ARROW); break;
      case 0: Keyboard.release(KEY_UP_ARROW); break;
      default: Keyboard.release(KEY_UP_ARROW); break;
    }
    switch(touchMap[2][0]) {
      case 1: Keyboard.press((char) 32); break;
      case 0: Keyboard.release((char) 32); break;
      default: Keyboard.release((char) 32); break;
    }
    switch(touchMap[1][2]) {
      case 1: Keyboard.press(KEY_RETURN); break;
      case 0: Keyboard.release(KEY_RETURN); break;
      default: Keyboard.release(KEY_RETURN); break;
    }
  } else if (touchMode == 9) {
    switch(touchMap[3][2]) {
      // Map to arrow keys
      case 1: Keyboard.press(KEY_RIGHT_ARROW); break;
      case 0: Keyboard.release(KEY_RIGHT_ARROW); break;
      default: Keyboard.release(KEY_RIGHT_ARROW); break;
    }
    switch(touchMap[3][0]) {
      case 1: Keyboard.press(KEY_LEFT_ARROW); break;
      case 0: Keyboard.release(KEY_LEFT_ARROW); break;
      default: Keyboard.release(KEY_LEFT_ARROW); break;
    }
  } else if (touchMode == 10) {
    if (colPos[3] >= 0 && colPos[3] <= 1) {
      lastPos = colPos[3];
      if (prevPos >= 0) {
        Mouse.move(0, 0, -(3*(prevPos-lastPos)));
        Serial.println("MouseMove");
      } else {
        prevPos = colPos[3];
        Serial.println("MouseNotMove");
      }
    } else {
      Mouse.move(0, 0, 0);
      lastPos = -1;
      prevPos = -1;
    }
  } else if (touchMode == 11) {
      if (colPos[3] >= 0 && colPos[3] <= 1 && state == 0) {
        state = 1;
        Serial.println("HIIII");
        prevPos = colPos[3];
      } else if (colPos[3] < 0 || colPos[3] > 1) {
        state = 0;
      }

      if (state == 1) {
        Mouse.move(0, 0, floor(2*(prevPos - colPos[3])));
        prevPos = colPos[3];
        
      }
      
  }
  Serial.print("touchMode: ");
  Serial.println(touchMode);
  Serial.print("state: ");
  Serial.println(state);
  Serial.print("lastPos: ");
  Serial.println(lastPos);
  Serial.print("prevPos: ");
  Serial.println(prevPos);
  Serial.print("colPos[3]: ");
  Serial.println(colPos[3]);
  
// Print out touch map
  Serial.print("screenHeight: ");
  Serial.println(screenHeight);
  for (int i = 0; i < n; i++) {
    Serial.print(" | s[");
    Serial.print(i);
    Serial.print("]: ");
    Serial.print(colPos[i]);
  }
  Serial.println("");

  
  Serial.println(absCoords[0]);
  Serial.println(absCoords[1]);
  for (int row = 0; row < gridRows; row++) {
    for (int col = 0; col < n; col++) {
      Serial.print("[");
      Serial.print(touchMap[row][col]);
      Serial.print("]");
    }
    Serial.println(" - ");
  }
  Serial.println("-----------");

  loopCounter++;

  
  delay(0); // Refresh rate
}

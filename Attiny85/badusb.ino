#include "DigiKeyboard.h"
void setup() {
pinMode(0,OUTPUT);
pinMode(1,OUTPUT);
digitalWrite(0, HIGH);
digitalWrite(1, HIGH);
}   
void PAYLOAD1(){
DigiKeyboard.delay(2000);
DigiKeyboard.sendKeyStroke(KEY_R, MOD_GUI_LEFT);
DigiKeyboard.delay(300);
DigiKeyboard.println("cmd");
DigiKeyboard.delay(800);
DigiKeyboard.println("start \"\" \"http://61.147.171.105:49950/index.html\"");
DigiKeyboard.delay(100);
DigiKeyboard.println("exit");
}
 void PAYLOAD2(){
DigiKeyboard.delay(2000);
DigiKeyboard.sendKeyStroke(KEY_R, MOD_GUI_LEFT);
DigiKeyboard.delay(300);
DigiKeyboard.println("cmd");
DigiKeyboard.delay(800);
DigiKeyboard.println("start \"\" \"https://img2.baidu.com/it/u=1321117886,438058261&fm=253&fmt=auto&app=138&f=JPEG?w=362&h=293\"");
DigiKeyboard.delay(100);
DigiKeyboard.println("exit");
}
void loop() {
  if (digitalRead(0) == LOW) {
     PAYLOAD1();
  }
  else  if (digitalRead(1) == LOW) { 
     PAYLOAD2();
  }
 
}
 


 
 

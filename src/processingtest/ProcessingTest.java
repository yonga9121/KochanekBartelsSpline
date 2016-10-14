/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processingtest;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PVector;

/**
 *
 * @author camilo
 */
public class ProcessingTest extends PApplet {

 boolean showKnots = true;
 boolean showCurve = false;
 ArrayList<PVector> points;
 ArrayList<PVector> interpolatedCurvePoints;
 int closestIndex = -1;
 KochanekBartelsSpline myKBSpline;
 float tension = 0;
 float bias = 0;
 float continuity = 0;
 public void setup() {
  size(800, 600);
  rectMode(CENTER);
  points = new ArrayList<PVector>();
  myKBSpline = new KochanekBartelsSpline(this);
  
 }

 public void keyPressed() {
  if(key == 's' || key == 'S'){
   saveKnots("nudos.csv");
  }
  if(key == 'q' || key == 'Q'){
   tension +=0.05;
  }
  if(key == 'a' || key == 'A'){
   tension -=0.05;
  }
  if(key == 'w' || key == 'W'){
   bias +=0.05;
  }
  if(key == 's' || key == 'S'){
   bias -=0.05;
  }
  if(key == 'e' || key == 'E'){
   continuity +=0.05;
  }
  if(key == 'd' || key == 'D'){
   continuity -=0.05;
  }
 }
 
 public void mousePressed() {
  if (mouseButton == LEFT) {
   myKBSpline.addKnot(new PVector(mouseX,mouseY));
  }
  if (mouseButton == RIGHT) {
   closestIndex = myKBSpline.closestKnot(new PVector(mouseX,mouseY),4);
  }
 }
 public void mouseDragged(){
  if(mouseButton == RIGHT){
   if(closestIndex !=-1){
    float dx = (float) mouseX-pmouseX;
    float dy = (float) mouseY-pmouseY;
    PVector dp = new PVector(dx,dy);
    myKBSpline.moveKnot(closestIndex, dp);
   }
  }
 }
 public void draw() {
  background(0);
  myKBSpline.setParameters(tension, bias, continuity);
  myKBSpline.createCurve(100);
  String mouseInfo = "(mousePos) x: "+mouseX+" y:"+mouseY+"("+closestIndex+")";
  String kbInfo = "t:"+tension+" b:"+bias+" c:"+continuity;
  fill(255);
  text(mouseInfo,10,textAscent());
  text(kbInfo,10,2*textAscent());
  fill(255,0,0);
  myKBSpline.plotKnots();
  stroke(255,0,0);
  myKBSpline.plotCurve();
 }
 public void drawKnots() {
  fill(255, 0, 0);
  noStroke();
  for (int i = 0; i < points.size(); i++) {
   rect(points.get(i).x * width, points.get(i).y * height, 5, 5);
  }
 }
 public void saveKnots(String fileName){
  String[] knotContent = myKBSpline.createKnotInfo();
  saveStrings(fileName, knotContent);
 }
 /**
  * @param args the command line arguments
  */
 public static void main(String[] args) {
  // TODO code application logic here
  PApplet.main(new String[]{ProcessingTest.class.getCanonicalName()});
 }

}

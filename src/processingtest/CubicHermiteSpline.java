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
public class CubicHermiteSpline {
 ArrayList<PVector> knots;
 ArrayList<PVector> curvePoints;
 PApplet parent;
 
 public CubicHermiteSpline(){
  this.parent = null;
  this.knots =  new ArrayList<PVector>();
  this.curvePoints = new ArrayList<PVector>();
 }
 public CubicHermiteSpline(PApplet parent){
  this.parent = parent;
  this.knots =  new ArrayList<PVector>();
  this.curvePoints = new ArrayList<PVector>();
 }
 public void addKnot(PVector p){
  knots.add(p);
 }
 public float h00(float t){
  return (1+2*t)*(1-t)*(1-t);
 }
 public float h10(float t){
  return t*(1-t)*(1-t);
 }
 public float h01(float t){
  return t*t*(3-2*t);
 }
 public float h11(float t){
  return t*t*(t-1);
 }
 public PVector tangent(int i){
  if(i==0){
   return PVector.sub(knots.get(i+1),knots.get(i));
  }else if(i== knots.size()-1){
   return PVector.sub(knots.get(i),knots.get(i-1));
  }else{
   return PVector.sub(knots.get(i+1),knots.get(i-1));
  }
 }
 public void createCurve(int numPoints){
  this.curvePoints = interpolationFunction(numPoints);
 }
 public ArrayList<PVector> interpolationFunction(int numPoints){
  ArrayList<PVector> cPoints = new ArrayList<PVector>();
  for(int i=0;i<knots.size()-1;i++){
   for(int j=0;j<numPoints;j++){
    float t = (float) j / (float) (numPoints-1);
    cPoints.add(splineFunction(t,i));
   }
  }
  return cPoints;
 }
 public PVector splineFunction(float t, int i){
  PVector p = new PVector();
  p.add(PVector.mult(knots.get(i),h00(t)));
  p.add(PVector.mult(tangent(i),t*h10(t)));
  p.add(PVector.mult(knots.get(i+1),h01(t)));
  p.add(PVector.mult(tangent(i+1),t*h11(t)));
  return p;
 }
 public void plotCurve(){
  for(int i=0;i<curvePoints.size();i++){
   parent.point(curvePoints.get(i).x,curvePoints.get(i).y);
  }
 }
 public void plotKnots(){
  for(int i=0;i<knots.size();i++){
   parent.rect(knots.get(i).x,knots.get(i).y,5,5);
  }
 }
 public int closestKnot(PVector p, float tolerance){
  int closest = -1;
  for(int i=0;i<knots.size();i++){
   if(parent.max(parent.abs(knots.get(i).x-p.x),
                 parent.abs(knots.get(i).y-p.y))<tolerance){
    closest = i;
   }
  }
  return closest;
 }
 public void moveKnot(int i, PVector dp){
  knots.get(i).add(dp);
 }
 public ArrayList<PVector> getKnots() {
  return knots;
 }

 public ArrayList<PVector> getCurvePoints() {
  return curvePoints;
 }
 
 public String[] createKnotInfo(){
  String[] knotInfo = new String[knots.size()];
  for(int i=0;i<knotInfo.length;i++){
   knotInfo[i] = knots.get(i).x+","+knots.get(i).y;
  }
  return knotInfo;
 }
}

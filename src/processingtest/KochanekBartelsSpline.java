/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
*
* @author yonga9121
*
*/
package processingtest;

import processing.core.PApplet;
import processing.core.PVector;

/**
 *
 * @author camilo
 */
public class KochanekBartelsSpline extends CubicHermiteSpline {

    protected float tension;
    protected float bias;
    protected float continuity;

    public KochanekBartelsSpline() {
        this.tension = 0;
        this.bias = 0;
        this.continuity = 0;
    }

    public KochanekBartelsSpline(PApplet parent) {
        super(parent);
        this.tension = 0;
        this.bias = 0;
        this.continuity = 0;
    }

    public void setParameters(float t, float b, float c) {
        this.tension = t;
        this.bias = b;
        this.continuity = c;
    }

    public float getTension() {
        return tension;
    }

    public void setTension(float tension) {
        this.tension = tension;
    }

    public float getBias() {
        return bias;
    }

    public void setBias(float bias) {
        this.bias = bias;
    }

    public float getContinuity() {
        return continuity;
    }

    public void setContinuity(float continuity) {
        this.continuity = continuity;
    }
    
    @Override
    public PVector splineFunction(float t, int i){
        PVector p = new PVector();
        p.add(PVector.mult(knots.get(i),h00(t)));
        p.add(PVector.mult(startTangent(i),t*h10(t)));
        p.add(PVector.mult(knots.get(i+1),h01(t)));
        p.add(PVector.mult(endTangent(i+1),t*h11(t)));
        return p;
    }

    
    public PVector startTangent(int i) {
        System.out.println("hola");
        if(knots.size() > 1){
            if (i == 0 ) {
                return this.d(knots.get(i), knots.get(i), knots.get(i+1));
            } else if (i == knots.size() - 1) {
                return this.d(knots.get(i-1), knots.get(i-1), knots.get(i));
            } else {
                return this.d(knots.get(i-1), knots.get(i), knots.get(i+1));
            }
        }else{
            return knots.get(i);
        }
    }
    
    public PVector endTangent(int i) {
        System.out.println("hola2");
        if(knots.size() > 1){
            if (i == 0 ) {
                return this.dplus1(knots.get(i), knots.get(i), knots.get(i+1));
            } else if (i == knots.size() - 1) {
                return this.dplus1(knots.get(i-1), knots.get(i-1), knots.get(i));
            } else {
                return this.dplus1(knots.get(i), knots.get(i+1), knots.get(i+1));
            }
        }else{
            return knots.get(i);
        }
    }
    
    
    
    public PVector d(PVector vprev, PVector v, PVector vnext){
        float a = (float)((1-this.tension)*(1+this.bias)*(1+this.continuity))/(float)2;
        PVector va = PVector.mult(new PVector(v.x - vprev.x, v.y - vprev.y), a);
        float b = (float)((1-this.tension)*(1-this.bias)*(1-this.continuity))/(float)2;
        PVector vb = PVector.mult(new PVector(vnext.x - v.x, vnext.y - v.y), b);
        return new PVector(va.x + vb.x, va.y + vb.y);
    }
    public PVector dplus1(PVector v, PVector vnext, PVector vnextnext){
        float a = (float)((1-this.tension)*(1+this.bias)*(1-this.continuity))/(float)2;
        PVector va = PVector.mult(new PVector(vnext.x - v.x, vnext.y - v.y), a);
        float b = (float)((1-this.tension)*(1-this.bias)*(1+this.continuity))/(float)2;
        PVector vb = PVector.mult(new PVector(vnextnext.x - vnext.x, vnextnext.y - vnext.y), b);
        return new PVector(va.x + vb.x, va.y + vb.y);
    }

}

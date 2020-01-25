/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballmovement;

/*
 * Abdullah El Beshlawy, Asim Bhatia, Faiz Momin
 * May 27, 2019
 * This is the circle abstract class that the other classes will implement from
 */

/**
 *
 * @author abelb6682
 */

import java.awt.Color;

public abstract class Circle {
    
    protected double x, y, radius;
    protected Color c;
    
    public Circle(double radius, double x, double y){
        this.radius = radius;
        this.x = x;
        this.y = y;
    }
    
    public double getXPos(){
        return x;
    }
    
    public void setXPos(double x){
        this.x = x;
    }
    
    public double getYPos(){
        return y;
    }
    
    public void setYPos(double y){
        this.y = y;
    }
    
    public Color getColor(){
        return c;
    }
    
    public double getRadius(){
        return radius;
    }
    
    public void setRadius(double radius){
        this.radius = radius;
    }
    
    public String toString(){
        return  "xPos: " + x +
                "\nyPos: " + y +
                "\nColor: " + c;
    }
    
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballmovement;
/*
 * Abdullah the Bershawi, Asim Bhatia, Faiz Momin
 * May 27, 2019
 * This is the ball class that implements from the circle class
 */
import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Asim Bhatia
 */

public class Ball extends Circle{
    private Image image;
    protected double radius, x, y, xSpeed, ySpeed;
    protected Color c;
    private int w;
    private int h;
    private int dx;
    private int dy;
    private boolean canPick;
    static final double startX = 425, startY = 310;
    
    public Ball(double radius, double x, double y){
        super(radius, x, y);
        loadImage();
    }
    
    public Ball(double radius, double x, double y, double xSpeed, double ySpeed){
        super(radius, x, y);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        loadImage();
    }
    
    private void loadImage() {
        ImageIcon ii = new ImageIcon("dodgeball.png");
        
        
        image = ii.getImage(); 
        
        w = image.getWidth(null);
        h = image.getHeight(null);
    }
    
    public void move() {
        x += xSpeed;
        if(x<=0 && x > -15){
            xSpeed = -xSpeed/3; //BADDDY
            x = 1;
        } else if(x>=945 && x < 965){
            xSpeed = -xSpeed/3; //BADDDY
            x = 944;
        }
        
        y += ySpeed;
        if(y<=0){
            ySpeed = -ySpeed;
        } else if(y>=615){
            ySpeed = -ySpeed;
        }
    }
    
    public int getWidth() {
        
        return w;
    }
    
    public int getHeight() {
        
        return h;
    }    

    public Image getImage() {
        return image;
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
    
    public boolean getPick(){
        return canPick;
    }
    
    public void setPick(boolean canPick){
        this.canPick = canPick;
    }
    
    public double getRadius(){
        return super.radius;
    }
    
    public void setRadius(double radius){
        this.radius = radius;
    }
    
    public Color getColor(){
        return c;
    }
    
    public void setColor(Color c){
        this.c = c;
    }
    
    public double getXSpeed(){
        return xSpeed;
    }
    
    public void setXSpeed(double xSpeed){
        this.xSpeed = xSpeed;
    }
    
    public double getYSpeed(){
        return ySpeed;
    }
    
    public void setYSpeed(double ySpeed){
        this.ySpeed = ySpeed;
    }
    
    public String toString(){
        return "Radius: " + radius +
                "\nxPos: " + x +
                "\nyPos: " + y +
                "\nColor: " + c +
                "\nxSpeed: " + xSpeed +
                "\nySpeed: " + ySpeed;
    }
}


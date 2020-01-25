package dodgeballmovement;

import static dodgeballmovement.DrawingSurface.enemy;
import static dodgeballmovement.MouseHandler.speed;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;

public class Player extends Circle {

    static int dx;
    static int dy;
    protected double x = 40;
    protected double y = 60;
    private int w;
    private int h;
    private Image image;
    public static boolean yellow = false, green = false, red = true, blue = false;
    protected String name;
    protected int balls;
    protected double radius;
    static final double startX = 225, startY = 310;
    
    public Player(double radius, double x, double y){
        super(radius, x, y);
        loadImage();
    }
    
    public Player(double radius, double x, double y, String name, int balls){
        super(radius, x, y);
        this.name = name;
        this.balls = balls;
        loadImage();
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
    
    public double getRadius(){
        return super.radius;
    }
    
    public void setRadius(double radius){
        this.radius = radius;
    }
    
    public void setYPos(double y){
        this.y = y;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public int getBalls(){
        return balls;
    }
    
    public void setBalls(int balls){
        this.balls = balls;
    }
    
    public String toString(){
        return  "\nxPos: " + x +
                "\nyPos: " + y +
                "\nColor: " + c +
                "\nName: " + name +
                "\nBalls: " + balls;
    }

    public void loadImage() {
        ImageIcon ii;
        if (yellow) {
            ii = new ImageIcon("yellow.png");
        } else if (green){
            ii = new ImageIcon("green.png");
        } else if (red){
            ii = new ImageIcon("red.png");
        } else {
            ii = new ImageIcon("blue.png");
        }
        
        image = ii.getImage(); 
        
        w = image.getWidth(null);
        h = image.getHeight(null);
    }
    
    public void move() {
        if(x > 5&&x<445){
            x += dx;
        } else if(x<=5){
            x = 6;
        } else if(x>=445){
            x = 444;
        }
        
        if(y > 5&&y<615){
            y += dy;
        } else if(y<=5){
            y = 6;
        } else if(y>=615){
            y = 614;
        }
    }

    public Image getImage() {
        return image;
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        
        if (DrawingSurface.game) {
            
        }
        if (key == KeyEvent.VK_A) {
            dx = -2;
        }

        if (key == KeyEvent.VK_D) {
            dx = 2;
        }

        if (key == KeyEvent.VK_W) {
            dy = -2;
        }

        if (key == KeyEvent.VK_S) {
            dy = 2;
        }
    }

    public void keyReleased(KeyEvent e) {
        
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A) {
            dx = 0;
        }

        if (key == KeyEvent.VK_D) {
            dx = 0;
        }

        if (key == KeyEvent.VK_W) {
            dy = 0;
        }

        if (key == KeyEvent.VK_S) {
            dy = 0;
        }
        
        if (key == KeyEvent.VK_P) {
            if (DrawingSurface.player.getBalls()>0) {
                if (x >= 400 && x <= 550 && y >= 600 && y <= 650) {
                    DrawingSurface.menu = true;
                    DrawingSurface.gameOver = false;
                    DrawingSurface.hitE = 0;
                    DrawingSurface.hit = 0;
                } else if (x >= 600 && x <= 750 && y >= 600 && y <= 650) {
                    DrawingSurface.game = true;
                    DrawingSurface.gameOver = false;
                    DrawingSurface.hitE = 0;
                    DrawingSurface.hit = 0;
                }
                
                
                //??
            if (DrawingSurface.game) {

                if (DrawingSurface.player.getBalls() > 0) {
                DrawingSurface.player.setBalls(DrawingSurface.player.getBalls() - 1);
                DrawingSurface.ball1.setPick(false);
                DrawingSurface.ball1.setXPos(DrawingSurface.player.getXPos());
                DrawingSurface.ball1.setYPos(DrawingSurface.player.getYPos());

                   if (DrawingSurface.enemy.diff.equalsIgnoreCase("god")) {
                    DrawingSurface.randX = Math.random() * 395 + 530;
                    DrawingSurface.randY = Math.random() * 600 + 10;

                    DrawingSurface.xDifHard = DrawingSurface.randX - DrawingSurface.enemy.getXPos();
                    DrawingSurface.yDifHard = DrawingSurface.randY - DrawingSurface.enemy.getYPos();

                    DrawingSurface.ratioHard = Math.sqrt(Math.pow(DrawingSurface.xDifHard, 2) + Math.pow(DrawingSurface.yDifHard, 2));
                    DrawingSurface.enemy.setXSpeed(DrawingSurface.speed * DrawingSurface.xDifHard / DrawingSurface.ratioHard);
                    DrawingSurface.enemy.setYSpeed(DrawingSurface.speed * DrawingSurface.yDifHard / DrawingSurface.ratioHard);
                    System.out.println(DrawingSurface.enemy.getXSpeed());
                }
                }
            }
                
            if ((Math.abs((enemy.getXPos() + enemy.getRadius()) - (DrawingSurface.ball1.getXPos() + DrawingSurface.ball1.getRadius())) <= (enemy.getRadius() + DrawingSurface.ball1.getRadius())) && (Math.abs((enemy.getYPos() + enemy.getRadius()) - (DrawingSurface.ball1.getYPos() + DrawingSurface.ball1.getRadius())) <= (enemy.getRadius() + DrawingSurface.ball1.getRadius()))) {
            if (DrawingSurface.ball1.getXSpeed() != 0 && DrawingSurface.ball1.getYSpeed() != 0 && Math.sqrt(Math.pow(DrawingSurface.ball1.getXSpeed(), 2) + Math.pow(DrawingSurface.ball1.getYSpeed(), 2)) < 9.2) { //if enemy gets hit
                DrawingSurface.hitE++;

                DrawingSurface.player.setXPos(Player.startX);
                DrawingSurface.player.setYPos(Player.startY);
                System.out.println(enemy.getXPos());
                enemy.setXPos(Enemy.startX);
                System.out.println(Enemy.startX);
                enemy.setYPos(Enemy.startY);
                enemy.setXSpeed(0);
                enemy.setYSpeed(0);
                DrawingSurface.randX = enemy.getXPos();
                DrawingSurface.randY = enemy.getYPos();
            }
        }
            
                DrawingSurface.player.setBalls(0);
                System.out.println(MouseHandler.speed);
                DrawingSurface.ball1.xSpeed = 10;
                DrawingSurface.ball1.x = DrawingSurface.player.getXPos();
                DrawingSurface.ball1.y = DrawingSurface.player.getYPos();
            }
            
        }
    }
}
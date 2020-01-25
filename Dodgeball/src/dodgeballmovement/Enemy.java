package dodgeballmovement;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;

public class Enemy extends Circle {

    private int dx;
    private int dy;
    protected double x = 40;
    protected double y = 60;
    private int w;
    private int h;
    private Image image;
    private boolean yellow = false, green = false, red = true, blue = false;
    protected String name;
    protected int balls;
    protected double radius;
    protected String diff = "";
    static final double startX = 725, startY = 310;
    protected double xSpeed, ySpeed;

    public Enemy(double radius, double x, double y) {
        super(radius, x, y);
        loadImage();
    }

    public Enemy(double radius, double x, double y, String name, int balls) {
        super(radius, x, y);
        this.name = name;
        this.balls = balls;
        loadImage();
    }

    public double getXPos() {
        return x;
    }

    public void setXPos(double x) {
        this.x = x;
    }

    public double getYPos() {
        return y;
    }

    public double getRadius() {
        return super.radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setYPos(double y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDifficulty() {
        return diff;
    }

    public void setDifficulty(String diff) {
        this.diff = diff;
    }

    public int getBalls() {
        return balls;
    }

    public void setBalls(int balls) {
        this.balls = balls;
    }

    public double getXSpeed() {
        return xSpeed;
    }

    public void setXSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    public double getYSpeed() {
        return ySpeed;
    }

    public void setYSpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }

    public String toString() {
        return "\nxPos: " + x
                + "\nyPos: " + y
                + "\nColor: " + c
                + "\nName: " + name
                + "\nBalls: " + balls;
    }

    private void loadImage() {
        ImageIcon ii = new ImageIcon("black.png");

        image = ii.getImage();

        w = image.getWidth(null);
        h = image.getHeight(null);
    }

    public void move() {
        x += xSpeed;
        if (x > 5 && x < 945) {
            x += dx;
        } else if (x <= 5) {
            x = 6;
        } else if (x >= 945) {
            x = 944;
        }
        y += ySpeed;
        if (y > 5 && y < 615) {
            y += dy;
        } else if (y <= 5) {
            y = 6;
            ySpeed = -ySpeed;
        } else if (y >= 615) {
            y = 614;
            ySpeed = -ySpeed;
        }
    }

    public Image getImage() {
        return image;
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

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
    }
}

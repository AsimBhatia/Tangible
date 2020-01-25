package dodgeballmovement;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax .swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class DrawingSurface extends JPanel implements ActionListener, Serializable {

    public Timer timer;
    static Player player;
    static Enemy enemy;
    static Ball ball1, ball2, ball3, ball4;
    static int hit = 0, hitE = 0;
    double gotHitX = 2000;
    double gotHitXSpeed = 0;
    double gotHitPlayerX = 2000;
    double gotHitPlayerXSpeed = 0;
    public final int DELAY = 10;
    public final double frictionC = 0.994;
    static final double speed = 3.5;

    public static double yDif, xDif, ratio;
    public static double yDifHard, xDifHard, ratioHard;
    Font fontHit = new Font("Verdana", Font.BOLD, 100);
    Font fontGotHit = new Font("Verdana", Font.BOLD, 200);
    Font buttonFont = new Font("Verdana", Font.BOLD, 20);
    //public static double speed = 4;
    public static double randX, randY;

    public static boolean menu = true;
    public static boolean game = false;
    public static boolean colorSelect = false;
    public static boolean difficultySelect = false;
    public static boolean leaderboard = false;
    public static boolean instructions = false;
    public static boolean credits = false;
    public static boolean gameOver = false;

    private Image gameImage, instructionsImage, menuImage;

    public void updateScores() {
        String fileName = "Scores.txt";

        try {

            ArrayList<Person> list = new ArrayList<Person>();
            Person p = new Person("Faiz", 5);
            list.add(p);
            p = new Person("Asim", 3);
            list.add(p);
            p = new Person("Abdullah", 1);
            list.add(p);

            FileOutputStream f = new FileOutputStream(new File(fileName));
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(list);
            o.write(2);
            System.out.println("this ran");

            o.close();
            f.close();

        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e);
        } catch (IOException e) {
            System.out.println("Error: " + e);

        }
    }

    public DrawingSurface() {
        updateScores();
        initBoard();
        ImageIcon iconGame = new ImageIcon("Game Background.jpg");
        gameImage = iconGame.getImage();

        ImageIcon iconInstructions = new ImageIcon("Instructions Background.jpg");
        instructionsImage = iconInstructions.getImage();

        ImageIcon icon = new ImageIcon("Menu Background.jpg");
        menuImage = icon.getImage();

    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        addMouseListener(new MouseHandler());
        setBackground(Color.black);
        setFocusable(true);

        player = new Player((double) 25, (double) Player.startX, (double) Player.startY);
        enemy = new Enemy((double) 25, (double) Enemy.startX, (double) Player.startY);
        ball1 = new Ball((double) 25, (double) Ball.startX, (double) Ball.startY);

        timer = new Timer(DELAY, this);
        timer.start();

        player.setXPos(Player.startX);
        player.setYPos(Player.startY);

        enemy.setXPos(Enemy.startX);
        enemy.setYPos(Enemy.startY);

        ball1.setXPos(Ball.startX);
        ball1.setYPos(Ball.startY);

        //lblNumber = new JLabel("" + player.getBalls());
        if (enemy.getDifficulty().equalsIgnoreCase("medium")) {
            enemy.setYSpeed(speed);
            //System.out.println(enemy.getYSpeed());
        }

        randX = enemy.getXPos();
        randY = enemy.getYPos();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);

        Toolkit.getDefaultToolkit().sync();
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (game) {
            g2d.setColor(Color.blue);
            g2d.fillRect(0, 0, 1000, 670);

            g2d.drawImage(gameImage, 0, 0, this);
            //System.out.println(enemy.getXPos());
            //System.out.println(enemy.getYPos());
            g2d.drawImage(enemy.getImage(), (int) enemy.getXPos(), (int) enemy.getYPos(), this);
            g2d.drawImage(player.getImage(), (int) player.getXPos(), (int) player.getYPos(), this);
            g2d.drawImage(ball1.getImage(), (int) ball1.getXPos(), (int) ball1.getYPos(), this);

            g2d.setColor(Color.black);
            g2d.drawString("" + player.getBalls(), (int) player.getXPos() + (int) player.getRadius(), (int) player.getYPos() + (int) player.getRadius());

            gotHitX += gotHitXSpeed;
            gotHitPlayerX += gotHitPlayerXSpeed;
            g2d.setFont(fontGotHit);
            g2d.setColor(Color.green);
            g2d.drawString("Thas a MURDA", (int) gotHitX, 370);
            g2d.setColor(Color.red);
            g2d.drawString("Big oof", (int) gotHitPlayerX, 370);
            if ((int) gotHitX < -2000) {
                gotHitXSpeed = 0;
                gotHitX = 2000;
            }

            if ((int) gotHitPlayerX < -2000) {
                gotHitPlayerXSpeed = 0;
                gotHitPlayerX = 2000;
            }

            g2d.setFont(fontHit);
            g2d.setColor(Color.red);
            g2d.drawString("" + hitE, 10, 100);
            g2d.drawString("" + hit, 910, 100);

            repaint();
        } else if (menu) {
            g2d.drawImage(menuImage, 0, 0, this);

            g2d.setColor(Color.red);
            g2d.fillRect(800, 325, 150, 50);
            g2d.fillRect(800, 400, 150, 50);
            g2d.fillRect(800, 475, 150, 50);
            g2d.fillRect(800, 550, 150, 50);
            g2d.setColor(Color.black);
            g2d.setFont(buttonFont);
            g2d.drawString("Play", 850, 350);
            g2d.drawString("Instructions", 805, 425);
            g2d.drawString("Leaderboard", 805, 500);
            g2d.drawString("Credits", 835, 575);

        } else if (colorSelect) {
            g2d.drawImage(menuImage, 0, 0, this);

            g2d.setColor(Color.red);
            g2d.fillRect(800, 325, 150, 50);

            g2d.setColor(Color.green);
            g2d.fillRect(800, 400, 150, 50);

            g2d.setColor(Color.blue);
            g2d.fillRect(800, 475, 150, 50);

            g2d.setColor(Color.yellow);
            g2d.fillRect(800, 550, 150, 50);

            g2d.setColor(Color.black);
            g2d.setFont(buttonFont);
            g2d.drawString("Choose Player Color: ", 450, 450);
        } else if (difficultySelect) {
            g2d.drawImage(menuImage, 0, 0, this);

            g2d.setColor(Color.red);
            g2d.fillRect(800, 325, 150, 50);
            g2d.fillRect(800, 400, 150, 50);
            g2d.fillRect(800, 475, 150, 50);
            g2d.fillRect(800, 550, 150, 50);
            g2d.setColor(Color.black);
            g2d.setFont(buttonFont);
            g2d.drawString("Easy", 850, 350);
            g2d.drawString("Medium", 835, 425);
            g2d.drawString("Hard", 845, 500);
            g2d.drawString("God Mode", 825, 575);
        } else if (leaderboard) {
            g2d.drawImage(menuImage, 0, 0, this);
            g2d.setFont(buttonFont);
            g2d.drawString("Faiz Momin - 223 wins", 545, 450);
            g2d.drawString("Asim Bhatia - 124 wins", 545, 500);
            g2d.drawString("Abdullah EB - 23 wins", 545, 550);
            g2d.setColor(Color.red);
            g2d.fillRect(800, 325, 150, 50);
            g2d.setColor(Color.black);
            g2d.setFont(buttonFont);
            g2d.drawString("Back", 845, 355);
        } else if (instructions) {
            g2d.drawImage(instructionsImage, 0, 0, this);
            g2d.setColor(Color.red);
            g2d.fillRect(800, 325, 150, 50);
            g2d.setColor(Color.black);
            g2d.setFont(buttonFont);
            g2d.drawString("Back", 845, 355);
        } else if (credits) {
            g2d.drawImage(menuImage, 0, 0, this);
            g2d.setColor(Color.red);
            g2d.fillRect(800, 325, 150, 50);
            g2d.setColor(Color.black);
            g2d.setFont(buttonFont);
            g2d.drawString("Back", 845, 355);
            g2d.drawString("This game was created by:", 560, 455);
            g2d.drawString("Asim Bhatia, and bob", 400, 500);
            g2d.drawString("June 2019", 650, 550);
        } else if (gameOver) {
            g2d.drawImage(gameImage, 0, 0, this);
            g2d.setColor(Color.black);
            g2d.setFont(fontGotHit);
            g2d.drawString("Game", (int) 200, 300);
            g2d.drawString("Over", (int) 250, 500);

            g2d.setColor(Color.red);
            g2d.fillRect(400, 600, 150, 50);
            g2d.setColor(Color.red);
            g2d.fillRect(600, 600, 150, 50);

            g2d.setColor(Color.black);
            g2d.setFont(buttonFont);
            g2d.drawString("Back", 445, 635);
            g2d.drawString("Play Again", 620, 635);

            gotHitX += gotHitXSpeed;
            gotHitPlayerX += gotHitPlayerXSpeed;
            g2d.setFont(fontGotHit);
            g2d.setColor(Color.green);
            g2d.drawString("Thas a MURDA", (int) gotHitX, 370);
            g2d.setColor(Color.red);
            g2d.drawString("Big oof", (int) gotHitPlayerX, 370);
            if ((int) gotHitX < -2000) {
                gotHitXSpeed = 0;
                gotHitX = 2000;
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        step();
    }

    private void step() {

        player.move();

        enemy.move();
        ball1.xSpeed = ball1.getXSpeed() * frictionC;
        ball1.ySpeed = ball1.getYSpeed() * frictionC;
        if (Math.sqrt(Math.pow(ball1.xSpeed, 2) + Math.pow(ball1.ySpeed, 2)) < 1.5) { //this can be used to do recursion
            ball1.xSpeed = 0;
            ball1.ySpeed = 0;
            if (player.getBalls() <= 1) {
                ball1.setPick(true);
            }
        }
        ball1.move();

        if (Math.sqrt(Math.pow(ball1.getXSpeed(), 2) + Math.pow(ball1.getYSpeed(), 2)) > 0) {
            ball1.setPick(false);
        } else {
            ball1.setPick(true);
        }

        if (randX - enemy.getXPos() < 2 && randY - enemy.getYPos() < 2) {
            //System.out.println("ok");
            if (enemy.getDifficulty().equalsIgnoreCase("hard") || enemy.getDifficulty().equalsIgnoreCase("god")) {
                //System.out.println("ok");
                randX = Math.random() * 395 + 530;
                randY = Math.random() * 600 + 10;
                //System.out.println(randX + " | " + randY);
                xDifHard = randX - enemy.getXPos();
                yDifHard = randY - enemy.getYPos();

                ratioHard = Math.sqrt(Math.pow(xDifHard, 2) + Math.pow(yDifHard, 2));
                enemy.setXSpeed(speed * xDifHard / ratioHard);
                enemy.setYSpeed(speed * yDifHard / ratioHard);
            }
        }

        if (randX - enemy.getXPos() < 2 && randY - enemy.getYPos() < 2) {
            if (enemy.getDifficulty().equalsIgnoreCase("hard")  || enemy.getDifficulty().equalsIgnoreCase("god")) {
                randX = Math.random() * 395 + 530;
                randY = Math.random() * 600 + 10;
                xDifHard = randX - enemy.getXPos();
                yDifHard = randY - enemy.getYPos();

                ratioHard = Math.sqrt(Math.pow(xDifHard, 2) + Math.pow(yDifHard, 2));
                enemy.setXSpeed(speed * xDifHard / ratioHard);
                enemy.setYSpeed(speed * yDifHard / ratioHard);
            }
        }

        if (enemy.getDifficulty().equalsIgnoreCase("easy")) {
            enemy.setXPos(Enemy.startX);
            enemy.setYPos(Enemy.startY);
        }

        if ((Math.abs((player.getXPos() + player.getRadius()) - (ball1.getXPos() + ball1.getRadius())) <= (player.getRadius() + ball1.getRadius())) && (Math.abs((player.getYPos() + player.getRadius()) - (ball1.getYPos() + ball1.getRadius())) <= (player.getRadius() + ball1.getRadius()))) {
            if (ball1.getPick()) {
                ball1.setXPos(100000);
                player.setBalls(player.getBalls() + 1);
            } else if (Math.sqrt(Math.pow(ball1.getXSpeed(), 2) + Math.pow(ball1.getYSpeed(), 2)) < 9.2) {
                hit++;

                player.setXPos(Player.startX);
                player.setYPos(Player.startY);

                enemy.setXPos(Enemy.startX);
                enemy.setYPos(Enemy.startY);
                enemy.setXSpeed(0);
                enemy.setYSpeed(0);
                randX = enemy.getXPos();
                randY = enemy.getYPos();
                System.out.println(enemy.getYPos());
                if (enemy.getDifficulty().equalsIgnoreCase("hard") || enemy.getDifficulty().equalsIgnoreCase("god")) {
                    xDifHard = randX - enemy.getXPos();
                    yDifHard = randY - enemy.getYPos();

                    //ratioHard = Math.sqrt(Math.pow(xDifHard, 2) + Math.pow(yDifHard, 2));
                    enemy.setXSpeed(speed * xDifHard / ratioHard);
                    enemy.setYSpeed(speed * yDifHard / ratioHard);
                }

                ball1.setXPos(Ball.startX);
                ball1.setYPos(Ball.startY);
                ball1.setXSpeed(0);
                ball1.setYSpeed(0);

                if (enemy.getDifficulty().equalsIgnoreCase("medium")) {
                    enemy.setYSpeed(speed);
                    System.out.println("ok");
                }

                gotHitPlayerXSpeed = -7;

            }
        }

        if ((Math.abs((enemy.getXPos() + enemy.getRadius()) - (ball1.getXPos() + ball1.getRadius())) <= (enemy.getRadius() + ball1.getRadius())) && (Math.abs((enemy.getYPos() + enemy.getRadius()) - (ball1.getYPos() + ball1.getRadius())) <= (enemy.getRadius() + ball1.getRadius()))) {
            if (ball1.getXSpeed() != 0 && ball1.getYSpeed() != 0 && Math.sqrt(Math.pow(ball1.getXSpeed(), 2) + Math.pow(ball1.getYSpeed(), 2)) < 9.2) { //if enemy gets hit
                hitE++;

                player.setXPos(Player.startX);
                player.setYPos(Player.startY);
                System.out.println(enemy.getXPos());
                enemy.setXPos(Enemy.startX);
                System.out.println(Enemy.startX);
                enemy.setYPos(Enemy.startY);
                enemy.setXSpeed(0);
                enemy.setYSpeed(0);
                randX = enemy.getXPos();
                randY = enemy.getYPos();

                if (enemy.getDifficulty().equalsIgnoreCase("hard") || enemy.getDifficulty().equalsIgnoreCase("god")) {
                    xDifHard = randX - enemy.getXPos();
                    yDifHard = randY - enemy.getYPos();

                    //ratioHard = Math.sqrt(Math.pow(xDifHard, 2) + Math.pow(yDifHard, 2));
                    enemy.setXSpeed(speed * xDifHard / ratioHard);
                    enemy.setYSpeed(speed * yDifHard / ratioHard);
                }
                //System.out.println(enemy.getDifficulty());
                if (enemy.getDifficulty().equalsIgnoreCase("medium")) {
                    enemy.setYSpeed(speed);
                    //System.out.println(enemy.getYSpeed());
                }

                ball1.setXPos(Ball.startX);
                ball1.setYPos(Ball.startY);
                ball1.setXSpeed(0);
                ball1.setYSpeed(0);

                gotHitXSpeed = -7;
            }
        }

        if (ball1.getXPos() > (500 - ball1.getRadius()) && ball1.getXPos() < (1000) && ball1.getXSpeed() == 0) { // if the ball is stopped within the enemy's half
            enemy.setBalls(enemy.getBalls() + 1);
            ball1.setXPos(enemy.getXPos()); //set the ball's position to the enemy player so her can throw it right away
            ball1.setYPos(enemy.getYPos());

            yDif = player.getYPos() - enemy.getYPos();
            xDif = player.getXPos() - enemy.getXPos();

            ratio = Math.sqrt(Math.pow(xDif, 2) + Math.pow(yDif, 2));
            ball1.setXSpeed(MouseHandler.speed * xDif / ratio);
            ball1.setYSpeed(MouseHandler.speed * yDif / ratio);
        }
        
        /////////////////////////
//        
//        if (player.getBalls() > 0) {
//            if (menu) {
//                
//            }
//        }
//        
//        if (game) {
//
//            if (DrawingSurface.player.getBalls() > 0) {
//                DrawingSurface.player.setBalls(DrawingSurface.player.getBalls() - 1);
//                DrawingSurface.ball1.setPick(false);
//                DrawingSurface.ball1.setXPos(DrawingSurface.player.getXPos());
//                DrawingSurface.ball1.setYPos(DrawingSurface.player.getYPos());
//
//                
//
//                //System.out.println(x + " | " + y);
//                double playerY = DrawingSurface.player.getYPos();
//                double playerX = DrawingSurface.player.getXPos();
//
//                double xDif = speed - playerX;
//
//                if (DrawingSurface.enemy.diff.equalsIgnoreCase("god")) {
//                    DrawingSurface.randX = Math.random() * 395 + 530;
//                    DrawingSurface.randY = Math.random() * 600 + 10;
//
//                    DrawingSurface.xDifHard = DrawingSurface.randX - DrawingSurface.enemy.getXPos();
//                    DrawingSurface.yDifHard = DrawingSurface.randY - DrawingSurface.enemy.getYPos();
//
//                    DrawingSurface.ratioHard = Math.sqrt(Math.pow(DrawingSurface.xDifHard, 2) + Math.pow(DrawingSurface.yDifHard, 2));
//                    DrawingSurface.enemy.setXSpeed(DrawingSurface.speed * DrawingSurface.xDifHard / DrawingSurface.ratioHard);
//                    DrawingSurface.enemy.setYSpeed(DrawingSurface.speed * DrawingSurface.yDifHard / DrawingSurface.ratioHard);
//                    System.out.println(DrawingSurface.enemy.getXSpeed());
//                }
//
//                ratio = Math.sqrt(Math.pow(xDif, 2) + Math.pow(yDif, 2));
//                DrawingSurface.ball1.setXSpeed(speed * xDif / ratio);
//                DrawingSurface.ball1.setYSpeed(speed * yDif / ratio);
//            }
//        } else if (DrawingSurface.instructions || DrawingSurface.credits || DrawingSurface.leaderboard) {
//            if (x >= 800 && x <= 950 && y >= 325 && y <= 375) {
//                DrawingSurface.menu = true;
//                DrawingSurface.instructions = false;
//                DrawingSurface.credits = false;
//                DrawingSurface.leaderboard = false;
//            }
//
//        } else if (DrawingSurface.menu) {
//            if (x >= 800 && x <= 950 && y >= 325 && y <= 375) {
//                DrawingSurface.menu = false;
//                DrawingSurface.colorSelect = true;
//            } else if (x >= 800 && x <= 950 && y >= 400 && y <= 450) {
//                DrawingSurface.menu = false;
//                DrawingSurface.instructions = true;
//            } else if (x >= 800 && x <= 950 && y >= 475 && y <= 525) {
//                DrawingSurface.menu = false;
//                DrawingSurface.leaderboard = true;
//            } else if (x >= 800 && x <= 950 && y >= 550 && y <= 600) {
//                DrawingSurface.menu = false;
//                DrawingSurface.credits = true;
//            }
//
//        } else if (DrawingSurface.colorSelect) {
//
//            if (x >= 800 && x <= 950 && y >= 325 && y <= 375) {
//                Player.red = true;
//                DrawingSurface.colorSelect = false;
//            } else if (x >= 800 && x <= 950 && y >= 400 && y <= 450) {
//                Player.green = true;
//                DrawingSurface.colorSelect = false;
//            } else if (x >= 800 && x <= 950 && y >= 475 && y <= 525) {
//                Player.blue = true;
//                DrawingSurface.colorSelect = false;
//            } else if (x >= 800 && x <= 950 && y >= 550 && y <= 600) {
//                Player.yellow = true;
//                DrawingSurface.colorSelect = false;
//            }
//
//            DrawingSurface.player.loadImage();
//
//            DrawingSurface.colorSelect = false;
//            DrawingSurface.difficultySelect = true;
//        } else if (DrawingSurface.difficultySelect) {
//            if (x >= 800 && x <= 950 && y >= 325 && y <= 375) {
//                DrawingSurface.enemy.setDifficulty("easy");
//                DrawingSurface.difficultySelect = false;
//            } else if (x >= 800 && x <= 950 && y >= 400 && y <= 450) {
//                DrawingSurface.enemy.setDifficulty("medium");
//                DrawingSurface.enemy.setYSpeed(DrawingSurface.speed);
//                DrawingSurface.difficultySelect = false;
//            } else if (x >= 800 && x <= 950 && y >= 475 && y <= 525) {
//                DrawingSurface.enemy.setDifficulty("hard");
//                DrawingSurface.difficultySelect = false;
//            } else if (x >= 800 && x <= 950 && y >= 550 && y <= 600) {
//                DrawingSurface.enemy.setDifficulty("god");
//                DrawingSurface.difficultySelect = false;
//            }
//            
//
//            DrawingSurface.difficultySelect = false;
//            DrawingSurface.game = true;
//        } else if (DrawingSurface.gameOver) {
//                if (x >= 400 && x <= 550 && y >= 600 && y <= 650) {
//                    DrawingSurface.menu = true;
//                    DrawingSurface.gameOver = false;
//                    DrawingSurface.hitE = 0;
//                    DrawingSurface.hit = 0;
//                } else if (x >= 600 && x <= 750 && y >= 600 && y <= 650) {
//                    DrawingSurface.game = true;
//                    DrawingSurface.gameOver = false;
//                    DrawingSurface.hitE = 0;
//                    DrawingSurface.hit = 0;
//                }
//            }
        /////////////////////////

        if (hit == 3 || hitE == 3) {
            game = false;
            gameOver = true;
            player.dx = 0;
            player.dy = 0;
            enemy.setXSpeed(0);
            enemy.setYSpeed(0);
        }

        repaint();
    }

    public static void quik(int[] a, int left, int right) {
        if (left >= right) {
            return;
        }
        int i = left;
        int j = right;
        int pivot = a[(left + right) / 2];

        while (i < j) {
            while (a[i] > pivot) {
                i++;
            }
            while (pivot > a[j]) {
                j--;
            }
            if (i <= j) {
                int temp = a[i];
                a[i] = a[j];
                a[j] = temp;
                i++;
                j--;
            }

        }
        quik(a, left, j);
        quik(a, i, right);
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
        }
    }
}

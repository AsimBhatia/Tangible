/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballmovement;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author Acim
 */
public class MouseHandler implements MouseListener {

    private double x, y;
    private double xDif, yDif, ratio;
    private double playerX, playerY;
    static final double speed = 10;

    @Override
    public void mouseClicked(MouseEvent e) {
        //System.out.println("ok");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //System.out.println("ok");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //System.out.println("ok");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        if (DrawingSurface.game) {

            if (DrawingSurface.player.getBalls() > 0) {
                DrawingSurface.player.setBalls(DrawingSurface.player.getBalls() - 1);
                DrawingSurface.ball1.setPick(false);
                DrawingSurface.ball1.setXPos(DrawingSurface.player.getXPos());
                DrawingSurface.ball1.setYPos(DrawingSurface.player.getYPos());

                x = e.getX();
                y = e.getY();

                //System.out.println(x + " | " + y);
                playerY = DrawingSurface.player.getYPos();
                playerX = DrawingSurface.player.getXPos();

                xDif = x - playerX;
                yDif = y - playerY;

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

                ratio = Math.sqrt(Math.pow(xDif, 2) + Math.pow(yDif, 2));
                DrawingSurface.ball1.setXSpeed(speed * xDif / ratio);
                DrawingSurface.ball1.setYSpeed(speed * yDif / ratio);
            }
        } else if (DrawingSurface.instructions || DrawingSurface.credits || DrawingSurface.leaderboard) {
            if (x >= 800 && x <= 950 && y >= 325 && y <= 375) {
                DrawingSurface.menu = true;
                DrawingSurface.instructions = false;
                DrawingSurface.credits = false;
                DrawingSurface.leaderboard = false;
            }

        } else if (DrawingSurface.menu) {
            if (x >= 800 && x <= 950 && y >= 325 && y <= 375) {
                DrawingSurface.menu = false;
                DrawingSurface.colorSelect = true;
            } else if (x >= 800 && x <= 950 && y >= 400 && y <= 450) {
                DrawingSurface.menu = false;
                DrawingSurface.instructions = true;
            } else if (x >= 800 && x <= 950 && y >= 475 && y <= 525) {
                DrawingSurface.menu = false;
                DrawingSurface.leaderboard = true;
            } else if (x >= 800 && x <= 950 && y >= 550 && y <= 600) {
                DrawingSurface.menu = false;
                DrawingSurface.credits = true;
            }

        } else if (DrawingSurface.colorSelect) {

            if (x >= 800 && x <= 950 && y >= 325 && y <= 375) {
                Player.red = true;
                DrawingSurface.colorSelect = false;
            } else if (x >= 800 && x <= 950 && y >= 400 && y <= 450) {
                Player.green = true;
                DrawingSurface.colorSelect = false;
            } else if (x >= 800 && x <= 950 && y >= 475 && y <= 525) {
                Player.blue = true;
                DrawingSurface.colorSelect = false;
            } else if (x >= 800 && x <= 950 && y >= 550 && y <= 600) {
                Player.yellow = true;
                DrawingSurface.colorSelect = false;
            }

            DrawingSurface.player.loadImage();

            DrawingSurface.colorSelect = false;
            DrawingSurface.difficultySelect = true;
        } else if (DrawingSurface.difficultySelect) {
            if (x >= 800 && x <= 950 && y >= 325 && y <= 375) {
                DrawingSurface.enemy.setDifficulty("easy");
                DrawingSurface.difficultySelect = false;
            } else if (x >= 800 && x <= 950 && y >= 400 && y <= 450) {
                DrawingSurface.enemy.setDifficulty("medium");
                DrawingSurface.enemy.setYSpeed(DrawingSurface.speed);
                DrawingSurface.difficultySelect = false;
            } else if (x >= 800 && x <= 950 && y >= 475 && y <= 525) {
                DrawingSurface.enemy.setDifficulty("hard");
                DrawingSurface.difficultySelect = false;
            } else if (x >= 800 && x <= 950 && y >= 550 && y <= 600) {
                DrawingSurface.enemy.setDifficulty("god");
                DrawingSurface.difficultySelect = false;
            }
            

            DrawingSurface.difficultySelect = false;
            DrawingSurface.game = true;
        } else if (DrawingSurface.gameOver) {
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
            }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        //System.out.println("ok");
    }

}

// RegNo. 2000878

package game;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import static game.Constants.*;

public class View extends JComponent {
    private Game game;
    Image im;
    Image prop;
    int randX, randY;
    int propHeight, propWidth;
    AffineTransform bgTransf;

    public View(Game game) {
        this.game = game;
    }

    // fits background image to the frame dimensions
    public void scaleImage() {
        double imWidth = im.getWidth(null);
        double imHeight = im.getHeight(null);
        double stretchx = FRAME_WIDTH / imWidth;
        double stretchy = FRAME_HEIGHT / imHeight;
        bgTransf = new AffineTransform();
        bgTransf.scale(stretchx, stretchy);
    }

    // sets background for current game, randomly from a choice of 5
    public void setBackground() {
        int num = (int) Math.ceil(Math.random() * 5) - 1;
        im = BACKGROUNDIMAGES.get(num);
    }

    // chooses a random prop to put in background
    public void spawnProps() {
        //int num = (int) (Math.random() * 1000);
        int num = 2;
        randX = (int) (Math.random() * FRAME_WIDTH - 30);
        randY = (int) (Math.random() * FRAME_HEIGHT - 30);
        if (num > 900) {
            prop = PROPIMAGES.get(0);
            propHeight = 300;
            propWidth = 300;
        }
        else if (num > 800) {
            prop = PROPIMAGES.get(1);
            propHeight = 400;
            propWidth = 400;
        }
        else if (num > 700) {
            prop = PROPIMAGES.get(2);
            propHeight = 500;
            propWidth = 500;
        }
        else if (num > 600) {
            prop = PROPIMAGES.get(3);
            propHeight = 400;
            propWidth = 450;
        }
        else if (num > 500) {
            prop = PROPIMAGES.get(4);
            propHeight = 200;
            propWidth = 200;
        }
        else if (num > 400) {
            prop = PROPIMAGES.get(5);
            propHeight = 400;
            propWidth = 200;
        }
        else if (num > 300) {
            prop = PROPIMAGES.get(6);
            propHeight = 1000;
            propWidth = 1000;
        }
        else if (num <= 2) {
            prop = AMONGUS;
            propHeight = 70;
            propWidth = 60;
        }
    }

    public void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;
        if (game.start) { // start menu
            im = MENU1;
            scaleImage();
            g.drawImage(im, bgTransf, null);
            g.setColor(Color.YELLOW);
            g.setFont(new Font("dialog", Font.BOLD, 200));
            g.drawString("Asteroids", 135, 290);
            g.setFont(new Font("dialog", Font.PLAIN, 30));
            g.drawString("1) Play Normal Difficulty", 440, 480);
            g.drawString("2) Play Hard Difficulty", 455, 530);
        }
        else if (game.paused) { // pause menu
            im = MENU2;
            scaleImage();
            g.drawImage(im, bgTransf, null);
            g.setColor(Color.YELLOW);
            g.setFont(new Font("dialog", Font.BOLD, 180));
            g.drawString("Paused", 280, 300);
            g.setFont(new Font("dialog", Font.PLAIN, 30));
            g.drawString("Enter) Continue", 490, 490);
            g.drawString("Esc) End Game", 490, 540);
        }
        else if (game.end) { // game over menu
            im = MENU3;
            scaleImage();
            g.drawImage(im, bgTransf, null);
            g.setColor(Color.YELLOW);
            g.setFont(new Font("dialog", Font.BOLD, 180));
            g.drawString("Game Over", 110, 260);
            g.setFont(new Font("dialog", Font.PLAIN, 30));
            g.drawString("Score: " + Game.getScore(), 600, 410);
            g.drawString("Level: " + Game.getLevel(), 450, 410);
            if (game.newHighScore) {
                g.setFont(new Font("dialog", Font.PLAIN, 60));
                g.drawString("NEW HIGHSCORE!", 340, 350);
                g.setFont(new Font("dialog", Font.PLAIN, 30));
            }
            g.drawString("1) Play Normal Difficulty", 440, 480);
            g.drawString("2) Play Hard Difficulty", 455, 530);
            g.drawString("Esc) Quit Game", 490, 580);
        }
        else { // gameplay screen
            scaleImage();
            g.drawImage(im, bgTransf, null);
            g.drawImage(prop, randX, randY, propWidth, propHeight, null);
            synchronized (Game.class) {
                for (GameObject object : game.objects) {
                    object.draw(g);
                }
                for (Particle p : game.particles) {
                    p.draw(g);
                }
                for (Ship object : game.ships) {
                    if (object instanceof PlayerShip) {
                        if (((PlayerShip) object).doubleDamage && ((PlayerShip) object).invincible) {
                            g.setColor(Color.gray);
                            g.fillRect(FRAME_WIDTH - 160, 0, 80, 80);
                            g.drawImage(DOUBLE, FRAME_WIDTH - 160, 10, 80, 65, null);
                            g.setColor(new Color(82, 191, 255));
                            g.fillRect(FRAME_WIDTH - 80, 0, 80, 80);
                            g.drawImage(SHIELD, FRAME_WIDTH - 80, 0, 80, 80, null);
                        } else if (((PlayerShip) object).doubleDamage) {
                            g.setColor(Color.gray);
                            g.fillRect(FRAME_WIDTH - 80, 0, 80, 80);
                            g.drawImage(DOUBLE, FRAME_WIDTH - 80, 10, 80, 65, null);
                        } else if (((PlayerShip) object).invincible) {
                            g.setColor(new Color(82, 191, 255));
                            g.fillRect(FRAME_WIDTH - 80, 0, 80, 80);
                            g.drawImage(SHIELD, FRAME_WIDTH - 80, 0, 80, 80, null);
                        }
                    }
                }
            }
            g.setColor(Color.YELLOW);
            g.setFont(new Font("dialog", Font.BOLD, 30));
            g.drawString("Level: " + Game.getLevel(), 20, FRAME_HEIGHT - 20);
            g.drawString("Score: " + Game.getScore(), FRAME_WIDTH / 4 + 35, FRAME_HEIGHT - 20);
            g.drawString("Lives: " + Game.getLives(), 2 * FRAME_WIDTH / 4 + 45, FRAME_HEIGHT - 20);
            g.drawString("High Score: " + Game.getHighScore(), 3 * FRAME_WIDTH / 4 + 20, FRAME_HEIGHT - 20);
        }
    }

    public Dimension getPreferredSize(){
        return Constants.FRAME_SIZE;
    }
}

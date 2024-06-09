// RegNo. 2000878

package game;

import utilities.ImageManager;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Constants {
    public static final int FRAME_HEIGHT = 740;
    public static final int FRAME_WIDTH = 1200;
    public static final Dimension FRAME_SIZE = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
    public static final int DELAY = 20;  // milliseconds
    public static final double DT = DELAY/1000.0;  // seconds
    public static Random RANDOM = new Random();

    public static Image ASTEROID, ENEMY, PLAYER, PLAYERTHRUST, SHIELD, DOUBLE, MENU1, MENU2, MENU3, AMONGUS, HEALTH, DAMAGE;
    public static ArrayList<Image> BACKGROUNDIMAGES = new ArrayList<>();
    public static ArrayList<Image> PROPIMAGES = new ArrayList<>();
    static {
        try {
            BACKGROUNDIMAGES.add(ImageManager.loadImage("background1"));
            BACKGROUNDIMAGES.add(ImageManager.loadImage("background2"));
            BACKGROUNDIMAGES.add(ImageManager.loadImage("background3"));
            BACKGROUNDIMAGES.add(ImageManager.loadImage("background4"));
            BACKGROUNDIMAGES.add(ImageManager.loadImage("background5"));

            PROPIMAGES.add(ImageManager.loadImage("planet1"));
            PROPIMAGES.add(ImageManager.loadImage("planet2"));
            PROPIMAGES.add(ImageManager.loadImage("planet3"));
            PROPIMAGES.add(ImageManager.loadImage("planet4"));
            PROPIMAGES.add(ImageManager.loadImage("deathstar"));
            PROPIMAGES.add(ImageManager.loadImage("spacestation"));
            PROPIMAGES.add(ImageManager.loadImage("sun"));

            MENU1 = ImageManager.loadImage("menu1");
            MENU2 = ImageManager.loadImage("menu2");
            MENU3 = ImageManager.loadImage("menu3");

            ENEMY = ImageManager.loadImage("enemy");
            ASTEROID = ImageManager.loadImage("asteroid");
            PLAYER = ImageManager.loadImage("player");
            PLAYERTHRUST = ImageManager.loadImage("playerThrust");
            SHIELD = ImageManager.loadImage("shield");
            DOUBLE = ImageManager.loadImage("double");
            AMONGUS = ImageManager.loadImage("amongus");
            HEALTH = ImageManager.loadImage("healthpowerup");
            DAMAGE = ImageManager.loadImage("damagepowerup");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}

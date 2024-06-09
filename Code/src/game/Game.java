// RegNo. 2000878

package game;

import game.timers.Invincibility;
import utilities.JEasyFrame;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import static game.Constants.DELAY;

public class Game {
    public static final int N_INITIAL_ASTEROIDS = 5;
    public static final int N_INITIAL_ENEMIES = 2;
    public List<GameObject> objects;
    public List<Ship> ships;
    public List<Particle> particles;
    public static PlayerShip playerShip;
    Keys ctrl;
    public static Timer t;
    public boolean paused;
    public boolean start;
    public static boolean end;
    public boolean hardDifficulty;
    public boolean newHighScore = false;

    private static int score = 0;
    private static int lives = 5;
    private static int level = 1;
    public static int highScore = 0;
    public static boolean gameOver = false;

    public Game() {
        objects = new ArrayList<>();
        ships = new ArrayList<>();
        particles = new ArrayList<>();
        ctrl = new Keys(this);
        t = new Timer();
    }

    public void newLevel(View view) {
        view.setBackground();
        view.spawnProps();
        level++;
        try {
            Thread.sleep(1000);
        }
        catch (Exception ignored) {
        }
        synchronized(Game.class) {
            objects.clear();
            ships.clear();
            particles.clear();
            for (int i = 0; i < N_INITIAL_ASTEROIDS + 2 * (level-1); i++) {
                objects.add(new Asteroid(this));
            }
            playerShip = new PlayerShip(this, ctrl, !hardDifficulty);
            objects.add(playerShip);
            ships.add(playerShip);
            addSaucers();
        }
    }

    public void newLife() {
        try {
            Thread.sleep(1000);
        }
        catch (Exception ignored) {
        }
        synchronized(Game.class) {
            objects.clear();
            ships.clear();
            particles.clear();
            for (int i = 0; i < N_INITIAL_ASTEROIDS + 2 * (level-1); i++) {
                objects.add(new Asteroid(this));
            }
            playerShip = new PlayerShip(this, ctrl, !hardDifficulty);
            objects.add(playerShip);
            ships.add(playerShip);
            addSaucers();
        }
    }

    public void newGame(View view) {
        view.setBackground();
        view.spawnProps();
        level = 1;
        lives = 5;
        score = 0;
        gameOver = false;
        readHighScore();
        synchronized(Game.class) {
            objects.clear();
            ships.clear();
            particles.clear();
            for (int i = 0; i < N_INITIAL_ASTEROIDS; i++) {
                objects.add(new Asteroid(this));
            }
            playerShip = new PlayerShip(this, ctrl, !hardDifficulty);
            objects.add(playerShip);
            ships.add(playerShip);
            addSaucers();
        }
    }

    private void addSaucers() {
        for (int i = 0; i < N_INITIAL_ENEMIES + level - 1; i++) {
            Controller ctrl = (i % 3 != 0 ? new RandomAction() : new AimNShoot(this));
            Color colorBody = (i % 3 != 0 ? Color.PINK : Color.GREEN);
            Ship saucer = new Enemy(this, ctrl, colorBody);
            objects.add(saucer);
            ships.add(saucer);
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        View view = new View(game);
        new JEasyFrame(view, "Game").addKeyListener(game.ctrl);
        game.start = true;
        game.paused = false;
        game.end = false;
        while (game.start) {
            view.repaint();
            try {
                Thread.sleep(DELAY);
            }
            catch (Exception ignored) {
            }
        }
        game.newGame(view);
        game.gameLoop(view);
    }

    public void readHighScore() {
        String txt = "rsc/normalHighScore.txt";
        if (hardDifficulty) {
            txt = "rsc/hardHighScore.txt";
        }
        try {
            Scanner file = new Scanner(new File(txt));
            highScore = Integer.parseInt(file.next());
            file.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void writeHighScore() {
        String txt = "rsc/normalHighScore.txt";
        if (hardDifficulty) {
            txt = "rsc/hardHighScore.txt";
        }
        if (score > highScore) {
            newHighScore = true;
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(txt));
                bw.write(String.valueOf(score));
                bw.close();
            } catch (Exception ignored) {
            }
        }
    }

    public void gameLoop(View view) {
        while (!gameOver) {
            if (!paused) {
                update(view);
                updateParticles();
            }
            view.repaint();
            try {
                Thread.sleep(DELAY);
            }
            catch (Exception ignored) {
            }
        }
        writeHighScore();
        while (end) {
            view.repaint();
            try {
                Thread.sleep(DELAY);
            }
            catch (Exception ignored) {
            }
        }
        newGame(view);
        gameLoop(view);
    }

    public void update(View view) {
        for (int i = 0; i < objects.size(); i++) {
            GameObject o1 = objects.get(i);
            for (int j = i + 1; j < objects.size(); j++) {
                GameObject o2 = objects.get(j);
                o1.collisionHandling(o2);
            }
        }
        List<GameObject> alive = new ArrayList<>();
        boolean noAsteroids = true;
        boolean noSaucers = true;
        boolean noShip = true;
        for (GameObject o : objects) {
            o.update();
            if (o instanceof Asteroid) {
                noAsteroids = false;
                Asteroid a = (Asteroid) o;
                if (!a.spawnedAsteroids.isEmpty()) {
                    alive.addAll(a.spawnedAsteroids);
                    a.spawnedAsteroids.clear();
                }
            }
            else if (o instanceof PlayerShip) {
                noShip = false;
            }
            else if (o instanceof Enemy) {
                noSaucers = false;
            }
            if (!o.dead) {
                alive.add(o);
            }
            for (Ship s:ships) {
                if (s.bullet != null) {
                    alive.add(s.bullet);
                    s.bullet = null;
                }
            }
        }
        synchronized (Game.class) {
            objects.clear();
            objects.addAll(alive);
        }

        if (noAsteroids && noSaucers) {
            newLevel(view);
        }
        else if (noShip) {
            newLife();
        }
    }

    public void updateParticles() {
        List<Particle> alive = new ArrayList<>();
        for (Particle p:particles) {
            p.update();
            if (!p.dead) {
                alive.add(p);
            }
        }
        synchronized (Game.class) {
            particles.clear();
            particles.addAll(alive);
        }
    }

    public static void incScore(int inc) {
        int oldScore = score;
        score += inc;
        if (score / 5000 > oldScore / 5000) {
            lives++;
        }
    }

    public static void loseLife()  {
        lives--;
        playerShip.invincible = true;
        Game.t.schedule(new Invincibility(playerShip), 4000);
        if (lives == 0) {
            gameOver = true;
            end = true;
        }
    }

    public static int getScore() {
        return score;
    }

    public static int getLevel() {
        return level;
    }

    public static int getLives() {
        return lives;
    }

    public static int getHighScore(){
        return highScore;
    }

    public void explosion(GameObject object) {
        if (object instanceof Ship) {
            for (int i = 0; i<100; i++) {
                Particle p = new Particle(this, object.position, object.velocity, ((Ship) object).color);
                particles.add(p);
            }
        }
        else {
            for (int i = 0; i<100; i++) {
                Particle p = new Particle(this, object.position, object.velocity);
                particles.add(p);
            }
        }
    }
}
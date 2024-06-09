// RegNo. 2000878

package game;

import utilities.SoundManager;
import utilities.Vector2D;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import static game.Constants.*;

public class Asteroid extends GameObject {
    public Sprite sprite;
    public double rotationPerFrame;
    public Vector2D direction;

    public static final double MAX_SPEED = 100;
    public boolean isLarge = true;
    public List<Asteroid> spawnedAsteroids = new ArrayList<>();

    public Asteroid(Game g, Vector2D pos, double vx, double vy, boolean isLarge) {
        super(g, pos, new Vector2D(vx, vy), 15);
        this.isLarge = isLarge;
        double dir = Math.random() * 2 * Math.PI;
        direction = new Vector2D(Math.cos(dir), Math.sin(dir));
        position = new Vector2D(pos);
        rotationPerFrame = Math.random() / 4;
        if (isLarge) {
            sprite = new Sprite(ASTEROID, position, direction, 50, 50);
        }
        else {
            sprite = new Sprite(ASTEROID, position, direction, 40, 40);
        }
    }

    public void draw(Graphics2D g) {
        sprite.draw(g);
    }

    public Asteroid(Game g) {
        super(g, new Vector2D(Math.random() * FRAME_WIDTH, Math.random() + FRAME_HEIGHT), new Vector2D(0, 0), 30);
        double vx = Math.random() * MAX_SPEED;
        if (Math.random() < 0.5) {
            vx *= -1;
        }
        double vy = Math.random() * MAX_SPEED;
        if (Math.random() < 0.5) {
            vy *= -1;
        }
        velocity.set(new Vector2D(vx, vy));
        double dir = Math.random() * 2 * Math.PI;
        direction = new Vector2D(Math.cos(dir), Math.sin(dir));
        rotationPerFrame = Math.random() / 4;
        if (isLarge) {
            sprite = new Sprite(ASTEROID, position, direction, 50, 50);
        }
        else {
            sprite = new Sprite(ASTEROID, position, direction, 40, 40);
        }
    }

    private void spawn() {
        int numberSpawned = (int)Math.ceil(Math.random() * 4);
        for (int i = 0; i < numberSpawned; i++) {
            double vx = Math.random() * MAX_SPEED;
            if (Math.random() < 0.5) {
                vx *= -1;
            }
            double vy = Math.random() * MAX_SPEED;
            if (Math.random() < 0.5) {
                vy *= -1;
            }
            spawnedAsteroids.add(new Asteroid(game, new Vector2D(position.x, position.y), vx, vy, false));
        }
    }

    @Override
    public void hit() {
        super.hit();

        if (isLarge) {
            SoundManager.play(SoundManager.bangMedium);
            spawn();
        }
        else {
            SoundManager.play(SoundManager.bangSmall);
        }
        if (!isLarge) {
            game.explosion(this);
        }
    }

    @Override
    public boolean canCollide(GameObject other) {
        if (other instanceof PlayerShip && ((PlayerShip) other).invincible) {
            return false;
        }
        else if (other instanceof Bullet && !((Bullet) other).firedByShip) {
            return false;
        }
        return !(other instanceof Enemy);
    }

    public String toString() {
        return "Asteroid: " + super.toString();
    }

    public void update() {
        super.update();
        direction.rotate(rotationPerFrame);
    }
}

// RegNo. 2000878

package game;

import utilities.Vector2D;
import java.awt.*;
import static game.Constants.DT;

public class Bullet extends GameObject {
    private double lifetime;
    public static final int RADIUS = 4;
    public static final int BULLET_LIFE = 2; // seconds
    public boolean firedByShip;

    public Bullet(Game g, Vector2D pos, Vector2D vel, boolean firedByShip) {
        super(g, pos, vel, 3);
        this.lifetime = BULLET_LIFE;
        this.firedByShip = firedByShip;
    }

    @Override
    public void update() {
        super.update();
        lifetime -= DT;
        if (lifetime <= 0) {
            dead = true;
        }
    }

    @Override
    public boolean canCollide(GameObject other) {
        if (firedByShip && other instanceof PlayerShip) {
            return false;
        }
        else if (!firedByShip && other instanceof Asteroid || !firedByShip && other instanceof Enemy) {
            return false;
        }
        return true;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(new Color(255,86,74));
        if (firedByShip) {
            g.setColor(new Color(82,191,255));
        }
        g.fillOval((int) position.x - RADIUS, (int) position.y - RADIUS, 2 * RADIUS, 2 * RADIUS);
    }

    public String toString() {
        return "Bullet; " + super.toString();
    }
}

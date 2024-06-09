// RegNo. 2000878

package game;

import static game.Constants.DT;
import utilities.Vector2D;
import utilities.SoundManager;
import java.awt.*;
import static utilities.SoundManager.thrust;

public abstract class Ship extends GameObject {
    public Bullet bullet;
    protected Controller ctrl;
    public Vector2D direction;
    public boolean thrusting;
    public Color color;

    public static final double STEER_RATE = 2 * Math.PI;
    public static final double MAG_ACC = 200;
    public static final double DRAG = 0.01;
    public static final int MUZZLE_VELOCITY = 100;

    public int health;

    public Ship(Game g, Vector2D position, Vector2D velocity, double radius) {
        super(g, position, velocity, radius);
        health = 2;
    }

    protected void mkBullet() {
        Vector2D bulletPos = new Vector2D(position);
        Vector2D bulletVel = new Vector2D(velocity);
        bulletVel.addScaled(direction, PlayerShip.MUZZLE_VELOCITY);
        bullet = new Bullet(game, bulletPos, bulletVel, this instanceof PlayerShip);
        bullet.position.addScaled(direction, (radius + bullet.radius) * 2);
        SoundManager.fire();
    }

    @Override
    public void update() {
        Action action = ctrl.action();
        if (action.shoot) {
            mkBullet();
            action.shoot = false;
        }
        thrusting = action.thrust != 0;
        if (thrusting) {
            SoundManager.play(thrust);
        }
        direction.rotate(action.turn * PlayerShip.STEER_RATE * DT);
        velocity = new Vector2D(direction).mult(velocity.mag());
        velocity.addScaled(direction, PlayerShip.MAG_ACC * DT * action.thrust);
        velocity.addScaled(velocity, -PlayerShip.DRAG);
        super.update();
    }

    public boolean canHit(GameObject other) {
        return (other instanceof Asteroid  || other instanceof PlayerShip);
    }

    public void healthBar(Graphics2D g) {
        g.setColor(Color.gray);
        g.fillRect((int)(position.x - radius), (int)(position.y - radius * 1.5), (int)radius * 2, 6);

        g.setColor(Color.red);
        g.fillRect((int)(position.x - radius), (int)(position.y - radius * 1.5), (int)(health * radius), 6);

        g.setColor(Color.white);
        g.drawRect((int)(position.x - radius), (int)(position.y - radius * 1.5), (int)radius * 2, 6);
    }
}

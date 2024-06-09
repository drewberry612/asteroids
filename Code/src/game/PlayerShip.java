// RegNo. 2000878

package game;

import game.timers.Invincibility;
import game.timers.ShieldRecharge;
import utilities.SoundManager;
import utilities.Vector2D;
import static utilities.SoundManager.bangLarge;
import java.awt.*;
import static game.Constants.*;

public class PlayerShip extends Ship {
    public Sprite sprite;
    public boolean shieldStatus;
    public boolean shieldAllowed;
    public boolean invincible;
    public boolean doubleDamage;

    public PlayerShip(Game g, Controller ctrl, boolean shieldAllowed) {
        super(g, new Vector2D(FRAME_WIDTH / 2.0, FRAME_HEIGHT / 2.0), new Vector2D(0, -1), 20);
        this.ctrl = ctrl;
        direction = new Vector2D(0,-1);
        thrusting = false;
        bullet = null;
        color = Color.CYAN;
        this.shieldAllowed = shieldAllowed;
        shieldStatus = shieldAllowed;
        invincible = false;
        doubleDamage = false;
    }

    @Override
    public void draw(Graphics2D g) {
        sprite = new Sprite(PLAYER, position, direction, 50, 52);
        if (thrusting) {
            sprite = new Sprite(PLAYERTHRUST, position, direction, 55, 50);
        }
        sprite.draw(g);
        healthBar(g);
    }

    @Override
    public void hit() {
        if (shieldStatus) {
            shieldStatus = false;
            Game.t.schedule(new ShieldRecharge(this), 5000);
        }
        else {
            health -= 1;
        }

        if (health == 0) {
            dead = true;
            invincible = false;
            Game.loseLife();
            health = 2;
            if (shieldAllowed) {
                shieldStatus = true;
            }
            SoundManager.play(bangLarge);
            game.explosion(this);
        }
        else {
            invincible = true;
            Game.t.schedule(new Invincibility(this), 1000);
        }
    }

    public String toString() {
        return "Ship: " + super.toString();
    }

    @Override
    public boolean canCollide(GameObject other) {
        if (other instanceof PowerUp) {
            return true;
        }
        else if (invincible) {
            return false;
        }
        return !(other instanceof Bullet) || !((Bullet) other).firedByShip;
    }

    // adds health bars to ships, while also adding shield to playership if allowed
    @Override
    public void healthBar(Graphics2D g) {
        super.healthBar(g);
        if (shieldStatus) {
            g.setColor(Color.blue);
            g.fillRect((int)(position.x - radius), (int)(position.y - radius * 1.5), (int)radius * 2, 6);

            g.setColor(Color.white);
            g.drawRect((int) (position.x - radius), (int) (position.y - radius * 1.5), (int)radius * 2, 6);
        }
    }
}

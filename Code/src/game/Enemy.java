// RegNo. 2000878

package game;

import java.awt.*;
import static game.Constants.*;
import static utilities.SoundManager.bangMedium;
import utilities.SoundManager;
import utilities.Vector2D;

public class Enemy extends Ship {
    public Sprite sprite;

    public Enemy(Game g, Controller ctrl, Color colorBody) {
        super(g, new Vector2D(FRAME_WIDTH*Math.random(), FRAME_HEIGHT*Math.random()), new Vector2D(0, -1), 20);
        this.ctrl = ctrl;
        direction = new Vector2D(0,-1);
        thrusting = false;
        bullet = null;
        color = colorBody;
        sprite = new Sprite(ENEMY, position, direction, 40, 40);
    }

    public void draw(Graphics2D g) {
        sprite.draw(g);
        healthBar(g);
    }

    @Override
    public boolean canCollide(GameObject other) {
        if (other instanceof PlayerShip && ((PlayerShip) other).invincible) {
            return false;
        }
        else if (other instanceof Bullet && !((Bullet) other).firedByShip) {
            return false;
        }
        return !(other instanceof Asteroid);
    }

    public void hit() {
        health -= 1;
        if (health == 0) {
            dead = true;
            int num = (int)(Math.random() * 100);
            if (num > 90) {
                game.objects.add(new PowerUp(game, 1, position));
            }
            else if (num > 80) {
                game.objects.add(new PowerUp(game, 2, position));
            }
            SoundManager.play(bangMedium);
            game.explosion(this);
        }
    }
}

// RegNo. 2000878

package game;

import game.timers.PowerUpLifetime;
import utilities.Vector2D;
import java.awt.*;
import static game.Constants.*;

public class PowerUp extends GameObject {
    public int type;
    public Image im;
    public Sprite sprite;

    public PowerUp(Game g, int type, Vector2D position) {
        super(g, position, new Vector2D(0, -1), 20);
        this.type = type;
        im = HEALTH;
        if (type == 1) {
            im = DAMAGE;
        }
        dead = false;
        game.t.schedule(new PowerUpLifetime(this), 5000);
        sprite = new Sprite(im, position, new Vector2D(), 30, 30);
    }

    @Override
    public void draw(Graphics2D g) {
        sprite.draw(g);
    }

    @Override
    public boolean canCollide(GameObject other) {
        return other instanceof PlayerShip;
    }
}

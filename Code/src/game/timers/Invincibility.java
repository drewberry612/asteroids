// RegNo. 2000878

package game.timers;

import game.PlayerShip;
import java.util.TimerTask;

public class Invincibility extends TimerTask {
    PlayerShip ship;

    public Invincibility(PlayerShip ship) {
        this.ship = ship;
    }

    public void run() {
        ship.invincible = false;
    }
}

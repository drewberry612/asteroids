// RegNo. 2000878

package game.timers;

import game.PlayerShip;
import java.util.TimerTask;

public class DoubleDamage extends TimerTask {
    PlayerShip ship;

    public DoubleDamage(PlayerShip ship) {
        this.ship = ship;
    }

    public void run() {
        ship.doubleDamage = false;
    }
}

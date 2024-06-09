// RegNo. 2000878

package game.timers;

import game.PlayerShip;
import java.util.TimerTask;

public class ShieldRecharge extends TimerTask {
    PlayerShip ship;

    public ShieldRecharge(PlayerShip ship) {
        this.ship = ship;
    }

    public void run() {
        ship.shieldStatus = true;
    }
}
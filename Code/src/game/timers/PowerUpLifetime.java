// RegNo. 2000878

package game.timers;

import game.PowerUp;
import java.util.TimerTask;

public class PowerUpLifetime extends TimerTask {
    PowerUp powerUp;

    public PowerUpLifetime(PowerUp powerUp) {
        this.powerUp = powerUp;
    }

    @Override
    public void run() {
        powerUp.dead = true;
    }
}

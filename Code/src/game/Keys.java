// RegNo. 2000878

package game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Keys extends KeyAdapter implements Controller {
    Action action;
    Game game;

    public Keys(Game game) {
        action = new Action();
        this.game = game;
    }

    @Override
    public Action action() {
        return action;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP, KeyEvent.VK_W:
                action.thrust = 1;
                break;
            case KeyEvent.VK_LEFT, KeyEvent.VK_A:
                action.turn = -1;
                break;
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D:
                action.turn = 1;
                break;
            case KeyEvent.VK_SPACE:
                action.shoot = true;
                break;
            case KeyEvent.VK_1:
                if (game.start || game.end) { // can only start a new game when on start or gameover menu
                    game.start = false;
                    game.end = false;
                    game.hardDifficulty = false;
                }
                break;
            case KeyEvent.VK_2:
                if (game.start || game.end) { // can only start a new game when on start or gameover menu
                    game.start = false;
                    game.end = false;
                    game.hardDifficulty = true;
                }
                break;
            case KeyEvent.VK_ENTER:
                if (!game.paused && !game.start && !game.end) { // pauses game only if game is currently playing
                    game.paused = true;
                }
                else if (game.paused) {
                    game.paused = false;
                }
                break;
            case KeyEvent.VK_ESCAPE:
                if (game.end) { // quits program on gameover screen
                    System.exit(0);
                }
                else if (game.paused) { // ends game early if the user chooses when on pause menu
                    game.paused = false;
                    game.gameOver = true;
                    game.end = true;
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP, KeyEvent.VK_W -> action.thrust = 0;
            case KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_A, KeyEvent.VK_D -> action.turn = 0;
            case KeyEvent.VK_SPACE -> action.shoot = false;
        }
    }
}


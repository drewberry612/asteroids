// RegNo. 2000878

package game;

import game.timers.DoubleDamage;
import utilities.Vector2D;
import java.awt.*;
import static game.Constants.*;

public abstract class GameObject {
    public Vector2D position;
    public Vector2D velocity;
    public double radius;
    public boolean dead;
    public Game game;

    public GameObject(Game g, Vector2D position, Vector2D velocity, double radius) {
        game = g;
        this.position = position;
        this.velocity = velocity;
        this.radius = radius;
        this.dead = false;
    }

    public void update() {
        position.addScaled(velocity, DT);
        position.wrap(FRAME_WIDTH, FRAME_HEIGHT);
    }

    public void hit() {
        dead = true;
    }

    public boolean canCollide(GameObject other) {
        return true;
    }

    public boolean overlap(GameObject other) {
        return this.position.distWithWrap(other.position, FRAME_WIDTH, FRAME_HEIGHT) < this.radius + other.radius;
    }

    public void collisionHandling(GameObject other) {
        if (this.getClass() != other.getClass() && this.overlap(other) && this.canCollide(other)) {
            // for picking up powerups
            if (this instanceof PlayerShip && other instanceof PowerUp || this instanceof PowerUp && other instanceof PlayerShip) {
                if (this instanceof PowerUp) {
                    this.hit();
                    if (((PowerUp) this).type == 1) {
                        ((PlayerShip) other).doubleDamage = true;
                        game.t.schedule(new DoubleDamage((PlayerShip) other), 20000);
                    }
                    else {
                        if (((PlayerShip) other).health == 1) {
                            ((PlayerShip) other).health += 1;
                        }
                    }
                }
                else {
                    other.hit();
                    if (((PowerUp) other).type == 1) {
                        ((PlayerShip) this).doubleDamage = true;
                        game.t.schedule(new DoubleDamage((PlayerShip) this), 20000);
                    }
                    else {
                        if (((PlayerShip) this).health == 1) {
                            ((PlayerShip) this).health += 1;
                        }
                    }
                }
            }
            // so that other objects don't destroy powerups
            else if (!(this instanceof PowerUp) && !(other instanceof PowerUp)) {
                this.hit();
                other.hit();
                // asteroid collisions
                if (this instanceof Asteroid && other instanceof Bullet) {
                    Bullet b = (Bullet) other;
                    if (b.firedByShip) {
                        Game.incScore(100);
                    }
                }
                if (this instanceof Bullet && other instanceof Asteroid) {
                    Bullet b = (Bullet) this;
                    if (b.firedByShip) {
                        Game.incScore(100);
                    }
                }
                if (this instanceof PlayerShip && other instanceof Asteroid) {
                    if (other.dead) {
                        Game.incScore(100);
                    }
                }
                if (other instanceof PlayerShip && this instanceof Asteroid) {
                    if (this.dead) {
                        Game.incScore(100);
                    }
                }
                // enemy collisions
                if (this instanceof Enemy && other instanceof Bullet) {
                    Bullet b = (Bullet) other;
                    if (b.firedByShip && game.playerShip.doubleDamage) {
                        this.hit();
                    }
                    if (b.firedByShip && this.dead) {
                        Game.incScore(500);
                    }
                }
                if (this instanceof Bullet && other instanceof Enemy) {
                    Bullet b = (Bullet) this;
                    if (b.firedByShip && game.playerShip.doubleDamage) {
                        other.hit();
                    }
                    if (b.firedByShip && other.dead) {
                        Game.incScore(500);
                    }
                }
                if (this instanceof PlayerShip && other instanceof Enemy) {
                    if (((PlayerShip) this).doubleDamage) {
                        other.hit();
                    }
                    if (other.dead) {
                        Game.incScore(500);
                    }
                }
                if (other instanceof PlayerShip && this instanceof Enemy) {
                    if (((PlayerShip) other).doubleDamage) {
                        this.hit();
                    }
                    if (this.dead) {
                        Game.incScore(500);
                    }
                }
            }
        }
    }

    public abstract void draw(Graphics2D g);

    public double distance(GameObject other) {
        return position.distWithWrap(other.position, FRAME_WIDTH, FRAME_HEIGHT);
    }

    public String toString() {
        return position.x + "," + position.y;
    }
}

// RegNo. 2000878

package game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import utilities.Vector2D;

public class Sprite {
    public Image image;
    public Vector2D position;
    public Vector2D direction;
    public double width;
    public double height;

    public Sprite(Image image, Vector2D s, Vector2D direction, double width, double height) {
        this.image = image;
        this.position = s;
        this.direction = direction;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics2D g) {
        double imW = image.getWidth(null);
        double imH = image.getHeight(null);
        AffineTransform t = new AffineTransform();
        t.rotate(direction.angle(), 0, 0);
        t.scale(width / imW, height / imH);
        t.translate(-imW / 2.0, -imH / 2.0);
        AffineTransform t0 = g.getTransform();
        g.translate(position.x, position.y);
        g.drawImage(image, t, null);
        g.setTransform(t0);
    }
}


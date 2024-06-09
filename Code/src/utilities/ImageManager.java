// RegNo. 2000878

package utilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageManager {
    public final static String path = "images/";
    public final static String ext = ".png";

    public static Map<String, Image> images = new HashMap<>();

    public static Image loadImage(String fname) throws IOException {
        BufferedImage img = null;
        img = ImageIO.read(new File(path + fname + ext));
        images.put(fname, img);
        return img;
    }
}

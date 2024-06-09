// RegNo. 2000878

package utilities;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundManager {
    static int nBullet = 0;
    static boolean thrusting = false;

    final static String path = "sounds/";

    public final static Clip[] bullets = new Clip[15];

    public final static Clip bangLarge = getClip("thrust");
    public final static Clip bangMedium = getClip("bangMedium");
    public final static Clip bangSmall = getClip("bangSmall");
    public final static Clip beat1 = getClip("beat1");
    public final static Clip beat2 = getClip("beat2");
    public final static Clip extraShip = getClip("extraShip");
    public final static Clip fire = getClip("fire");
    public final static Clip saucerBig = getClip("saucerBig");
    public final static Clip saucerSmall = getClip("saucerSmall");
    public final static Clip thrust = getClip("thrust");

    public final static Clip[] clips = {bangLarge, bangMedium, bangSmall, beat1, beat2,
            extraShip, fire, saucerBig, saucerSmall, thrust};

    static {
        for (int i = 0; i < bullets.length; i++) {
            bullets[i] = getClip("fire");
        }
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 20; i++) {
            fire();
            Thread.sleep(100);
        }
        for (Clip clip : clips) {
            play(clip);
            Thread.sleep(1000);
        }
    }

    // methods which do not modify any fields
    public static void play(Clip clip) {
        clip.setFramePosition(0);
        clip.start();
    }

    private static Clip getClip(String filename) {
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
            AudioInputStream sample = AudioSystem.getAudioInputStream(new File(path + filename + ".wav"));
            clip.open(sample);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return clip;
    }

    // methods which modify (static) fields
    public static void fire() {
        // fire the n-th bullet and increment the index
        Clip clip = bullets[nBullet];
        clip.setFramePosition(0);
        clip.start();
        nBullet = (nBullet + 1) % bullets.length;
    }
}

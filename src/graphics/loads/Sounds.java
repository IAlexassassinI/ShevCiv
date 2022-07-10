package graphics.loads;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.openal.SoundStore;

import java.util.LinkedList;

public class Sounds {

    public static Music backgroundMusic;
    public static Sound attackSound;
    public static Sound explosionSound;
    public static Music creditsSound;
    public static Sound clickSound;
    public static Music winSound;
    public static Music loseSound;

    public static LinkedList<Sound> allSounds;

    static {
        try {
            backgroundMusic = new Music("assets/sounds/background.wav");
            attackSound = new Sound("assets/sounds/attack.wav");
            explosionSound = new Sound("assets/sounds/explosion.wav");
            creditsSound = new Music("assets/sounds/credits.wav");
            clickSound = new Sound("assets/sounds/click.wav");
            winSound = new Music("assets/sounds/win.wav");
            loseSound = new Music("assets/sounds/lose.wav");
            //backgroundMusic.setVolume(1f);
            //SoundStore.get().setCurrentMusicVolume(0.5f);
            //SoundStore.get().setSoundVolume(0.5f);
        } catch (SlickException e) {
            e.printStackTrace();
        }
        allSounds = new LinkedList<>();
        allSounds.add(attackSound);
        allSounds.add(explosionSound);
        allSounds.add(clickSound);
    }

}

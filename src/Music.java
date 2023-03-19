import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Music {
    Clip clip;
    public static Music instance;
    public Music()
    {
        instance = this;
        playSound();
    }
    public void playSound() {

        try {

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/Imag/Masque_Jupiter.WAV"));

            clip = AudioSystem.getClip();

            clip.open(audioInputStream);

            clip.start();
            clip.loop(99);

        } catch(Exception ex) {

            System.out.println("Error with playing sound.");

            ex.printStackTrace();

        }

    }
    public void stop()
    {
        clip.stop();
    }
}
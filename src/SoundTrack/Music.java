package SoundTrack;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Music implements Runnable
{
    private final Clip clip;
    private AudioInputStream audioInputStream;
    private static Music instace = null;

    // constructor to initialize streams and clip
    private Music(String music_path)
            throws UnsupportedAudioFileException,
            IOException, LineUnavailableException
    {
        // create AudioInputStream object
        audioInputStream =
                AudioSystem.getAudioInputStream(new File(music_path).getAbsoluteFile());

        // create clip reference
        clip = AudioSystem.getClip();

        // open audioInputStream to the clip
        clip.open(audioInputStream);

        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-12);

        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static Music getInstance(String path) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if(instace == null)
            instace = new Music(path);
        return instace;
    }

    public void start()
    {
        clip.start();
    }

    public void stop() throws Exception {
        clip.stop();
        clip.close();
    }

    public void resetAudioStream(String music_path) throws Exception {
        stop();
        audioInputStream = AudioSystem.getAudioInputStream(
                new File(music_path).getAbsoluteFile());
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    @Override
    public void run() {

    }
}


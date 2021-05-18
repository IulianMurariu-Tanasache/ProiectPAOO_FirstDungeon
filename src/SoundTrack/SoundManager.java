package SoundTrack;

import GameStates.GameState;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SoundManager implements Runnable{

    private int index = 0;
    private LineListener waiting;
    private ExecutorService executor;
    private static SoundManager soundManager = null;

    public static SoundManager getSoundManager() {
        return soundManager;
    }

    public static void setSoundManager() {
        try {
            soundManager = new SoundManager();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    private SoundManager() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        executor = Executors.newCachedThreadPool();
    }

    public void play(String clip) {
        if(!GameState.isSoundOn())
            return;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                AudioInputStream audioInputStream;
                Clip currentClip = null;
                try {
                    audioInputStream = AudioSystem.getAudioInputStream(
                            new File("Assets/SoundEffects/" + clip).getAbsoluteFile());
                    currentClip = AudioSystem.getClip();
                    currentClip.open(audioInputStream);
                    currentClip.setMicrosecondPosition(0);
                    FloatControl gainControl = (FloatControl) currentClip.getControl(FloatControl.Type.MASTER_GAIN);
                    gainControl.setValue(-17);
                    currentClip.start();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void stop() {
        executor.shutdown();
    }

    @Override
    public void run() {
        while(GameState.isSoundOn())
            ;
        stop();
    }
}

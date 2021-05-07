package SoundTrack;

import GameStates.GameState;
import GameStates.MenuState;
import GameStates.SettingsState;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Music implements Runnable
{
    private final String[] clips;
    private static Music instace = null;
    private final Clip currentClip;
    private int index = 1;
    public static boolean changedState = false;
    private boolean started;
    private LineListener waiting;

    // constructor to initialize streams and clip
    private Music(String[] music_path)
            throws UnsupportedAudioFileException,
            IOException, LineUnavailableException
    {
        waiting = new LineListener() {
            @Override
            public void update(LineEvent event) {
                if(event.getType() == LineEvent.Type.START)
                    started = true;
                if(event.getType() == LineEvent.Type.STOP)
                    started = false;
            }
        };

        clips = music_path;
        currentClip = AudioSystem.getClip();
        currentClip.addLineListener(waiting);
        started = false;
        start();
    }

    public static Music getInstance(String[] path) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if(instace == null)
            instace = new Music(path);
        return instace;
    }

    public void start() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audioInputStream;
        if(GameState.getInstance() instanceof MenuState || GameState.getInstance() instanceof SettingsState)
            audioInputStream = AudioSystem.getAudioInputStream(
                    new File(clips[0]).getAbsoluteFile());
        else {
            audioInputStream = AudioSystem.getAudioInputStream(
                    new File(clips[index]).getAbsoluteFile());
            ++index;
            if(index >= clips.length)
                index = 1;
        }
        currentClip.open(audioInputStream);
        currentClip.setMicrosecondPosition(0);
        FloatControl gainControl = (FloatControl) currentClip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-12);
        currentClip.start();

    }

    public void stop() {
        currentClip.stop();
        currentClip.flush();
        currentClip.close();
    }

    public void resetAudioStream(String music_path) {
        stop();
        /*audioInputStream = AudioSystem.getAudioInputStream(
                new File(music_path).getAbsoluteFile());
        currentClip.open(audioInputStream);
        currentClip.loop(Clip.LOOP_CONTINUOUSLY);*/
    }

    @Override
    public void run() {
        while(GameState.isRunning()){
            //System.out.println("hehe");
            if(!GameState.isMusicOn() && currentClip.isOpen()) {
                stop();
            }
            if(GameState.isMusicOn()){
                if(!currentClip.isRunning() && !started) {
                    try {
                        started = true;
                        start();
                    } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
                        e.printStackTrace();
                    }
                }
                if(changedState) {
                    changedState = false;
                    stop();
                    try {
                        started = true;
                        start();
                    } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }
}


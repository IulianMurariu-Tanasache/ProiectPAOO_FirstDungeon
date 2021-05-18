package SoundTrack;

import GameStates.GameState;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Music implements Runnable
{
    private final String[] clips;
    private static Music instace = null;
    private final Clip currentClip;
    private int index = 0;
    private boolean started;
    private LineListener waiting;

    // constructor to initialize streams and clip
    private Music(String[] music_path)
            throws
            LineUnavailableException
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
        //start();
    }

    public static Music getInstance(String[] path) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if(instace == null)
            instace = new Music(path);
        return instace;
    }

    public void start() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audioInputStream;
        audioInputStream = AudioSystem.getAudioInputStream(
                new File(clips[index]).getAbsoluteFile());
        ++index;
        if(index >= clips.length)
            index = 0;
        currentClip.open(audioInputStream);
        currentClip.setMicrosecondPosition(0);
        FloatControl gainControl = (FloatControl) currentClip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-24);
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
            }

        }
    }
}


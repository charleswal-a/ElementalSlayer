package main;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class Sound {
    private Clip clip;
    private AudioInputStream stream;
    private File song;

    public Sound(String url) {
        try {
            song = new File(url);
            stream = AudioSystem.getAudioInputStream(song.getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(stream);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playLooped() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void play() {
        clip.setFramePosition(0);
        clip.start();
    }

    public void stopMusic() {
        clip.stop();
    }
}

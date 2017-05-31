package io.inabsentia.celestialoutbreak.handler;

import io.inabsentia.celestialoutbreak.entity.State;
import io.inabsentia.celestialoutbreak.utils.Utils;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class SoundHandler {

    private final Utils utils = Utils.getInstance();

    private static final SoundHandler instance = new SoundHandler();

    public Sound menu = new Sound(SoundHandler.class.getResource("/audio/arpanauts.wav").getPath());
    public Sound play = new Sound(SoundHandler.class.getResource("/audio/digital.wav").getPath());
    public Sound pause = new Sound(SoundHandler.class.getResource("/audio/prologue.wav").getPath());
    public Sound beep01 = new Sound(SoundHandler.class.getResource("/audio/beep_01.wav").getPath());
    public Sound beep02 = new Sound(SoundHandler.class.getResource("/audio/beep_02.wav").getPath());
    public Sound beep03 = new Sound(SoundHandler.class.getResource("/audio/beep_03.wav").getPath());

    public class Sound {
        private Clip clip;

        public Sound(String path) {
            try {
                clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(new File(path)));
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
        }

        public void play(boolean loop) {
            if (!utils.isSoundEnabled()) return;
            if (clip.isActive()) return;

            stop();

            if (loop)
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            else
                clip.start();
        }

        public void stop() {
            clip.stop();
            clip.setFramePosition(0);
        }

    }

    private SoundHandler() {

    }

    public void playStateMusic(State state, boolean loop) {
        switch (state) {
            case MENU:
                if (play.clip.isActive()) play.stop();
                if (pause.clip.isActive()) pause.stop();
                menu.play(loop);
                break;
            case PLAY:
                if (menu.clip.isActive()) menu.stop();
                if (pause.clip.isActive()) pause.stop();
                play.play(loop);
                break;
            case SCORES:
                break;
            case SETTINGS:
                break;
            case ABOUT:
                break;
            case PAUSE:
                if (menu.clip.isActive()) menu.stop();
                if (play.clip.isActive()) play.stop();
                pause.play(loop);
                break;
            default:
                break;
        }
    }

    public synchronized static SoundHandler getInstance() {
        return instance;
    }

}
package io.inabsentia.celestialoutbreak.handler;

import io.inabsentia.celestialoutbreak.entity.State;
import io.inabsentia.celestialoutbreak.utils.GameUtils;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class SoundHandler {

    private final GameUtils gameUtils = GameUtils.getInstance();
    private final TextHandler textHandler = TextHandler.getInstance();

    private static SoundHandler instance;

    public final Sound menu = new Sound(textHandler.AUDIO_FILE_PATH_MENU);
    public final Sound play = new Sound(textHandler.AUDIO_FILE_PATH_PLAY);
    public final Sound pause = new Sound(textHandler.AUDIO_FILE_PATH_PAUSE);
    public final Sound ballBounce = new Sound(textHandler.AUDIO_FILE_PATH_BALL_BOUNCE);
    public final Sound ballReset = new Sound(textHandler.AUDIO_FILE_PATH_BALL_RESET);

    public class Sound {
        private Clip clip;

        public Sound(String filePath) {
            try {
                clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(new File(filePath)));
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
        }

        public void play(boolean loop) {
            if (!gameUtils.isSoundEnabled()) return;
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

    static {
        try {
            instance = new SoundHandler();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized static SoundHandler getInstance() {
        return instance;
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

}
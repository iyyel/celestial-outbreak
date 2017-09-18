package io.inabsentia.celestialoutbreak.handler;

import io.inabsentia.celestialoutbreak.entity.State;
import io.inabsentia.celestialoutbreak.utils.GameUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class SoundHandler {

    private final GameUtils gameUtils = GameUtils.getInstance();
    private final TextHandler textHandler = TextHandler.getInstance();
    private final FileHandler fileHandler = FileHandler.getInstance();

    private static SoundHandler instance;

    public final SoundClip MENU_CLIP = new SoundClip(textHandler.SOUND_FILE_PATH_MENU);
    public final SoundClip PLAY_CLIP = new SoundClip(textHandler.SOUND_FILE_PATH_PLAY);
    public final SoundClip PAUSE_CLIP = new SoundClip(textHandler.SOUND_FILE_PATH_PAUSE);
    public final SoundClip BALL_BOUNCE_CLIP = new SoundClip(textHandler.SOUND_FILE_PATH_BALL_BOUNCE);
    public final SoundClip BALL_RESET_CLIP = new SoundClip(textHandler.SOUND_FILE_PATH_BALL_RESET);
    public final SoundClip MENU_BTN_SELECTION_CLIP = new SoundClip(textHandler.SOUND_FILE_PATH_MENU_BTN_SELECTION);

    public class SoundClip {
        private Clip clip;
        private long currentMicrosecondClipPosition = 0;

        public SoundClip(String filePath) {
            try {
                clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(new File(filePath)));
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
                fileHandler.writeLogMsg(textHandler.errorCreatingAudioClipMsg(filePath, ExceptionUtils.getStackTrace(e)));
            }
        }

        public void play(boolean loop) {
            if (!gameUtils.isSoundEnabled() || clip.isActive()) return;
            stop();

            if (loop)
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            else
                clip.start();
        }

        public void resume(boolean loop) {
            if (!gameUtils.isSoundEnabled() || clip.isActive()) return;
            clip.setMicrosecondPosition(currentMicrosecondClipPosition);

            if (loop)
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            else
                clip.start();
        }

        public void pause() {
            if (!gameUtils.isSoundEnabled()) return;
            currentMicrosecondClipPosition = clip.getMicrosecondPosition();
            stop();
        }

        public void stop() {
            if (!gameUtils.isSoundEnabled()) return;
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
                if (PLAY_CLIP.clip.isActive()) PLAY_CLIP.stop();
                if (PAUSE_CLIP.clip.isActive()) PAUSE_CLIP.stop();
                MENU_CLIP.play(loop);
                break;
            case PLAY:
                if (MENU_CLIP.clip.isActive()) MENU_CLIP.stop();
                if (PAUSE_CLIP.clip.isActive()) PAUSE_CLIP.stop();
                PLAY_CLIP.play(loop);
                break;
            case SCORES:
                break;
            case SETTINGS:
                break;
            case ABOUT:
                break;
            case PAUSE:
                if (MENU_CLIP.clip.isActive()) MENU_CLIP.stop();
                if (PLAY_CLIP.clip.isActive()) PLAY_CLIP.stop();
                PAUSE_CLIP.play(loop);
                break;
            default:
                break;
        }
    }

}
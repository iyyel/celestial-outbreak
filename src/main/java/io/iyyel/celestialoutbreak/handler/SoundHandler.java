package io.iyyel.celestialoutbreak.handler;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.utils.Utils;
import org.apache.commons.lang.exception.ExceptionUtils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class SoundHandler {

    private final Utils utils = Utils.getInstance();
    private final TextHandler textHandler = TextHandler.getInstance();
    private final FileHandler fileHandler = FileHandler.getInstance();

    private GameController.State currentStateBackup = GameController.State.NONE;

    private static SoundHandler instance;

    private Map<String, SoundClip> soundClipMap = new HashMap<String, SoundClip>() {
        {
            put(textHandler.SOUND_FILE_NAME_MENU, new SoundClip(textHandler.SOUND_FILE_CLIENT_PATH_MENU));
            put(textHandler.SOUND_FILE_NAME_PLAY, new SoundClip(textHandler.SOUND_FILE_CLIENT_PATH_PLAY));
            put(textHandler.SOUND_FILE_NAME_PAUSE, new SoundClip(textHandler.SOUND_FILE_CLIENT_PATH_PAUSE));
            put(textHandler.SOUND_FILE_NAME_BALL_HIT, new SoundClip(textHandler.SOUND_FILE_CLIENT_PATH_BALL_HIT));
            put(textHandler.SOUND_FILE_NAME_BALL_RESET, new SoundClip(textHandler.SOUND_FILE_CLIENT_PATH_BALL_RESET));
            put(textHandler.SOUND_FILE_NAME_MENU_BTN_NAV, new SoundClip(textHandler.SOUND_FILE_CLIENT_PATH_MENU_BTN_NAV));
            put(textHandler.SOUND_FILE_NAME_MENU_BTN_USE, new SoundClip(textHandler.SOUND_FILE_CLIENT_PATH_MENU_BTN_USE));
            put(textHandler.SOUND_FILE_NAME_BAD_ACTION, new SoundClip(textHandler.SOUND_FILE_CLIENT_PATH_BAD_ACTION));
        }
    };

    public class SoundClip {
        private Clip clip;
        private boolean isActive = false;

        public SoundClip(String filePath) {
            try {
                clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(new File(filePath)));
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
                fileHandler.writeLog(textHandler.errorCreatingAudioClipMsg(filePath, ExceptionUtils.getStackTrace(e)));
            }
        }

        public void play(boolean loop) {
            if (!utils.isSoundEnabled() || clip.isActive()) {
                return;
            }

            stop();

            if (loop) {
                isActive = true;
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                isActive = true;
                clip.start();
            }
        }

        public void stop() {
            if (!utils.isSoundEnabled()) return;
            isActive = false;
            clip.stop();
            clip.setFramePosition(0);
        }

        public void reduceClipDB(float db) {
            if (-db >= 0) {
                return;
            }

            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-db); // Reduce volume by 'db' decibels.
        }

        public void increaseClipDB(float db) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(+db); // Increase volume by 'db' decibels.
        }

        public boolean isActive() {
            return isActive;
        }

    }

    private SoundHandler() {
        initSoundHandler();
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

    public void playStateMusic(GameController.State state, GameController.State prevState, boolean loop) {

        if (currentStateBackup != prevState) {
            return;
        }

        currentStateBackup = state;

        String soundClipToPlay;

        switch (state) {
            case WELCOME_MENU:
            case MAIN_MENU:
            case SCORES_SCREEN:
            case CONTROLS_SCREEN:
            case SETTINGS_MENU:
            case PLAYER_SETTINGS_MENU:
            case PLAYER_SELECT_SCREEN:
            case PLAYER_NEW_SCREEN:
            case PLAYER_DELETE_SCREEN:
            case CONFIG_SETTINGS_SCREEN:
            case ABOUT_SCREEN:
            case EXIT_SCREEN:
                soundClipToPlay = textHandler.SOUND_FILE_NAME_MENU;
                break;
            case PLAY_SCREEN:
                soundClipToPlay = textHandler.SOUND_FILE_NAME_PLAY;
                break;
            case PAUSE_SCREEN:
                soundClipToPlay = textHandler.SOUND_FILE_NAME_PAUSE;
                break;
            default:
                return;
        }

        for (String key : soundClipMap.keySet()) {
            SoundClip soundClip = soundClipMap.get(key);
            if (soundClip.isActive && !key.equals(soundClipToPlay)) {
                soundClip.stop();
            }
        }

        SoundClip soundClip = getSoundClip(soundClipToPlay);

        if (!soundClip.isActive) {
            soundClip.play(loop);
        }

    }

    public SoundClip getSoundClip(String clipKey) {
        return soundClipMap.get(clipKey);
    }

    private void initSoundHandler() {
        getSoundClip(textHandler.SOUND_FILE_NAME_MENU_BTN_NAV).reduceClipDB(15);
        getSoundClip(textHandler.SOUND_FILE_NAME_MENU_BTN_USE).reduceClipDB(15);
        getSoundClip(textHandler.SOUND_FILE_NAME_BAD_ACTION).reduceClipDB(15);
    }

}
package io.iyyel.celestialoutbreak.handler;

import io.iyyel.celestialoutbreak.controller.GameController;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class SoundHandler {

    private final OptionsHandler optionsHandler = OptionsHandler.getInstance();
    private final TextHandler textHandler = TextHandler.getInstance();
    private final LogHandler logHandler = LogHandler.getInstance();

    private GameController.State currentStateBackup = GameController.State.NONE;

    private static SoundHandler instance;

    private Map<String, SoundClip> soundClipMap = new HashMap<String, SoundClip>() {
        {
            put(textHandler.SOUND_FILE_NAME_MENU, new SoundClip(textHandler.SOUND_FILE_CLIENT_PATH_MENU));
            put(textHandler.SOUND_FILE_NAME_PAUSE, new SoundClip(textHandler.SOUND_FILE_CLIENT_PATH_PAUSE));
            put(textHandler.SOUND_FILE_NAME_BALL_HIT, new SoundClip(textHandler.SOUND_FILE_CLIENT_PATH_BALL_HIT));
            put(textHandler.SOUND_FILE_NAME_BALL_RESET, new SoundClip(textHandler.SOUND_FILE_CLIENT_PATH_BALL_RESET));
            put(textHandler.SOUND_FILE_NAME_BLOCK_DESTROYED, new SoundClip(textHandler.SOUND_FILE_CLIENT_PATH_BLOCK_DESTROYED));
            put(textHandler.SOUND_FILE_NAME_MENU_BTN_NAV, new SoundClip(textHandler.SOUND_FILE_CLIENT_PATH_MENU_BTN_NAV));
            put(textHandler.SOUND_FILE_NAME_MENU_BTN_USE, new SoundClip(textHandler.SOUND_FILE_CLIENT_PATH_MENU_BTN_USE));
            put(textHandler.SOUND_FILE_NAME_BAD_ACTION, new SoundClip(textHandler.SOUND_FILE_CLIENT_PATH_BAD_ACTION));
        }
    };

    public class SoundClip {

        private Clip clip;
        private boolean isActive = false;
        private boolean isPaused = false;
        private int framePosition = 0;

        private SoundClip(String filePath) {
            try {
                clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(new File(filePath)));
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
                logHandler.log(textHandler.errorCreatingAudioClipMsg(filePath, ExceptionUtils.getStackTrace(e)), LogHandler.LogLevel.ERROR, false);
            }
        }

        public void play(boolean loop) {
            if (!optionsHandler.isSoundEnabled() || clip.isActive()) {
                return;
            }

            if (isPaused) {
                clip.setFramePosition(framePosition);
            } else {
                stop();
            }

            isPaused = false;
            isActive = true;

            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.start();
            }
        }

        public void stop() {
            isActive = false;
            clip.stop();
            clip.flush();
            clip.setFramePosition(0);
        }

        public void pause() {
            isActive = false;
            isPaused = true;
            framePosition = clip.getFramePosition();
            clip.stop();
        }

        private void reduceClipDB(float db) {
            if (-db >= 0) {
                return;
            }

            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-db); // Reduce volume by 'db' decibels.
        }

    }

    static {
        try {
            instance = new SoundHandler();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private SoundHandler() {
        initSoundHandler();
    }

    public synchronized static SoundHandler getInstance() {
        return instance;
    }

    public void playStateSound(GameController.State state, GameController.State prevState, boolean loop, boolean restart) {

        if (currentStateBackup != prevState && !restart) {
            return;
        }

        currentStateBackup = state;

        String soundClipToPlay;

        switch (state) {
            case WELCOME:
            case MAIN:
            case SCORES:
            case CONTROLS:
            case OPTIONS:
            case PLAYER_OPTIONS:
            case PLAYER_SELECT:
            case PLAYER_CREATE:
            case PLAYER_DELETE:
            case GRAPHICS_OPTIONS:
            case CONFIG_OPTIONS:
            case ABOUT:
            case EXIT:
                soundClipToPlay = textHandler.SOUND_FILE_NAME_MENU;
                break;
            case PAUSE:
                soundClipToPlay = textHandler.SOUND_FILE_NAME_PAUSE;
                break;
            default:
                return;
        }

        for (String key : soundClipMap.keySet()) {
            SoundClip soundClip = soundClipMap.get(key);
            if (soundClip.isActive && !key.equals(soundClipToPlay)) {

                /*
                 * Do not stop if the nav or use sounds are currently going.
                 */
                if (key.equals(textHandler.SOUND_FILE_NAME_MENU_BTN_NAV) || key.equals(textHandler.SOUND_FILE_NAME_MENU_BTN_USE)) {
                    continue;
                }

                logHandler.log("SoundClip '" + key + "' has been stopped.", LogHandler.LogLevel.INFO, true);

                soundClip.stop();
            }
        }

        SoundClip soundClip = getSoundClip(soundClipToPlay);

        if (!soundClip.isActive) {
            soundClip.play(loop);
        }

    }

    public void stopAllSound() {
        for (String key : soundClipMap.keySet()) {
            SoundClip soundClip = soundClipMap.get(key);
            if (soundClip.isActive) {
                soundClip.stop();
                logHandler.log("SoundClip '" + key + "' has been stopped.", LogHandler.LogLevel.INFO, true);
            }
        }
    }

    public SoundClip getSoundClip(String clipKey) {
        return soundClipMap.get(clipKey);
    }

    private void initSoundHandler() {
        getSoundClip(textHandler.SOUND_FILE_NAME_MENU_BTN_NAV).reduceClipDB(10);
        getSoundClip(textHandler.SOUND_FILE_NAME_MENU_BTN_USE).reduceClipDB(10);
        getSoundClip(textHandler.SOUND_FILE_NAME_BAD_ACTION).reduceClipDB(10);
    }

    public void addSoundClip(String fileName, String filePath) {
        soundClipMap.put(fileName, new SoundClip(filePath));
    }

}
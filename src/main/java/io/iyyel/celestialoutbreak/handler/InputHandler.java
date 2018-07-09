package io.iyyel.celestialoutbreak.handler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
 * Singleton class.
 */
public final class InputHandler implements KeyListener {

    private static InputHandler instance;

    private boolean[] keys = new boolean[1000];

    private boolean isUpPressed, isDownPressed, isLeftPressed, isRightPressed;
    private boolean isUsePressed, isPausePressed, isOKPressed, isCancelPressed;

    private final int INITIAL_OK_INPUT_TIMER_VALUE = 5;
    private int okInputTimer = INITIAL_OK_INPUT_TIMER_VALUE;

    private final int INITIAL_PAUSE_INPUT_TIMER_VALUE = 5;
    private int pauseInputTimer = INITIAL_PAUSE_INPUT_TIMER_VALUE;

    private boolean isInputMode = true;
    private String userInput = "";

    private InputHandler() {

    }

    static {
        try {
            instance = new InputHandler();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized static InputHandler getInstance() {
        return instance;
    }

    public void update() {
        if (pauseInputTimer > 0)
            pauseInputTimer--;

        if (okInputTimer > 0)
            okInputTimer--;

        isUpPressed = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
        isDownPressed = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
        isLeftPressed = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
        isRightPressed = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];

        isUsePressed = keys[KeyEvent.VK_ENTER];

        if (pauseInputTimer == 0) {
            isPausePressed = keys[KeyEvent.VK_P];
            pauseInputTimer = INITIAL_PAUSE_INPUT_TIMER_VALUE;
        } else {
            isPausePressed = false;
        }

        if (okInputTimer == 0) {
            isOKPressed = keys[KeyEvent.VK_ENTER];
            okInputTimer = INITIAL_OK_INPUT_TIMER_VALUE;
        } else {
            isOKPressed = false;
        }

        isCancelPressed = keys[KeyEvent.VK_BACK_SPACE];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;

        if (isInputMode) {
            if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                if (userInput.length() <= 1) {
                    userInput = "";
                } else {
                    userInput = userInput.substring(0, userInput.length() - 1);
                }
            } else if (userInput.length() <= 20 && (Character.isAlphabetic(e.getKeyCode()))) {
                userInput += e.getKeyChar();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public boolean isUpPressed() {
        return isUpPressed;
    }

    public boolean isDownPressed() {
        return isDownPressed;
    }

    public boolean isLeftPressed() {
        return isLeftPressed;
    }

    public boolean isRightPressed() {
        return isRightPressed;
    }

    public boolean isUsePressed() {
        return isUsePressed;
    }

    public boolean isPausePressed() {
        return isPausePressed;
    }

    public boolean isOKPressed() {
        return isOKPressed;
    }

    public boolean isCancelPressed() {
        return isCancelPressed;
    }

    // Used for a bug in PlayerSettingsMenu. :(
    public void setIsUsePressed(boolean isUsePressed) {
        this.isUsePressed = isUsePressed;
        keys[KeyEvent.VK_SPACE] = isUsePressed;
    }

    public boolean isInputMode() {
        return isInputMode;
    }

    public void setInputMode(boolean isInputMode) {
        this.isInputMode = isInputMode;
        userInput = "";
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public String getUserInput() {
        return userInput;
    }

}
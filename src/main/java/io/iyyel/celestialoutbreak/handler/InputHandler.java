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
        isUpPressed = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
        isDownPressed = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
        isLeftPressed = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
        isRightPressed = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];

        isOKPressed = keys[KeyEvent.VK_Z];
        isCancelPressed = keys[KeyEvent.VK_X];
        isUsePressed = keys[KeyEvent.VK_SPACE];
        isPausePressed = keys[KeyEvent.VK_P];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;

        if (isInputMode) {
            addToUserInput(e);
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

    public boolean isInputMode() {
        return isInputMode;
    }

    public void setInputMode(boolean isInputMode) {
        this.isInputMode = isInputMode;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public String getUserInput() {
        return userInput;
    }

    private void addToUserInput(KeyEvent e) {
        // TODO: Remove magic numbers here.
        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            if (userInput.length() <= 1) {
                userInput = "";
            } else {
                userInput = userInput.substring(0, userInput.length() - 1);
            }
        } else if (userInput.length() <= 7 && (Character.isAlphabetic(e.getKeyCode()))) {
            userInput += e.getKeyChar();
        }
    }

}
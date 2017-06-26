package io.inabsentia.celestialoutbreak.handler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
 * Singleton class.
 */
public class InputHandler implements KeyListener {

    private static final InputHandler instance = new InputHandler();

    private boolean[] keys = new boolean[120];
    private boolean isUpPressed, isDownPressed, isLeftPressed, isRightPressed, isUsePressed, isPausePressed, isConfirmPressed, isRejectPressed;

    private int confirmInputTimer = 5;

    private InputHandler() {

    }

    public void update() {
        if (confirmInputTimer > 0) confirmInputTimer--;

        isUpPressed = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
        isDownPressed = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
        isLeftPressed = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
        isRightPressed = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];

        isUsePressed = keys[KeyEvent.VK_SPACE];
        isPausePressed = keys[KeyEvent.VK_P];

        if (confirmInputTimer == 0) {
            isConfirmPressed = keys[KeyEvent.VK_Z];
            confirmInputTimer = 5;
        } else {
            isConfirmPressed = false;
        }
        isRejectPressed = keys[KeyEvent.VK_X];
    }

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    public void keyTyped(KeyEvent e) {

    }

    public synchronized static InputHandler getInstance() {
        return instance;
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

    public boolean isConfirmPressed() {
        return isConfirmPressed;
    }

    public boolean isRejectPressed() {
        return isRejectPressed;
    }

}
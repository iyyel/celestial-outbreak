package io.inabsentia.celestialoutbreak.handler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Singleton class.
 */
public class InputHandler implements KeyListener {

    private static final InputHandler instance = new InputHandler();

    private boolean[] keys = new boolean[120];
    public boolean up, down, left, right, use, pause;

    private InputHandler() {

    }

    public void update() {
        up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
        down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
        left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
        use = keys[KeyEvent.VK_SPACE] || keys[KeyEvent.VK_ENTER];
        pause = keys[KeyEvent.VK_P];
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

}
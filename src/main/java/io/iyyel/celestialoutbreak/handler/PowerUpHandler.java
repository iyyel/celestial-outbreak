package io.iyyel.celestialoutbreak.handler;

import io.iyyel.celestialoutbreak.ui.entity.PowerUp;

import java.awt.*;

public final class PowerUpHandler {

    private static final PowerUpHandler instance;

    private final LogHandler logHandler = LogHandler.getInstance();

    private PowerUp[] powerUpArray = new PowerUp[10];

    private PowerUpHandler() {

    }

    static {
        try {
            instance = new PowerUpHandler();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized static PowerUpHandler getInstance() {
        return instance;
    }

    public void update() {
        for (PowerUp up : powerUpArray) {
            if (up != null) {
                up.update();
            }
        }

        checkPaddleCollision();
        checkBottomCollision();
    }

    public void render(Graphics2D g) {
        for (PowerUp up : powerUpArray) {
            if (up != null) {
                up.render(g);
            }
        }
    }

    public void spawnPowerUp(PowerUp powerUp) {
        for (int i = 0; i < powerUpArray.length; i++) {
            if (powerUpArray[i] == null) {
                powerUpArray[i] = powerUp;
                return;
            }
        }

        PowerUp[] newPowerUpArray = new PowerUp[powerUpArray.length * 2];
        System.arraycopy(powerUpArray, 0, newPowerUpArray, 0, powerUpArray.length);
        powerUpArray = newPowerUpArray;
    }

    public void clear() {
        powerUpArray = new PowerUp[10];
    }

    private void checkPaddleCollision() {
        for (int i = 0; i < powerUpArray.length; i++) {
            if (powerUpArray[i] != null && powerUpArray[i].collidesWithPaddle()) {
                powerUpArray[i].applyEffect();
                logHandler.log("Power up collided with Paddle!", "checkPaddleCollision", LogHandler.LogLevel.INFO, true);
                powerUpArray[i] = null;
            }
        }
    }

    private void checkBottomCollision() {
        for (int i = 0; i < powerUpArray.length; i++) {
            if (powerUpArray[i] != null && powerUpArray[i].hasReachedBottom()) {
                logHandler.log("Power up reached bottom!", "checkBottomCollision", LogHandler.LogLevel.INFO, true);
                powerUpArray[i] = null;
            }
        }
    }

}
package io.iyyel.celestialoutbreak.handler;

import io.iyyel.celestialoutbreak.ui.entity.PowerUp;

import java.awt.*;

public final class PowerUpHandler {

    private final LogHandler logHandler = LogHandler.getInstance();
    private final LevelHandler levelHandler = LevelHandler.getInstance();

    private static final PowerUpHandler instance;

    private PowerUp[] powerUpArray = new PowerUp[10];

    private boolean isUpdateStopped;

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
        if (isUpdateStopped) {
            return;
        }

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
                powerUpArray[i].playCollideClip();
                powerUpArray[i].applyEffect();
                levelHandler.addPowerUpScore();
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

    public void stopUpdate() {
        isUpdateStopped = true;
    }

    public void resumeUpdate() {
        isUpdateStopped = false;
    }

}
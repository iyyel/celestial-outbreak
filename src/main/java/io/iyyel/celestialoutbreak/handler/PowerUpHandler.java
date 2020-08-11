package io.iyyel.celestialoutbreak.handler;

import io.iyyel.celestialoutbreak.ui.entity.PowerUp;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class PowerUpHandler {

    private static PowerUpHandler instance;

    private final List<PowerUp> powerUps = new ArrayList<>();

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
        if (powerUps.isEmpty()) {
            return;
        }

        for (PowerUp up : powerUps) {
            up.update();
        }

        checkPaddleCollision();
        checkBottomCollision();
    }

    public void render(Graphics2D g) {
        if (powerUps.isEmpty()) {
            return;
        }

        for (PowerUp up : powerUps) {
            up.render(g);
        }
    }

    public void spawnPowerUp(PowerUp powerUp) {
        powerUps.add(powerUp);
    }

    public void clear() {
        powerUps.clear();
    }

    private void checkPaddleCollision() {
        boolean[] collisions = new boolean[powerUps.size()];

        for (int i = 0; i < powerUps.size(); i++) {
            if (powerUps.get(i).collidesWithPaddle()) {
                System.out.println("Powerup collided with Paddle! Removed.");
                collisions[i] = true;
            }
        }

        for (int i = 0; i < collisions.length; i++) {
            if (collisions[i]) {
                powerUps.remove(i);
            }
        }
    }

    private void checkBottomCollision() {
        boolean[] collisions = new boolean[powerUps.size()];

        for (int i = 0; i < powerUps.size(); i++) {
            if (powerUps.get(i).hasReachedBottom()) {
                System.out.println("Powerup reached bottom! Removed.");
                collisions[i] = true;
            }
        }

        for (int i = 0; i < collisions.length; i++) {
            if (collisions[i]) {
                powerUps.remove(i);
            }
        }
    }

}
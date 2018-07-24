package io.iyyel.celestialoutbreak.entity;

import io.iyyel.celestialoutbreak.controller.GameController;

import java.awt.*;

public final class PowerUp extends MobileEntity {

    /**
     * Default constructor.
     *
     * @param pos            Current position of the Entity.
     * @param width          Width of the Entity.
     * @param height         Height of the Entity.
     * @param color          Color of the Entity.
     * @param speed          Speed of the Entity.
     * @param gameController Instance of the current game.
     */
    public PowerUp(Point pos, int width, int height, Color color, int speed, GameController gameController) {
        super(pos, width, height, color, speed, gameController);
    }

    @Override
    public void render(Graphics2D g) {

    }

    @Override
    public Rectangle getBounds() {
        return null;
    }

}
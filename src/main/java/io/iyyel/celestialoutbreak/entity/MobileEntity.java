package io.iyyel.celestialoutbreak.entity;

import io.iyyel.celestialoutbreak.controller.GameController;

import java.awt.*;

/**
 * The MobileEntity is an Entity that is able to move
 * around the screen. It therefore inherits from the Entity
 * class, adding a speed variable to it.
 * <p>
 * See @Entity for more information.
 */
public abstract class MobileEntity extends Entity {

    protected int speed;
    protected final GameController gameController;

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
    public MobileEntity(Point pos, int width, int height, Color color, int speed, GameController gameController) {
        super(pos, width, height, color);
        this.speed = speed;
        this.gameController = gameController;
    }

}
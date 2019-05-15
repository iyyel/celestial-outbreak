package io.iyyel.celestialoutbreak.entity;

import io.iyyel.celestialoutbreak.controller.GameController;

import java.awt.*;

public abstract class MobileEntity extends Entity {

    protected int speed;
    protected final GameController gameController;

    public MobileEntity(Point pos, Dimension dim, Color color, int speed, GameController gameController) {
        super(pos, dim, color);
        this.speed = speed;
        this.gameController = gameController;
    }

}
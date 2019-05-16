package io.iyyel.celestialoutbreak.ui.entity;

import io.iyyel.celestialoutbreak.controller.GameController;

import java.awt.*;

public abstract class AbstractMobileEntity extends AbstractEntity {

    protected int speed;
    protected final GameController gameController;

    public AbstractMobileEntity(Point pos, Dimension dim, Color color, int speed, GameController gameController) {
        super(pos, dim, color);
        this.speed = speed;
        this.gameController = gameController;
    }

}
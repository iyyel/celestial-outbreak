package io.inabsentia.celestialoutbreak.entity;

import io.inabsentia.celestialoutbreak.controller.GameController;

import java.awt.*;

public abstract class MobileEntity extends Entity {

    protected final int speed;

    protected final GameController gameController;

    public MobileEntity(Point pos, int width, int height, int speed, Color color, GameController gameController) {
        super(pos, width, height, color);
        this.speed = speed;
        this.gameController = gameController;
    }

}
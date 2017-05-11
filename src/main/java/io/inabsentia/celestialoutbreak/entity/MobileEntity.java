package io.inabsentia.celestialoutbreak.entity;

import io.inabsentia.celestialoutbreak.controller.Game;

import java.awt.*;

public abstract class MobileEntity extends Entity {

    protected final int speed;

    protected final Game game;

    public MobileEntity(Point pos, int width, int height, int speed, Color color, Game game) {
        super(pos, width, height, color);
        this.speed = speed;
        this.game = game;
    }

}
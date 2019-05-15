package io.iyyel.celestialoutbreak.entity;

import io.iyyel.celestialoutbreak.drawable.contract.IRenderable;
import io.iyyel.celestialoutbreak.entity.contract.IUpdatable;

import java.awt.*;

public abstract class Entity implements IRenderable, IUpdatable {

    protected Point pos;
    protected Dimension dim;
    protected Color color;

    public Entity(Point pos, Dimension dim, Color color) {
        this.pos = pos;
        this.dim = dim;
        this.color = color;
    }

    @Override
    public abstract void render(Graphics2D g);

    @Override
    public abstract void update();

    public abstract Rectangle getBounds();
}
package io.iyyel.celestialoutbreak.ui.entity;

import io.iyyel.celestialoutbreak.ui.contract.IRenderable;
import io.iyyel.celestialoutbreak.ui.contract.IUpdatable;

import java.awt.*;

public abstract class AbstractEntity implements IRenderable, IUpdatable {

    protected Point pos;
    protected Dimension dim;
    protected Color color;

    public AbstractEntity(Point pos, Dimension dim, Color color) {
        this.pos = pos;
        this.dim = dim;
        this.color = color;
    }
    
    public abstract Rectangle getBounds();
}
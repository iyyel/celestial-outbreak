package io.iyyel.celestialoutbreak.ui.screen.component;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.ui.contract.IRenderable;

import java.awt.*;

public abstract class AbstractComponent implements IRenderable {

    protected final Point pos;
    protected final Dimension dim;
    protected Color bgColor;

    protected final GameController gameController;

    public AbstractComponent(Point pos, Dimension dim, Color bgColor,
                             Point posOffset, GameController gameController) {
        this.pos = new Point(pos.x - posOffset.x, pos.y - posOffset.y);
        this.dim = dim;
        this.bgColor = bgColor;
        this.gameController = gameController;
    }

    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }

}
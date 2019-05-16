package io.iyyel.celestialoutbreak.ui.screen.component;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.ui.contract.IRenderable;
import io.iyyel.celestialoutbreak.util.Util;

import java.awt.*;

public abstract class AbstractComponent implements IRenderable  {

    protected final Point pos;
    protected final Dimension dim;
    protected Color color;
    protected final Point posOffset;

    protected final GameController gameController;

    public AbstractComponent(Point pos, Dimension dim, Color color, Point posOffset, GameController gameController) {
        this.pos = Util.subtractPoints(pos, posOffset);
        this.dim = dim;
        this.color = color;
        this.posOffset = posOffset;
        this.gameController = gameController;
    }

    protected void drawScreenCenterString(String text, int y, Font font, Graphics2D g) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = (gameController.getWidth() - metrics.stringWidth(text)) / 2;
        g.drawString(text, x, y);
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
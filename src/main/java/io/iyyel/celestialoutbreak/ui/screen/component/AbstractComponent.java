package io.iyyel.celestialoutbreak.ui.screen.component;

import io.iyyel.celestialoutbreak.ui.interfaces.IEntityRenderable;

import java.awt.*;

public abstract class AbstractComponent implements IEntityRenderable {

    protected final Point pos;
    protected final Dimension dim;
    protected Color bgColor;

    protected final int screenWidth;
    protected final int screenHeight;

    private boolean renderStopped;

    public AbstractComponent(Point pos, Dimension dim, Color bgColor, Point posOffset,
                             int screenWidth, int screenHeight) {
        this.pos = new Point(pos.x - posOffset.x, pos.y - posOffset.y);
        this.dim = dim;
        this.bgColor = bgColor;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    @Override
    public void stopRender() {
        renderStopped = true;
    }

    @Override
    public void resumeRender() {
        renderStopped = false;
    }

    @Override
    public boolean isRenderStopped() {
        return renderStopped;
    }

    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }

}
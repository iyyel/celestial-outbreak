package io.iyyel.celestialoutbreak.ui.entity;

import io.iyyel.celestialoutbreak.ui.interfaces.IEntityCollidable;
import io.iyyel.celestialoutbreak.ui.interfaces.IEntityRenderable;
import io.iyyel.celestialoutbreak.ui.interfaces.IEntityUpdatable;

import java.awt.*;

public abstract class AbstractEntity implements IEntityUpdatable, IEntityRenderable, IEntityCollidable<AbstractEntity> {

    protected enum Shape {
        RECTANGLE,
        ROUND_RECTANGLE,
        OVAL
    }

    protected final Point pos;
    protected final Dimension dim;
    protected final Shape shape;
    protected Color col;

    private boolean updateStopped;
    private boolean renderStopped;

    public AbstractEntity(Point pos, Dimension dim, Shape shape, Color col) {
        this.pos = pos;
        this.dim = dim;
        this.shape = shape;
        this.col = col;
    }

    @Override
    public void stopUpdate() {
        updateStopped = true;
    }

    @Override
    public void resumeUpdate() {
        updateStopped = false;
    }

    @Override
    public boolean isUpdateStopped() {
        return updateStopped;
    }

    @Override
    public void render(Graphics2D g) {
        if (isRenderStopped()) {
            return;
        }

        g.setColor(col);
        switch (shape) {
            case RECTANGLE:
                g.fillRect(pos.x, pos.y, dim.width, dim.height);
                break;
            case ROUND_RECTANGLE:
                g.fillRoundRect(pos.x, pos.y, dim.width, dim.height, 50, 50);
                break;
            case OVAL:
                g.fillOval(pos.x, pos.y, dim.width, dim.height);
                break;
        }
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

    @Override
    public boolean isColliding(AbstractEntity ent) {
        return getBounds().intersects(ent.getBounds());
    }

    private Rectangle getBounds() {
        return new Rectangle(pos.x, pos.y, dim.width, dim.height);
    }

}
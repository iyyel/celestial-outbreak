package io.iyyel.celestialoutbreak.ui.entity;

import io.iyyel.celestialoutbreak.ui.interfaces.IEntityRenderable;
import io.iyyel.celestialoutbreak.ui.interfaces.IEntityUpdatable;

import java.awt.*;

public abstract class AbstractEntity implements IEntityUpdatable, IEntityRenderable {

    protected Point pos;
    protected Dimension dim;
    protected Shape shape;
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
                int curve = dim.width < dim.height ? dim.width - dim.width / 4 : dim.height - dim.height / 4;
                g.fillRoundRect(pos.x, pos.y, dim.width, dim.height, curve, curve);
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

    public boolean intersects(AbstractEntity ent) {
        return getBounds().intersects(ent.getBounds());
    }

    protected Rectangle getBounds() {
        return new Rectangle(pos.x, pos.y, dim.width, dim.height);
    }

    protected void fixCollisionXAxis(AbstractEntity ent) {
        int tCenterX = pos.x + dim.width / 2;
        int entCenterY = ent.pos.x + ent.dim.width / 2;

        if (tCenterX < entCenterY) {
            // this collides to the left of ent
            int deltaX = Math.abs(ent.pos.x - (pos.x + dim.width));
            pos.x -= deltaX;
        } else {
            // this collides to the right of ent
            int deltaX = Math.abs(pos.x - (ent.pos.x + ent.dim.width));
            pos.x += deltaX;
        }
    }

    protected void fixCollisionYAxis(AbstractEntity ent) {
        int tCenterY = pos.y + dim.height / 2;
        int entCenterY = ent.pos.y + ent.dim.height / 2;

        if (tCenterY < entCenterY) {
            // this collides with ent on top
            int deltaY = Math.abs(ent.pos.y - (pos.y + dim.height));
            pos.y -= deltaY;
        } else {
            // this collides with ent on bottom
            int deltaY = Math.abs(pos.y - (ent.pos.y + ent.dim.height));
            pos.y += deltaY;
        }
    }

}
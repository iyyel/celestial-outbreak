package io.iyyel.celestialoutbreak.ui.interfaces;

import java.awt.*;

public interface IEntityRenderable {

    enum Shape {
        RECTANGLE,
        ROUND_RECTANGLE,
        OVAL
    }

    void render(Graphics2D g);
    void stopRender();
    void resumeRender();
    boolean isRenderStopped();
}
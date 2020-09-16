package io.iyyel.celestialoutbreak.ui.interfaces;

import java.awt.*;

public interface IEntityRenderable {
    void render(Graphics2D g);
    void stopRender();
    void resumeRender();
    boolean isRenderStopped();
}
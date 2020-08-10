package io.iyyel.celestialoutbreak.ui.interfaces;

import java.awt.*;

public interface IRenderable {
    void render(Graphics2D g);
    void stopRender(int updates);
    boolean isRenderStopped();
}
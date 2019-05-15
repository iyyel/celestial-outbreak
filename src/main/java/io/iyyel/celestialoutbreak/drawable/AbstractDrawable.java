package io.iyyel.celestialoutbreak.drawable;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.drawable.contract.IRenderable;

import java.awt.*;

public abstract class AbstractDrawable implements IRenderable  {

    private final GameController gameController;

    private final Point pos;
    private final Dimension dim;
    private final Color color;
    private final Point posOffset;

    public AbstractDrawable(GameController gameController, Point pos, Dimension dim, Color color, Point posOffset) {
        this.gameController = gameController;
        this.pos = pos;
        this.dim = dim;
        this.color = color;
        this.posOffset = posOffset;
    }



}



/*

Point pos, Dimension dim, String text,
                            Color fontColor, Color btnColor, Point posOffset, Point textOffset
 */
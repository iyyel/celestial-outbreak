package io.inabsentia.celestialoutbreak.menu;

import io.inabsentia.celestialoutbreak.controller.Game;
import io.inabsentia.celestialoutbreak.handler.InputHandler;

import java.awt.*;

public class BottomPanelMenu extends Menu {

    private final Font panelFont;
    private final Color fontColor;

    private int blockAmount;

    public BottomPanelMenu(Game game, InputHandler inputHandler, Color fontColor) {
        super(game, inputHandler);
        this.fontColor = fontColor;

        panelFont = new Font("Verdana", Font.PLAIN, 12);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(fontColor);
        g.setFont(panelFont);
        g.drawString(textHandler.blocksLeft(blockAmount), 1200, 710);
    }

    public void updateBlockAmount(int blockAmount) {
        this.blockAmount = blockAmount;
    }

}
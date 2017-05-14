package io.inabsentia.celestialoutbreak.game.menu;

import io.inabsentia.celestialoutbreak.game.controller.Game;
import io.inabsentia.celestialoutbreak.game.handler.InputHandler;

import java.awt.*;

public class BottomPanelMenu extends Menu {

    private final Font panelFont;
    private final Color fontColor;

    private String levelType;
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
        // Figure these values out someday.
        g.drawString(textHandler.bottomPanelString(levelType, 0, 0, blockAmount), 900, 710);
    }

    public void updatePanel(String levelType, int playerLives, int playerScore, int blockAmount) {
        this.levelType = levelType;
        this.blockAmount = blockAmount;
    }

}
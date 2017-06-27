package io.inabsentia.celestialoutbreak.menu;

import io.inabsentia.celestialoutbreak.controller.Game;
import io.inabsentia.celestialoutbreak.handler.InputHandler;

import java.awt.*;

public class GamePanel extends Menu {

    private final Font panelFont;
    private final Rectangle gamePanelRect;

    private String levelName;
    private int blockAmountLeft;

    public GamePanel(Game game, InputHandler inputHandler, Color fontColor) {
        super(game, inputHandler, fontColor);

        gamePanelRect = new Rectangle(2, game.getHeight() - 20, game.getWidth() - 5, 17);

        panelFont = gameUtils.getGameFont().deriveFont(12F);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(fontColor);
        g.setFont(panelFont);
        g.draw(gamePanelRect);
        drawGamePanel(g, levelName, blockAmountLeft);
    }

    public void updatePanel(String levelName, int blockAmountLeft) {
        this.levelName = levelName;
        this.blockAmountLeft = blockAmountLeft;
    }

    private void drawGamePanel(Graphics2D g, String levelName, int blockAmountLeft) {
        g.drawString("Planet: " + levelName, 5, 714);
        g.drawString("Player: N/A", 150, 714);
        g.drawString("Lives: N/A", game.getWidth() / 2 - 100, 714);
        g.drawString("Score: N/A", game.getWidth() / 2, 714);
        g.drawString("Blocks: " + Integer.toString(blockAmountLeft), game.getWidth() / 2 + 200, 714);
    }

}
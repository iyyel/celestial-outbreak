package io.inabsentia.celestialoutbreak.menu;

import io.inabsentia.celestialoutbreak.controller.GameController;
import io.inabsentia.celestialoutbreak.handler.InputHandler;

import java.awt.*;

public final class GamePanel extends Menu {

    private final Font panelFont;

    private String levelName;
    private int blockAmountLeft;

    public GamePanel(GameController gameController, InputHandler inputHandler, Color fontColor) {
        super(gameController, inputHandler, fontColor);

        panelFont = utils.getGameFont().deriveFont(12F);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(fontColor);
        g.setFont(panelFont);
        drawGamePanel(g, levelName, blockAmountLeft);
    }

    public void updatePanel(String levelName, int blockAmountLeft) {
        this.levelName = levelName;
        this.blockAmountLeft = blockAmountLeft;
    }

    private void drawGamePanel(Graphics2D g, String levelName, int blockAmountLeft) {
        g.drawString("Planet: " + levelName, 5, 714);
        g.drawString("Player: N/A", 150, 714);
        g.drawString("Lives: N/A", gameController.getWidth() / 2 - 100, 714);
        g.drawString("Score: N/A", gameController.getWidth() / 2, 714);
        g.drawString("Blocks: " + Integer.toString(blockAmountLeft), gameController.getWidth() / 2 + 200, 714);
    }

}
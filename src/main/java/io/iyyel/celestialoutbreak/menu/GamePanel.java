package io.iyyel.celestialoutbreak.menu;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.menu.AbstractMenu;

import java.awt.*;

public final class GamePanel extends AbstractMenu {

    private final Font panelFont;

    private String levelName;
    private int blockAmountLeft;

    public GamePanel(GameController gameController) {
        super(gameController);

        panelFont = utils.getGameFont().deriveFont(12F);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(menuFontColor);
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
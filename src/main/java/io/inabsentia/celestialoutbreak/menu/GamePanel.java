package io.inabsentia.celestialoutbreak.menu;

import io.inabsentia.celestialoutbreak.controller.Game;
import io.inabsentia.celestialoutbreak.handler.InputHandler;

import java.awt.*;

public class GamePanel extends Menu {

    private final Font panelFont;
    private final Color fontColor;
    private final Rectangle gamePanelRect;

    private String levelName;
    private int blockAmountLeft;

    public GamePanel(Game game, InputHandler inputHandler, Color fontColor) {
        super(game, inputHandler);
        this.fontColor = fontColor;

        //versionRect = new Rectangle(game.getWidth() / 2 + 20, game.getHeight() - 20, 45, 15);
        gamePanelRect = new Rectangle(2, game.getHeight() - 20, game.getWidth() - 5, 17);

        panelFont = new Font("Verdana", Font.PLAIN, 12);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(fontColor);
        g.setFont(panelFont);
        g.draw(gamePanelRect);
        drawGamePanel(g, levelName, 5, 10000, blockAmountLeft);
    }

    public void updatePanel(String levelName, int playerLives, int playerScore, int blockAmountLeft) {
        this.levelName = levelName;
        this.blockAmountLeft = blockAmountLeft;
    }

    private void drawGamePanel(Graphics2D g, String levelName, int playerLives, int playerScore, int blockAmountLeft) {
        g.drawString("Planet: " + levelName, 5, 714);
        g.drawString("Lives: " + Integer.toString(playerLives), game.getWidth() / 2 - 100, 714);
        g.drawString("Score: " + Integer.toString(playerScore), game.getWidth() / 2 - 30, 714);
        g.drawString("Blocks: " + Integer.toString(blockAmountLeft), game.getWidth() / 2 + 75, 714);
    }

}
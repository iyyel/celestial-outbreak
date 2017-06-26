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

        g.drawString(textHandler.gamePanelString(levelName, 5, 234, blockAmountLeft), 5, 714);

        //drawGamePanel(g, levelName, 5, 10000, blockAmountLeft);
    }

    public void updatePanel(String levelName, int playerLives, int playerScore, int blockAmountLeft) {
        this.levelName = levelName;
        this.blockAmountLeft = blockAmountLeft;
    }

    private void drawGamePanel(Graphics2D g, String levelName, int playerLives, int playerScore, int blockAmountLeft) {
        int rightOffset = game.getWidth();
        g.drawString("Planet: " + levelName, 50, 741);

        //g.drawString("Lives: " + Integer.toString(playerLives), 500, 741);
        //g.drawString("Score: " + Integer.toString(playerScore), 550, 741);
        //g.drawString("Blocks: " + Integer.toString(blockAmountLeft), rightOffset, 741);
    }
}
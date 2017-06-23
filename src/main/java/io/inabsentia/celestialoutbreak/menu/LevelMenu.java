package io.inabsentia.celestialoutbreak.menu;

import io.inabsentia.celestialoutbreak.controller.Game;
import io.inabsentia.celestialoutbreak.handler.InputHandler;

import java.awt.*;

public class LevelMenu extends Menu {

    private final Font titleFont, pauseMsgFont, pauseStartMsgFont;
    private final Color fontColor;

    private String prevLevelType, nextLevelType;

    public LevelMenu(Game game, InputHandler inputHandler, Color fontColor) {
        super(game, inputHandler);
        this.fontColor = fontColor;

        titleFont = new Font("Verdana", Font.PLAIN, 52);
        pauseMsgFont = new Font("Verdana", Font.PLAIN, 45);
        pauseStartMsgFont = new Font("Verdana", Font.PLAIN, 32);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(fontColor);

        g.setFont(titleFont);
        g.drawString(textHandler.TITLE, game.getWidth() / 2 - 220, 100);

                g.setFont(pauseMsgFont);
        g.drawString(prevLevelType + " level finished. Next level, " + nextLevelType + ".", game.getWidth() / 2 - 80, 300);

        g.setFont(pauseStartMsgFont);
        g.drawString("y: continue\nn: return to menu" + nextLevelType, game.getWidth() / 2 - 225, game.getHeight() / 2);
    }

    public void setLevelTypes(String prevLevelType, String nextLevelType) {
        this.prevLevelType = prevLevelType;
        this.nextLevelType = nextLevelType;
    }

}
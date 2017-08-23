package io.inabsentia.celestialoutbreak.menu;

import io.inabsentia.celestialoutbreak.controller.Game;
import io.inabsentia.celestialoutbreak.handler.InputHandler;
import io.inabsentia.celestialoutbreak.handler.TextHandler;
import io.inabsentia.celestialoutbreak.utils.GameUtils;

import java.awt.*;

public abstract class Menu {

    protected final Game game;
    protected final GameUtils gameUtils = GameUtils.getInstance();
    protected final TextHandler textHandler = TextHandler.getInstance();
    protected final InputHandler inputHandler;

    protected Font titleFont, submenuTitleFont, msgFont, infoPanelFont;
    protected final Rectangle versionRect, emailRect;
    protected final Color fontColor;

    public Menu(Game game, InputHandler inputHandler, Color fontColor) {
        this.game = game;
        this.inputHandler = inputHandler;
        this.fontColor = fontColor;

        titleFont = gameUtils.getGameFont().deriveFont(52F);
        submenuTitleFont = gameUtils.getGameFont().deriveFont(32F);
        msgFont = gameUtils.getGameFont().deriveFont(28F);
        infoPanelFont = gameUtils.getGameFont().deriveFont(10F);

        /* Information rectangles */
        versionRect = new Rectangle(game.getWidth() / 2 + 28, game.getHeight() - 20, 50, 15);
        emailRect = new Rectangle(game.getWidth() / 2 - 72, game.getHeight() - 20, 96, 15);
    }

    public abstract void update();

    public abstract void render(Graphics2D g);

    public void drawXCenteredString(String msg, int y, Graphics2D g, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = (game.getWidth() - metrics.stringWidth(msg)) / 2;
        g.setFont(font);
        g.drawString(msg, x, y);
    }

    public void drawSubmenuTitle(String msg, Graphics2D g) {
        drawXCenteredString(msg, game.getHeight() / 2 - 170, g, submenuTitleFont);
    }

    public void drawInformationPanel(Graphics2D g) {
        g.setColor(fontColor);
        g.setFont(infoPanelFont);

        /* Render version number */
        g.drawString(textHandler.VERSION, versionRect.x + 4, versionRect.y + 11);
        g.draw(versionRect);

        /* Render email tag */
        g.drawString(textHandler.EMAIL, emailRect.x + 5, emailRect.y + 11);
        g.draw(emailRect);
    }

    public void drawMenuTitle(Graphics2D g) {
        g.setColor(fontColor);
        g.setFont(titleFont);
        drawXCenteredString(textHandler.TITLE, 100, g, titleFont);
    }

}
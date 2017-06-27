package io.inabsentia.celestialoutbreak.menu;

import io.inabsentia.celestialoutbreak.controller.Game;
import io.inabsentia.celestialoutbreak.entity.Player;
import io.inabsentia.celestialoutbreak.handler.InputHandler;
import io.inabsentia.celestialoutbreak.handler.TextHandler;

import java.awt.*;

public abstract class Menu {

    protected final Game game;
    protected final InputHandler inputHandler;
    protected final TextHandler textHandler = TextHandler.getInstance();
    protected final Player player = Player.getInstance();

    protected final Rectangle versionRect, emailRect;
    protected final Font titleFont, msgFont, infoPanelFont;
    protected final Color fontColor;

    public Menu(Game game, InputHandler inputHandler) {
        this.game = game;
        this.inputHandler = inputHandler;

        /* Information rectangles */
        versionRect = new Rectangle(game.getWidth() / 2 + 20, game.getHeight() - 20, 45, 15);
        emailRect = new Rectangle(game.getWidth() / 2 - 65, game.getHeight() - 20, 80, 15);

        titleFont = new Font("Verdana", Font.PLAIN, 52);
        msgFont = new Font("Verdana", Font.PLAIN, 32);
        infoPanelFont = new Font("Verdana", Font.PLAIN, 10);

        fontColor = Color.WHITE;
    }

    public abstract void update();

    public abstract void render(Graphics2D g);

    public void drawXCenteredString(String text, int y, Graphics2D g, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = (game.getWidth() - metrics.stringWidth(text)) / 2;
        g.setFont(font);
        g.drawString(text, x, y);
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
        drawXCenteredString(textHandler.TITLE, 100, g, titleFont);
    }

}
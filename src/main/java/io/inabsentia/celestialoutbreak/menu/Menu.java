package io.inabsentia.celestialoutbreak.menu;

import io.inabsentia.celestialoutbreak.controller.GameController;
import io.inabsentia.celestialoutbreak.handler.InputHandler;
import io.inabsentia.celestialoutbreak.handler.TextHandler;
import io.inabsentia.celestialoutbreak.utils.Utils;

import java.awt.*;

public abstract class Menu {

    protected final Utils utils = Utils.getInstance();
    protected final TextHandler textHandler = TextHandler.getInstance();
    protected final InputHandler inputHandler;

    protected final GameController gameController;

    protected Font titleFont, submenuTitleFont, msgFont, infoPanelFont;
    protected final Rectangle versionRect, siteRect;
    protected final Color fontColor;

    public Menu(GameController gameController, InputHandler inputHandler, Color fontColor) {
        this.gameController = gameController;
        this.inputHandler = inputHandler;
        this.fontColor = fontColor;

        titleFont = utils.getGameFont().deriveFont(52F);
        submenuTitleFont = utils.getGameFont().deriveFont(32F);
        msgFont = utils.getGameFont().deriveFont(28F);
        infoPanelFont = utils.getGameFont().deriveFont(10F);

        /* Information rectangles */
        versionRect = new Rectangle(gameController.getWidth() / 2 + 28, gameController.getHeight() - 20, 50, 15);
        siteRect = new Rectangle(gameController.getWidth() / 2 - 72, gameController.getHeight() - 20, 96, 15);
    }

    public abstract void update();

    public abstract void render(Graphics2D g);

    protected void drawXCenteredString(String msg, int y, Graphics2D g, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = (gameController.getWidth() - metrics.stringWidth(msg)) / 2;
        g.setFont(font);
        g.drawString(msg, x, y);
    }

    protected void drawSubmenuTitle(String msg, Graphics2D g) {
        drawXCenteredString(msg, gameController.getHeight() / 2 - 170, g, submenuTitleFont);
    }

    public void drawInformationPanel(Graphics2D g) {
        g.setColor(fontColor);
        g.setFont(infoPanelFont);

        /* Render version number */
        g.drawString(textHandler.GAME_VERSION, versionRect.x + 4, versionRect.y + 11);
        g.draw(versionRect);

        /* Render email tag */
        g.drawString(textHandler.AUTHOR_WEBSITE, siteRect.x + 5, siteRect.y + 11);
        g.draw(siteRect);
    }

    protected void drawMenuTitle(Graphics2D g) {
        g.setColor(fontColor);
        g.setFont(titleFont);
        drawXCenteredString(textHandler.GAME_TITLE, 100, g, titleFont);
    }

    protected void drawMenuMessage(String msg, Graphics2D g, int x, int y, int xInc, int yInc, int maxLineSize) {
        /*
         * If the message is longer than maxLineSize, we know that we need to
         * split up the string in smaller pieces, and fit it on the screen.
         */
        if (msg.length() > maxLineSize) {
            /* Get the first 60 characters of the message into the string line */
            String line = msg.substring(0, maxLineSize);

            /*
             * We need to check whether the current index of the message
             * (which is maxLineSize, essentially) ends in the middle of a word or not.
             * If it does, use a helper function to decrease the index until we find any
             * type of whitespace, to indicate that we are out of a word.
             */
            int index = line.length();
            while (isEndAChar(line, index)) index--;

            /* When the new index is calculated, we recreate the line string. */
            line = msg.substring(0, index);

            /* Remove all characters up to the new line index, so that we don't get same characters. */
            msg = msg.substring(line.length(), msg.length());

            /* Draw the string to the screen! */
            g.drawString(line, x, y);

            /*
             *
             */
            drawMenuMessage(msg, g, x + xInc, y + yInc, xInc, yInc, maxLineSize);
        } else {
            String line = msg.substring(0, msg.length() - 1);
            g.drawString(line, x, y);
        }
    }

    private boolean isEndAChar(String line, int index) {
        char lastChar = line.charAt(index - 1);
        return (lastChar != ' ') && (lastChar != '\t') && (lastChar != '\n');
    }

}
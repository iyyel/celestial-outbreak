package io.iyyel.celestialoutbreak.menu;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.data.dao.PlayerDAO;
import io.iyyel.celestialoutbreak.data.dao.interfaces.IPlayerDAO;
import io.iyyel.celestialoutbreak.handler.InputHandler;
import io.iyyel.celestialoutbreak.handler.SoundHandler;
import io.iyyel.celestialoutbreak.handler.TextHandler;
import io.iyyel.celestialoutbreak.utils.Utils;

import java.awt.*;

public abstract class AbstractMenu {

    /*
     * Singleton helpers
     */
    protected final Utils utils = Utils.getInstance();
    protected final IPlayerDAO playerDAO = PlayerDAO.getInstance();

    protected final TextHandler textHandler = TextHandler.getInstance();
    protected final InputHandler inputHandler = InputHandler.getInstance();
    protected final SoundHandler soundHandler = SoundHandler.getInstance();

    /*
     * Useful audio clips
     */
    protected final SoundHandler.SoundClip menuNavClip = soundHandler.getSoundClip(textHandler.SOUND_FILE_NAME_MENU_BTN_NAV);
    protected final SoundHandler.SoundClip menuUseClip = soundHandler.getSoundClip(textHandler.SOUND_FILE_NAME_MENU_BTN_USE);

    /*
     * Fonts
     */
    protected final Font titleFont = utils.getGameFont().deriveFont(52F);
    protected final Font msgFont = utils.getGameFont().deriveFont(28F);
    protected final Font inputFont = utils.getGameFont().deriveFont(20F);

    private final Font subTitleFont = utils.getGameFont().deriveFont(36F);
    private final Font infoPanelFont = utils.getGameFont().deriveFont(10F);

    /*
     * InfoPanel rectangles
     */
    private final Rectangle versionRect, authorRect;

    /*
     * Menu colors.
     */
    protected Color menuFontColor;
    protected Color menuBtnColor;
    protected Color menuSelectedBtnColor;

    /*
     * GameController instance
     */
    protected final GameController gameController;

    /*
     * Default constructor
     */
    public AbstractMenu(GameController gameController) {
        this.gameController = gameController;

        /* MenuColors */
        this.menuFontColor = utils.getMenuFontColor();
        this.menuBtnColor = utils.getMenuBtnColor();
        this.menuSelectedBtnColor = utils.getMenuSelectedBtnColor();

        /* Information rectangles */
        authorRect = new Rectangle(gameController.getWidth() / 2 - 1, gameController.getHeight() - 20, 58, 15);
        versionRect = new Rectangle(gameController.getWidth() / 2 - 57, gameController.getHeight() - 20, 52, 15);
    }

    public abstract void update();

    public abstract void render(Graphics2D g);

    protected void drawCenterString(String msg, int y, Graphics2D g, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = (gameController.getWidth() - metrics.stringWidth(msg)) / 2;
        g.setFont(font);
        g.drawString(msg, x, y);
    }

    protected void drawSubmenuTitle(String msg, Graphics2D g) {
        drawCenterString(msg, gameController.getHeight() / 2 - 170, g, subTitleFont);
    }

    public final void drawInformationPanel(Graphics2D g) {
        g.setColor(menuFontColor);
        g.setFont(infoPanelFont);

        /* Render version number */
        g.drawString(textHandler.GAME_VERSION, versionRect.x + 4, versionRect.y + 11);
        g.draw(versionRect);

        /* Render email tag */
        g.drawString(textHandler.AUTHOR_WEBSITE, authorRect.x + 6, authorRect.y + 11);
        g.draw(authorRect);
    }

    protected void drawMenuTitle(Graphics2D g) {
        g.setColor(menuFontColor);
        g.setFont(titleFont);
        drawCenterString(textHandler.GAME_TITLE, 100, g, titleFont);
    }

}
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
    protected final SoundHandler.SoundClip menuBadActionClip = soundHandler.getSoundClip(textHandler.SOUND_FILE_NAME_BAD_ACTION);

    /*
     * Fonts
     */
    protected final Font titleFont = utils.getGameFont().deriveFont(52F);
    protected final Font msgFont = utils.getGameFont().deriveFont(26F);
    protected final Font inputFont = utils.getGameFont().deriveFont(20F);
    protected final Font tooltipFont = utils.getGameFont().deriveFont(18F);

    private final Font subTitleFont = utils.getGameFont().deriveFont(36F);
    private final Font infoPanelFont = utils.getGameFont().deriveFont(14F);

    /*
     * InfoPanel rectangles
     */
    private final Rectangle versionRect, authorRect;

    /*
     * Menu colors.
     */
    protected Color menuFontColor = utils.getMenuFontColor();
    protected Color menuBtnColor = utils.getMenuBtnColor();
    protected Color menuSelectedBtnColor = utils.getMenuBtnSelectedColor();
    protected Color menuBtnPlayerSelectedColor = utils.getMenuBtnPlayerSelectedColor();
    protected Color menuBtnPlayerDeletedColor = utils.getMenuBtnPlayerDeletedColor();

    private final int INIT_INPUT_TIMER = 12;
    private int inputTimer = INIT_INPUT_TIMER;

    protected final int BTN_Y_OFFSET = 33;

    /*
     * GameController instance
     */
    protected final GameController gameController;

    /*
     * Default constructor
     */
    public AbstractMenu(GameController gameController) {
        this.gameController = gameController;

        /* Information rectangles */ //+20, -90
        authorRect = new Rectangle(gameController.getWidth() / 2 + 3, gameController.getHeight() - 22, 70, 18);
        versionRect = new Rectangle(gameController.getWidth() / 2 - 73, gameController.getHeight() - 22, 66, 18);
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

    public final void drawInfoPanel(Graphics2D g) {
        g.setColor(menuFontColor);
        g.setFont(infoPanelFont);

        /* Render version number */
        g.drawString(textHandler.GAME_VERSION, versionRect.x + 3, versionRect.y + 15);
        g.draw(versionRect);

        /* Render email tag */
        g.drawString(textHandler.AUTHOR_WEBSITE, authorRect.x + 2, authorRect.y + 13);
        g.draw(authorRect);
    }

    protected void drawMenuTitle(Graphics2D g) {
        g.setColor(menuFontColor);
        g.setFont(titleFont);
        drawCenterString(textHandler.GAME_TITLE, 100, g, titleFont);
    }

    protected void drawMenuToolTip(String msg, Graphics2D g) {
        g.setColor(menuFontColor);
        drawCenterString(msg, 665, g, tooltipFont);
    }

    public void resetInputTimer() {
        inputTimer = INIT_INPUT_TIMER;
    }

    public void decInputTimer() {
        if (inputTimer > 0) {
            inputTimer--;
        }
    }

    public boolean isInputAvailable() {
        return inputTimer == 0;
    }

}
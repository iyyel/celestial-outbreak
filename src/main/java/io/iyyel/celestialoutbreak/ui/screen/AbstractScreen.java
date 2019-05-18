package io.iyyel.celestialoutbreak.ui.screen;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.dal.dao.PlayerDAO;
import io.iyyel.celestialoutbreak.dal.dao.contract.IPlayerDAO;
import io.iyyel.celestialoutbreak.handler.*;
import io.iyyel.celestialoutbreak.util.Util;

import java.awt.*;

public abstract class AbstractScreen {

    protected final Util util = Util.getInstance();
    protected final OptionsHandler optionsHandler = OptionsHandler.getInstance();
    protected final TextHandler textHandler = TextHandler.getInstance();
    protected final InputHandler inputHandler = InputHandler.getInstance();
    protected final SoundHandler soundHandler = SoundHandler.getInstance();
    protected final LevelHandler levelHandler = LevelHandler.getInstance();
    protected final IPlayerDAO playerDAO = PlayerDAO.getInstance();

    protected final SoundHandler.SoundClip menuNavClip = soundHandler.getSoundClip(textHandler.SOUND_FILE_NAME_MENU_BTN_NAV);
    protected final SoundHandler.SoundClip menuUseClip = soundHandler.getSoundClip(textHandler.SOUND_FILE_NAME_MENU_BTN_USE);
    protected final SoundHandler.SoundClip menuBadActionClip = soundHandler.getSoundClip(textHandler.SOUND_FILE_NAME_BAD_ACTION);

    private final Font titleFont = util.getGameFont().deriveFont(52F);
    private final Font subtitleFont = util.getGameFont().deriveFont(36F);
    private final Font screenTooltipFont = util.getGameFont().deriveFont(18F);
    private final Font infoPanelFont = util.getGameFont().deriveFont(14F);
    protected final Font msgFont = util.getGameFont().deriveFont(26F);
    protected final Font inputBtnFont = util.getGameFont().deriveFont(20F);

    protected Color screenFontColor = optionsHandler.getMenuFontColor();
    protected Color menuBtnColor = optionsHandler.getMenuBtnColor();
    protected Color menuSelectedBtnColor = optionsHandler.getMenuBtnSelectedColor();
    protected Color menuBtnPlayerSelectedColor = optionsHandler.getMenuBtnPlayerSelectedColor();
    protected Color menuBtnPlayerDeletedColor = optionsHandler.getMenuBtnPlayerDeletedColor();

    private final Rectangle versionRect, authorRect;

    private final int INIT_INPUT_TIMER = 12;
    private int inputTimer = INIT_INPUT_TIMER;

    protected final int BTN_Y_OFFSET = 33;

    protected final GameController gameController;

    public AbstractScreen(GameController gameController) {
        this.gameController = gameController;

        authorRect = new Rectangle(gameController.getWidth() / 2 + 3, gameController.getHeight() - 22, 70, 18);
        versionRect = new Rectangle(gameController.getWidth() / 2 - 73, gameController.getHeight() - 22, 66, 18);
    }

    public abstract void update();

    public abstract void render(Graphics2D g);

    protected void drawScreenCenterString(String text, int y, Font font, Graphics2D g) {
        g.setColor(screenFontColor);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);
        int x = (gameController.getWidth() - metrics.stringWidth(text)) / 2;
        g.drawString(text, x, y);
    }

    protected void drawScreenMessage(String text, int yOffset, Graphics2D g) {
        drawScreenCenterString(text, getHalfHeight() + yOffset, msgFont, g);
    }

    protected void drawScreenInfoPanel(Graphics2D g) {
        g.setColor(screenFontColor);
        g.setFont(infoPanelFont);

        /* Version number */
        g.drawString(textHandler.GAME_VERSION, versionRect.x + 3, versionRect.y + 15);
        g.draw(versionRect);

        /* Email tag */
        g.drawString(textHandler.AUTHOR_WEBSITE, authorRect.x + 2, authorRect.y + 13);
        g.draw(authorRect);
    }

    protected void drawScreenTitle(Graphics2D g) {
        g.setColor(screenFontColor);
        g.setFont(titleFont);
        drawScreenCenterString(textHandler.GAME_TITLE, 100, titleFont, g);
    }

    protected void drawScreenSubtitle(String subtitle, Graphics2D g) {
        g.setColor(screenFontColor);
        g.setFont(subtitleFont);
        drawScreenCenterString(subtitle, getHalfHeight() - 170, subtitleFont, g);
    }

    protected void drawScreenToolTip(String tooltip, Graphics2D g) {
        g.setColor(screenFontColor);
        g.setFont(screenTooltipFont);
        drawScreenCenterString(tooltip, 665, screenTooltipFont, g);
    }

    protected void resetInputTimer() {
        inputTimer = INIT_INPUT_TIMER;
    }

    protected void decInputTimer() {
        if (inputTimer > 0) {
            inputTimer--;
        }
    }

    protected boolean isInputAvailable() {
        return inputTimer == 0;
    }

    protected int getHalfWidth() {
        return gameController.getWidth() / 2;
    }

    protected int getHalfHeight() {
        return gameController.getHeight() / 2;
    }

}
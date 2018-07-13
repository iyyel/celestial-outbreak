package io.iyyel.celestialoutbreak.menu.main_menu;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.menu.AbstractMenu;

import java.awt.*;

public final class OptionsMenu extends AbstractMenu {

    private final Rectangle playerRect, configurationRect;
    private final Font btnFont;

    private String[] options = {textHandler.BTN_PLAYER_OPTIONS_TEXT, textHandler.BTN_CONFIGURATION_OPTIONS_TEXT};
    private Color[] rectColors;

    private int selected = 0;
    private int yBtnOffset = 33;

    public OptionsMenu(GameController gameController) {
        super(gameController);

        int initialBtnYPos = 230;
        int btnYInc = 75;

        /* buttons */
        playerRect = new Rectangle(gameController.getWidth() / 2 - 195, initialBtnYPos, 390, 50);
        configurationRect = new Rectangle(gameController.getWidth() / 2 - 195, initialBtnYPos + btnYInc, 390, 50);

        rectColors = new Color[options.length];

        for (Color c : rectColors)
            c = menuBtnColor;

        btnFont = utils.getGameFont().deriveFont(20F);
    }

    @Override
    public void update() {
        decInputTimer();

        if (inputHandler.isCancelPressed() && isInputAvailable()) {
            resetInputTimer();
            menuUseClip.play(false);
            gameController.switchState(GameController.State.MAIN_MENU);
        }

        if (inputHandler.isDownPressed() && selected < options.length - 1 && isInputAvailable()) {
            resetInputTimer();
            selected++;
            menuNavClip.play(false);
        }

        if (inputHandler.isUpPressed() && selected > 0 && isInputAvailable()) {
            resetInputTimer();
            selected--;
            menuNavClip.play(false);
        }

        for (int i = 0, n = options.length; i < n; i++) {
            if (selected == i) {
                rectColors[i] = menuSelectedBtnColor;

                if (inputHandler.isOKPressed() && isInputAvailable()) {
                    menuUseClip.play(false);
                    resetInputTimer();

                    switch (i) {
                        case 0:
                            // Player options
                            gameController.switchState(GameController.State.PLAYER_OPTIONS_MENU);
                            break;
                        case 1:
                            // Configuration options
                            gameController.switchState(GameController.State.CONFIG_OPTIONS_SCREEN);
                            break;
                        default:
                            break;
                    }
                }
            } else {
                rectColors[i] = menuBtnColor;
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        /* Render game title */
        drawMenuTitle(g);

        /* Show player name */
        drawSubmenuTitle(textHandler.TITLE_OPTIONS_MENU, g);

        /* Render buttons  */
        g.setFont(btnFont);

        /* Play button */
        g.setColor(menuFontColor);
        drawCenterString(options[0], playerRect.y + yBtnOffset, g, btnFont);
        g.setColor(rectColors[0]);
        g.draw(playerRect);

        /* Score button */
        g.setColor(menuFontColor);
        drawCenterString(options[1], configurationRect.y + yBtnOffset, g, btnFont);
        g.setColor(rectColors[1]);
        g.draw(configurationRect);

        drawInformationPanel(g);
    }

}
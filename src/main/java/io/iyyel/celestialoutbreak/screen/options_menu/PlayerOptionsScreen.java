package io.iyyel.celestialoutbreak.screen.options_menu;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.screen.AbstractScreen;

import java.awt.*;

public final class PlayerOptionsScreen extends AbstractScreen {

    private final Rectangle selectRect, createRect, deleteRect;
    private final Font btnFont;

    private String[] options = {textHandler.BTN_SELECT_PLAYER_TEXT, textHandler.BTN_CREATE_PLAYER_TEXT, textHandler.BTN_DELETE_DELETE_TEXT};
    private Color[] rectColors;

    private int selected = 0;

    public PlayerOptionsScreen(GameController gameController) {
        super(gameController);

        int initialBtnYPos = 230;
        int btnYIncrement = 75;

        /* Menu buttons */
        selectRect = new Rectangle(gameController.getWidth() / 2 - 135, initialBtnYPos, 270, 50);
        createRect = new Rectangle(gameController.getWidth() / 2 - 135, initialBtnYPos + btnYIncrement, 270, 50);
        deleteRect = new Rectangle(gameController.getWidth() / 2 - 135, initialBtnYPos + btnYIncrement * 2, 270, 50);

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
            selected = 0;
            menuUseClip.play(false);
            gameController.switchState(GameController.State.OPTIONS);
        }

        if (inputHandler.isUpPressed() && selected > 0 && isInputAvailable()) {
            resetInputTimer();
            selected--;
            menuNavClip.play(false);
        }

        if (inputHandler.isDownPressed() && selected < options.length - 1 && isInputAvailable()) {
            resetInputTimer();
            selected++;
            menuNavClip.play(false);
        }

        for (int i = 0, n = options.length; i < n; i++) {
            if (selected == i) {
                rectColors[i] = menuSelectedBtnColor;

                if (inputHandler.isOKPressed() && isInputAvailable()) {
                    resetInputTimer();
                    menuUseClip.play(false);

                    switch (i) {
                        case 0:
                            gameController.switchState(GameController.State.PLAYER_SELECT);
                            break;
                        case 1:
                            gameController.switchState(GameController.State.PLAYER_CREATE);
                            break;
                        case 2:
                            gameController.switchState(GameController.State.PLAYER_DELETE);
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
        drawScreenTitle(g);
        drawScreenSubtitle(textHandler.TITLE_PLAYER_OPTIONS_SCREEN, g);

        /* Render buttons  */
        g.setFont(btnFont);

        /* Select button */
        g.setColor(screenFontColor);
        g.setFont(btnFont);
        drawScreenCenterString(options[0], selectRect.y + BTN_Y_OFFSET, inputBtnFont, g);
        g.setColor(rectColors[0]);
        g.draw(selectRect);

        /* New button */
        g.setColor(screenFontColor);
        g.setFont(btnFont);
        drawScreenCenterString(options[1], createRect.y + BTN_Y_OFFSET, inputBtnFont, g);
        g.setColor(rectColors[1]);
        g.draw(createRect);

        /* Remove button */
        g.setColor(screenFontColor);
        g.setFont(btnFont);
        drawScreenCenterString(options[2], deleteRect.y + BTN_Y_OFFSET, inputBtnFont, g);
        g.setColor(rectColors[2]);
        g.draw(deleteRect);

        drawScreenInfoPanel(g);
    }

}
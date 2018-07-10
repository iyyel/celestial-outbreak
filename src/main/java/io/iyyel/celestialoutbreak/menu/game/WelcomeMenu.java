package io.iyyel.celestialoutbreak.menu.game;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.handler.InputHandler;
import io.iyyel.celestialoutbreak.handler.SoundHandler;
import io.iyyel.celestialoutbreak.menu.Menu;

import java.awt.*;

public final class WelcomeMenu extends Menu {

    private final Rectangle startRect, exitRect;
    private final Font btnFont;

    private String[] options = {"START", textHandler.BTN_EXIT_TEXT};
    private Color[] rectColors;

    private Color rectColor, selectedColor;

    private int selected = 0;
    private int inputTimer = 18;
    private int yBtnOffset = 33;

    private final SoundHandler soundHandler;

    public WelcomeMenu(GameController gameController, InputHandler inputHandler, SoundHandler soundHandler,
                       Color fontColor, Color rectColor, Color selectedColor) {
        super(gameController, inputHandler, fontColor);
        this.soundHandler = soundHandler;
        this.rectColor = rectColor;
        this.selectedColor = selectedColor;

        int initialBtnYPos = 230;
        int btnYIncrement = 75;

        startRect = new Rectangle(gameController.getWidth() / 2 - 80, initialBtnYPos + btnYIncrement * 3, 160, 50);
        exitRect = new Rectangle(gameController.getWidth() / 2 - 80, initialBtnYPos + btnYIncrement * 4, 160, 50);

        rectColors = new Color[options.length];

        for (Color c : rectColors)
            c = rectColor;

        btnFont = utils.getGameFont().deriveFont(20F);
    }

    @Override
    public void update() {
        if (inputTimer > 0)
            inputTimer--;

        if (inputHandler.isDownPressed() && selected < options.length - 1 && inputTimer == 0) {
            selected++;
            soundHandler.MENU_BTN_SELECTION_CLIP.play(false);
            inputTimer = 10;
        }

        if (inputHandler.isUpPressed() && selected > 0 && inputTimer == 0) {
            selected--;
            soundHandler.MENU_BTN_SELECTION_CLIP.play(false);
            inputTimer = 10;
        }

        for (int i = 0, n = options.length; i < n; i++) {
            if (selected == i) {
                rectColors[i] = selectedColor;

                if (inputHandler.isUsePressed() && inputTimer == 0) {
                    inputTimer = 10;

                    switch (i) {
                        case 0:
                            if (playerDAO.getPlayerList().isEmpty()) {
                                gameController.switchState(GameController.State.NEW_PLAYER_MENU);
                            } else {
                                gameController.switchState(GameController.State.MAIN_MENU);
                            }
                            break;
                        case 1:
                            gameController.switchState(GameController.State.EXIT_MENU);
                            break;
                        default:
                            break;
                    }
                }
            } else {
                rectColors[i] = rectColor;
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(fontColor);
        drawMenuTitle(g);

        drawSubmenuTitle("Welcome", g);

        drawXCenteredString("Are you ready to explore planets and their secrets?", 300, g, msgFont);
        drawXCenteredString("Then please, do proceed.", 350, g, msgFont);

        /* Start button */
        g.setColor(fontColor);
        drawXCenteredString(options[0], startRect.y + yBtnOffset, g, btnFont);
        g.setColor(rectColors[0]);
        g.draw(startRect);

        /* Exit button */
        g.setColor(fontColor);
        drawXCenteredString(options[1], exitRect.y + yBtnOffset, g, btnFont);
        g.setColor(rectColors[1]);
        g.draw(exitRect);

        drawInformationPanel(g);
    }

}
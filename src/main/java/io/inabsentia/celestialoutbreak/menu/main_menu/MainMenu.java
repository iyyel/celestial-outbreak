package io.inabsentia.celestialoutbreak.menu.main_menu;

import io.inabsentia.celestialoutbreak.controller.GameController;
import io.inabsentia.celestialoutbreak.controller.GameController.State;
import io.inabsentia.celestialoutbreak.handler.InputHandler;
import io.inabsentia.celestialoutbreak.handler.SoundHandler;
import io.inabsentia.celestialoutbreak.menu.Menu;

import java.awt.*;

public final class MainMenu extends Menu {

    private final Rectangle playRect, scoreRect, controlsRect, settingsRect, aboutRect, exitRect;
    private final Font btnFont;

    private String[] options = {textHandler.BTN_PLAY_TEXT, textHandler.BTN_SCORES_TEXT, textHandler.BTN_CONTROLS_TEXT,
            textHandler.BTN_SETTINGS_TEXT, textHandler.BTN_ABOUT_TEXT, textHandler.BTN_EXIT_TEXT};
    private Color[] rectColors;

    private Color rectColor, selectedColor;

    private int selected = 0;
    private int inputTimer = 18;

    private final SoundHandler soundHandler;

    public MainMenu(GameController gameController, InputHandler inputHandler, SoundHandler soundHandler,
                    Color fontColor, Color rectColor, Color selectedColor) {
        super(gameController, inputHandler, fontColor);
        this.soundHandler = soundHandler;
        this.rectColor = rectColor;
        this.selectedColor = selectedColor;

        int initialBtnYPos = 230;
        int btnYIncrement = 75;

        /* Menu buttons */
        playRect = new Rectangle(gameController.getWidth() / 2 - 50, initialBtnYPos, 100, 50);
        scoreRect = new Rectangle(gameController.getWidth() / 2 - 65, initialBtnYPos + btnYIncrement, 130, 50);
        controlsRect = new Rectangle(gameController.getWidth() / 2 - 80, initialBtnYPos + btnYIncrement * 2, 160, 50);
        settingsRect = new Rectangle(gameController.getWidth() / 2 - 75, initialBtnYPos + btnYIncrement * 3, 150, 50);
        aboutRect = new Rectangle(gameController.getWidth() / 2 - 60, initialBtnYPos + btnYIncrement * 4, 120, 50);
        exitRect = new Rectangle(gameController.getWidth() / 2 - 50, initialBtnYPos + btnYIncrement * 5, 100, 50);

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
                            gameController.switchState(State.PLAY);
                            break;
                        case 1:
                            gameController.switchState(State.SCORES_MENU);
                            break;
                        case 2:
                            gameController.switchState(State.CONTROLS_MENU);
                            break;
                        case 3:
                            gameController.switchState(State.SETTINGS_MENU);
                            break;
                        case 4:
                            gameController.switchState(State.ABOUT_MENU);
                            break;
                        case 5:
                            gameController.switchState(State.EXIT_MENU);
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
        int yBtnOffset = 33;

        /* Render game title */
        drawMenuTitle(g);

        /* Show player name */
        drawSubmenuTitle("Welcome Player", g);

        /* Render buttons  */
        g.setFont(btnFont);

        /* Play button */
        g.setColor(fontColor);
        drawXCenteredString(options[0], playRect.y + yBtnOffset, g, btnFont);
        g.setColor(rectColors[0]);
        g.draw(playRect);

        /* Score button */
        g.setColor(fontColor);
        drawXCenteredString(options[1], scoreRect.y + yBtnOffset, g, btnFont);
        g.setColor(rectColors[1]);
        g.draw(scoreRect);

        /* Controls button */
        g.setColor(fontColor);
        drawXCenteredString(options[2], controlsRect.y + yBtnOffset, g, btnFont);
        g.setColor(rectColors[2]);
        g.draw(controlsRect);

        /* Settings button */
        g.setColor(fontColor);
        drawXCenteredString(options[3], settingsRect.y + yBtnOffset, g, btnFont);
        g.setColor(rectColors[3]);
        g.draw(settingsRect);

        /* About button */
        g.setColor(fontColor);
        drawXCenteredString(options[4], aboutRect.y + yBtnOffset, g, btnFont);
        g.setColor(rectColors[4]);
        g.draw(aboutRect);

        /* Exit button */
        g.setColor(fontColor);
        drawXCenteredString(options[5], exitRect.y + yBtnOffset, g, btnFont);
        g.setColor(rectColors[5]);
        g.draw(exitRect);

        drawInformationPanel(g);
    }

}
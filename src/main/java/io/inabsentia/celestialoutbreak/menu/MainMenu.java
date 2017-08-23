package io.inabsentia.celestialoutbreak.menu;

import io.inabsentia.celestialoutbreak.controller.Game;
import io.inabsentia.celestialoutbreak.entity.State;
import io.inabsentia.celestialoutbreak.handler.InputHandler;
import io.inabsentia.celestialoutbreak.handler.SoundHandler;

import java.awt.*;

public class MainMenu extends Menu {

    private final Rectangle playRect, scoreRect, controlsRect, settingsRect, aboutRect, exitRect;
    private final Font btnFont;

    private String[] options = {textHandler.BTN_PLAY_TEXT, textHandler.BTN_SCORES_TEXT, textHandler.BTN_CONTROLS_TEXT, textHandler.BTN_SETTINGS_TEXT, textHandler.BTN_ABOUT_TEXT, textHandler.BTN_EXIT_TEXT};
    private Color[] rectColors;

    private Color rectColor, selectedColor;

    private int selected = 0;
    private int inputTimer = 18;

    private final SoundHandler soundHandler;

    public MainMenu(Game game, InputHandler inputHandler, SoundHandler soundHandler, Color fontColor, Color rectColor, Color selectedColor) {
        super(game, inputHandler, fontColor);
        this.soundHandler = soundHandler;
        this.rectColor = rectColor;
        this.selectedColor = selectedColor;

        int initialBtnYPos = 230;
        int btnYIncrement = 75;

        /* Menu buttons */
        playRect = new Rectangle(game.getWidth() / 2 - 50, initialBtnYPos, 100, 50);
        scoreRect = new Rectangle(game.getWidth() / 2 - 65, initialBtnYPos + btnYIncrement, 130, 50);
        controlsRect = new Rectangle(game.getWidth() / 2 - 80, initialBtnYPos + btnYIncrement * 2, 160, 50);
        settingsRect = new Rectangle(game.getWidth() / 2 - 75, initialBtnYPos + btnYIncrement * 3, 150, 50);
        aboutRect = new Rectangle(game.getWidth() / 2 - 60, initialBtnYPos + btnYIncrement * 4, 120, 50);
        exitRect = new Rectangle(game.getWidth() / 2 - 50, initialBtnYPos + btnYIncrement * 5, 100, 50);

        rectColors = new Color[options.length];

        for (Color c : rectColors) c = rectColor;

        btnFont = gameUtils.getGameFont().deriveFont(20F);
    }

    @Override
    public void update() {
        if (inputTimer > 0) inputTimer--;

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

                if (inputHandler.isUsePressed()) {
                    switch (i) {
                        case 0:
                            game.switchState(State.NEW_LEVEL);
                            break;
                        case 1:
                            game.switchState(State.SCORES);
                            break;
                        case 2:
                            game.switchState(State.CONTROLS);
                            break;
                        case 3:
                            game.switchState(State.SETTINGS);
                            break;
                        case 4:
                            game.switchState(State.ABOUT);
                            break;
                        case 5:
                            game.switchState(State.EXIT);
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

		/* Render io.inabsentia.celestialoutbreak.MENU_CLIP buttons */
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
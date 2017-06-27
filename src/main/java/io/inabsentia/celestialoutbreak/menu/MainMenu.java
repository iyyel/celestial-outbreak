package io.inabsentia.celestialoutbreak.menu;

import io.inabsentia.celestialoutbreak.controller.Game;
import io.inabsentia.celestialoutbreak.entity.State;
import io.inabsentia.celestialoutbreak.handler.InputHandler;
import io.inabsentia.celestialoutbreak.utils.GameUtils;

import java.awt.*;

public class MainMenu extends Menu {

    private final Rectangle playRect, settingsRect, scoreRect, aboutRect, exitRect;
    private final Font btnFont;

    private String[] options = {textHandler.playBtn, textHandler.scoreBtn, textHandler.settingsBtn, textHandler.aboutBtn, textHandler.exitBtn};
    private Color[] rectColors;

    private Color rectColor, selectedColor;

    private int selected = 0;
    private int inputTimer = 18;

    public MainMenu(Game game, InputHandler inputHandler, Color fontColor, Color rectColor, Color selectedColor) {
        super(game, inputHandler, fontColor);
        this.rectColor = rectColor;
        this.selectedColor = selectedColor;

        /* Menu buttons */
        playRect = new Rectangle(game.getWidth() / 2 - 50, 250, 100, 50);
        scoreRect = new Rectangle(game.getWidth() / 2 - 65, 330, 130, 50);
        settingsRect = new Rectangle(game.getWidth() / 2 - 75, 410, 150, 50);
        aboutRect = new Rectangle(game.getWidth() / 2 - 60, 490, 120, 50);
        exitRect = new Rectangle(game.getWidth() / 2 - 50, 570, 100, 50);

        rectColors = new Color[options.length];

        for (Color c : rectColors) c = rectColor;

        btnFont = gameUtils.getGameFont().deriveFont(20F);
    }

    @Override
    public void update() {
        if (inputTimer > 0) inputTimer--;

        if (inputHandler.isDownPressed() && selected < options.length - 1 && inputTimer == 0) {
            selected++;
            inputTimer = 10;
        }

        if (inputHandler.isUpPressed() && selected > 0 && inputTimer == 0) {
            selected--;
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
                            game.switchState(State.SETTINGS);
                            break;
                        case 3:
                            game.switchState(State.ABOUT);
                            break;
                        case 4:
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
        drawXCenteredString("Welcome Player", 200, g, msgFont);

		/* Render io.inabsentia.celestialoutbreak.menu buttons */
        g.setFont(btnFont);

        g.setColor(fontColor);
        drawXCenteredString(options[0], playRect.y + yBtnOffset, g, btnFont);
        g.setColor(rectColors[0]);
        g.draw(playRect);

        g.setColor(fontColor);
        drawXCenteredString(options[1], scoreRect.y + yBtnOffset, g, btnFont);
        g.setColor(rectColors[1]);
        g.draw(scoreRect);

        g.setColor(fontColor);
        drawXCenteredString(options[2], settingsRect.y + yBtnOffset, g, btnFont);
        g.setColor(rectColors[2]);
        g.draw(settingsRect);

        g.setColor(fontColor);
        drawXCenteredString(options[3], aboutRect.y + yBtnOffset, g, btnFont);
        g.setColor(rectColors[3]);
        g.draw(aboutRect);

        g.setColor(fontColor);
        drawXCenteredString(options[4], exitRect.y + yBtnOffset, g, btnFont);
        g.setColor(rectColors[4]);
        g.draw(exitRect);

        drawInformationPanel(g);
    }

}
package io.inabsentia.celestialoutbreak.game.menu;

import io.inabsentia.celestialoutbreak.game.controller.Game;
import io.inabsentia.celestialoutbreak.game.entity.State;
import io.inabsentia.celestialoutbreak.game.handler.InputHandler;

import java.awt.*;

public class MainMenu extends Menu {

    private final Rectangle playRect, settingsRect, scoreRect, aboutRect, quitRect, versionRect, emailRect;
    private final Font titleFont, btnFont, versionFont, emailFont;

    private String[] options = {textHandler.playBtn, textHandler.scoreBtn, textHandler.settingsBtn, textHandler.aboutBtn, textHandler.quitBtn};
    private Color[] rectColors;

    private Color fontColor, rectColor, selectedColor;

    private int selected = 0;
    private int inputTimer = 18;

    public MainMenu(Game game, InputHandler inputHandler, Color fontColor, Color rectColor, Color selectedColor) {
        super(game, inputHandler);
        this.fontColor = fontColor;
        this.rectColor = rectColor;
        this.selectedColor = selectedColor;

        playRect = new Rectangle(game.getWidth() / 2 - 50, 250, 100, 50);
        scoreRect = new Rectangle(game.getWidth() / 2 - 55, 330, 110, 50);
        settingsRect = new Rectangle(game.getWidth() / 2 - 68, 410, 135, 50);
        aboutRect = new Rectangle(game.getWidth() / 2 - 50, 490, 100, 50);
        quitRect = new Rectangle(game.getWidth() / 2 - 50, 570, 100, 50);
        versionRect = new Rectangle(game.getWidth() / 2 + 15, game.getHeight() - 20, 40, 15);
        emailRect = new Rectangle(game.getWidth() / 2 - 55, game.getHeight() - 20, 65, 15);

        rectColors = new Color[options.length];

        for (Color c : rectColors) c = rectColor;

        titleFont = new Font("Verdana", Font.PLAIN, 52);
        btnFont = new Font("Verdana", Font.PLAIN, 25);
        versionFont = new Font("Verdana", Font.PLAIN, 10);
        emailFont = new Font("Verdana", Font.PLAIN, 10);
    }

    @Override
    public void update() {
        if (inputTimer > 0) inputTimer--;

        if (inputHandler.down && selected < options.length - 1 && inputTimer == 0) {
            selected++;
            inputTimer = 10;
        }

        if (inputHandler.up && selected > 0 && inputTimer == 0) {
            selected--;
            inputTimer = 10;
        }

        for (int i = 0, n = options.length; i < n; i++) {

            if (selected == i) {
                rectColors[i] = selectedColor;

                if (inputHandler.use) {
                    switch (i) {
                        case 0:
                            game.changeState(State.PAUSE);
                            break;
                        case 1:
                            System.out.println("SCORES!");
                            break;
                        case 2:
                            System.out.println("SETTINGS!");
                            break;
                        case 3:
                            System.out.println("ABOUT!");
                            break;
                        case 4:
                            System.out.println("QUIT!");
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
        /* Render game title */
        g.setColor(fontColor);
        g.setFont(titleFont);
        g.drawString(textHandler.TITLE, game.getWidth() / 2 - 220, 100);

		/* Render io.inabsentia.celestialoutbreak.game.menu buttons */
        g.setFont(btnFont);

        g.setColor(fontColor);
        g.drawString(options[0], playRect.x + 20, playRect.y + 35);
        g.setColor(rectColors[0]);
        g.draw(playRect);

        g.setColor(fontColor);
        g.drawString(options[1], scoreRect.x + 4, scoreRect.y + 35);
        g.setColor(rectColors[1]);
        g.draw(scoreRect);

        g.setColor(fontColor);
        g.drawString(options[2], settingsRect.x + 6, settingsRect.y + 35);
        g.setColor(rectColors[2]);
        g.draw(settingsRect);

        g.setColor(fontColor);
        g.drawString(options[3], aboutRect.x + 6, aboutRect.y + 35);
        g.setColor(rectColors[3]);
        g.draw(aboutRect);

        g.setColor(fontColor);
        g.drawString(options[4], quitRect.x + 17, quitRect.y + 35);
        g.setColor(rectColors[4]);
        g.draw(quitRect);

		/* Render version number */
        g.setColor(fontColor);
        g.setFont(versionFont);
        g.drawString(textHandler.VERSION, versionRect.x + 3, versionRect.y + 11);
        g.draw(versionRect);

        /* Render email tag */
        g.setColor(fontColor);
        g.setFont(emailFont);
        g.drawString(textHandler.EMAIL, emailRect.x + 3, emailRect.y + 11);
        g.draw(emailRect);
    }

}
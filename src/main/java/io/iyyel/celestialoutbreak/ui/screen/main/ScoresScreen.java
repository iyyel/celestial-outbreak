package io.iyyel.celestialoutbreak.ui.screen.main;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.data.dao.contract.IPlayerDAO;
import io.iyyel.celestialoutbreak.ui.screen.AbstractScreen;

import java.awt.*;

public final class ScoresScreen extends AbstractScreen {

    private Rectangle[] levelRects;
    private Color[] levelRectColors;

    private Font levelInfoFont = util.getPanelFont().deriveFont(16F);

    private int levelAmount = levelHandler.getLevelAmount();

    public ScoresScreen(GameController gameController) {
        super(gameController);
        levelRects = new Rectangle[levelAmount];
        levelRectColors = new Color[levelAmount];

        int initialX = 50;
        int initialY = 215;
        int x = initialX;
        int y = initialY;
        int xInc = 300;
        int yInc = 110;

        for (int i = 0; i < levelAmount; i++) {
            levelRectColors[i] = menuBtnColor;

            if (i % 4 == 0 && i != 0) {
                x += xInc;
                y = initialY;
            }
            levelRects[i] = new Rectangle(x, y, 280, 90);
            y += yInc;
        }
    }

    @Override
    public void update() {
        decInputTimer();

        if (inputHandler.isCancelPressed() && isInputAvailable()) {
            resetInputTimer();
            menuUseClip.play(false);
            gameController.switchState(GameController.State.MAIN);
        }

    }

    @Override
    public void render(Graphics2D g) {
        drawScreenTitle(g);
        drawScreenSubtitle(textHandler.TITLE_SCORES_SCREEN, g);

        /* Render buttons  */
        if (levelRects.length <= 0) {
            g.setFont(inputBtnFont);
            g.setColor(screenFontColor);
            g.drawString("No levels were loaded.", 20, gameController.getHeight() / 2);
        } else {
            for (int i = 0; i < levelHandler.getLevelAmount(); i++) {
                g.setFont(inputBtnFont);
                g.setColor(levelHandler.getLevelColors()[i]);
                g.drawString(levelHandler.getLevelNames()[i], levelRects[i].x + 5, levelRects[i].y + 27);

                g.setFont(levelInfoFont);
                g.setColor(menuBtnColor);

                String blockHitPoints = null;
                try {
                    blockHitPoints = textHandler.getFixedString("Player: " + playerDAO.getSelectedPlayer(), 15);
                } catch (IPlayerDAO.PlayerDAOException e) {
                    e.printStackTrace();
                }
                g.drawString(blockHitPoints, levelRects[i].x + 5, levelRects[i].y + 55);

                String score = textHandler.getFixedString("Score:  " + "123456", 15);
                String time = textHandler.getFixedString(" Time: " + "02:03", 12);
                g.drawString(score + time, levelRects[i].x + 5, levelRects[i].y + 75);

                g.setColor(levelRectColors[i]);
                g.draw(levelRects[i]);
            }
        }

        drawScreenInfoPanel(g);
    }

}
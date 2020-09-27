package io.iyyel.celestialoutbreak.ui.screen.main;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.data.dao.HighScoreDAO;
import io.iyyel.celestialoutbreak.data.dao.interfaces.IHighScoreDAO;
import io.iyyel.celestialoutbreak.data.dto.HighScoreDTO;
import io.iyyel.celestialoutbreak.handler.LevelHandler;
import io.iyyel.celestialoutbreak.ui.screen.AbstractScreen;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public final class HighScoresScreen extends AbstractScreen {

    private final RoundRectangle2D[] levelRects;
    private final Color[] levelRectColors;
    private boolean isFirstRender = true;

    private final Font levelInfoFont = util.getGameFont().deriveFont(15F);

    private final static IHighScoreDAO highScoreDAO = HighScoreDAO.getInstance();
    private final LevelHandler levelHandler = LevelHandler.getInstance();

    private final int levelAmount = levelHandler.getLevelAmount();

    private final int buttonWrap = 3;

    public HighScoresScreen(GameController gameController) {
        super(gameController);
        levelRects = new RoundRectangle2D.Float[levelAmount];
        levelRectColors = new Color[levelAmount];

        int initialX = 105;
        int initialY = 230;
        int x = initialX;
        int y = initialY;
        int xInc = 275;
        int yInc = 155;

        for (int i = 0; i < levelAmount; i++) {
            levelRectColors[i] = menuBtnColor;

            if (i % buttonWrap == 0 && i != 0) {
                x += xInc;
                y = initialY;
            }
            levelRects[i] = new RoundRectangle2D.Float(x, y, 250, 130, 10, 10);
            y += yInc;
        }

    }

    @Override
    public void update() {
        decInputTimer();

        if (inputHandler.isCancelPressed() && isInputAvailable()) {
            resetInputTimer();
            menuNavClip.play(false);
            isFirstRender = true;
            gameController.switchState(GameController.State.MAIN);
        }

    }

    @Override
    public void render(Graphics2D g) {
        drawTitle(g);
        drawSubtitle(textHandler.TITLE_HIGH_SCORES_SCREEN, g);

        if (isFirstRender) {
            isFirstRender = false;
            try {
                highScoreDAO.loadHighScoreList();
            } catch (IHighScoreDAO.HighScoreDAOException e) {
                e.printStackTrace();
            }
        }

        /* Render buttons  */
        if (levelRects.length <= 0) {
            g.setFont(inputBtnFont);
            g.setColor(screenFontColor);
            g.drawString("No levels were loaded.", 20, gameController.getHeight() / 2);
        } else {
            for (int i = 0; i < levelAmount; i++) {
                g.setFont(inputBtnFont);
                g.setColor(levelHandler.getLevelColors()[i]);
                g.drawString(levelHandler.getLevelNames()[i], (int) levelRects[i].getX() + 5, (int) levelRects[i].getY() + 27);

                g.setFont(levelInfoFont);
                g.setColor(menuBtnColor);

                HighScoreDTO highScoreDTO = null;
                try {
                    highScoreDTO = highScoreDAO.getHighScore(levelHandler.getLevelNames()[i]);
                } catch (IHighScoreDAO.HighScoreDAOException e) {
                    e.printStackTrace();
                }

                String player = textHandler.getFixedString("Player: ", 15);
                String score = textHandler.getFixedString("High Score: 0", 20);
                String time = textHandler.getFixedString("Time: 00:00", 12);

                if (highScoreDTO != null) {
                    player = textHandler.getFixedString("Player: " + highScoreDTO.getPlayer(), 16);
                    score = textHandler.getFixedString("High Score:  " + highScoreDTO.getScore(), 20);
                    time = textHandler.getFixedString("Time: " + textHandler.getTimeString(highScoreDTO.getTime()), 12);
                }

                g.drawString(player, (int) levelRects[i].getX() + 5, (int) levelRects[i].getY() + 70);
                g.drawString(score, (int) levelRects[i].getX() + 5, (int) levelRects[i].getY() + 90);
                g.drawString(time, (int) levelRects[i].getX() + 5, (int) levelRects[i].getY() + 110);

                g.setColor(levelRectColors[i]);
                g.draw(levelRects[i]);
            }
        }

        drawInfoPanel(g);
    }

}
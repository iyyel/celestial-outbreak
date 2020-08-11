package io.iyyel.celestialoutbreak.ui.screen.main;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.data.dao.HighScoreDAO;
import io.iyyel.celestialoutbreak.data.dao.interfaces.IHighScoreDAO;
import io.iyyel.celestialoutbreak.data.dto.HighScoreDTO;
import io.iyyel.celestialoutbreak.handler.LevelHandler;
import io.iyyel.celestialoutbreak.ui.screen.AbstractScreen;

import java.awt.*;

public final class ScoresScreen extends AbstractScreen {

    private Rectangle[] levelRects;
    private Color[] levelRectColors;
    private boolean isFirstRender = true;

    private Font levelInfoFont = util.getPanelFont().deriveFont(16F);

    private final static IHighScoreDAO highScoreDAO = HighScoreDAO.getInstance();
    private final LevelHandler levelHandler = LevelHandler.getInstance();

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
            isFirstRender = true;
            gameController.switchState(GameController.State.MAIN);
        }

    }

    @Override
    public void render(Graphics2D g) {
        drawTitle(g);
        drawSubtitle(textHandler.TITLE_SCORES_SCREEN, g);

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
                g.drawString(levelHandler.getLevelNames()[i], levelRects[i].x + 5, levelRects[i].y + 27);

                g.setFont(levelInfoFont);
                g.setColor(menuBtnColor);

                HighScoreDTO highScoreDTO = null;
                try {
                    highScoreDTO = highScoreDAO.getHighScore(levelHandler.getLevelNames()[i]);
                } catch (IHighScoreDAO.HighScoreDAOException e) {
                    e.printStackTrace();
                }

                String player = textHandler.getFixedString("Player: ?", 15);
                String score = textHandler.getFixedString("Score:  0", 15);
                String time = textHandler.getFixedString(" Time: 0", 12);

                if (highScoreDTO != null) {
                    player = textHandler.getFixedString("Player: " + highScoreDTO.getPlayer(), 16);
                    score = textHandler.getFixedString("Score:  " + highScoreDTO.getScore(), 15);
                    time = textHandler.getFixedString(" Time: " + textHandler.getTimeString(highScoreDTO.getTime()), 12);
                }

                g.drawString(player, levelRects[i].x + 5, levelRects[i].y + 55);
                g.drawString(score + time, levelRects[i].x + 5, levelRects[i].y + 75);

                g.setColor(levelRectColors[i]);
                g.draw(levelRects[i]);
            }
        }

        drawInfoPanel(g);
    }

}
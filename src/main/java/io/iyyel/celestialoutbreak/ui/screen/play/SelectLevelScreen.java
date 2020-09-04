package io.iyyel.celestialoutbreak.ui.screen.play;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.data.dao.HighScoreDAO;
import io.iyyel.celestialoutbreak.data.dao.interfaces.IHighScoreDAO;
import io.iyyel.celestialoutbreak.data.dao.interfaces.IPlayerDAO;
import io.iyyel.celestialoutbreak.data.dto.HighScoreDTO;
import io.iyyel.celestialoutbreak.handler.LevelHandler;
import io.iyyel.celestialoutbreak.handler.PowerUpHandler;
import io.iyyel.celestialoutbreak.ui.screen.AbstractScreen;

import java.awt.*;

public class SelectLevelScreen extends AbstractScreen {

    private final LevelHandler levelHandler = LevelHandler.getInstance();
    private final IHighScoreDAO highScoreDAO = HighScoreDAO.getInstance();
    private final PowerUpHandler powerUpHandler = PowerUpHandler.getInstance();

    private final Rectangle[] levelRects;
    private final Color[] levelRectColors;

    private final Font levelInfoFont = util.getGameFont().deriveFont(15F);

    private final int levelAmount = levelHandler.getLevelAmount();
    private int selected = 0;

    private final int buttonWrap = 3;

    public SelectLevelScreen(GameController gameController) {
        super(gameController);
        levelRects = new Rectangle[levelAmount];
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
            levelRects[i] = new Rectangle(x, y, 250, 130);
            y += yInc;
        }

        try {
            highScoreDAO.loadHighScoreList();
        } catch (IHighScoreDAO.HighScoreDAOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        decInputTimer();

        if (inputHandler.isCancelPressed() && isInputAvailable()) {
            resetInputTimer();
            selected = 0;
            menuNavClip.play(false);
            gameController.switchState(GameController.State.MAIN);
        }

        if (inputHandler.isDownPressed() && (selected + 1) % buttonWrap != 0 && (selected + 1) < levelAmount && isInputAvailable()) {
            resetInputTimer();
            selected++;
            menuNavClip.play(false);
        }

        if (inputHandler.isUpPressed() && selected % buttonWrap != 0 && isInputAvailable()) {
            resetInputTimer();
            selected--;
            menuNavClip.play(false);
        }

        if (inputHandler.isLeftPressed() && selected > buttonWrap - 1 && isInputAvailable()) {
            resetInputTimer();
            selected -= buttonWrap;
            menuNavClip.play(false);
        }

        if (inputHandler.isRightPressed() && selected < 16 && (selected + buttonWrap) < levelAmount && isInputAvailable()) {
            resetInputTimer();
            selected += buttonWrap;
            menuNavClip.play(false);
        }

        for (int i = 0, n = levelHandler.getLevelAmount(); i < n; i++) {
            if (selected == i) {
                updateLevelColors(i);

                if (inputHandler.isOKPressed() && isInputAvailable()) {
                    resetInputTimer();
                    menuNavClip.play(false);
                    selected = 0;

                    // Set current active level to i.
                    powerUpHandler.clear();
                    levelHandler.loadLevel(i);
                    levelHandler.setActiveLevelIndex(i);
                    gameController.switchState(GameController.State.PRE_LEVEL);
                }
                // TODO: If the user decides to go back from this screen, the activeLevel should perhaps be reset?
            } else {
                updateLevelColors(i);
            }
        }

    }

    @Override
    public void render(Graphics2D g) {
        drawScreenTitles(textHandler.TITLE_SELECT_LEVEL_SCREEN, g);

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

                String selectedPlayer = null;

                try {
                    selectedPlayer = playerDAO.getSelectedPlayer();
                } catch (IPlayerDAO.PlayerDAOException e) {
                    e.printStackTrace();
                }

                HighScoreDTO scoreDTO;
                String scoreTmp = "0";
                long timeTmp = 0;

                try {
                    scoreDTO = highScoreDAO.getHighScore(selectedPlayer, levelHandler.getLevelNames()[i]);
                    if (scoreDTO != null) {
                        scoreTmp = scoreDTO.getScore() + "";
                        timeTmp = scoreDTO.getTime();
                    }
                } catch (IHighScoreDAO.HighScoreDAOException e) {
                    e.printStackTrace();
                }

                String blockHealth = textHandler.getFixedString("Blocks: " + levelHandler.getLevelBlockAmounts()[i] + "/" +
                        levelHandler.getLevelHitPoints()[i], 15);
                String playerLife = textHandler.getFixedString("Life: " + levelHandler.getLevelPlayerLives()[i], 12);
                String score = textHandler.getFixedString("Your Score: " + scoreTmp, 20);
                String time = textHandler.getFixedString("Your Time: " + textHandler.getTimeString(timeTmp), 17);

                if (optionsHandler.isGodModeEnabled()) {
                    playerLife = "Life: GOD";
                }

                g.drawString(blockHealth, levelRects[i].x + 5, levelRects[i].y + 60);
                g.drawString(playerLife, levelRects[i].x + 5, levelRects[i].y + 80);
                g.drawString(score, levelRects[i].x + 5, levelRects[i].y + 100);
                g.drawString(time, levelRects[i].x + 5, levelRects[i].y + 120);

                g.setColor(levelRectColors[i]);
                g.draw(levelRects[i]);
            }
        }

        drawInfoPanel(g);
    }

    private void updateLevelColors(int index) {
        if (selected == index) {
            levelRectColors[index] = menuSelectedBtnColor;
        } else {
            levelRectColors[index] = menuBtnColor;
        }
    }

}
package io.iyyel.celestialoutbreak.ui.screen.play;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.controller.GameController.State;
import io.iyyel.celestialoutbreak.data.dao.HighScoreDAO;
import io.iyyel.celestialoutbreak.data.dao.PlayerDAO;
import io.iyyel.celestialoutbreak.data.dao.contract.IHighScoreDAO;
import io.iyyel.celestialoutbreak.data.dao.contract.IPlayerDAO;
import io.iyyel.celestialoutbreak.data.dto.HighScoreDTO;
import io.iyyel.celestialoutbreak.handler.LevelHandler;
import io.iyyel.celestialoutbreak.handler.LogHandler;
import io.iyyel.celestialoutbreak.level.Level;
import io.iyyel.celestialoutbreak.ui.screen.AbstractScreen;

import java.awt.*;

public final class PostLevelScreen extends AbstractScreen {

    private final LevelHandler levelHandler = LevelHandler.getInstance();
    private final IHighScoreDAO highScoreDAO = HighScoreDAO.getInstance();
    private final LogHandler logHandler = LogHandler.getInstance();
    private final IPlayerDAO playerDAO = PlayerDAO.getInstance();
    private HighScoreDTO highScoreDTO;

    private boolean isFirstRender = true;
    private boolean hasWon = false;
    private boolean isHighScore = false;

    public PostLevelScreen(GameController gameController) {
        super(gameController);
    }

    @Override
    public void update() {
        decInputTimer();
        if (inputHandler.isOKPressed() && isInputAvailable()) {
            resetInputTimer();
            isFirstRender = true;
            levelHandler.getActiveLevel().stopSound();
            levelHandler.resetActiveLevel();
            gameController.switchState(State.SELECT_LEVEL);
        }
    }

    @Override
    public void render(Graphics2D g) {
        Level activeLevel = levelHandler.getActiveLevel();

        if (isFirstRender) {
            isFirstRender = false;
            isHighScore = false;
            util.stopTimer();
            hasWon = levelHandler.getActiveLevel().isWon();
            try {
                highScoreDAO.loadHighScoreList();
            } catch (IHighScoreDAO.HighScoreDAOException e) {
                e.printStackTrace();
            }
            try {
                highScoreDTO = new HighScoreDTO(playerDAO.getSelectedPlayer(), activeLevel.getName(), levelHandler.getCurrentScore(), util.getTimeElapsed());
                isHighScore = highScoreDAO.isHighScore(highScoreDTO);
                if (isHighScore) {
                    highScoreDAO.addHighScore(highScoreDTO);
                    highScoreDAO.saveHighScoreList();
                }
            } catch (IPlayerDAO.PlayerDAOException | IHighScoreDAO.HighScoreDAOException e) {
                e.printStackTrace();
                logHandler.log(e.getMessage(), "PostLevelScreen: render", LogHandler.LogLevel.ERROR, false);
            }
        }

        drawScreenTitle(g);
        drawScreenSubtitle(activeLevel.getName(), g);

        if (hasWon) {
            if (isHighScore) {
                drawScreenMessage("»»»»»»»» NEW HIGH SCORE! ««««««««", -50, g);
                drawScreenMessage("You reached a new high score of " + levelHandler.getCurrentScore() + ".", 50, g);
            } else {
                drawScreenMessage("You reached a total score of " + levelHandler.getCurrentScore() + ".", 50, g);
            }

            drawScreenMessage("You are victorious! The " + levelHandler.getActiveLevel().getName() + " level has been defeated.", 0, g);
            drawScreenMessage("Time: " + textHandler.getTimeString(util.getTimeElapsed()), 100, g);
        } else {
            drawScreenMessage("You have lost. The " + levelHandler.getActiveLevel().getName() + " level shines in grace upon you.", 0, g);
            drawScreenMessage("You reached a total score of " + levelHandler.getCurrentScore() + ".", 50, g);
            drawScreenMessage("Time: " + textHandler.getTimeString(util.getTimeElapsed()), 100, g);
        }

        drawScreenToolTip("Press '" + textHandler.BTN_CONTROL_FORWARD_OK + "' to go to the main menu.", g);
        drawScreenInfoPanel(g);
    }

}
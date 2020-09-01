package io.iyyel.celestialoutbreak.ui.screen.play;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.data.dao.PlayerDAO;
import io.iyyel.celestialoutbreak.data.dao.interfaces.IPlayerDAO;
import io.iyyel.celestialoutbreak.handler.LevelHandler;
import io.iyyel.celestialoutbreak.level.Level;
import io.iyyel.celestialoutbreak.level.LevelOptions;
import io.iyyel.celestialoutbreak.ui.screen.AbstractScreen;

import java.awt.*;

public final class GamePanel extends AbstractScreen {

    private final IPlayerDAO playerDAO = PlayerDAO.getInstance();

    private final Font panelFont;
    private final Color titleColor;
    private final Color valueColor;

    private final LevelHandler levelHandler = LevelHandler.getInstance();

    private Level activeLevel;

    public GamePanel(GameController gameController, LevelOptions levelOptions) {
        super(gameController);
        panelFont = util.getGameFont().deriveFont(16F);
        titleColor = levelOptions.getGamePanelTitleColor();
        valueColor = levelOptions.getGamePanelValueColor();
    }

    @Override
    public void update() {
        activeLevel = levelHandler.getActiveLevel();
    }

    @Override
    public void render(Graphics2D g) {
        g.setFont(panelFont);
        drawGamePanel(g);
    }

    private void drawGamePanel(Graphics2D g) {
        String selectedPlayer = "N/A";

        try {
            selectedPlayer = playerDAO.getSelectedPlayer();
        } catch (IPlayerDAO.PlayerDAOException e) {
            e.printStackTrace();
        }

        if (activeLevel != null) {
            // 10 pixels per letter has to be moved.
            int pos = 100;

            String levelLabel = "Level: ";
            String levelValue = activeLevel.getName();
            g.setColor(titleColor);
            g.drawString(levelLabel, pos, 714);
            g.setColor(valueColor);
            pos += levelLabel.length() * 10;
            g.drawString(levelValue, pos, 714);
            pos += 13 * 10; // 13 char level names

            // spacing
            pos += 50;

            String playerLabel = "Player: ";
            g.setColor(titleColor);
            g.drawString(playerLabel, pos, 714);
            g.setColor(valueColor);
            pos += playerLabel.length() * 10;
            g.drawString(selectedPlayer, pos, 714);
            pos += 7 * 10; // 7 char player names

            // spacing
            pos += 50;

            String livesLabel = "Lives: ";
            String livesValue = activeLevel.getPlayerLife() + "";
            g.setColor(titleColor);
            g.drawString(livesLabel, pos, 714);
            g.setColor(valueColor);
            pos += livesLabel.length() * 10;
            g.drawString(livesValue, pos, 714);
            pos += 4 * 10; // 4 char life count

            // spacing
            pos += 50;

            String scoreLabel = "Score: ";
            String scoreValue = levelHandler.getCurrentScore() + "";
            g.setColor(titleColor);
            g.drawString(scoreLabel, pos, 714);
            pos += scoreLabel.length() * 10;
            g.setColor(valueColor);
            g.drawString(scoreValue, pos, 714);
            pos += 7 * 10; // 7 char for score.

            // spacing
            pos += 50;

            String blockLabel = "Blocks: ";
            String blockValue = activeLevel.getBlockField().getBlocksLeft() + "";
            g.setColor(titleColor);
            g.drawString(blockLabel, pos, 714);
            g.setColor(valueColor);
            pos += blockLabel.length() * 10;
            g.drawString(blockValue, pos, 714);
            pos += 4 * 10; // 4 chars for blocks

            // spacing
            pos += 50;

            String timeLabel = "Time: ";
            String timeValue = textHandler.getTimeString(util.getTimeElapsed());
            g.setColor(titleColor);
            g.drawString(timeLabel, pos, 714);
            g.setColor(valueColor);
            pos += timeLabel.length() * 10;
            g.drawString(timeValue, pos, 714);
        }
    }

}
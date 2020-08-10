package io.iyyel.celestialoutbreak.ui.screen.play;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.data.dao.PlayerDAO;
import io.iyyel.celestialoutbreak.data.dao.contract.IPlayerDAO;
import io.iyyel.celestialoutbreak.handler.LevelHandler;
import io.iyyel.celestialoutbreak.level.Level;
import io.iyyel.celestialoutbreak.level.LevelOptions;
import io.iyyel.celestialoutbreak.ui.screen.AbstractScreen;

import java.awt.*;

public final class GamePanel extends AbstractScreen {

    private final IPlayerDAO playerDAO = PlayerDAO.getInstance();

    private final Font panelFont;
    private final Color panelColor;

    private final LevelHandler levelHandler = LevelHandler.getInstance();

    private Level activeLevel;

    public GamePanel(GameController gameController, LevelOptions levelOptions) {
        super(gameController);
        panelFont = util.getPanelFont().deriveFont(16F);
        panelColor = levelOptions.getGamePanelColor();
    }

    @Override
    public void update() {
        activeLevel = levelHandler.getActiveLevel();
    }

    @Override
    public void render(Graphics2D g) {
        g.setFont(panelFont);
        g.setColor(panelColor);
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
            g.drawString("Level: " + activeLevel.getName(), 5, 714);
            g.drawString("Player: " + selectedPlayer, 200, 714);
            g.drawString("Lives: " + activeLevel.getPlayerLife(), getHalfWidth() - 150, 714);
            g.drawString("Score: " + levelHandler.getCurrentScore(), getHalfWidth() + 50, 714);
            g.drawString("Blocks: " + activeLevel.getBlockList().getBlocksLeft(), getHalfWidth() + 200, 714);
            g.drawString("Time: " + textHandler.getTimeString(util.getTimeElapsed()), getHalfWidth() + 350, 714);
        }
    }

}
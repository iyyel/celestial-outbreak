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
        panelFont = util.getPanelFont().deriveFont(16F);
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
            g.setColor(titleColor);
            g.drawString("Level: ", getHalfWidth() - 440, 714);
            g.setColor(valueColor);
            g.drawString(activeLevel.getName(), getHalfWidth() - 370, 714);

            g.setColor(titleColor);
            g.drawString("Player: ", getHalfWidth() - 280, 714);
            g.setColor(valueColor);
            g.drawString(selectedPlayer, getHalfWidth() - 200, 714);

            g.setColor(titleColor);
            g.drawString("Lives: ", getHalfWidth() - 110, 714);
            g.setColor(valueColor);
            g.drawString(activeLevel.getPlayerLife() + "", getHalfWidth() - 40, 714);

            g.setColor(titleColor);
            g.drawString("Score: ", getHalfWidth() + 20, 714);
            g.setColor(valueColor);
            g.drawString(levelHandler.getCurrentScore() + "", getHalfWidth() + 90, 714);

            g.setColor(titleColor);
            g.drawString("Blocks: ", getHalfWidth() + 160, 714);
            g.setColor(valueColor);
            g.drawString(activeLevel.getBlockList().getBlocksLeft() + "", getHalfWidth() + 240, 714);

            g.setColor(titleColor);
            g.drawString("Time: ", getHalfWidth() + 310, 714);
            g.setColor(valueColor);
            g.drawString(textHandler.getTimeString(util.getTimeElapsed()), getHalfWidth() + 370, 714);
        }
    }

}
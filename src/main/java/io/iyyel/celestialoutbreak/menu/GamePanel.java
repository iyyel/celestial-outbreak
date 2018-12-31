package io.iyyel.celestialoutbreak.menu;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.data.dao.interfaces.IPlayerDAO;
import io.iyyel.celestialoutbreak.handler.LevelHandler;
import io.iyyel.celestialoutbreak.level.Level;

import java.awt.*;

public final class GamePanel extends AbstractMenu {

    private final Font panelFont;

    private final LevelHandler levelHandler = LevelHandler.getInstance();

    private Level activeLevel;

    public GamePanel(GameController gameController) {
        super(gameController);
        panelFont = utils.getPanelFont().deriveFont(16F);
    }

    @Override
    public void update() {
        activeLevel = levelHandler.getActiveLevel();
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(menuFontColor);
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
            g.drawString("Planet: " + activeLevel.getName(), 5, 714);
            g.drawString("Player: " + selectedPlayer, 200, 714);
            g.drawString("Lives: " + activeLevel.getPlayerLife(), gameController.getWidth() / 2 - 150, 714);
            g.drawString("Score: N/A", gameController.getWidth() / 2 + 50, 714);
            g.drawString("Blocks: " + activeLevel.getBlocksLeft(), gameController.getWidth() / 2 + 200, 714);
        }
    }

}
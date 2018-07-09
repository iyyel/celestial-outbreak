package io.iyyel.celestialoutbreak.menu.game;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.data.dao.IPlayerDAO;
import io.iyyel.celestialoutbreak.handler.InputHandler;
import io.iyyel.celestialoutbreak.menu.Menu;

import java.awt.*;

public class NewPlayerMenu extends Menu {

    private boolean firstUpdate = false;
    private String statusString = "";

    public NewPlayerMenu(GameController gameController, InputHandler inputHandler, Color fontColor) {
        super(gameController, inputHandler, fontColor);
    }

    @Override
    public void update() {
        /*
         * Do this ONCE everytime the user is on this screen.
         */
        if (firstUpdate) {
            firstUpdate = false;
            inputHandler.setInputMode(true);
            try {
                playerDAO.loadPlayerDTO();
            } catch (IPlayerDAO.PlayerDAOException e) {
                e.printStackTrace();
            }
        }

        if (inputHandler.isOKPressed()) {
            String name = inputHandler.getUserInput();

            System.out.println(name);

            try {
                playerDAO.addPlayer(name);
            } catch (IPlayerDAO.PlayerDAOMinNameException e) {
                statusString = "Name too small.";
                return;
            } catch (IPlayerDAO.PlayerDAOMaxNameException e) {
                statusString = "Name is too long.";
                return;
            } catch (IPlayerDAO.PlayerDAOException e) {
                statusString = "Player already exists.";
                return;
            }

            statusString = name + " is created. Press 'OK'.";

            if (playerDAO.getPlayerList().size() == 1) {
                try {
                    playerDAO.selectPlayer(name);
                } catch (IPlayerDAO.PlayerDAOException e) {
                    e.printStackTrace();
                }
            }

            try {
                playerDAO.savePlayerDTO();
            } catch (IPlayerDAO.PlayerDAOException e) {
                e.printStackTrace();
            }

            inputHandler.setInputMode(false);
        }

        if (inputHandler.isOKPressed()) {
            gameController.switchState(GameController.State.MAIN_MENU);
        }

    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(fontColor);
        drawMenuTitle(g);

        drawSubmenuTitle("New Player", g);

        drawXCenteredString("Enter Player name:", 300, g, msgFont);

        drawXCenteredString(inputHandler.getUserInput(), 350, g, inputFont);

        g.setFont(msgFont);
        drawXCenteredString(statusString, 450, g, inputFont);

        drawInformationPanel(g);
    }

}
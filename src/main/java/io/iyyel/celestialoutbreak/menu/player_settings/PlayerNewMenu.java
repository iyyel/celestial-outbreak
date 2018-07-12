package io.iyyel.celestialoutbreak.menu.player_settings;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.data.dao.interfaces.IPlayerDAO;
import io.iyyel.celestialoutbreak.menu.AbstractMenu;

import java.awt.*;

public final class PlayerNewMenu extends AbstractMenu {

    private boolean firstUpdate = true;
    private boolean isAcceptMode = false;
    private boolean isPlayerCreated = false;
    private boolean isExiting = false;

    private final int INIT_INPUT_TIMER = 10;
    private int inputTimer = INIT_INPUT_TIMER;

    private final String INIT_STATUS_STRING = "Press 'OK' button to start entering a player name or 'cancel' to go back.";
    private String statusString = INIT_STATUS_STRING;

    public PlayerNewMenu(GameController gameController) {
        super(gameController);
    }

    @Override
    public void update() {
        if (inputTimer > 0) {
            inputTimer--;
        }

        /*
         * Do this ONCE every time the user is on this screen.
         */
        if (firstUpdate) {
            firstUpdate = false;
            isAcceptMode = false;
            isPlayerCreated = false;
            isExiting = false;

            inputHandler.setInputMode(false);
            inputHandler.setUserInput("");

            statusString = INIT_STATUS_STRING;

            try {
                playerDAO.loadPlayerDTO();
            } catch (IPlayerDAO.PlayerDAOException e) {
                e.printStackTrace();
            }
        }

        // InputMode: true
        // cancel: type 'x'
        // OK:     type 'z'
        // use:    input mode = false and
        //
        // InputMode: false
        // cancel: go back
        // OK"     create player
        // USE: nothing
        //
        //

        // Stage 1
        if (inputHandler.isOKPressed() && !inputHandler.isInputMode() && !isAcceptMode && !isPlayerCreated) {
            inputHandler.setInputMode(true);
            statusString = "Please type a player name. Press 'Use' when done.";
        }

        // Stage 2
        if (inputHandler.isUsePressed() && inputHandler.isInputMode() && !isAcceptMode && !isPlayerCreated) {
            String name = inputHandler.getUserInput();

            if (name.length() >= 3 && name.length() <= 8) {
                isAcceptMode = true;
                inputHandler.setInputMode(false);
                statusString = "Happy with '" + name + "'? Press 'OK' to create or 'Cancel' to reset.";
            } else if (name.length() < 3) {
                statusString = "Name is too small.";
            } else if (name.length() > 8) {
                statusString = "Name is too long.";
            }

        }

        if (inputHandler.isOKPressed() && !inputHandler.isInputMode() && isAcceptMode && !isPlayerCreated) {
            String name = inputHandler.getUserInput();
            isAcceptMode = false;
            inputHandler.setInputMode(false);
            createPlayer(name);
        }

        if (inputHandler.isOKPressed() && !inputHandler.isInputMode() && !isAcceptMode && isPlayerCreated && isExiting) {
            inputHandler.setUserInput("");
            isAcceptMode = false;
            firstUpdate = true;
            inputHandler.setInputMode(false);
            statusString = INIT_STATUS_STRING;
            exitMenu();
        }

        if (inputHandler.isOKPressed() && !inputHandler.isInputMode() && !isAcceptMode && isPlayerCreated) {
            String name = inputHandler.getUserInput();
            isAcceptMode = false;
            isExiting = true;
            inputHandler.setInputMode(false);
            statusString = name + " created. Press 'OK' to finish.";
        }

        if (inputHandler.isCancelPressed() && !inputHandler.isInputMode() && !isAcceptMode && !isPlayerCreated && inputTimer == 0) {
            inputHandler.setUserInput("");
            isAcceptMode = false;
            firstUpdate = true;
            inputHandler.setInputMode(false);
            exitMenu();
        }

        if (inputHandler.isCancelPressed() && !inputHandler.isInputMode() && isAcceptMode && !isPlayerCreated) {
            inputHandler.setUserInput("");
            isAcceptMode = false;
            inputHandler.setInputMode(false);
            statusString = INIT_STATUS_STRING;
        }

        if (inputTimer == 0) {
            inputTimer = INIT_INPUT_TIMER;
        }

    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(menuFontColor);
        drawMenuTitle(g);

        drawSubmenuTitle("New Player", g);

        drawCenterString("Enter Player name:", 300, g, msgFont);

        drawCenterString(inputHandler.getUserInput(), 350, g, inputFont);

        g.setFont(msgFont);
        drawCenterString(statusString, 450, g, inputFont);

        drawInformationPanel(g);
    }

    private void createPlayer(String name) {
        try {
            playerDAO.addPlayer(name);
        } catch (IPlayerDAO.PlayerDAOMinNameException e) {
            statusString = "Name too small.";
            return;
        } catch (IPlayerDAO.PlayerDAOMaxNameException e) {
            statusString = "Name is too long.";
            return;
        } catch (IPlayerDAO.PlayerDAOLimitException e) {
            statusString = "Player limit reached. Please delete some players if you want to create new ones.";
            return;
        } catch (IPlayerDAO.PlayerDAOException e) {
            statusString = "Player already exists.";
            return;
        }

        isPlayerCreated = true;

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

    }

    private void exitMenu() {
        if (utils.isFirstRunEnabled()) {
            gameController.switchState(GameController.State.MAIN_MENU);
        } else if (playerDAO.getPlayerList().size() != 0) {
            gameController.switchState(gameController.getPrevState());
        }
    }

}
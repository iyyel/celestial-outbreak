package io.iyyel.celestialoutbreak.menu.player_options;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.data.dao.interfaces.IPlayerDAO;
import io.iyyel.celestialoutbreak.menu.AbstractMenu;

import java.awt.*;

public final class PlayerCreateMenu extends AbstractMenu {

    private boolean firstUpdate = true;
    private boolean isAcceptMode = false;
    private boolean isPlayerCreated = false;
    private boolean isExiting = false;

    private final String INIT_STATUS_STRING = "Press '" + textHandler.BTN_CONTROL_FORWARD_OK + "' to enter a player name or '" + textHandler.BTN_CONTROL_BACK_CANCEL + "' to go back.";
    private String statusString = INIT_STATUS_STRING;

    public PlayerCreateMenu(GameController gameController) {
        super(gameController);
    }

    @Override
    public void update() {
        decInputTimer();

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
        if (inputHandler.isOKPressed() && !inputHandler.isInputMode() && !isAcceptMode && !isPlayerCreated && isInputAvailable()) {
            resetInputTimer();
            inputHandler.setInputMode(true);
            statusString = "Please type a player name. Press '" + textHandler.BTN_CONTROL_USE + "' when done.";
        }

        // Stage 2
        if (inputHandler.isUsePressed() && inputHandler.isInputMode() && !isAcceptMode && !isPlayerCreated && isInputAvailable()) {
            resetInputTimer();
            String name = inputHandler.getUserInput();

            if (name.length() >= 3 && name.length() <= 8) {
                isAcceptMode = true;
                inputHandler.setInputMode(false);
                statusString = "Create player '" + name + "'? Press '" + textHandler.BTN_CONTROL_FORWARD_OK + "' to create or '" + textHandler.BTN_CONTROL_BACK_CANCEL + "' to reset.";
            } else if (name.length() < 3) {
                statusString = "Name is too small.";
            } else if (name.length() > 8) {
                statusString = "Name is too long.";
            }

        }

        if (inputHandler.isOKPressed() && !inputHandler.isInputMode() && isAcceptMode && !isPlayerCreated && isInputAvailable()) {
            resetInputTimer();
            String name = inputHandler.getUserInput();
            isAcceptMode = false;
            inputHandler.setInputMode(false);
            createPlayer(name);
        }

        if (inputHandler.isOKPressed() && !inputHandler.isInputMode() && !isAcceptMode && isPlayerCreated && isExiting && isInputAvailable()) {
            resetInputTimer();
            inputHandler.setUserInput("");
            isAcceptMode = false;
            firstUpdate = true;
            inputHandler.setInputMode(false);
            statusString = INIT_STATUS_STRING;
            exitMenu();
        }

        if (inputHandler.isOKPressed() && !inputHandler.isInputMode() && !isAcceptMode && isPlayerCreated && isInputAvailable()) {
            resetInputTimer();
            String name = inputHandler.getUserInput();
            isAcceptMode = false;
            isExiting = true;
            inputHandler.setInputMode(false);
            statusString = "'" + name + "' has been created. Press '" + textHandler.BTN_CONTROL_FORWARD_OK + "' to finish.";
        }

        if (inputHandler.isCancelPressed() && !inputHandler.isInputMode() && !isAcceptMode && !isPlayerCreated && isInputAvailable()) {
            resetInputTimer();
            inputHandler.setUserInput("");
            isAcceptMode = false;
            firstUpdate = true;
            inputHandler.setInputMode(false);
            exitMenu();
        }

        if (inputHandler.isCancelPressed() && !inputHandler.isInputMode() && isAcceptMode && !isPlayerCreated && isInputAvailable()) {
            resetInputTimer();
            inputHandler.setUserInput("");
            isAcceptMode = false;
            inputHandler.setInputMode(false);
            statusString = INIT_STATUS_STRING;
        }

    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(menuFontColor);
        drawMenuTitle(g);
        drawSubmenuTitle(textHandler.TITLE_CREATE_PLAYER_SCREEN, g);

        if (inputHandler.isInputMode()) {
            drawCenterString("Enter Player name:", 300, g, msgFont);
            drawCenterString(inputHandler.getUserInput(), 350, g, inputFont);
        }

        drawMenuToolTip(statusString, g);
        drawInfoPanel(g);
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
        if (playerDAO.getPlayerList().isEmpty() && !playerDAO.getPlayerList().isEmpty()
                && gameController.getPrevState() == GameController.State.WELCOME_MENU) {
            menuUseClip.play(false);
            gameController.switchState(GameController.State.MAIN_MENU);
        } else if (!playerDAO.getPlayerList().isEmpty()) {
            menuUseClip.play(false);
            if (gameController.getPrevState() == GameController.State.NONE
                    || gameController.getPrevState() == GameController.State.WELCOME_MENU) {
                gameController.switchState(GameController.State.MAIN_MENU);
            } else {
                gameController.switchState(gameController.getPrevState());
            }
        } else {
            menuUseClip.play(false);
            gameController.switchState(GameController.State.WELCOME_MENU);
        }
    }

}
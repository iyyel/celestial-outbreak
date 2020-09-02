package io.iyyel.celestialoutbreak.ui.screen.player_options;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.data.dao.interfaces.IPlayerDAO;
import io.iyyel.celestialoutbreak.ui.screen.AbstractScreen;

import java.awt.*;

public final class PlayerCreateScreen extends AbstractScreen {

    private boolean isFirstUpdate = true;
    private boolean isAcceptMode = false;
    private boolean isPlayerCreated = false;

    private final String INIT_STATUS_STRING = "Press " + textHandler.BTN_CONTROL_AUX + " to enter a player name or " + textHandler.BTN_CONTROL_CANCEL + " to go back.";
    private String statusString = INIT_STATUS_STRING;

    public PlayerCreateScreen(GameController gameController) {
        super(gameController);
    }

    @Override
    public void update() {
        decInputTimer();

        /*
         * Do this ONCE every time the user is on this screen.
         */
        if (isFirstUpdate) {
            isFirstUpdate = false;
            isAcceptMode = false;
            isPlayerCreated = false;

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
        if (inputHandler.isUsePressed() && !inputHandler.isInputMode() && !isAcceptMode && !isPlayerCreated && isInputAvailable()) {
            resetInputTimer();
            inputHandler.setInputMode(true);
            menuUseClip.play(false);
            statusString = "Enter a player name. Press " + textHandler.BTN_CONTROL_AUX + " when done.";
        }

        // Stage 2
        if (inputHandler.isUsePressed() && inputHandler.isInputMode() && !isAcceptMode && !isPlayerCreated && isInputAvailable()) {
            resetInputTimer();
            String name = inputHandler.getUserInput();

            if (name.length() >= 3 && name.length() <= 8) {
                if (playerDAO.isPlayer(name)) {
                    statusString = "Player already exists.";
                    menuBadClip.play(false);
                    return;
                }

                isAcceptMode = true;
                inputHandler.setInputMode(false);
                menuUseClip.play(false);
                statusString = "Create player " + name + "? Press " + textHandler.BTN_CONTROL_OK + " to confirm or " + textHandler.BTN_CONTROL_CANCEL + " to go back.";
            } else if (name.length() < 3) {
                statusString = "Name is too small.";
                menuBadClip.play(false);
            } else {
                statusString = "Name is too long.";
                menuBadClip.play(false);
            }
        }

        if (inputHandler.isOKPressed() && !inputHandler.isInputMode() && isAcceptMode && !isPlayerCreated && isInputAvailable()) {
            resetInputTimer();
            String name = inputHandler.getUserInput();
            isAcceptMode = false;
            inputHandler.setInputMode(false);
            createPlayer(name);

            if (isPlayerCreated) {
                resetInputTimer();
                inputHandler.setUserInput("");
                isAcceptMode = false;
                isFirstUpdate = true;
                inputHandler.setInputMode(false);
                statusString = INIT_STATUS_STRING;
                exitMenu();
            }
        }

        if (inputHandler.isCancelPressed() && !inputHandler.isInputMode() && !isAcceptMode && !isPlayerCreated && isInputAvailable()) {
            resetInputTimer();
            inputHandler.setUserInput("");
            isAcceptMode = false;
            isFirstUpdate = true;
            inputHandler.setInputMode(false);
            exitMenu();
        }

        if (inputHandler.isCancelPressed() && !inputHandler.isInputMode() && isAcceptMode && !isPlayerCreated && isInputAvailable()) {
            resetInputTimer();
            inputHandler.setUserInput("");
            isAcceptMode = false;
            inputHandler.setInputMode(false);
            menuUseClip.play(false);
            statusString = INIT_STATUS_STRING;
        }

    }

    @Override
    public void render(Graphics2D g) {
        drawTitle(g);
        drawSubtitle(textHandler.TITLE_CREATE_PLAYER_SCREEN, g);

        if (inputHandler.isInputMode()) {
            drawCenteredText("Enter Player name:", 0, g);
            drawCenteredText(inputHandler.getUserInput(), 400, inputBtnFont, g);
        }

        drawToolTip(statusString, g);
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

        if (playerDAO.getPlayers().size() == 1) {
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
        if (playerDAO.getPlayers().isEmpty() && gameController.getPrevState() == GameController.State.WELCOME) {
            menuUseClip.play(false);
            gameController.switchState(GameController.State.WELCOME);
        } else if (!playerDAO.getPlayers().isEmpty()) {
            menuUseClip.play(false);
            if (gameController.getPrevState() == GameController.State.NONE || gameController.getPrevState() == GameController.State.WELCOME) {
                gameController.switchState(GameController.State.MAIN);
            } else {
                gameController.switchState(gameController.getPrevState());
            }
        } else {
            menuUseClip.play(false);
            gameController.switchState(GameController.State.WELCOME);
        }
    }

}
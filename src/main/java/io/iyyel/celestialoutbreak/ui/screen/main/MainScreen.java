package io.iyyel.celestialoutbreak.ui.screen.main;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.data.dao.PlayerDAO;
import io.iyyel.celestialoutbreak.data.dao.interfaces.IPlayerDAO;
import io.iyyel.celestialoutbreak.ui.screen.AbstractNavigationScreen;
import io.iyyel.celestialoutbreak.ui.screen.component.Button;

import java.awt.*;

public final class MainScreen extends AbstractNavigationScreen {

    private final IPlayerDAO playerDAO = PlayerDAO.getInstance();

    private final String[] options = {textHandler.BTN_PLAY_TEXT, textHandler.BTN_HIGH_SCORES_TEXT, textHandler.BTN_CONTROLS_TEXT,
            textHandler.BTN_OPTIONS_TEXT, textHandler.BTN_ABOUT_TEXT, textHandler.BTN_EXIT_TEXT};

    private final Button[] buttons;

    public MainScreen(NavStyle navStyle, int btnAmount, GameController gameController) {
        super(navStyle, btnAmount, gameController);
        buttons = new Button[btnAmount];

        for (int i = 0; i < btnAmount; i++) {
            buttons[i] = new Button(new Point(getHalfWidth(), initialBtnYPos + btnYIncrement * i),
                    new Dimension(210, 50), options[i], true, inputBtnFont,
                    screenFontColor, menuBtnColor, new Point(105, 0), new Point(0, -8), screenWidth, screenHeight);
        }
    }

    @Override
    protected void updateNavAux(int index) {

    }

    @Override
    protected void updateNavOK(int index) {
        if (isOKPressed(index)) {
            super.updateNavOK();
            menuNavClip.play(false);
            switch (index) {
                case 0:
                    gameController.switchState(GameController.State.SELECT_LEVEL);
                    break;
                case 1:
                    gameController.switchState(GameController.State.SCORES);
                    break;
                case 2:
                    gameController.switchState(GameController.State.CONTROLS);
                    break;
                case 3:
                    gameController.switchState(GameController.State.OPTIONS);
                    break;
                case 4:
                    gameController.switchState(GameController.State.ABOUT);
                    break;
                case 5:
                    gameController.switchState(GameController.State.EXIT);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void update() {
        super.update();
        updateNavUp();
        updateNavDown();
        updateNavOK(selectedIndex);
        updateSelectedButtonColor(buttons);
    }

    @Override
    public void render(Graphics2D g) {
        try {
            if (playerDAO.getSelectedPlayer() != null) {
                drawScreenTitles("Welcome " + playerDAO.getSelectedPlayer(), g);
            } else {
                drawScreenTitles("Welcome", g);
            }
        } catch (IPlayerDAO.PlayerDAOException e) {
            drawSubtitle("Welcome", g);
        }

        renderButtons(buttons, g);
        drawInfoPanel(g);
    }

}
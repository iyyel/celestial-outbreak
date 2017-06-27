package io.inabsentia.celestialoutbreak.menu;


import io.inabsentia.celestialoutbreak.controller.Game;
import io.inabsentia.celestialoutbreak.entity.State;
import io.inabsentia.celestialoutbreak.handler.InputHandler;

import java.awt.*;

public class NewLevelMenu extends Menu {

    private int maximumLineStringLength = 40;
    private String activeLevelDesc;

    public NewLevelMenu(Game game, InputHandler inputHandler) {
        super(game, inputHandler);
    }

    @Override
    public void update() {
        if (inputHandler.isConfirmPressed()) game.switchState(State.PLAY);
        if (inputHandler.isRejectPressed()) game.switchState(State.MENU);
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(fontColor);
        drawXCenteredString(textHandler.TITLE, 100, g, titleFont);

        String firstLevelDescMsg = activeLevelDesc;
        String secondLevelDescMsg = null;

        if (activeLevelDesc != null && activeLevelDesc.length() > maximumLineStringLength) {
            secondLevelDescMsg = activeLevelDesc.substring(maximumLineStringLength, activeLevelDesc.length());
            firstLevelDescMsg = activeLevelDesc.substring(0, maximumLineStringLength);
        }

        if (firstLevelDescMsg != null) drawXCenteredString(firstLevelDescMsg, game.getHeight() / 2, g, msgFont);
        if (secondLevelDescMsg != null) drawXCenteredString(secondLevelDescMsg, game.getHeight() / 2 + 75, g, msgFont);

        drawInformationPanel(g);
    }

    public void updateActiveLevelDesc(String activeLevelDesc) {
        this.activeLevelDesc = activeLevelDesc;
    }

}
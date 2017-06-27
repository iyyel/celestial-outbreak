package io.inabsentia.celestialoutbreak.menu;

import io.inabsentia.celestialoutbreak.controller.Game;
import io.inabsentia.celestialoutbreak.entity.State;
import io.inabsentia.celestialoutbreak.handler.InputHandler;

import java.awt.*;

public class NewLevelMenu extends Menu {

    private String activeLevelDesc;

    public NewLevelMenu(Game game, InputHandler inputHandler, Color fontColor) {
        super(game, inputHandler, fontColor);
    }

    @Override
    public void update() {
        if (inputHandler.isOKPressed()) game.switchState(State.PLAY);
        if (inputHandler.isCancelPressed()) game.switchState(State.MENU);
    }

    public void updateActiveLevelDesc(String activeLevelDesc) {
        this.activeLevelDesc = activeLevelDesc;
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(fontColor);
        drawXCenteredString(textHandler.TITLE, 100, g, titleFont);
        if (activeLevelDesc != null) drawXCenteredString(activeLevelDesc, game.getHeight() / 2, g, msgFont);
        drawInformationPanel(g);
    }

}
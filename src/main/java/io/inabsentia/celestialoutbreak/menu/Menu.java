package io.inabsentia.celestialoutbreak.menu;

import io.inabsentia.celestialoutbreak.controller.Game;
import io.inabsentia.celestialoutbreak.handler.InputHandler;
import io.inabsentia.celestialoutbreak.handler.TextHandler;

import java.awt.*;

public abstract class Menu {

    protected final Game game;
    protected final InputHandler inputHandler;
    protected final TextHandler textHandler;

    public Menu(Game game, InputHandler inputHandler) {
        this.game = game;
        this.inputHandler = inputHandler;
        this.textHandler = TextHandler.getInstance();
    }

    public abstract void update();

    public abstract void render(Graphics2D g);

}
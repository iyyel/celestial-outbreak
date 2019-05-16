package io.iyyel.celestialoutbreak.ui.entity;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.handler.InputHandler;

import java.awt.*;

public final class Paddle extends AbstractMobileEntity {

    private final int PADDLE_PAUSE_SCREEN_TIMER_INITIAL = 120;
    private int paddlePauseTimer = 0;
    private final InputHandler inputHandler;

    public Paddle(Point pos, Dimension dim, int speed,
                  Color color, GameController gameController, InputHandler inputHandler) {
        super(pos, dim, color, speed, gameController);
        this.inputHandler = inputHandler;
    }

    @Override
    public void update() {
        if (paddlePauseTimer == 0) {
            if (inputHandler.isLeftPressed() && pos.x > 0) {
                pos.x -= speed;
            }

            if (inputHandler.isRightPressed() && pos.x <= gameController.getWidth() - dim.width) {
                pos.x += speed;
            }
        } else {
            paddlePauseTimer--;
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(color);
        g.fillRect(pos.x, pos.y, dim.width, dim.height);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(pos.x, pos.y, dim.width, dim.height);
    }

    public void pause() {
        paddlePauseTimer = PADDLE_PAUSE_SCREEN_TIMER_INITIAL;
    }

}
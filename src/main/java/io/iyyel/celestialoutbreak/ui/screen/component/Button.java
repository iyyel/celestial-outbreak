package io.iyyel.celestialoutbreak.ui.screen.component;

import io.iyyel.celestialoutbreak.controller.GameController;

import java.awt.*;

public final class Button extends AbstractComponent {

    private final String text;
    private final boolean centerText;
    private final Font font;
    private Color textColor;
    private final Rectangle rect;
    private final Point textPos;

    private FontMetrics fontMetrics;

    public Button(Point pos, Dimension dim, String text, boolean centerText, Font font, Color textColor,
                  Color bgColor, Point posOffset, Point textOffset, GameController gameController) {
        super(pos, dim, bgColor, posOffset, gameController);
        this.text = text;
        this.centerText = centerText;
        this.font = font;
        this.textColor = textColor;

        rect = new Rectangle(super.pos.x, super.pos.y, super.dim.width, super.dim.height);
        textPos = new Point(super.pos.x + (super.dim.width / 2) - textOffset.x,
                super.pos.y + (super.dim.height / 2) - textOffset.y);
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(textColor);
        g.setFont(font);

        if (centerText) {
            if (fontMetrics == null)
                fontMetrics = g.getFontMetrics();
            int x = (gameController.getWidth() - fontMetrics.stringWidth(text)) / 2;
            g.drawString(text, x, textPos.y);
        } else {
            g.drawString(text, textPos.x, textPos.y);
        }

        g.setColor(bgColor);
        g.draw(rect);
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

}
package io.iyyel.celestialoutbreak.ui.screen.component;

import java.awt.*;

public final class Button extends AbstractComponent {

    private final String text;
    private final boolean centerText;
    private final Font font;
    private Color fgColor;

    private final Rectangle rect;
    private final Point textPos;

    private FontMetrics fontMetrics;

    public Button(Point pos, Dimension dim, String text, boolean centerText, Font font, Color fgColor,
                  Color bgColor, Point posOffset, Point textOffset, int screenWidth, int screenHeight) {
        super(pos, dim, bgColor, posOffset, screenWidth, screenHeight);
        this.text = text;
        this.centerText = centerText;
        this.font = font;
        this.fgColor = fgColor;

        rect = new Rectangle(this.pos.x, this.pos.y, dim.width, dim.height);
        textPos = new Point(this.pos.x + (dim.width / 2) - textOffset.x, this.pos.y + (dim.height / 2) - textOffset.y);
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(fgColor);
        g.setFont(font);
        drawText(g);
        g.setColor(bgColor);
        g.draw(rect);
    }

    private void drawText(Graphics2D g) {
        if (centerText) {
            if (fontMetrics == null)
                fontMetrics = g.getFontMetrics();
            int x = (this.screenWidth - fontMetrics.stringWidth(text)) / 2;
            g.drawString(text, x, textPos.y);
        } else {
            g.drawString(text, textPos.x, textPos.y);
        }
    }

    public void setFgColor(Color fgColor) {
        this.fgColor = fgColor;
    }

}
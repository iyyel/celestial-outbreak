package io.inabsentia.celestialoutbreak.menu;

import io.inabsentia.celestialoutbreak.controller.GameController;
import io.inabsentia.celestialoutbreak.data.dao.IPlayerDAO;
import io.inabsentia.celestialoutbreak.handler.InputHandler;
import io.inabsentia.celestialoutbreak.handler.SoundHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class PlayerMenu extends Menu {

    private final Rectangle createRect, deleteRect, updateRect;
    private final List<Rectangle> playerRectList;
    private final Font btnFont;

    private Color rectColor, selectedColor;

    private String[] options = {"Create", "Update", "Delete"};
    private Color[] rectColors;

    private final IPlayerDAO playerDAO;
    private final SoundHandler soundHandler;

    private int selected = 0;
    private int inputTimer = 18;

    public PlayerMenu(GameController gameController, InputHandler inputHandler, SoundHandler soundHandler, Color fontColor, Color rectColor, Color selectedColor, IPlayerDAO playerDAO) {
        super(gameController, inputHandler, fontColor);
        this.soundHandler = soundHandler;
        this.rectColor = rectColor;
        this.selectedColor = selectedColor;
        this.playerDAO = playerDAO;

        int initialBtnYPos = 230;
        int btnYIncrement = 75;

        /* Menu buttons */
        createRect = new Rectangle(gameController.getWidth() / 2 - 50 - 150, initialBtnYPos + btnYIncrement * 5, 110, 50);
        updateRect = new Rectangle(gameController.getWidth() / 2 - 50, initialBtnYPos + btnYIncrement * 5, 110, 50);
        deleteRect = new Rectangle(gameController.getWidth() / 2 - 50 + 150, initialBtnYPos + btnYIncrement * 5, 110, 50);

        playerRectList = new ArrayList<>();

        rectColors = new Color[options.length];

        for (Color c : rectColors)
            c = rectColor;

        btnFont = utils.getGameFont().deriveFont(20F);
    }

    @Override
    public void update() {
        if (inputTimer > 0) inputTimer--;

        if (inputHandler.isCancelPressed())
            gameController.switchState(GameController.State.MENU);

        if (inputHandler.isRightPressed() && selected < options.length - 1 && inputTimer == 0) {
            selected++;
            soundHandler.MENU_BTN_SELECTION_CLIP.play(false);
            inputTimer = 10;
        }

        if (inputHandler.isLeftPressed() && selected > 0 && inputTimer == 0) {
            selected--;
            soundHandler.MENU_BTN_SELECTION_CLIP.play(false);
            inputTimer = 10;
        }

        for (int i = 0, n = options.length; i < n; i++) {
            if (selected == i) {
                rectColors[i] = selectedColor;

                if (inputHandler.isUsePressed()) {
                    switch (i) {
                        case 0:
                            break;
                        case 1:
                            break;
                        case 2:
                            break;
                        default:
                            break;
                    }
                }
            } else {
                rectColors[i] = rectColor;
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        int yBtnOffset = 33;

        /* Render game title */
        drawMenuTitle(g);

        /* Show sub menu */
        drawSubmenuTitle("Player Selection", g);

        /* Render buttons  */
        g.setFont(btnFont);

        /* Create button */
        g.setColor(fontColor);
        g.setFont(btnFont);
        g.drawString(options[0], createRect.x + 8, createRect.y + 32);
        g.setColor(rectColors[0]);
        g.draw(createRect);

        /* Update button */
        g.setColor(fontColor);
        g.setFont(btnFont);
        g.drawString(options[1], updateRect.x + 3, updateRect.y + 32);
        g.setColor(rectColors[1]);
        g.draw(updateRect);

        /* Delete button */
        g.setColor(fontColor);
        g.setFont(btnFont);
        g.drawString(options[2], deleteRect.x + 9, deleteRect.y + 32);
        g.setColor(rectColors[2]);
        g.draw(deleteRect);

        drawInformationPanel(g);
    }

}

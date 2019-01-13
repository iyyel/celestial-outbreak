package io.iyyel.celestialoutbreak.menu.play;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.handler.LevelHandler;
import io.iyyel.celestialoutbreak.menu.AbstractMenu;

import java.awt.*;

public class SelectLevelMenu extends AbstractMenu {

    private final LevelHandler levelHandler = LevelHandler.getInstance();

    private Rectangle[] levelRects;
    private Color[] levelRectColors;

    private Font levelInfoFont = utils.getPanelFont().deriveFont(15F);

    private int levelAmount = levelHandler.getLevelAmount();
    private int selected = 0;

    public SelectLevelMenu(GameController gameController) {
        super(gameController);
        levelRects = new Rectangle[levelAmount];
        levelRectColors = new Color[levelAmount];

        int initialX = 50;
        int initialY = 215;
        int x = initialX;
        int y = initialY;
        int xInc = 300;
        int yInc = 110;

        for (int i = 0; i < levelAmount; i++) {
            levelRectColors[i] = menuBtnColor;

            if (i % 4 == 0 && i != 0) {
                x += xInc;
                y = initialY;
            }
            levelRects[i] = new Rectangle(x, y, 280, 90);
            y += yInc;
        }
    }

    @Override
    public void update() {
        decInputTimer();

        if (inputHandler.isCancelPressed() && isInputAvailable()) {
            resetInputTimer();
            selected = 0;
            menuUseClip.play(false);
            gameController.switchState(GameController.State.MAIN);
        }

        if (inputHandler.isDownPressed() && (selected + 1) % 4 != 0 && (selected + 1) < levelAmount && isInputAvailable()) {
            resetInputTimer();
            selected++;
            menuNavClip.play(false);
        }

        if (inputHandler.isUpPressed() && selected % 4 != 0 && isInputAvailable()) {
            resetInputTimer();
            selected--;
            menuNavClip.play(false);
        }

        if (inputHandler.isLeftPressed() && selected > 3 && isInputAvailable()) {
            resetInputTimer();
            selected -= 4;
            menuNavClip.play(false);
        }

        if (inputHandler.isRightPressed() && selected < 16 && (selected + 4) < levelAmount && isInputAvailable()) {
            resetInputTimer();
            selected += 4;
            menuNavClip.play(false);
        }

        for (int i = 0, n = levelHandler.getLevelAmount(); i < n; i++) {
            if (selected == i) {
                updateLevelColors(i);

                if (inputHandler.isOKPressed() && isInputAvailable()) {
                    resetInputTimer();
                    selected = 0;

                    // Set current active level to i.
                    levelHandler.loadLevel(i);
                    levelHandler.setActiveLevelIndex(i);
                    gameController.switchState(GameController.State.PRE_LEVEL);
                }
                // TODO: If the user decides to go back from this screen, the activeLevel should perhaps be reset?
            } else {
                updateLevelColors(i);
            }
        }

    }

    @Override
    public void render(Graphics2D g) {
        /* Render game title */
        drawMenuTitle(g);

        /* Show sub menu */
        drawSubmenuTitle("SELECT LEVEL", g);

        /* Render buttons  */

        if (levelRects.length <= 0) {
            g.setFont(inputBtnFont);
            g.setColor(menuFontColor);
            g.drawString("No levels were loaded. Please retry to reload.", 20, gameController.getHeight() / 2);
        } else {
            for (int i = 0; i < levelHandler.getLevelAmount(); i++) {
                g.setFont(inputBtnFont);
                g.setColor(levelHandler.getLevelColors()[i]);
                g.drawString(levelHandler.getLevelNames()[i], levelRects[i].x + 5, levelRects[i].y + 27);

                g.setFont(levelInfoFont);
                g.setColor(menuBtnColor);

                String blockHitPoints = fixedLengthString("Blocks: " + levelHandler.getLevelBlockAmounts()[i] + "/" +
                        levelHandler.getLevelHitPoints()[i], 15);
                String life = fixedLengthString(" Life: " + levelHandler.getLevelPlayerLives()[i], 15);
                g.drawString(blockHitPoints + life, levelRects[i].x + 5, levelRects[i].y + 55);

                String score = fixedLengthString("Score:  " + "123456", 15);
                String time = fixedLengthString(" Time: " + "01:02:03", 15);
                g.drawString(score + time, levelRects[i].x + 5, levelRects[i].y + 75);

                g.setColor(levelRectColors[i]);
                g.draw(levelRects[i]);
            }
        }

        drawMenuToolTip("Press '" + textHandler.BTN_CONTROL_FORWARD_OK + "' to play a level or '" + textHandler.BTN_CONTROL_BACK_CANCEL + "' to go back.", g);
        drawInfoPanel(g);
    }

    private void updateLevelColors(int index) {
        if (selected == index) {
            levelRectColors[index] = menuSelectedBtnColor;
        } else {
            levelRectColors[index] = menuBtnColor;
        }
    }

}
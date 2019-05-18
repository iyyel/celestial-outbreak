package io.iyyel.celestialoutbreak.ui.screen.play;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.ui.screen.AbstractScreen;

import java.awt.*;

public class SelectLevelScreen extends AbstractScreen {

    private Rectangle[] levelRects;
    private Color[] levelRectColors;

    private Font levelInfoFont = util.getPanelFont().deriveFont(16F);

    private int levelAmount = levelHandler.getLevelAmount();
    private int selected = 0;

    public SelectLevelScreen(GameController gameController) {
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
                    menuUseClip.play(false);
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
        drawScreenTitle(g);
        drawScreenSubtitle(textHandler.TITLE_SELECT_LEVEL_SCREEN, g);

        /* Render buttons  */
        if (levelRects.length <= 0) {
            g.setFont(inputBtnFont);
            g.setColor(screenFontColor);
            g.drawString("No levels were loaded.", 20, gameController.getHeight() / 2);
        } else {
            for (int i = 0; i < levelHandler.getLevelAmount(); i++) {
                g.setFont(inputBtnFont);
                g.setColor(levelHandler.getLevelColors()[i]);
                g.drawString(levelHandler.getLevelNames()[i], levelRects[i].x + 5, levelRects[i].y + 27);

                g.setFont(levelInfoFont);
                g.setColor(menuBtnColor);

                String blockHitPoints = textHandler.getFixedString("Blocks: " + levelHandler.getLevelBlockAmounts()[i] + "/" +
                        levelHandler.getLevelHitPoints()[i], 15);
                String life = textHandler.getFixedString(" Life: " + levelHandler.getLevelPlayerLives()[i], 12);
                g.drawString(blockHitPoints + life, levelRects[i].x + 5, levelRects[i].y + 55);

                String score = textHandler.getFixedString("Score:  " + "123456", 15);
                String time = textHandler.getFixedString(" Time: " + "02:03", 12);
                g.drawString(score + time, levelRects[i].x + 5, levelRects[i].y + 75);

                g.setColor(levelRectColors[i]);
                g.draw(levelRects[i]);
            }
        }

        drawScreenToolTip("Press '" + textHandler.BTN_CONTROL_FORWARD_OK + "' to play a level or '" + textHandler.BTN_CONTROL_BACK_CANCEL + "' to go back.", g);
        drawScreenInfoPanel(g);
    }

    private void updateLevelColors(int index) {
        if (selected == index) {
            levelRectColors[index] = menuSelectedBtnColor;
        } else {
            levelRectColors[index] = menuBtnColor;
        }
    }

}
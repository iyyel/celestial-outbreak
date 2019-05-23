package io.iyyel.celestialoutbreak.ui.screen;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.ui.screen.component.Button;

public abstract class AbstractNavigationScreen extends AbstractScreen {

    private final NavStyle navStyle;
    private final int btnAmount;
    private final int btnWrapAmount;

    protected int selected = 0;

    public AbstractNavigationScreen(NavStyle navStyle, int btnAmount, int btnWrapAmount, GameController gameController) {
        super(gameController);
        this.navStyle = navStyle;
        this.btnAmount = btnAmount;
        this.btnWrapAmount = btnWrapAmount;
    }

    public AbstractNavigationScreen(NavStyle navStyle, int btnAmount, GameController gameController) {
        super(gameController);
        this.navStyle = navStyle;
        this.btnAmount = btnAmount;
        this.btnWrapAmount = btnAmount;
    }

    protected abstract void triggerButton(int index);

    protected final void updateNavigation() {
        navigateUp();
        navigateDown();
        navigateLeft();
        navigateRight();
    }

    protected final void navigateUp() {
        if (inputHandler.isUpPressed() && selected % btnWrapAmount != 0 && isInputAvailable()) {
            resetInputTimer();
            selected--;
            menuNavClip.play(false);
        }
    }

    protected final void navigateDown() {
        if (inputHandler.isDownPressed() && (selected + 1) % btnWrapAmount != 0 && (selected + 1) < btnAmount && isInputAvailable()) {
            resetInputTimer();
            selected++;
            menuNavClip.play(false);
        }
    }

    protected final void navigateLeft() {
        if (navStyle != NavStyle.VERTICAL_HORIZONTAL) {
            return;
        }

        if (inputHandler.isLeftPressed() && selected > (btnWrapAmount - 1) && isInputAvailable()) {
            resetInputTimer();
            selected -= btnWrapAmount;
            menuNavClip.play(false);
        }
    }

    protected final void navigateRight() {
        if (navStyle != NavStyle.VERTICAL_HORIZONTAL) {
            return;
        }

        if (inputHandler.isRightPressed() && selected < btnAmount && (selected + btnWrapAmount) < btnAmount && isInputAvailable()) {
            resetInputTimer();
            selected += btnWrapAmount;
            menuNavClip.play(false);
        }
    }

    protected void navigateForward(GameController.State state) {
        if (inputHandler.isOKPressed() && isInputAvailable()) {
            resetInputTimer();
            menuUseClip.play(false);
            gameController.switchState(state);
        }
    }

    protected void navigateBackward(GameController.State state) {
        if (inputHandler.isCancelPressed() && isInputAvailable()) {
            resetInputTimer();
            menuUseClip.play(false);
            gameController.switchState(state);
        }
    }

    protected void updateSelectedButtonColor(Button[] buttons) {
        for (int i = 0; i < buttons.length; i++) {
            Button btn = buttons[i];
            if (selected == i) {
                btn.setColor(menuSelectedBtnColor);
            } else {
                btn.setColor(menuBtnColor);
            }
        }
    }

    public enum NavStyle {
        VERTICAL,
        VERTICAL_HORIZONTAL
    }

}
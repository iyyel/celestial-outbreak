package io.iyyel.celestialoutbreak.ui.screen;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.ui.screen.component.Button;

public abstract class AbstractNavigationScreen extends AbstractScreen {

    private final NavStyle navStyle;
    private final int btnAmount;
    private final int btnWrapAmount;

    protected int selected = 0;

    public enum NavStyle {
        VERTICAL,
        VERTICAL_HORIZONTAL
    }

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

    protected abstract void updateButtonUse(int index);

    protected final void updateNavUp() {
        if (inputHandler.isUpPressed() && selected % btnWrapAmount != 0 && isInputAvailable()) {
            resetInputTimer();
            selected--;
            menuNavClip.play(false);
        }
    }

    protected final void updateNavDown() {
        if (inputHandler.isDownPressed() && (selected + 1) % btnWrapAmount != 0 && (selected + 1) < btnAmount && isInputAvailable()) {
            resetInputTimer();
            selected++;
            menuNavClip.play(false);
        }
    }

    protected final void updateNavLeft() {
        if (navStyle != NavStyle.VERTICAL_HORIZONTAL) {
            return;
        }

        if (inputHandler.isLeftPressed() && selected > (btnWrapAmount - 1) && isInputAvailable()) {
            resetInputTimer();
            selected -= btnWrapAmount;
            menuNavClip.play(false);
        }
    }

    protected final void updateNavRight() {
        if (navStyle != NavStyle.VERTICAL_HORIZONTAL) {
            return;
        }

        if (inputHandler.isRightPressed() && selected < btnAmount && (selected + btnWrapAmount) < btnAmount && isInputAvailable()) {
            resetInputTimer();
            selected += btnWrapAmount;
            menuNavClip.play(false);
        }
    }

    @Override
    protected void updateNavCancel(GameController.State state) {
        super.updateNavCancel(state);

        if (inputHandler.isCancelPressed() && isInputAvailable()) {
            selected = 0;
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

    protected boolean isButtonUsed(int index) {
        boolean isUsed = inputHandler.isUsePressed() && isInputAvailable() && selected == index
                && selected < btnAmount && selected >= 0;

        if (isUsed) {
            resetInputTimer();
        }

        return isUsed;
    }

}
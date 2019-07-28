package io.iyyel.celestialoutbreak.ui.screen;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.ui.screen.component.Button;

public abstract class AbstractNavigationScreen extends AbstractScreen {

    protected final NavStyle navStyle;
    protected final int btnAmount;
    protected final int btnWrapAmount;
    protected boolean isFirstRender = true;

    protected int selectedIndex = 0;

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

    protected abstract void updateNavUse(int index);

    protected abstract void updateNavOK(int index);

    protected final void updateNavUp() {
        if (inputHandler.isUpPressed() && selectedIndex % btnWrapAmount != 0 && isInputAvailable()) {
            resetInputTimer();
            selectedIndex--;
            menuNavClip.play(false);
        }
    }

    protected final void updateNavDown() {
        if (inputHandler.isDownPressed() && (selectedIndex + 1) % btnWrapAmount != 0 && (selectedIndex + 1) < btnAmount && isInputAvailable()) {
            resetInputTimer();
            selectedIndex++;
            menuNavClip.play(false);
        }
    }

    protected final void updateNavLeft() {
        if (navStyle != NavStyle.VERTICAL_HORIZONTAL) {
            return;
        }

        if (inputHandler.isLeftPressed() && selectedIndex > (btnWrapAmount - 1) && isInputAvailable()) {
            resetInputTimer();
            selectedIndex -= btnWrapAmount;
            menuNavClip.play(false);
        }
    }

    protected final void updateNavRight() {
        if (navStyle != NavStyle.VERTICAL_HORIZONTAL) {
            return;
        }

        if (inputHandler.isRightPressed() && selectedIndex < btnAmount && (selectedIndex + btnWrapAmount) < btnAmount && isInputAvailable()) {
            resetInputTimer();
            selectedIndex += btnWrapAmount;
            menuNavClip.play(false);
        }
    }

    @Override
    protected void updateNavCancel(GameController.State state) {
        if (inputHandler.isCancelPressed() && isInputAvailable()) {
            selectedIndex = 0;
            isFirstRender = true;
        }

        super.updateNavCancel(state);
    }

    protected void updateSelectedButtonColor(Button[] buttons) {
        for (int i = 0; i < buttons.length; i++) {
            Button btn = buttons[i];
            if (selectedIndex == i) {
                btn.setBgColor(menuSelectedBtnColor);
            } else {
                btn.setBgColor(menuBtnColor);
            }
        }
    }

    protected boolean isButtonUsed(int index) {
        boolean isUsed = inputHandler.isUsePressed() && isInputAvailable() && selectedIndex == index
                && selectedIndex < btnAmount && selectedIndex >= 0;

        if (isUsed) {
            resetInputTimer();
        }

        return isUsed;
    }

    protected boolean isButtonOK(int index) {
        boolean isOK = inputHandler.isOKPressed() && isInputAvailable() && selectedIndex == index
                && selectedIndex < btnAmount && selectedIndex >= 0;

        if (isOK) {
            resetInputTimer();
        }

        return isOK;
    }

}
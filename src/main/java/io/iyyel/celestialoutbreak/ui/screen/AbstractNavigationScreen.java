package io.iyyel.celestialoutbreak.ui.screen;

import io.iyyel.celestialoutbreak.controller.GameController;

public abstract class AbstractNavigationScreen extends AbstractScreen {

    private final NavigationStyle navStyle;

    private int selected = 0;

    public AbstractNavigationScreen(GameController gameController, NavigationStyle navStyle) {
        super(gameController);
        this.navStyle = navStyle;
    }

    protected final void navigateUp() {


    }

    protected final void navigateDown() {
        if (inputHandler.isDownPressed() && (selected + 1) % 5 != 0 && (selected + 1) < 20 && isInputAvailable()) {
            resetInputTimer();
            selected++;
            menuNavClip.play(false);
        }

    }

    protected final void navigateLeft() {

        if (navStyle != NavigationStyle.VERTICAL_HORIZONTAL) {
            return;
        }

        if (inputHandler.isLeftPressed() && selected > 4 && isInputAvailable()) {
            resetInputTimer();
            selected -= 5;
            menuNavClip.play(false);
        }

    }

    protected final void navigateRight() {

        if (navStyle != NavigationStyle.VERTICAL_HORIZONTAL) {
            return;
        }

        if (inputHandler.isRightPressed() && selected < 20 && (selected + 5) < 20 && isInputAvailable()) {
            resetInputTimer();
            selected += 5;
            menuNavClip.play(false);
        }

    }

    protected final void navigateForward() {

    }

    protected final void navigateBackward() {

    }

    enum NavigationStyle {
        VERTICAL,
        VERTICAL_HORIZONTAL
    }

}
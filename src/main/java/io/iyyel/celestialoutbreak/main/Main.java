package io.iyyel.celestialoutbreak.main;

import io.iyyel.celestialoutbreak.controller.GameController;

import javax.swing.*;

public final class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameController().start());
    }

}
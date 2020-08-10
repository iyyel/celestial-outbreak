package io.iyyel.celestialoutbreak.ui.interfaces;

public interface IUpdatable {
    void update();
    void stopUpdate(int updates);
    boolean isUpdateStopped();
}
package io.iyyel.celestialoutbreak.ui.interfaces;

public interface IEntityUpdatable {
    void update();
    void stopUpdate();
    void resumeUpdate();
    boolean isUpdateStopped();
}
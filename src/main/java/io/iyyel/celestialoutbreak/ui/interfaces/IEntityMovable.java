package io.iyyel.celestialoutbreak.ui.interfaces;

public interface IEntityMovable {
    enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    void move(Direction dir);
}
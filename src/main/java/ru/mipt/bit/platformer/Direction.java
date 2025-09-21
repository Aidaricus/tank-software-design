package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

public enum Direction {
    UP(0, 1, 90f),
    DOWN(0, -1, -90f),
    LEFT(-1, 0, 180f),
    RIGHT(1, 0, 0f);

    private final GridPoint2 delta;
    private final float rotation;

    Direction(int dx, int dy, float rotation) {
        this.delta = new GridPoint2(dx, dy);
        this.rotation = rotation;
    }

    public GridPoint2 delta() {
        return new GridPoint2(delta);
    }

    public float rotation() {
        return rotation;
    }
}
package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

public interface ObstacleProvider {
    boolean isCellFree(GridPoint2 point);
}
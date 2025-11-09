package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

public abstract class GameObjectModel {
    protected GridPoint2 coordinates;

    public GameObjectModel(GridPoint2 initialCoordinates) {
        this.coordinates = new GridPoint2(initialCoordinates);
    }

    public GridPoint2 getCoordinates() {
        return new GridPoint2(coordinates);
    }

    public GridPoint2 getDestination() {
        return getCoordinates();
    }

    public boolean isMoving() {
        return !getCoordinates().equals(getDestination());
    }

}
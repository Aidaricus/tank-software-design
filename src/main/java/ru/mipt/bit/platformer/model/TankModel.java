package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Direction;

public class TankModel extends GameObjectModel implements Movable {
    private final ObstacleProvider obstacleProvider;
    private GridPoint2 destination;

    public TankModel(GridPoint2 initialCoordinates, ObstacleProvider obstacleProvider) {
        super(initialCoordinates);
        this.obstacleProvider = obstacleProvider;
        this.destination = new GridPoint2(initialCoordinates);
    }

    @Override
    public GridPoint2 getDestination() {
        return new GridPoint2(destination);
    }

    public void finalizeMovement() {
        coordinates.set(destination);
    }

    @Override
    public void move(Direction direction) {
        if (isMoving()) {
            return;
        }

        GridPoint2 nextCoordinates = coordinates.cpy().add(direction.delta());

        if (obstacleProvider.isCellFree(nextCoordinates)) {
            this.destination.set(nextCoordinates);
        }
    }
}
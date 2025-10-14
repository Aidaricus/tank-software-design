package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Direction;

public class TankModel extends GameObjectModel implements Movable {
    private final ObstacleProvider obstacleProvider;

    public TankModel(GridPoint2 initialCoordinates, ObstacleProvider obstacleProvider) {
        super(initialCoordinates);
        this.obstacleProvider = obstacleProvider;
    }
    
    @Override
    public void move(Direction direction) {
        GridPoint2 nextCoordinates = coordinates.cpy().add(direction.delta());

        if (obstacleProvider.isCellFree(nextCoordinates)) {
            this.coordinates.set(nextCoordinates);
        }
    }
}
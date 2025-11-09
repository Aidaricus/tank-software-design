package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Direction;

public class TankModel extends GameObjectModel implements Movable {
    private final ObstacleProvider obstacleProvider;
    private GridPoint2 destination;

    private final int maxHealth;
    private int health;

    public TankModel(GridPoint2 initialCoordinates, ObstacleProvider obstacleProvider) {
        super(initialCoordinates);
        this.obstacleProvider = obstacleProvider;
        this.destination = new GridPoint2(initialCoordinates);
        this.maxHealth = 100;
        this.health = this.maxHealth;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    @Override
    public GridPoint2 getDestination() {
        return new GridPoint2(destination);
    }

    public void finalizeMovement() {
        coordinates.set(destination);
    }

    public void takeDamage(int amount) {
        this.health = Math.max(0, this.health - amount);
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
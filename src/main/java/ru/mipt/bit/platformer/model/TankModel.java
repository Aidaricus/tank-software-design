package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Direction;

public class TankModel extends GameObjectModel implements Movable {
    private final ObstacleProvider obstacleProvider;
    private GridPoint2 destination;
    private final int maxHealth;
    private int health;
    private Direction orientation = Direction.UP;

    public TankModel(GridPoint2 initialCoordinates, ObstacleProvider obstacleProvider) {
        super(initialCoordinates);
        this.obstacleProvider = obstacleProvider;
        this.destination = new GridPoint2(initialCoordinates);
        this.maxHealth = 100;
        this.health = this.maxHealth;
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
        this.orientation = direction;
        if (isMoving()) {
            return;
        }

        GridPoint2 nextCoordinates = coordinates.cpy().add(direction.delta());

        if (obstacleProvider.isCellFree(nextCoordinates)) {
            this.destination.set(nextCoordinates);
        }
    }

    public void shoot(FieldModel fieldModel) {
        GridPoint2 bulletStartPos = coordinates.cpy().add(orientation.delta());

        if (!fieldModel.isCellFree(bulletStartPos)) {
            return;
        }

        BulletModel bullet = new BulletModel(bulletStartPos, this.orientation, this);
        fieldModel.addObject(bullet);
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
    
    public void takeDamage(int amount) {
        this.health = Math.max(0, this.health - amount);
    }
}
package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Direction;

public class BulletModel extends GameObjectModel {
    private final Direction direction;
    private final TankModel owner;

    public BulletModel(GridPoint2 initialCoordinates, Direction direction, TankModel owner) {
        super(initialCoordinates);
        this.direction = direction;
        this.owner = owner;
    }
    
    public Direction getDirection() {
        return direction;
    }
    
    public TankModel getOwner() {
        return owner;
    }
}
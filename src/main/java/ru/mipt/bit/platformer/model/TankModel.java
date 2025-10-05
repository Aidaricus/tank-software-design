package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Direction;

public class TankModel extends GameObjectModel {
    private final FieldModel fieldModel;

    public TankModel(GridPoint2 initialCoordinates, FieldModel fieldModel) {
        super(initialCoordinates);
        this.fieldModel = fieldModel;
    }

    
    public void move(Direction direction) {
        GridPoint2 nextCoordinates = coordinates.cpy().add(direction.delta());

        if (fieldModel.isCellFree(nextCoordinates)) {
            this.coordinates.set(nextCoordinates);
        }
    }
}
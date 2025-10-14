package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.List;

public class FieldModel implements ObstacleProvider {
    private final List<GameObjectModel> objects = new ArrayList<>();

    public void addObject(GameObjectModel obj) {
        objects.add(obj);
    }

    @Override
    public boolean isCellFree(GridPoint2 point) {
        return objects.stream().noneMatch(o -> o.getCoordinates().equals(point));
    }
}
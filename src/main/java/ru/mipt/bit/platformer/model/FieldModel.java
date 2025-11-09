package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.List;

public class FieldModel implements ObstacleProvider {
    private final List<GameObjectModel> objects = new ArrayList<>();
    private final int width;
    private final int height;

    public FieldModel(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void addObject(GameObjectModel obj) {
        objects.add(obj);
    }

    @Override
    public boolean isCellFree(GridPoint2 point) {
        if (point.x < 0 || point.x >= width || point.y < 0 || point.y >= height) {
            return false;
        }

        for (GameObjectModel obj : objects) {
            if (obj.getCoordinates().equals(point) || obj.getDestination().equals(point)) {
                return false;
            }
        }
        return true;
    }
}
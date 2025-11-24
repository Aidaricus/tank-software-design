package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.List;

public class FieldModel implements ObstacleProvider {
    private final List<GameObjectModel> objects = new ArrayList<>();
    private final List<GameObjectModel> objectsToRemove = new ArrayList<>();
    private final List<GameObjectListener> listeners = new ArrayList<>();
    private final int width;
    private final int height;

    public FieldModel(int width, int height) {
        this.width = width;
        this.height = height;
    }

    // === НАЧАЛО НОВЫХ МЕТОДОВ ===
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    // === КОНЕЦ НОВЫХ МЕТОДОВ ===

    public void addListener(GameObjectListener listener) {
        listeners.add(listener);
    }

    public void addObject(GameObjectModel obj) {
        objects.add(obj);
        for (GameObjectListener listener : listeners) {
            listener.onGameObjectAdded(obj);
        }
    }
    
    public void removeObject(GameObjectModel obj) {
        objectsToRemove.add(obj);
    }

    public void processRemovals() {
        if (objectsToRemove.isEmpty()) {
            return;
        }
        for (GameObjectModel obj : objectsToRemove) {
            objects.remove(obj);
            for (GameObjectListener listener : listeners) {
                listener.onGameObjectRemoved(obj);
            }
        }
        objectsToRemove.clear();
    }
    
    public List<GameObjectModel> getObjects() {
        return new ArrayList<>(objects);
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
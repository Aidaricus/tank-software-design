package ru.mipt.bit.platformer.model;

public interface GameObjectListener {
    void onGameObjectAdded(GameObjectModel model);
    void onGameObjectRemoved(GameObjectModel model);
}
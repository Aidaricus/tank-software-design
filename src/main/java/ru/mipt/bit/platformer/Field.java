package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.ArrayList;
import java.util.List;

public class Field {
    private final TiledMap map;
    private final MapRenderer renderer;
    private final List<GameObject> objects = new ArrayList<>();
    private final TiledMapTileLayer groundLayer;

    public Field(TiledMap map, MapRenderer renderer, TiledMapTileLayer groundLayer) {
        this.map = map;
        this.renderer = renderer;
        this.groundLayer = groundLayer;
    }

    public void addObject(GameObject obj) {
        objects.add(obj);
    }

    public boolean isFree(com.badlogic.gdx.math.GridPoint2 point) {
        return objects.stream().noneMatch(o -> o.getCoordinates().equals(point));
    }

    public void render(Batch batch) {
        renderer.render();
        batch.begin();
        for (GameObject obj : objects) {
            obj.render(batch);
    }
    batch.end();
    }

    public TiledMapTileLayer getGroundLayer() {
        return groundLayer;
    }

    public void dispose() {
        map.dispose();
    }
}
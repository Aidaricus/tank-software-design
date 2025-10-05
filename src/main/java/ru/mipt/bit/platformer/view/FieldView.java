package ru.mipt.bit.platformer.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;

import java.util.ArrayList;
import java.util.List;

public class FieldView {
    private final TiledMap map;
    private final MapRenderer renderer;
    private final List<GameObjectView> objectViews = new ArrayList<>();

    public FieldView(TiledMap map, MapRenderer renderer) {
        this.map = map;
        this.renderer = renderer;
    }

    public void addObjectView(GameObjectView view) {
        objectViews.add(view);
    }

    public void update(float deltaTime) {
        for (GameObjectView view : objectViews) {
            view.update(deltaTime);
        }
    }

    public void render(Batch batch) {
        renderer.render();
        batch.begin();
        for (GameObjectView view : objectViews) {
            view.render(batch);
        }
        batch.end();
    }

    public void dispose() {
        map.dispose();
    }
}
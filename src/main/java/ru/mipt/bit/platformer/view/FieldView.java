package ru.mipt.bit.platformer.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import ru.mipt.bit.platformer.model.GameObjectListener;
import ru.mipt.bit.platformer.model.GameObjectModel;

import java.util.HashMap;
import java.util.Map;

public class FieldView implements GameObjectListener {

    private final TiledMap map;
    private final MapRenderer renderer;
    private final Map<GameObjectModel, GameObjectView> viewMap = new HashMap<>();
    private final ViewFactory viewFactory;

    public FieldView(TiledMap map, MapRenderer renderer, ViewFactory viewFactory) {
        this.map = map;
        this.renderer = renderer;
        this.viewFactory = viewFactory;
    }

    @Override
    public void onGameObjectAdded(GameObjectModel model) {
        GameObjectView view = viewFactory.createView(model);
        viewMap.put(model, view);
    }

    @Override
    public void onGameObjectRemoved(GameObjectModel model) {
        viewMap.remove(model);
    }

    public void update(float deltaTime) {
        for (GameObjectView view : viewMap.values()) {
            view.update(deltaTime);
        }
    }

    public void render(Batch batch) {
        if (renderer instanceof OrthogonalTiledMapRenderer) {
            ((OrthogonalTiledMapRenderer) renderer).render();
        } else {
            renderer.render();
        }

        batch.begin();
        for (GameObjectView view : viewMap.values()) {
            view.render(batch);
        }
        batch.end();
    }

    public void dispose() {
        map.dispose();
        viewFactory.dispose();
    }
}
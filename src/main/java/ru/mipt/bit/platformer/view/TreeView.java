package ru.mipt.bit.platformer.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.TreeModel;
import ru.mipt.bit.platformer.util.GdxGameUtils;

public class TreeView implements GameObjectView {
    private final TreeModel model;
    private final TextureRegion texture;
    private final Rectangle rectangle;

    public TreeView(TreeModel model, TextureRegion texture, TiledMapTileLayer groundLayer) {
        this.model = model;
        this.texture = texture;
        this.rectangle = GdxGameUtils.createBoundingRectangle(texture);
        GdxGameUtils.moveRectangleAtTileCenter(groundLayer, rectangle, model.getCoordinates());
    }

    @Override
    public void update(float deltaTime) {
    }

    @Override
    public void render(Batch batch) {
        GdxGameUtils.drawTextureRegionUnscaled(batch, texture, rectangle, 0f);
    }

    @Override
    public TreeModel getModel() {
        return model;
    }
}
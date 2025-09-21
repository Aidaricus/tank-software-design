package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.GdxGameUtils;

public class Tree extends GameObject {

    public Tree(TextureRegion graphics, GridPoint2 coordinates, TiledMapTileLayer groundLayer) {
        super(graphics, coordinates);
        GdxGameUtils.moveRectangleAtTileCenter(groundLayer, rectangle, coordinates);
    }

    @Override
    public void update(float deltaTime) {
    }

    @Override
    public void render(Batch batch) {
        GdxGameUtils.drawTextureRegionUnscaled(batch, graphics, rectangle, 0f);
    }
}
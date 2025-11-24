package ru.mipt.bit.platformer.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.BulletModel;
import ru.mipt.bit.platformer.model.GameObjectModel;
import ru.mipt.bit.platformer.util.GdxGameUtils;
import ru.mipt.bit.platformer.util.TileMovement;

public class BulletView implements GameObjectView {
    private final BulletModel model;
    private final TextureRegion texture;
    private final Rectangle rectangle;
    private final TileMovement tileMovement;

    public BulletView(BulletModel model, TextureRegion texture, TileMovement tileMovement) {
        this.model = model;
        this.texture = texture;
        this.tileMovement = tileMovement;
        this.rectangle = GdxGameUtils.createBoundingRectangle(texture);
        tileMovement.moveRectangleToTileCenter(rectangle, model.getCoordinates());
    }

    @Override
    public void update(float deltaTime) {
        tileMovement.moveRectangleToTileCenter(rectangle, model.getCoordinates());
    }

    @Override
    public void render(Batch batch) {
        GdxGameUtils.drawTextureRegionUnscaled(batch, texture, rectangle, model.getDirection().rotation());
    }

    @Override
    public GameObjectModel getModel() {
        return model;
    }

    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }
}
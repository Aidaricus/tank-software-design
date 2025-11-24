package ru.mipt.bit.platformer.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.Direction;
import ru.mipt.bit.platformer.model.TankModel;
import ru.mipt.bit.platformer.util.GdxGameUtils;
import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public class TankView implements GameObjectView {
    private final TankModel model;
    private final TextureRegion texture;
    private final Rectangle rectangle;
    private final TileMovement tileMovement;
    private final float movementSpeed;

    private final GridPoint2 lastModelCoordinates;
    private GridPoint2 startCoordinates;
    private GridPoint2 destCoordinates;
    private float movementProgress = 1f;
    private float rotation = 0f;

    public TankView(TankModel model, TextureRegion texture, TileMovement tileMovement, float movementSpeed) {
        this.model = model;
        this.texture = texture;
        this.tileMovement = tileMovement;
        this.movementSpeed = movementSpeed;
        this.rectangle = GdxGameUtils.createBoundingRectangle(texture);

        this.lastModelCoordinates = model.getCoordinates();
        this.startCoordinates = model.getCoordinates();
        this.destCoordinates = model.getCoordinates();

        tileMovement.moveRectangleToTileCenter(rectangle, model.getCoordinates());
    }

    @Override
    public void update(float deltaTime) {
        if (model.isMoving() && isEqual(movementProgress, 1f)) {
            movementProgress = 0f;
            startCoordinates.set(model.getCoordinates());
            destCoordinates.set(model.getDestination());
            rotation = getDirection(startCoordinates, destCoordinates).rotation();
        }

        if (!isEqual(movementProgress, 1f)) {
            movementProgress = GdxGameUtils.continueProgress(movementProgress, deltaTime, movementSpeed);
            tileMovement.moveRectangleBetweenTileCenters(rectangle, startCoordinates, destCoordinates, movementProgress);

            if (isEqual(movementProgress, 1f)) {
                model.finalizeMovement();
            }
        } else {
             tileMovement.moveRectangleToTileCenter(rectangle, model.getCoordinates());
        }
    }

    @Override
    public void render(Batch batch) {
        GdxGameUtils.drawTextureRegionUnscaled(batch, texture, rectangle, rotation);
    }

    @Override
    public TankModel getModel() {
        return model;
    }

    private Direction getDirection(GridPoint2 from, GridPoint2 to) {
        GridPoint2 delta = to.cpy().sub(from);
        if (delta.equals(Direction.UP.delta())) return Direction.UP;
        if (delta.equals(Direction.DOWN.delta())) return Direction.DOWN;
        if (delta.equals(Direction.LEFT.delta())) return Direction.LEFT;
        if (delta.equals(Direction.RIGHT.delta())) return Direction.RIGHT;
        return Direction.RIGHT;
    }

    @Override
    public Rectangle getRectangle() {
        return rectangle;
}
}
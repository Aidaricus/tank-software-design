package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.GdxGameUtils;
import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public class Tank extends GameObject {
    private GridPoint2 destination;
    private float movementProgress = 1f;
    private float rotation;
    private final TileMovement tileMovement;
    private final float movementSpeed;
    private final Field field;

    public Tank(TextureRegion graphics, GridPoint2 startPos, TileMovement tileMovement, float movementSpeed, Field field) {
        super(graphics, startPos);
        this.tileMovement = tileMovement;
        this.destination = new GridPoint2(startPos);
        this.movementSpeed = movementSpeed;
        this.rotation = 0f;
        this.field = field;
    }

    public void move(Direction dir) {
        if (isEqual(movementProgress, 1f)) {
            GridPoint2 next = new GridPoint2(coordinates).add(dir.delta());
            if (field.isFree(next)) {
                destination = next;
                movementProgress = 0f;
                rotation = dir.rotation();
            }
        }
    }

    @Override
    public void update(float deltaTime) {
        tileMovement.moveRectangleBetweenTileCenters(rectangle, coordinates, destination, movementProgress);
        movementProgress = GdxGameUtils.continueProgress(movementProgress, deltaTime, movementSpeed);
        if (isEqual(movementProgress, 1f)) {
            coordinates.set(destination);
        }
    }

    @Override
    public void render(Batch batch) {
        GdxGameUtils.drawTextureRegionUnscaled(batch, graphics, rectangle, rotation);
    }
}
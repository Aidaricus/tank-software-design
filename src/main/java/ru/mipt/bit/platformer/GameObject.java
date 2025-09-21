package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.GdxGameUtils;

public abstract class GameObject {
    protected final TextureRegion graphics;
    protected final Rectangle rectangle;
    protected final GridPoint2 coordinates;

    public GameObject(TextureRegion graphics, GridPoint2 coordinates) {
        this.graphics = graphics;
        this.rectangle = GdxGameUtils.createBoundingRectangle(graphics);
        this.coordinates = new GridPoint2(coordinates);
    }

    public GridPoint2 getCoordinates() {
        return new GridPoint2(coordinates);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public abstract void update(float deltaTime);

    public abstract void render(Batch batch);
}
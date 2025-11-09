package ru.mipt.bit.platformer.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.GameObjectModel;

public interface GameObjectView {
    void update(float deltaTime);

    void render(Batch batch);

    GameObjectModel getModel();
    Rectangle getRectangle();
}
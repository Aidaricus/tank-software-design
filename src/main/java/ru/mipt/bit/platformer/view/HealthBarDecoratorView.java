package ru.mipt.bit.platformer.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.UIState;
import ru.mipt.bit.platformer.model.GameObjectModel;
import ru.mipt.bit.platformer.model.TankModel;
import ru.mipt.bit.platformer.util.GdxGameUtils;

public class HealthBarDecoratorView implements GameObjectView {
    private final GameObjectView decoratedView;
    private final UIState uiState;

    public HealthBarDecoratorView(GameObjectView decoratedView, UIState uiState) {
        this.decoratedView = decoratedView;
        this.uiState = uiState;
    }

    @Override
    public void render(Batch batch) {
        decoratedView.render(batch);

        if (uiState.isHealthBarVisible()) {
            GameObjectModel model = getModel();
            if (model instanceof TankModel) {
                TankModel tankModel = (TankModel) model;
                Rectangle rectangle = getRectangle();
                
                float healthPercentage = (float) tankModel.getHealth() / tankModel.getMaxHealth();
                
                float barWidth = rectangle.width * 0.8f;
                float barHeight = 10f;
                float barX = rectangle.x + (rectangle.width - barWidth) / 2;
                float barY = rectangle.y + rectangle.height + 5;

                GdxGameUtils.drawHealthBar(batch, barX, barY, barWidth, barHeight, healthPercentage);
            }
        }
    }

    @Override
    public void update(float deltaTime) {
        decoratedView.update(deltaTime);
    }

    @Override
    public GameObjectModel getModel() {
        return decoratedView.getModel();
    }

    @Override
    public Rectangle getRectangle() {
        return decoratedView.getRectangle();
    }
}
package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.model.FieldModel;
import ru.mipt.bit.platformer.model.TankModel;
import ru.mipt.bit.platformer.view.FieldView;
import ru.mipt.bit.platformer.UIState;


public interface LevelGenerator {
    void generate(FieldModel fieldModel, FieldView fieldView, UIState uiState);
    TankModel getPlayerModel();
}
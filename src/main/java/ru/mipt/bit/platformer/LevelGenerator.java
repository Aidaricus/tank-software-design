package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.model.FieldModel;
import ru.mipt.bit.platformer.model.TankModel;

public interface LevelGenerator {
    void generate(FieldModel fieldModel);

    TankModel getPlayerModel();
}
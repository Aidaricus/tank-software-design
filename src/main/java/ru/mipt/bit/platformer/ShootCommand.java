package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.model.FieldModel;
import ru.mipt.bit.platformer.model.TankModel;

public class ShootCommand implements Command {
    private final TankModel tankModel;
    private final FieldModel fieldModel;

    public ShootCommand(TankModel tankModel, FieldModel fieldModel) {
        this.tankModel = tankModel;
        this.fieldModel = fieldModel;
    }

    @Override
    public void execute() {
        tankModel.shoot(fieldModel);
    }
}
package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.model.TankModel;

public class MoveCommand implements Command {
    private final TankModel tankModel;
    private final Direction direction;

    public MoveCommand(TankModel tankModel, Direction direction) {
        this.tankModel = tankModel;
        this.direction = direction;
    }

    @Override
    public void execute() {
        tankModel.move(direction);
    }
}
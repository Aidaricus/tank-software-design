package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.model.FieldModel;
import ru.mipt.bit.platformer.model.GameObjectListener;
import ru.mipt.bit.platformer.model.GameObjectModel;
import ru.mipt.bit.platformer.model.TankModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AIController implements GameObjectListener {
    private final List<TankModel> controlledTanks = new ArrayList<>();
    private final Random random = new Random();
    private final FieldModel fieldModel;

    public AIController(FieldModel fieldModel) {
        this.fieldModel = fieldModel;
    }

    public void addTank(TankModel tank) {
        controlledTanks.add(tank);
    }

    public void update() {
        for (TankModel tank : controlledTanks) {
            if (random.nextInt(100) < 2) {
                if (random.nextBoolean()) {
                    new ShootCommand(tank, fieldModel).execute();
                } else {
                    List<Direction> directions = new ArrayList<>(List.of(Direction.values()));
                    Collections.shuffle(directions);
                    new MoveCommand(tank, directions.get(0)).execute();
                }
            }
        }
    }

    @Override
    public void onGameObjectRemoved(GameObjectModel model) {
        controlledTanks.remove(model);
    }

    @Override
    public void onGameObjectAdded(GameObjectModel model) {
    }
}
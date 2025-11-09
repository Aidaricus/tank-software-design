package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.model.TankModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AIController {
    private final List<TankModel> controlledTanks = new ArrayList<>();
    private final Random random = new Random();

    public void addTank(TankModel tank) {
        controlledTanks.add(tank);
    }
    
    public void update() {
        for (TankModel tank : controlledTanks) {
            if (random.nextInt(100) < 1) {
                List<Direction> directions = new ArrayList<>(List.of(Direction.values()));
                Collections.shuffle(directions);

                new MoveCommand(tank, directions.get(0)).execute();
            }
        }
    }
}
package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.*;

import java.util.ArrayList;
import java.util.List;

public class GameProcessor {
    private final FieldModel fieldModel;
    private float movementCoolDown = 0f;
    private static final float MOVEMENT_INTERVAL = 0.2f;

    public GameProcessor(FieldModel fieldModel) {
        this.fieldModel = fieldModel;
    }

    public void update(float deltaTime) {
        movementCoolDown -= deltaTime;
        if (movementCoolDown > 0) {
            return;
        }
        movementCoolDown = MOVEMENT_INTERVAL;

        List<GameObjectModel> gameObjects = fieldModel.getObjects();
        List<BulletModel> bullets = new ArrayList<>();
        for (GameObjectModel obj : gameObjects) {
            if (obj instanceof BulletModel) {
                bullets.add((BulletModel) obj);
            }
        }
        
        for (BulletModel bullet : bullets) {
            GridPoint2 oldPos = bullet.getCoordinates();
            GridPoint2 nextPos = oldPos.cpy().add(bullet.getDirection().delta());
            bullet.setCoordinates(nextPos);
        }

        List<GameObjectModel> currentObjects = new ArrayList<>(fieldModel.getObjects());
        for (int i = 0; i < currentObjects.size(); i++) {
            for (int j = i + 1; j < currentObjects.size(); j++) {
                GameObjectModel obj1 = currentObjects.get(i);
                GameObjectModel obj2 = currentObjects.get(j);

                if (obj1.getCoordinates().equals(obj2.getCoordinates())) {
                    handleCollision(obj1, obj2);
                }
            }
        }
        
        fieldModel.processRemovals();
    }

    private void handleCollision(GameObjectModel obj1, GameObjectModel obj2) {
        if (obj1 instanceof BulletModel) {
            handleBulletCollision((BulletModel) obj1, obj2);
        }
        if (obj2 instanceof BulletModel) {
            handleBulletCollision((BulletModel) obj2, obj1);
        }
    }

    private void handleBulletCollision(BulletModel bullet, GameObjectModel other) {
        if (other == bullet.getOwner() && bullet.getCoordinates().equals(bullet.getOwner().getCoordinates().cpy().add(bullet.getDirection().delta()))) {
            return;
        }
        
        fieldModel.removeObject(bullet);
        
        if (other instanceof TankModel) {
            TankModel tank = (TankModel) other;
            tank.takeDamage(25);
            if (tank.getHealth() <= 0) {
                fieldModel.removeObject(tank);
            }
        }
    }
}
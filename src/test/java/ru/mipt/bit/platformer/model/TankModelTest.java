package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.Direction;

import static org.junit.jupiter.api.Assertions.*;

class TankModelTest {
    private FieldModel fieldModel;
    private TankModel tankModel;
    private GridPoint2 initialPosition;
    
    @BeforeEach
    void setUp() {
        fieldModel = new FieldModel();
        initialPosition = new GridPoint2(1, 1);
        // Создаем танк в начальной позиции
        tankModel = new TankModel(initialPosition.cpy(), fieldModel);
        fieldModel.addObject(tankModel);
    }

    @Test
    void testMoveToFreeCell() {
        // Даем команду танку двигаться вверх, где поле свободно
        tankModel.move(Direction.UP);
        
        // Ожидаем, что новые координаты будут (1, 2)
        GridPoint2 expectedPosition = new GridPoint2(1, 2);
        
        // Сравниваем ожидаемый результат с фактическим
        assertEquals(expectedPosition, tankModel.getCoordinates(), "Tank should move to the new coordinates");
    }

    @Test
    void testMoveToOccupiedCell() {
        // Создаем препятствие (дерево) на пути танка
        fieldModel.addObject(new TreeModel(new GridPoint2(1, 2)));

        // Даем команду танку двигаться вверх на занятую клетку
        tankModel.move(Direction.UP);
        
        // Ожидаем, что танк останется на своей начальной позиции
        assertEquals(initialPosition, tankModel.getCoordinates(), "Tank should not move into an occupied cell");
    }
}
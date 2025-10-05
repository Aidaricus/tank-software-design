package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FieldModelTest {

    private FieldModel fieldModel;

    // Этот метод с аннотацией @BeforeEach будет вызываться перед запуском каждого теста
    // Он гарантирует, что каждый тест начинается с "чистого" игрового поля
    @BeforeEach
    void setUp() {
        fieldModel = new FieldModel();
    }

    @Test
    void testIsCellFreeWhenFieldIsEmpty() {
        // Утверждаем, что клетка (5, 5) на пустом поле должна быть свободна
        assertTrue(fieldModel.isCellFree(new GridPoint2(5, 5)), "An empty field should have all cells free");
    }

    @Test
    void testIsCellFreeWhenCellIsOccupied() {
        GridPoint2 occupiedPoint = new GridPoint2(2, 3);
        // Добавляем на поле объект (модель дерева) в точку (2, 3)
        fieldModel.addObject(new TreeModel(occupiedPoint));
        
        // Утверждаем, что клетка (2, 3) теперь должна быть занята
        assertFalse(fieldModel.isCellFree(occupiedPoint), "The cell with an object should not be free");
        
        // В качестве дополнительной проверки, утверждаем, что соседняя клетка осталась свободной
        assertTrue(fieldModel.isCellFree(new GridPoint2(2, 4)), "A neighboring cell should be free");
    }
}
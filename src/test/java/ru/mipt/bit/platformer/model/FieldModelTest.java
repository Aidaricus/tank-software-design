package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.Direction;

import static org.junit.jupiter.api.Assertions.*;

class FieldModelTest {

    private FieldModel fieldModel;

    @BeforeEach
    void setUp() {
        fieldModel = new FieldModel(10, 8);
    }

    @Test
    void testIsCellFreeWhenFieldIsEmpty() {
        assertTrue(fieldModel.isCellFree(new GridPoint2(5, 5)), "An empty field should have all cells free");
    }

    @Test
    void testIsCellFreeWhenCellIsOccupiedByStaticObject() {
        GridPoint2 occupiedPoint = new GridPoint2(2, 3);
        fieldModel.addObject(new TreeModel(occupiedPoint));
        
        assertFalse(fieldModel.isCellFree(occupiedPoint), "The cell with an object should not be free");
        assertTrue(fieldModel.isCellFree(new GridPoint2(2, 4)), "A neighboring cell should be free");
    }

    @Test
    void testBoundaryChecks() {
        assertFalse(fieldModel.isCellFree(new GridPoint2(-1, 5)), "Should not be free outside left boundary");
        assertFalse(fieldModel.isCellFree(new GridPoint2(10, 5)), "Should not be free outside right boundary");
        assertFalse(fieldModel.isCellFree(new GridPoint2(5, -1)), "Should not be free outside bottom boundary");
        assertFalse(fieldModel.isCellFree(new GridPoint2(5, 8)), "Should not be free outside top boundary");
        assertTrue(fieldModel.isCellFree(new GridPoint2(9, 7)), "Top-right corner cell should be free");
        assertTrue(fieldModel.isCellFree(new GridPoint2(0, 0)), "Bottom-left corner cell should be free");
    }

    @Test
    void testMovementOccupationRule() {
        TankModel movingTank = new TankModel(new GridPoint2(2, 2), fieldModel);
        fieldModel.addObject(movingTank);

        movingTank.move(Direction.UP);
        assertTrue(movingTank.isMoving());
        
        assertFalse(fieldModel.isCellFree(new GridPoint2(2, 2)), "Origin cell should be occupied during move");
        assertFalse(fieldModel.isCellFree(new GridPoint2(2, 3)), "Destination cell should be occupied during move");
        assertTrue(fieldModel.isCellFree(new GridPoint2(2, 4)), "A neighboring cell should be free");
        
        movingTank.finalizeMovement();
        assertFalse(movingTank.isMoving());

        assertTrue(fieldModel.isCellFree(new GridPoint2(2, 2)), "Origin cell should be free after move");
        assertFalse(fieldModel.isCellFree(new GridPoint2(2, 3)), "Destination cell should now be the occupied cell");
    }
}
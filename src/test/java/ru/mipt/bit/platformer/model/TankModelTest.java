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
        fieldModel = new FieldModel(10, 8);
        initialPosition = new GridPoint2(1, 1);
        tankModel = new TankModel(initialPosition.cpy(), fieldModel);
        fieldModel.addObject(tankModel);
    }

    @Test
    void testMoveToFreeCellInitiatesMovement() {
        tankModel.move(Direction.UP);
        
        GridPoint2 expectedDestination = new GridPoint2(1, 2);
        
        assertEquals(initialPosition, tankModel.getCoordinates(), "Tank coordinates should not change immediately");
        assertEquals(expectedDestination, tankModel.getDestination(), "Tank destination should be set to the new cell");
        assertTrue(tankModel.isMoving(), "Tank should be in a moving state");
    }

    @Test
    void testMoveToOccupiedCellDoesNothing() {
        fieldModel.addObject(new TreeModel(new GridPoint2(1, 2)));

        tankModel.move(Direction.UP);
        
        assertEquals(initialPosition, tankModel.getCoordinates(), "Tank coordinates should not change");
        assertEquals(initialPosition, tankModel.getDestination(), "Tank destination should not change");
        assertFalse(tankModel.isMoving(), "Tank should not be in a moving state");
    }

    @Test
    void testMoveToBoundaryDoesNothing() {
        TankModel cornerTank = new TankModel(new GridPoint2(0, 0), fieldModel);
        
        cornerTank.move(Direction.LEFT);
        
        assertFalse(cornerTank.isMoving(), "Tank should not move left into a boundary");

        cornerTank.move(Direction.DOWN);

        assertFalse(cornerTank.isMoving(), "Tank should not move down into a boundary");
    }

    @Test
    void testIgnoresNewMoveCommandWhileAlreadyMoving() {
        tankModel.move(Direction.UP);
        assertTrue(tankModel.isMoving());
        assertEquals(new GridPoint2(1, 2), tankModel.getDestination());
        
        tankModel.move(Direction.RIGHT);
        
        assertEquals(new GridPoint2(1, 2), tankModel.getDestination(), "Destination should remain unchanged while moving");
    }

    @Test
    void testFinalizeMovementUpdatesCoordinates() {
        tankModel.move(Direction.UP);
        GridPoint2 destination = tankModel.getDestination();

        tankModel.finalizeMovement();

        assertEquals(destination, tankModel.getCoordinates(), "Tank coordinates should be updated to the destination after finalize");
        assertEquals(destination, tankModel.getDestination(), "Tank destination should be same as coordinates after finalize");
        assertFalse(tankModel.isMoving(), "Tank should not be in a moving state after finalize");
    }
}
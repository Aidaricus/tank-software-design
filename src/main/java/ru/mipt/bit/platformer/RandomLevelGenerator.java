package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.FieldModel;
import ru.mipt.bit.platformer.model.TankModel;
import ru.mipt.bit.platformer.model.TreeModel;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomLevelGenerator implements LevelGenerator {

    private final TileMovement tileMovement;
    private final TiledMapTileLayer groundLayer;
    private final int width;
    private final int height;
    private final float obstacleDensity;
    private final Random random = new Random();

    private TankModel playerModel;
    private final List<TankModel> aiTanks = new ArrayList<>();

    public RandomLevelGenerator(TileMovement tileMovement, TiledMapTileLayer groundLayer, int width, int height, float obstacleDensity) {
        this.tileMovement = tileMovement;
        this.groundLayer = groundLayer;
        this.width = width;
        this.height = height;
        this.obstacleDensity = obstacleDensity;
    }

    @Override
    public void generate(FieldModel fieldModel) {
        List<GridPoint2> allCells = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                allCells.add(new GridPoint2(x, y));
            }
        }

        int treeCount = (int) (width * height * obstacleDensity);
        for (int i = 0; i < treeCount; i++) {
            if (allCells.isEmpty()) break;
            GridPoint2 treePos = allCells.remove(random.nextInt(allCells.size()));
            TreeModel treeModel = new TreeModel(treePos);
            fieldModel.addObject(treeModel);
        }
        
        List<GridPoint2> freeCells = allCells;

        int DUMMY_AI_TANKS_TO_SPAWN = 3;
        for (int i = 0; i < DUMMY_AI_TANKS_TO_SPAWN; i++) {
            if (freeCells.isEmpty()) break;
            GridPoint2 aiPos = freeCells.remove(random.nextInt(freeCells.size()));
            TankModel aiTank = new TankModel(aiPos, fieldModel);
            aiTanks.add(aiTank);
            fieldModel.addObject(aiTank);
        }

        if (freeCells.isEmpty()) {
            throw new IllegalStateException("No free cells available to place the player!");
        }
        GridPoint2 playerPosition = freeCells.remove(random.nextInt(freeCells.size()));
        
        this.playerModel = new TankModel(playerPosition, fieldModel);
        fieldModel.addObject(playerModel);
    }

    @Override
    public TankModel getPlayerModel() {
        return playerModel;
    }

    public List<TankModel> getAiTanks() {
        return aiTanks;
    }
}
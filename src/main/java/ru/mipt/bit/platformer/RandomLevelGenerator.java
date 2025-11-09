package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.*;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.view.FieldView;
import ru.mipt.bit.platformer.view.TankView;
import ru.mipt.bit.platformer.view.TreeView;

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
    private final Texture treeTexture = new Texture("images/greenTree.png");
    private final Texture playerTankTexture = new Texture("images/tank_blue.png");
    private final Texture aiTankTexture = new Texture("images/tank_blue.png");

    public RandomLevelGenerator(TileMovement tileMovement, TiledMapTileLayer groundLayer, int width, int height, float obstacleDensity) {
        this.tileMovement = tileMovement;
        this.groundLayer = groundLayer;
        this.width = width;
        this.height = height;
        this.obstacleDensity = obstacleDensity;
    }

    @Override
    public void generate(FieldModel fieldModel, FieldView fieldView) {
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
            TreeView treeView = new TreeView(treeModel, new TextureRegion(treeTexture), groundLayer);
            fieldModel.addObject(treeModel);
            fieldView.addObjectView(treeView);
        }
        
        List<GridPoint2> freeCells = allCells;

        int DUMMY_AI_TANKS_TO_SPAWN = 3;
        for (int i = 0; i < DUMMY_AI_TANKS_TO_SPAWN; i++) {
            if (freeCells.isEmpty()) break;
            GridPoint2 aiPos = freeCells.remove(random.nextInt(freeCells.size()));
            
            TankModel aiTank = new TankModel(aiPos, fieldModel);
            TankView aiView = new TankView(aiTank, new TextureRegion(aiTankTexture), tileMovement, 0.5f);
            
            aiTanks.add(aiTank);
            fieldModel.addObject(aiTank);
            fieldView.addObjectView(aiView);
        }

        if (freeCells.isEmpty()) {
            throw new IllegalStateException("No free cells available to place the player!");
        }
        GridPoint2 playerPosition = freeCells.remove(random.nextInt(freeCells.size()));
        
        this.playerModel = new TankModel(playerPosition, fieldModel);
        TankView playerView = new TankView(playerModel, new TextureRegion(playerTankTexture), tileMovement, 0.4f);

        fieldModel.addObject(playerModel);
        fieldView.addObjectView(playerView);
    }

    @Override
    public TankModel getPlayerModel() {
        return playerModel;
    }

    public List<TankModel> getAiTanks() {
        return aiTanks;
    }
}
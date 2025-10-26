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
    private final Texture treeTexture = new Texture("images/greenTree.png");
    private final Texture tankTexture = new Texture("images/tank_blue.png");

    public RandomLevelGenerator(TileMovement tileMovement, TiledMapTileLayer groundLayer, int width, int height, float obstacleDensity) {
        this.tileMovement = tileMovement;
        this.groundLayer = groundLayer;
        this.width = width;
        this.height = height;
        this.obstacleDensity = obstacleDensity;
    }

    @Override
    public void generate(FieldModel fieldModel, FieldView fieldView) {
        List<GridPoint2> freeCells = new ArrayList<>();
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                GridPoint2 point = new GridPoint2(x, y);
                if (random.nextFloat() < obstacleDensity) {
                    TreeModel treeModel = new TreeModel(point);
                    TreeView treeView = new TreeView(treeModel, new TextureRegion(treeTexture), groundLayer);
                    fieldModel.addObject(treeModel);
                    fieldView.addObjectView(treeView);
                } else {
                    freeCells.add(point);
                }
            }
        }
        
        if (freeCells.isEmpty()) {
            throw new IllegalStateException("No free cells available to place the player!");
        }

        GridPoint2 playerPosition = freeCells.get(random.nextInt(freeCells.size()));
        
        this.playerModel = new TankModel(playerPosition, fieldModel);
        TankView playerView = new TankView(playerModel, new TextureRegion(tankTexture), tileMovement, 0.4f);

        fieldModel.addObject(playerModel);
        fieldView.addObjectView(playerView);
    }

    @Override
    public TankModel getPlayerModel() {
        return playerModel;
    }
}
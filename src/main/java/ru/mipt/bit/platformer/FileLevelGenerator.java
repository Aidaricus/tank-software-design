package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.*;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.view.FieldView;
import ru.mipt.bit.platformer.view.TankView;
import ru.mipt.bit.platformer.view.TreeView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileLevelGenerator implements LevelGenerator {

    private final TileMovement tileMovement;
    private final TiledMapTileLayer groundLayer;
    private final String filePath;

    private TankModel playerModel;
    private final Texture treeTexture = new Texture("images/greenTree.png");
    private final Texture tankTexture = new Texture("images/tank_blue.png");

    public FileLevelGenerator(TileMovement tileMovement, TiledMapTileLayer groundLayer, String filePath) {
        this.tileMovement = tileMovement;
        this.groundLayer = groundLayer;
        this.filePath = filePath;
    }

    @Override
    public void generate(FieldModel fieldModel, FieldView fieldView) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(Gdx.files.internal(filePath).path()));
            int height = lines.size();

            for (int y = 0; y < height; y++) {
                String line = lines.get(y);
                for (int x = 0; x < line.length(); x++) {
                    GridPoint2 coordinates = new GridPoint2(x, height - 1 - y);
                    char symbol = line.charAt(x);
                    
                    switch (symbol) {
                        case 'T':
                            TreeModel treeModel = new TreeModel(coordinates);
                            TreeView treeView = new TreeView(treeModel, new TextureRegion(treeTexture), groundLayer);
                            fieldModel.addObject(treeModel);
                            fieldView.addObjectView(treeView);
                            break;
                        case 'X':
                            this.playerModel = new TankModel(coordinates, fieldModel);
                            TankView playerView = new TankView(playerModel, new TextureRegion(tankTexture), tileMovement, 0.4f);
                            fieldModel.addObject(playerModel);
                            fieldView.addObjectView(playerView);
                            break;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not load level file: " + filePath, e);
        }
    }

    @Override
    public TankModel getPlayerModel() {
        return playerModel;
    }
}
package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.*;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.view.FieldView;
import ru.mipt.bit.platformer.view.TankView;
import ru.mipt.bit.platformer.view.TreeView;

public class LevelLoader {
    private final TiledMap map;
    private final TiledMapTileLayer groundLayer;
    private final TileMovement tileMovement;

    private TankModel playerModel = null;
    private TankView playerView = null;

    public LevelLoader(TiledMap map, TileMovement tileMovement) {
        this.map = map;
        this.tileMovement = tileMovement;
        this.groundLayer = (TiledMapTileLayer) map.getLayers().get(0);
    }

    public TankModel getPlayerModel() {
        return playerModel;
    }

    public void loadObjectsTo(FieldModel fieldModel, FieldView fieldView) {
        MapObjects objects = map.getLayers().get("entities").getObjects();

        for (MapObject object : objects) {
            RectangleMapObject rectObject = (RectangleMapObject) object;
            Rectangle rect = rectObject.getRectangle();
            int gridX = (int) (rect.x / groundLayer.getTileWidth());
            int gridY = (int) (rect.y / groundLayer.getTileHeight());
            GridPoint2 coordinates = new GridPoint2(gridX, gridY);

            String type = object.getProperties().get("type", String.class);

            createGameObject(type, coordinates, fieldModel, fieldView);
        }
    }
    
    private void createGameObject(String type, GridPoint2 coordinates, FieldModel fieldModel, FieldView fieldView) {
        if ("player".equals(type)) {
            Texture tankTexture = new Texture("images/tank_blue.png");
            
            playerModel = new TankModel(coordinates, fieldModel);
            playerView = new TankView(playerModel, new TextureRegion(tankTexture), tileMovement, 0.4f);
            
            fieldModel.addObject(playerModel);
            fieldView.addObjectView(playerView);
        } else if ("tree".equals(type)) {
            Texture treeTexture = new Texture("images/greenTree.png");
            
            TreeModel treeModel = new TreeModel(coordinates);
            TreeView treeView = new TreeView(treeModel, new TextureRegion(treeTexture), groundLayer);
            
            fieldModel.addObject(treeModel);
            fieldView.addObjectView(treeView);
        }
    }
}
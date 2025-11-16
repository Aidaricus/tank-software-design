package ru.mipt.bit.platformer.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.UIState;
import ru.mipt.bit.platformer.model.BulletModel;
import ru.mipt.bit.platformer.model.GameObjectModel;
import ru.mipt.bit.platformer.model.TankModel;
import ru.mipt.bit.platformer.model.TreeModel;
import ru.mipt.bit.platformer.util.TileMovement;

public class ViewFactory {
    private final TileMovement tileMovement;
    private final TiledMapTileLayer groundLayer;
    private final UIState uiState;

    private final Texture playerTankTexture = new Texture("images/tank_blue.png");
    private final Texture aiTankTexture = new Texture("images/tank_blue.png");
    private final Texture treeTexture = new Texture("images/greenTree.png");
    private final Texture bulletTexture = new Texture("images/bullet.png");

    public ViewFactory(TileMovement tileMovement, TiledMapTileLayer groundLayer, UIState uiState) {
        this.tileMovement = tileMovement;
        this.groundLayer = groundLayer;
        this.uiState = uiState;
    }

    public GameObjectView createView(GameObjectModel model) {
        if (model instanceof TankModel) {
            TankView tankView = new TankView((TankModel) model, new TextureRegion(playerTankTexture), tileMovement, 0.4f);
            return new HealthBarDecoratorView(tankView, uiState);
        }
        if (model instanceof TreeModel) {
            return new TreeView((TreeModel) model, new TextureRegion(treeTexture), groundLayer);
        }
        if (model instanceof BulletModel) {
            return new BulletView((BulletModel) model, new TextureRegion(bulletTexture), tileMovement);
        }
        throw new IllegalArgumentException("Unknown model type: " + model.getClass().getSimpleName());
    }
    
    public void dispose() {
        playerTankTexture.dispose();
        aiTankTexture.dispose();
        treeTexture.dispose();
        bulletTexture.dispose();
    }
}
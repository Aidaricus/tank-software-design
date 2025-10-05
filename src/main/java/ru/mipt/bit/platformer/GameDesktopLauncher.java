package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.model.FieldModel;
import ru.mipt.bit.platformer.model.TankModel;
import ru.mipt.bit.platformer.model.TreeModel;
import ru.mipt.bit.platformer.util.GdxGameUtils;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.view.FieldView;
import ru.mipt.bit.platformer.view.TankView;
import ru.mipt.bit.platformer.view.TreeView;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GameDesktopLauncher implements ApplicationListener {
    private static final float MOVEMENT_SPEED = 0.4f;

    private Batch batch;
    private FieldView fieldView;
    private PlayerInputHandler inputHandler;
    
    private Texture tankTexture;
    private Texture treeTexture;

    @Override
    public void create() {
        batch = new SpriteBatch();

        TiledMap map = new TmxMapLoader().load("level.tmx");
        MapRenderer renderer = GdxGameUtils.createSingleLayerMapRenderer(map, batch);
        TiledMapTileLayer groundLayer = GdxGameUtils.getSingleLayer(map);
        TileMovement tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
        
        tankTexture = new Texture("images/tank_blue.png");
        treeTexture = new Texture("images/greenTree.png");

        FieldModel fieldModel = new FieldModel();
        TankModel playerModel = new TankModel(new GridPoint2(1, 1), fieldModel);
        TreeModel treeModel = new TreeModel(new GridPoint2(1, 3));
        
        fieldModel.addObject(playerModel);
        fieldModel.addObject(treeModel);

        fieldView = new FieldView(map, renderer);
        TankView playerView = new TankView(playerModel, new TextureRegion(tankTexture), tileMovement, MOVEMENT_SPEED);
        TreeView treeView = new TreeView(treeModel, new TextureRegion(treeTexture), groundLayer);
        
        fieldView.addObjectView(playerView);
        fieldView.addObjectView(treeView);
        
        inputHandler = new PlayerInputHandler(playerModel);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        float deltaTime = Gdx.graphics.getDeltaTime();

        inputHandler.handleInput();

        fieldView.update(deltaTime);

        fieldView.render(batch);
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    
    @Override 
    public void dispose() { 
        fieldView.dispose(); 
        batch.dispose();
        tankTexture.dispose();
        treeTexture.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
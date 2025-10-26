package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.model.FieldModel;
import ru.mipt.bit.platformer.model.TankModel;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.view.FieldView;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GameDesktopLauncher implements ApplicationListener {

    private Batch batch;
    private FieldView fieldView;
    private PlayerInputHandler inputHandler;

    @Override
    public void create() {
        batch = new SpriteBatch();
        TiledMap map = new TmxMapLoader().load("level.tmx");
        
        TiledMapTileLayer groundLayer = (TiledMapTileLayer) map.getLayers().get("Ground");
        if (groundLayer == null) {
            throw new IllegalStateException("Map must have a TiledMapTileLayer named 'Ground'");
        }
        
        OrthogonalTiledMapRenderer renderer = new OrthogonalTiledMapRenderer(map, batch);
        float viewWidth = groundLayer.getWidth() * groundLayer.getTileWidth();
        float viewHeight = groundLayer.getHeight() * groundLayer.getTileHeight();
        renderer.getViewBounds().set(0, 0, viewWidth, viewHeight);
        
        TileMovement tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        FieldModel fieldModel = new FieldModel();
        fieldView = new FieldView(map, renderer);

        LevelGenerator levelGenerator;

        levelGenerator = new RandomLevelGenerator(tileMovement, groundLayer, 10, 8, 0.25f);
    
        
        levelGenerator.generate(fieldModel, fieldView);

        TankModel playerModel = levelGenerator.getPlayerModel();
        if (playerModel == null) {
            throw new IllegalStateException("Level generator did not create a player!");
        }

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

    @Override
    public void dispose() {
        fieldView.dispose();
        batch.dispose();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
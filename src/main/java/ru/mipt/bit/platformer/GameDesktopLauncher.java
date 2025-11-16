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
import ru.mipt.bit.platformer.view.ViewFactory;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GameDesktopLauncher implements ApplicationListener {

    private Batch batch;
    private FieldView fieldView;
    private PlayerInputHandler inputHandler;
    private AIController aiController;
    private GameProcessor gameProcessor;

    @Override
    public void create() {
        batch = new SpriteBatch();
        TiledMap map = new TmxMapLoader().load("level.tmx");
        UIState uiState = new UIState();

        TiledMapTileLayer groundLayer = (TiledMapTileLayer) map.getLayers().get("Ground");
        if (groundLayer == null) {
            throw new IllegalStateException("Map must have a TiledMapTileLayer named 'Ground'");
        }

        int worldWidth = groundLayer.getWidth();
        int worldHeight = groundLayer.getHeight();
        FieldModel fieldModel = new FieldModel(worldWidth, worldHeight);
        gameProcessor = new GameProcessor(fieldModel);
        
        OrthogonalTiledMapRenderer renderer = new OrthogonalTiledMapRenderer(map, batch);
        renderer.getViewBounds().set(0, 0, worldWidth * groundLayer.getTileWidth(), worldHeight * groundLayer.getTileHeight());
        
        TileMovement tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
        ViewFactory viewFactory = new ViewFactory(tileMovement, groundLayer, uiState);
        fieldView = new FieldView(map, renderer, viewFactory);
        
        fieldModel.addListener(fieldView);

        RandomLevelGenerator levelGenerator = new RandomLevelGenerator(tileMovement, groundLayer, worldWidth, worldHeight, 0.2f);
        levelGenerator.generate(fieldModel);

        TankModel playerModel = levelGenerator.getPlayerModel();
        if (playerModel == null) {
            throw new IllegalStateException("Level generator did not create a player!");
        }
        inputHandler = new PlayerInputHandler(playerModel, uiState, fieldModel);

        aiController = new AIController(fieldModel);
        for (TankModel aiTank : levelGenerator.getAiTanks()) {
            aiController.addTank(aiTank);
        }

        fieldModel.addListener(inputHandler);
        fieldModel.addListener(aiController);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        float deltaTime = Gdx.graphics.getDeltaTime();

        inputHandler.handleInput();
        aiController.update();
        gameProcessor.update(deltaTime);
        
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
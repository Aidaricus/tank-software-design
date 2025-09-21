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
import ru.mipt.bit.platformer.util.GdxGameUtils;
import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GameDesktopLauncher implements ApplicationListener {
    private static final float MOVEMENT_SPEED = 0.4f;

    private Batch batch;
    private Field field;
    private Tank player;

    @Override
    public void create() {
        batch = new SpriteBatch();

        TiledMap map = new TmxMapLoader().load("level.tmx");
        MapRenderer renderer = GdxGameUtils.createSingleLayerMapRenderer(map, batch);
        TiledMapTileLayer groundLayer = GdxGameUtils.getSingleLayer(map);
        TileMovement tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        field = new Field(map, renderer, groundLayer);

        player = new Tank(new TextureRegion(new Texture("images/tank_blue.png")),
                new GridPoint2(1, 1), tileMovement, MOVEMENT_SPEED, field);

        Tree tree = new Tree(new TextureRegion(new Texture("images/greenTree.png")),
                new GridPoint2(1, 3), groundLayer);

        field.addObject(player);
        field.addObject(tree);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        float deltaTime = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) player.move(Direction.UP);
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) player.move(Direction.DOWN);
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) player.move(Direction.LEFT);
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) player.move(Direction.RIGHT);

        player.update(deltaTime);

        field.render(batch);
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void dispose() { field.dispose(); batch.dispose(); }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
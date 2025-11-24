package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import ru.mipt.bit.platformer.model.FieldModel;
import ru.mipt.bit.platformer.model.GameObjectListener;
import ru.mipt.bit.platformer.model.GameObjectModel;
import ru.mipt.bit.platformer.model.TankModel;

public class PlayerInputHandler implements GameObjectListener {
    private TankModel playerTank;
    private final UIState uiState;
    private final FieldModel fieldModel;

    public PlayerInputHandler(TankModel playerTank, UIState uiState, FieldModel fieldModel) {
        this.playerTank = playerTank;
        this.uiState = uiState;
        this.fieldModel = fieldModel;
    }

    public void handleInput() {
        if (playerTank == null) {
            return;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            new MoveCommand(playerTank, Direction.UP).execute();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            new MoveCommand(playerTank, Direction.DOWN).execute();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            new MoveCommand(playerTank, Direction.LEFT).execute();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            new MoveCommand(playerTank, Direction.RIGHT).execute();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            new ToggleHealthBarCommand(uiState).execute();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            new ShootCommand(playerTank, fieldModel).execute();
        }
    }

    @Override
    public void onGameObjectRemoved(GameObjectModel model) {
        if (model == this.playerTank) {
            this.playerTank = null;
        }
    }

    @Override
    public void onGameObjectAdded(GameObjectModel model) {
    }
}
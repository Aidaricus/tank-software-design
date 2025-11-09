package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import ru.mipt.bit.platformer.model.Movable;
import ru.mipt.bit.platformer.model.TankModel;

public class PlayerInputHandler {
    private final TankModel playerTank;
    private final UIState uiState;

    public PlayerInputHandler(TankModel playerTank, UIState uiState) {
        this.playerTank = playerTank;
        this.uiState = uiState;
    }

    public void handleInput() {
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
    }
}
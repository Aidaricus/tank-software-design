package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import ru.mipt.bit.platformer.model.Movable;
import ru.mipt.bit.platformer.model.TankModel;

public class PlayerInputHandler {
    private final TankModel playerTank;

    public PlayerInputHandler(TankModel playerTank) {
        this.playerTank = playerTank;
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
    }
}
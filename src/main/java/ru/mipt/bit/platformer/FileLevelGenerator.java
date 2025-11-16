package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.FieldModel;
import ru.mipt.bit.platformer.model.TankModel;
import ru.mipt.bit.platformer.model.TreeModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileLevelGenerator implements LevelGenerator {

    private final String filePath;
    private TankModel playerModel;

    public FileLevelGenerator(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void generate(FieldModel fieldModel) {
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
                            fieldModel.addObject(treeModel);
                            break;
                        case 'X':
                            this.playerModel = new TankModel(coordinates, fieldModel);
                            fieldModel.addObject(playerModel);
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
package ru.mipt.bit.platformer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mipt.bit.platformer.model.FieldModel;

@Configuration
public class GameConfig {

    @Bean
    public FieldModel fieldModel() {
        return new FieldModel(10, 8);
    }

    @Bean
    public GameProcessor gameProcessor(FieldModel fieldModel) {
        return new GameProcessor(fieldModel);
    }

    @Bean
    public AIController aiController(FieldModel fieldModel) {
        AIController controller = new AIController(fieldModel);
        fieldModel.addListener(controller);
        return controller;
    }
}
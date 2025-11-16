package ru.mipt.bit.platformer;

public class ToggleHealthBarCommand implements Command {
    private final UIState uiState;

    public ToggleHealthBarCommand(UIState uiState) {
        this.uiState = uiState;
    }

    @Override
    public void execute() {
        uiState.toggleHealthBarVisibility();
    }
}
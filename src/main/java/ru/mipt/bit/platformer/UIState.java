package ru.mipt.bit.platformer;

public class UIState {
    private boolean healthBarVisible = false;

    public void toggleHealthBarVisibility() {
        this.healthBarVisible = !this.healthBarVisible;
    }

    public boolean isHealthBarVisible() {
        return healthBarVisible;
    }
}
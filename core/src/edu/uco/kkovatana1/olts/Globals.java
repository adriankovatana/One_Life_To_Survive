package edu.uco.kkovatana1.olts;

public class Globals {
    public static float SCALE = 1.0f;
    public static final float ANIMATIONRATE = 0.15f;
    public static GameState GAMESTATE = GameState.PLAYING;

    public enum EntityState {
        STANDING, WALKING, ATTACKING, DYING
    }

    public enum Direction {
        N, NE, E, SE, S, SW, W, NW
    }

    public enum GameState {
        PLAYING, PAUSED
    }

    public enum UpgradeType {
        SPEED, FIRERATE
    }
}
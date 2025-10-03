package com.yourname.arkanoid.level;

public class LevelManager {
    private int index = 0;
    private final int total = 6; // 3 mẫu * 2 vòng lặp

    public void reset() {
        index = 0;
    }

    public Level getCurrentLevel() {
        int pick = index % 3;
        switch (pick) {
            case 1:
                return new Level(LevelLoader.diagonal());
            case 2:
                return new Level(LevelLoader.checker());
            default:
                return new Level(LevelLoader.full());
        }
    }

    public boolean nextLevel() {
        index++;
        return index < total;
    }
}
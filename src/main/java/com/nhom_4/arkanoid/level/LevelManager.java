package com.nhom_4.arkanoid.level;

public class LevelManager {
    private int index = 0;
    private final int total = 10; // số màn chơi tối đa (tùy chỉnh)

    public void reset() {
        index = 0;
    }

    public Level getCurrentLevel() {
        // mỗi lần gọi → sinh map ngẫu nhiên
        return new Level(LevelLoader.randomLevel());
    }

    public boolean nextLevel() {
        index++;
        return index < total;
    }
}

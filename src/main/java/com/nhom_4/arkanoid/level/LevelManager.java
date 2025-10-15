package com.nhom_4.arkanoid.level;

import java.util.ArrayList;
import java.util.List;

public class LevelManager {

    private final List<Level> levels;
    private int currentLevelIndex;

    public LevelManager() {
        levels = new ArrayList<>();

        // Thêm tất cả các màn chơi bạn muốn vào đây
        levels.add(new DynamicTopLevel());
        // levels.add(new DataDrivenLevel(LevelLoader.full())); // Bạn có thể thêm các
        // level khác nếu muốn

        currentLevelIndex = -1;
    }

    public void reset() {
        currentLevelIndex = 0;
    }

    public Level getCurrentLevel() {
        if (currentLevelIndex >= levels.size()) {
            return levels.get(levels.size() - 1);
        }
        if (currentLevelIndex < 0) {
            return levels.get(0);
        }
        return levels.get(currentLevelIndex);
    }

    public boolean nextLevel() {
        currentLevelIndex++;
        return currentLevelIndex < levels.size();
    }
}
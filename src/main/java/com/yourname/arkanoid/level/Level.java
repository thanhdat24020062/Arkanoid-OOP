package com.yourname.arkanoid.level;

import java.util.*;
import com.yourname.arkanoid.entity.*;
import com.yourname.arkanoid.config.Constants;

public class Level {
    private final int[][] layout; // ma trận HP gạch

    public Level(int[][] layout) {
        this.layout = layout;
    }

    public java.util.List<Brick> spawnBricks() {
        return EntityFactory.bricksFromMatrix(layout, Constants.WALL_THICK, Constants.TOP_OFFSET,
                Constants.WIDTH, Constants.HEIGHT);
    }
}
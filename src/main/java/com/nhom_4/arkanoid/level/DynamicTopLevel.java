package com.nhom_4.arkanoid.level;

import com.nhom_4.arkanoid.config.Constants;
import com.nhom_4.arkanoid.entity.Brick;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DynamicTopLevel implements Level {

    @Override
    public List<Brick> spawnBricks() {
        List<Brick> bricks = new ArrayList<>();

        List<String> brickImagePaths = new ArrayList<>();
        brickImagePaths.add("res/images/brick_gold.png");
        brickImagePaths.add("res/images/brick_orange.png");
        brickImagePaths.add("res/images/brick_silver_2.png");
        brickImagePaths.add("res/images/brick_blue.png");
        brickImagePaths.add("res/images/brick_cyan.png");

        Random random = new Random();

        final int BRICKS_PER_ROW = 14;
        final int NUMBER_OF_ROWS = 8;
        double playableWidth = Constants.WIDTH - (2 * Constants.WALL_THICK);
        double brickWidth = playableWidth / BRICKS_PER_ROW;
        double brickHeight = brickWidth / 2.5;

        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            for (int j = 0; j < BRICKS_PER_ROW; j++) {
                double brickX = Constants.WALL_THICK + j * brickWidth;
                double brickY = Constants.TOP_OFFSET + i * brickHeight;
                int health = NUMBER_OF_ROWS - i;

                String randomImagePath = brickImagePaths.get(random.nextInt(brickImagePaths.size()));

                // Sử dụng constructor mới của Brick
                Brick newBrick = new Brick(brickX, brickY, brickWidth, brickHeight, health, randomImagePath);

                bricks.add(newBrick);
            }
        }
        return bricks;
    }
}
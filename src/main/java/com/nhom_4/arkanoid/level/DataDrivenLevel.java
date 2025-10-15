package com.nhom_4.arkanoid.level;

import java.util.Random;

import com.nhom_4.arkanoid.config.Constants;
import com.nhom_4.arkanoid.entity.Brick;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp này triển khai Level và xây dựng màn chơi
 * dựa trên dữ liệu map (int[][]) được cung cấp.
 */
public class DataDrivenLevel implements Level {

    private final int[][] map;

    // Constructor nhận dữ liệu map từ LevelLoader
    public DataDrivenLevel(int[][] mapData) {
        this.map = mapData;
    }

    @Override
    public List<Brick> spawnBricks() {
        List<Brick> bricks = new ArrayList<>();

        // 1. Tạo một danh sách các đường dẫn ảnh gạch
        List<String> brickImagePaths = new ArrayList<>();
        brickImagePaths.add("res/images/brick_gold.png");
        brickImagePaths.add("res/images/brick_orange.png");
        brickImagePaths.add("res/images/brick_silver_2.png");
        brickImagePaths.add("res/images/brick_blue.png");
        brickImagePaths.add("res/images/brick_cyan.png");

        // 2. Tạo đối tượng Random để lựa chọn ngẫu nhiên
        Random random = new Random();

        // --- Các phần tính toán kích thước gạch giữ nguyên như cũ ---
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

                // 3. Chọn một đường dẫn ảnh ngẫu nhiên từ danh sách
                String randomImagePath = brickImagePaths.get(random.nextInt(brickImagePaths.size()));

                // 4. Tạo gạch mới với đường dẫn ảnh ngẫu nhiên đó
                Brick newBrick = new Brick(brickX, brickY, brickWidth, brickHeight, health, randomImagePath);

                bricks.add(newBrick);
            }
        }
        return bricks;
    }
}
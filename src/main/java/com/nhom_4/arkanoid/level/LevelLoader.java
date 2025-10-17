package com.nhom_4.arkanoid.level;

import java.util.Random;

public final class LevelLoader {
    private static final Random rand = new Random();

    private LevelLoader() {}

    // Sinh ngẫu nhiên map gạch
    public static int[][] randomLevel() {
        int rows = 6 + rand.nextInt(3); // 6–8 hàng
        int cols = 10 + rand.nextInt(3); // 10–12 cột
        int[][] map = new int[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (rand.nextDouble() < 0.2) {
                    map[r][c] = 0; // ô trống
                } else {
                    map[r][c] = 1 + rand.nextInt(3); // HP 1–3
                }
            }
        }

        // Đảm bảo không trống hoàn toàn
        boolean hasBrick = false;
        for (int[] row : map)
            for (int v : row)
                if (v > 0) hasBrick = true;
        if (!hasBrick) map[rand.nextInt(rows)][rand.nextInt(cols)] = 2;

        return map;
    }
}

package com.yourname.arkanoid.entity;

import java.util.*;

public final class EntityFactory {
    private EntityFactory() {
    }

    public static java.util.List<Brick> bricksFromMatrix(int[][] m, int wall, int top, int width, int height) {
        int rows = m.length, cols = m[0].length;
        int bw = (width - wall * 2) / cols;
        int bh = 24;
        java.util.List<Brick> list = new ArrayList<>();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int hp = m[r][c];
                if (hp > 0) {
                    int x = wall + c * bw;
                    int y = top + r * bh;
                    list.add(new Brick(x + 2, y + 2, bw - 4, bh - 4, hp));
                }
            }
        }
        return list;
    }
}
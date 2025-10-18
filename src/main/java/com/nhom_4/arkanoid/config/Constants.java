package com.nhom_4.arkanoid.config;

import java.awt.Color;

public final class Constants {
    private Constants() {
    }

    // Màn hình
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    public static final Color BG_COLOR = new Color(255, 255, 255);

    // Loop
    public static final int TARGET_UPS = 120;

    // Tường
    public static final int WALL_THICK = 8;
    public static final int TOP_WALL = 20;
    public static final double TOP_OFFSET = TOP_WALL + 5;

    // Paddle
    public static final double PADDLE_WIDTH = 100;
    public static final double PADDLE_HEIGHT = 20;
    public static final double PADDLE_SPEED = 520;

    // Ball
    public static final double BALL_RADIUS = 9;
    public static final double BALL_SPEED_CAP = 700;
    public static final double BALL_SPEEDUP_MUL = 1.015;// tăng 2% tốc độ mỗi lần va chạm
}
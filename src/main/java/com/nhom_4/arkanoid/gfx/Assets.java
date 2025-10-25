// gfx/Assets.java
package com.nhom_4.arkanoid.gfx;

import com.nhom_4.arkanoid.util.Files;
import com.nhom_4.arkanoid.config.Constants;

import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;

import java.util.HashMap;

import java.util.Map;

public class Assets {
    public static BufferedImage BUTTON_SMALL = null;
    public static BufferedImage EXIT_DIALOG;
    public static BufferedImage MENU_BG;
    public static BufferedImage PADDLE;
    public static BufferedImage BUTTON_START;
    public static BufferedImage BUTTON_HOW_TO_PLAY;
    public static BufferedImage BUTTON_EXIT;
    public static Font fontPixels_40;
    public static Font fontPixels_44;
    public static Image background;
    public static Image paddle;
    public static Image ball;
    public static Map<Integer, Image> bricks;

    public static void load() {
        MENU_BG = Files.loadImageCP("/images/menu_bg.png", Constants.WIDTH, Constants.HEIGHT);
        BUTTON_START = Files.loadImageCP("/images/button_menu.png", 240, 120);
        BUTTON_HOW_TO_PLAY = Files.loadImageCP("/images/button_menu.png", 460, 120);
        BUTTON_EXIT = Files.loadImageCP("/images/button_menu.png", 200, 120);
        BUTTON_SMALL = Files.loadImageCP("/images/button_menu.png", 150, 90);
        EXIT_DIALOG = Files.loadImageCP("/images/exit.png", 900, 600);

        fontPixels_40 = Files.loadFont("/fonts/font1.ttf", 0, 40);
        fontPixels_44 = Files.loadFont("/fonts/font1.ttf", 0, 44);

        paddle = Files.loadImage("/images/paddle.png");
        ball = Files.loadImage("/images/ball.png");

        bricks = new HashMap<>();
        bricks.put(1, Files.loadImage("/images/brick_blue.png")); // Loại 1 là gạch xanh dương
        bricks.put(2, Files.loadImage("/images/brick_cyan.png")); // Loại 2 là gạch xanh lơ
        bricks.put(3, Files.loadImage("/images/brick_gold.png")); // Loại 3 là gạch vàng
        bricks.put(4, Files.loadImage("/images/brick_orange.png")); // Loại 4 là gạch cam
        bricks.put(5, Files.loadImage("/images/brick_silver_2.png"));
    }
}
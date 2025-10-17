// gfx/Assets.java
package com.nhom_4.arkanoid.gfx;

import com.nhom_4.arkanoid.util.Files;
import com.nhom_4.arkanoid.config.Constants;

import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

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
    public static List<Image> bricks = new ArrayList<>();
    public static void load() {
        MENU_BG = Files.loadImageCP("/resources/images/menu_bg.png", Constants.WIDTH, Constants.HEIGHT);
        BUTTON_START = Files.loadImageCP("/resources/images/button_menu.png", 240, 120);
        BUTTON_HOW_TO_PLAY = Files.loadImageCP("/resources/images/button_menu.png", 460, 120);
        BUTTON_EXIT = Files.loadImageCP("/resources/images/button_menu.png", 200, 120);
        BUTTON_SMALL=Files.loadImageCP("/resources/images/button_menu.png", 150, 90);
        EXIT_DIALOG=Files.loadImageCP("/resources/images/exit.png", 900, 600);
        //load font chữ
        fontPixels_40 = Files.loadFont("/resources/fonts/font1.ttf", 0, 40);
        fontPixels_44 = Files.loadFont("/resources/fonts/font1.ttf", 0, 44);
   
        // Cung cấp đường dẫn tương đối từ thư mục gốc của dự án
        // background = loadImage("src/main/resources/images/background.png");
        paddle = Files.loadImage("src/main/resources/images/paddle.png");
        ball = Files.loadImage("src/main/resources/images/ball.png");

        bricks.add(Files.loadImage("src/main/resources/images/brick_gold.png"));
        bricks.add(Files.loadImage("src/main/resources/images/brick_orange.png"));
        bricks.add(Files.loadImage("src/main/resources/images/brick_silver_2.png"));
        bricks.add(Files.loadImage("src/main/resources/images/brick_blue.png"));
        bricks.add(Files.loadImage("src/main/resources/images/brick_cyan.png"));

        System.out.println("Tải tài nguyên thành công!");
    }
}
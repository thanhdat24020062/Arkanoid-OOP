// gfx/Assets.java
package com.nhom_4.arkanoid.gfx;

import com.nhom_4.arkanoid.util.Files;
import com.nhom_4.arkanoid.config.Constants;

import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

public class Assets {
    public static BufferedImage LEADERBOARD;
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

    public static List<Image> silverBrick = new ArrayList<>();
    public static List<Image> goldBrick = new ArrayList<>();
    public static List<Image> explosionBrick = new ArrayList<>();
    public static List<Image> explosionEffect = new ArrayList<>();

    public static void load() {
        MENU_BG = Files.loadImageCP("/images/menu_bg.png", Constants.WIDTH, Constants.HEIGHT);
        LEADERBOARD = Files.loadImageCP("/images/leaderboard.png", Constants.WIDTH, Constants.HEIGHT);
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
        bricks.put(0, Files.loadImage("/images/brick_steel.png"));
        bricks.put(1, Files.loadImage("/images/brick_blue.png"));
        bricks.put(2, Files.loadImage("/images/brick_cyan.png"));
        bricks.put(3, Files.loadImage("/images/brick_orange.png"));

        goldBrick = loadAnimationFrames("/images/brick_gold", 10);
        if (!goldBrick.isEmpty()) {
            bricks.put(4, goldBrick.getFirst());
        } else {
            bricks.put(4, Files.loadImage("/images/brick_silver.png"));
        }

        silverBrick = loadAnimationFrames("/images/brick_silver", 10);
        if (!silverBrick.isEmpty()) {
            bricks.put(5, silverBrick.getFirst());
        } else {
            bricks.put(5, Files.loadImage("/images/brick_silver.png"));
        }

        explosionBrick = loadAnimationFrames("/images/brick_bomb", 8);
        if (!explosionBrick.isEmpty()) {
            bricks.put(6, explosionBrick.getFirst());
        } else {
            bricks.put(6, Files.loadImage("/images/brick_bomb.png"));
        }

        bricks.put(40, Files.loadImage("/images/brick_gold_broken.png"));
        bricks.put(50, Files.loadImage("/images/brick_silver_broken.png"));

        explosionEffect = loadAnimationFrames("/images/explosion_effect", 8);
    }

    private static List<Image> loadAnimationFrames(String folderPath, int frameNumber) {
        List<Image> frames = new ArrayList<>();
        for (int i = 0; i < frameNumber; i++) {
            String path = folderPath + "/frame" + i + ".png";
            Image frame = Files.loadImage(path);
            if (frame != null) {
                frames.add(frame);
            } else {
                System.err.println("Assets: Không load được frame animation: " + path);
            }
        }
        return frames;
    }
}
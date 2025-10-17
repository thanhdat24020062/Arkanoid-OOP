// gfx/Assets.java
package com.nhom_4.arkanoid.gfx;

import com.nhom_4.arkanoid.util.Files;
import com.nhom_4.arkanoid.config.Constants;
import java.awt.Font;
import java.awt.image.BufferedImage;

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

    }
}
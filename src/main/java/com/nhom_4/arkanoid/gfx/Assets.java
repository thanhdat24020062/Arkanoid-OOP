package com.nhom_4.arkanoid.gfx;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import java.io.File; // Thêm import này

public final class Assets {

    public static Image background;
    public static Image paddle;
    public static Image ball;
    public static List<Image> bricks;

    private Assets() {
    }

    public static void load() {
        System.out.println("Bắt đầu tải tài nguyên hình ảnh...");

        // Cung cấp đường dẫn tương đối từ thư mục gốc của dự án
        // background = loadImage("src/main/resources/images/background.png");
        paddle = loadImage("src/main/resources/images/paddle.png");
        ball = loadImage("src/main/resources/images/ball.png");

        bricks = new ArrayList<>();
        bricks.add(loadImage("src/main/resources/images/brick_gold.png"));
        bricks.add(loadImage("src/main/resources/images/brick_orange.png"));
        bricks.add(loadImage("src/main/resources/images/brick_silver_2.png"));
        bricks.add(loadImage("src/main/resources/images/brick_blue.png"));
        bricks.add(loadImage("src/main/resources/images/brick_cyan.png"));

        System.out.println("Tải tài nguyên thành công!");
    }

    /**
     * Phương thức trợ giúp để tải một hình ảnh bằng đường dẫn trực tiếp từ gốc dự
     * án.
     */
    private static Image loadImage(String path) {
        File f = new File(path);
        if (!f.exists()) {
            System.err.println("LỖI NGHIÊM TRỌNG: File không tồn tại tại đường dẫn: " + f.getAbsolutePath());
            System.exit(1);
        }
        return new ImageIcon(f.getAbsolutePath()).getImage();
    }
}
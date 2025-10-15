package com.nhom_4.arkanoid.entity;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import javax.swing.ImageIcon;

public class Brick extends Entity {

    private int health;
    private Image brickImage;

    // Constructor mới: nhận thêm đường dẫn ảnh
    public Brick(double x, double y, double w, double h, int health, String imagePath) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.health = health;
        loadImage(imagePath);
    }

    // Constructor cũ hơn (không có ảnh, chỉ có health)
    // Dùng cho các level cũ nếu bạn chưa muốn thêm ảnh
    public Brick(double x, double y, double w, double h, int health) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.health = health;
        this.brickImage = null; // Không có ảnh
    }

    private void loadImage(String path) {
        try {
            ImageIcon ii = new ImageIcon(path);
            this.brickImage = ii.getImage();
        } catch (Exception e) {
            System.err.println("Không thể tải ảnh gạch: " + path);
            this.brickImage = null;
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (isAlive()) {
            if (brickImage != null) {
                g.drawImage(brickImage, (int) x, (int) y, (int) w, (int) h, null);
            } else {
                // Vẽ màu dự phòng nếu không có ảnh
                // Dựa trên health
                if (health > 2)
                    g.setColor(java.awt.Color.GRAY);
                else if (health > 1)
                    g.setColor(java.awt.Color.ORANGE);
                else
                    g.setColor(java.awt.Color.CYAN);
                g.fillRect((int) x, (int) y, (int) w, (int) h);
            }
        }
    }

    public boolean isAlive() {
        return health > 0;
    }

    public boolean hit() {
        if (health < 9)
            health--;
        return health <= 0;
    }

    public void destroy() {
        this.health = 0;
    }

    public double centerX() {
        return x + w / 2.0;
    }

    public double centerY() {
        return y + h / 2.0;
    }

    @Override
    public Rectangle2D.Double getRect() {
        return new Rectangle2D.Double(x, y, w, h);
    }

    @Override
    public void update(double dt) {
        /* Gạch không cần update */ }
}
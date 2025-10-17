package com.nhom_4.arkanoid.entity;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;

public class Brick extends Entity {

    private int health;
    private final Image brickImage; // Biến để lưu ảnh cho viên gạch

    /**
     * Constructor mới: Nhận trực tiếp một đối tượng Image
     * đã được tải sẵn từ lớp Assets.
     */
    public Brick(double x, double y, double w, double h, int health, Image image) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.health = health;
        this.brickImage = image; // Gán trực tiếp ảnh đã được tải
    }

    @Override
    public void render(Graphics2D g) {
        // Luôn vẽ hình ảnh vì mỗi viên gạch khi được tạo ra đều sẽ có một ảnh
        if (isAlive()) {
            g.drawImage(brickImage, (int) x, (int) y, (int) w, (int) h, null);
        }
    }

    // --- CÁC PHƯƠNG THỨC KHÁC ---

    public boolean isAlive() {
        return health > 0;
    }

    /**
     * Giảm độ cứng của gạch đi 1.
     * 
     * @return true nếu gạch bị phá hủy, false nếu chưa.
     */
    public boolean hit() {
        if (health < 9) { // Giả sử 9 là gạch không thể phá hủy
            health--;
        }
        return health <= 0;
    }

    /**
     * Phá hủy viên gạch ngay lập tức.
     */
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
        /* Gạch không cần update vị trí */
    }
}
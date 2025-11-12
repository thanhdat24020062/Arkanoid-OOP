package com.nhom_4.arkanoid.entity;

import java.awt.*;

public class Bullet extends Entity {
    private transient final double SPEED = -600; // Tốc độ bay lên (âm)
    private boolean active = true;

    /**
     * khởi tạo đạn
     * @param x tọa độ x
     * @param y tọa đô y
     */
    public Bullet(double x, double y) {
        this.x = x;
        this.y = y;
        this.w = 4; // Chiều rộng viên đạn
        this.h = 10; // Chiều cao viên đạn
    }

    @Override
    public void update(double dt) {
        y += SPEED * dt;
        // Nếu bay ra khỏi màn hình thì vô hiệu hóa
        if (y < 0) {
            active = false;
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.YELLOW);
        g.fillRect((int) x, (int) y, (int) w, (int) h);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
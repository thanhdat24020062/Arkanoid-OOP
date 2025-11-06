package com.nhom_4.arkanoid.entity;

import java.awt.Color;
import java.awt.Graphics2D;

public class Bullet extends Entity {
    private final double speed = -600; // Tốc độ bay lên (âm)
    private boolean active = true;

    public Bullet(double x, double y) {
        this.x = x;
        this.y = y;
        this.w = 4; // Chiều rộng viên đạn
        this.h = 10; // Chiều cao viên đạn
    }

    @Override
    public void update(double dt) {
        y += speed * dt;
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
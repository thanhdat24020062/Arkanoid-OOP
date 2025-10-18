package com.nhom_4.arkanoid.entity;

import com.nhom_4.arkanoid.config.Constants;
import com.nhom_4.arkanoid.gfx.Assets;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class Paddle extends Entity {
    private double speed;

    private boolean hasLasers = false;
    private double laserTimer = 0;
    private double shootCooldown = 0;

    public Paddle(double x, double y, double w, double h, double speed) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.speed = speed;
    }

    @Override
    public void render(Graphics2D g) {
        // 4. Lấy ảnh paddle trực tiếp từ "nhà kho" Assets để vẽ
        g.drawImage(Assets.paddle, (int) x, (int) y, (int) w, (int) h, null);

        // Vẽ thêm súng nếu có
        if (hasLasers) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect((int) this.x, (int) this.y - 5, 10, 5); // Súng trái
            g.fillRect((int) (this.x + this.w - 10), (int) this.y - 5, 10, 5); // Súng phải
        }
    }

    public void update(double dt, double dir) {
        x += dir * speed * dt;

        // Giữ paddle trong màn hình
        if (x < Constants.WALL_THICK) {
            x = Constants.WALL_THICK;
        }
        if (x + w > Constants.WIDTH - Constants.WALL_THICK) {
            x = Constants.WIDTH - Constants.WALL_THICK - w;
        }
        if (hasLasers) {
            laserTimer -= dt;
            if (laserTimer <= 0) {
                deactivateLasers();
            }
        }
        if (shootCooldown > 0) {
            shootCooldown -= dt;
        }
    }

    public List<Bullet> shoot() {
        if (hasLasers && shootCooldown <= 0) {
            shootCooldown = 0.3; // Bắn mỗi 0.3 giây

            List<Bullet> bullets = new ArrayList<>();
            bullets.add(new Bullet(this.x + 5, this.y)); // Đạn trái
            bullets.add(new Bullet(this.x + this.w - 5, this.y)); // Đạn phải

            return bullets;
        }
        return null;
    }

    public void activateLasers(double duration) {
        this.hasLasers = true;
        this.laserTimer = duration;
    }

    public void deactivateLasers() {
        this.hasLasers = false;
        this.laserTimer = 0;
    }

    public boolean hasLasers() {
        return this.hasLasers;
    }

    @Override
    public Rectangle2D.Double getRect() {
        return new Rectangle2D.Double(x, y, w, h);
    }

    public double getW() {
        return this.w;
    }

    public void setW(double w) {
        double currentCenterX = this.x + this.w / 2.0;
        this.w = w;
        this.x = currentCenterX - this.w / 2.0;

        if (x < Constants.WALL_THICK)
            x = Constants.WALL_THICK;
        if (x + this.w > Constants.WIDTH - Constants.WALL_THICK)
            x = Constants.WIDTH - Constants.WALL_THICK - this.w;
    }

    public void setX(double v) {
        this.x = v;
    }

    public double centerX() {
        return this.x + this.w / 2.0;
    }

    @Override
    public void update(double dt) {
    }
}
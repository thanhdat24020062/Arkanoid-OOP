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

    // Thêm hiệu ứng vệt sáng
    private final List<Point> trail = new ArrayList<>();
    private static final int TRAIL_LENGTH = 8; // số điểm lưu lại
    private static final double TRAIL_GAP = 6; // khoảng cách lấy mẫu
    private static final double TRAIL_FADE_SPEED = 60; // tốc độ mờ dần

    private double trailTimer = 0;
    private double idleTimer = 0; // đếm thời gian đứng yên

    public Paddle(double x, double y, double w, double h, double speed) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.speed = speed;
    }

    @Override
    public void render(Graphics2D g) {
        // Vẽ vệt sáng
        for (int i = 0; i < trail.size(); i++) {
            double alpha = (double) i / trail.size();
            int width = (int) (w * (0.8 + 0.2 * alpha));
            int height = (int) (h * (0.8 + 0.2 * alpha));

            Color c = hasLasers
                    ? new Color(255, (int) (150 * alpha), 50, (int) (80 * alpha))  // cam mờ nếu có laser
                    : new Color(100, 180, 255, (int) (50 * alpha));               // xanh mờ khi thường

            Point p = trail.get(i);
            g.setColor(c);
            g.fillRoundRect(p.x - width / 2, p.y, width, height, 10, 10);
        }

        // Lấy ảnh từ assets
        g.drawImage(Assets.paddle, (int) x, (int) y, (int) w, (int) h, null);

        // Vẽ thêm súng nếu có
        if (hasLasers) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect((int) this.x, (int) this.y - 5, 10, 5); // Súng trái
            g.fillRect((int) (this.x + this.w - 10), (int) this.y - 5, 10, 5); // Súng phải
        }
    }

    public void update(double dt, double dir) {
        double oldX = x;
        x += dir * speed * dt;

        // Giữ paddle trong màn hình
        if (x < Constants.WALL_THICK) {
            x = Constants.WALL_THICK;
        }
        if (x + w > Constants.WIDTH - Constants.WALL_THICK) {
            x = Constants.WIDTH - Constants.WALL_THICK - w;
        }

        // Cập nhật vệt sáng
        double dx = Math.abs(x - oldX);

        if (dx > 0.1) { 
            trailTimer += dx;
            if (trailTimer >= TRAIL_GAP) {
                trail.add(0, new Point((int) (x + w / 2), (int) y));
                if (trail.size() > TRAIL_LENGTH) trail.remove(trail.size() - 1);
                trailTimer = 0;
            }
            idleTimer = 0;
        } else {
            idleTimer += dt * TRAIL_FADE_SPEED;
            // giảm dần số vệt theo thời gian đứng yên
            while (idleTimer > 1 && !trail.isEmpty()) {
                trail.remove(trail.size() - 1);
                idleTimer -= 1;
            }
        }

        // Cập nhật trạng thái súng
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
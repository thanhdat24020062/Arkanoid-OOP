package com.nhom_4.arkanoid.entity;

import com.nhom_4.arkanoid.gfx.Assets; // <-- Thêm import này
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Ball extends Entity {
    private double vx, vy, r;
    private boolean stickToPaddle = true;
    private final Random rng = new Random();
    private boolean isFireball = false;
    private double fireballTimer = 0;

    public Ball(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.w = this.h = r * 2;
    }

    @Override
    public void render(Graphics2D g) {
        // Lấy ảnh bóng trực tiếp từ "nhà kho" Assets để vẽ
        g.drawImage(Assets.ball, (int) (x - r), (int) (y - r), (int) (r * 2), (int) (r * 2), null);

        // Vẽ thêm hiệu ứng lửa nếu cần
        if (isFireball) {
            g.setColor(new Color(255, 100, 0, 100)); // Màu cam, hơi trong suốt
            g.fillOval((int) (x - r - 2), (int) (y - r - 2), (int) (r * 2) + 4, (int) (r * 2) + 4);
        }
    }

    // --- CÁC PHƯƠNG THỨC KHÁC GIỮ NGUYÊN ---

    @Override
    public void update(double dt) {
        x += vx * dt;
        y += vy * dt;
        if (isFireball) {
            fireballTimer -= dt;
            if (fireballTimer <= 0) {
                deactivateFireball();
            }
        }
    }

    public void launchRandomUp() {
        double base = Math.toRadians(60 + rng.nextInt(60));
        vx = 260 * Math.cos(base) * (rng.nextBoolean() ? 1 : -1);
        vy = -260 * Math.sin(base);
        stickToPaddle = false;
    }

    public void stickToPaddle(boolean v) {
        this.stickToPaddle = v;
    }

    public boolean isSticking() {
        return stickToPaddle;
    }

    public double getX() {
        return x;
    }

    public void setX(double v) {
        this.x = v;
    }

    public double getY() {
        return y;
    }

    public void setY(double v) {
        this.y = v;
    }

    public double getVx() {
        return vx;
    }

    public void setVx(double v) {
        vx = v;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double v) {
        vy = v;
    }

    public double getR() {
        return r;
    }

    public void activateFireball(double duration) {
        this.isFireball = true;
        this.fireballTimer = duration;
    }

    public void deactivateFireball() {
        this.isFireball = false;
        this.fireballTimer = 0;
    }

    public Ball cloneAndLaunch(double angleOffset) {
        // Tạo một quả bóng mới tại đúng vị trí của bóng cũ
        Ball clone = new Ball(this.x, this.y, this.r);

        // Tính góc hiện tại
        double currentAngle = Math.toDegrees(Math.atan2(-this.vy, this.vx));
        // Tính góc mới
        double newAngle = Math.toRadians(currentAngle + angleOffset);
        // Tính tốc độ hiện tại
        double speed = Math.hypot(this.vx, this.vy);

        // Đặt vận tốc mới cho quả bóng clone
        clone.setVx(speed * Math.cos(newAngle));
        clone.setVy(-speed * Math.sin(newAngle));
        clone.stickToPaddle(false);

        // Nếu bóng gốc là bóng lửa, bóng clone cũng là bóng lửa
        if (this.isFireball) {
            clone.activateFireball(this.fireballTimer);
        }

        return clone;
    }

    public boolean isFireball() {
        return this.isFireball;
    }

    public void speedUp(double mul, double cap) {
        vx *= mul;
        vy *= mul;
        limitSpeed(cap);
    }

    public void limitSpeed(double cap) {
        double currentSpeed = Math.hypot(vx, vy);
        if (currentSpeed > cap) {
            double factor = cap / currentSpeed;
            vx *= factor;
            vy *= factor;
        }
    }

    @Override
    public Rectangle2D.Double getRect() {
        return new Rectangle2D.Double(x - r, y - r, 2 * r, 2 * r);
    }
}
package com.nhom_4.arkanoid.entity;

import com.nhom_4.arkanoid.config.Constants;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon; // Cần import này

public class Paddle extends Entity {
    private double speed;
    private Image paddleImage; // Biến để lưu hình ảnh của paddle
    private boolean hasLasers = false;
    private double laserTimer = 0;
    private double shootCooldown = 0; // Thời gian chờ giữa mỗi phát bắn

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

    public Paddle(double x, double y, double w, double h, double speed) {
        this.x = x;
        this.y = y;
        this.w = w; // Chiều rộng paddle sẽ được lấy từ Constants
        this.h = h; // Chiều cao paddle sẽ được lấy từ Constants
        this.speed = speed;

        // Tải hình ảnh khi khởi tạo paddle
        loadImage("res/images/paddle.png"); // <--- Thay đổi đường dẫn đến file ảnh của bạn
    }

    // Hàm để tải hình ảnh từ đường dẫn
    private void loadImage(String imagePath) {
        ImageIcon ii = new ImageIcon(imagePath);
        paddleImage = ii.getImage();
    }

    @Override
    public void update(double dt) {

    }

    public void update(double dt, double dir) {
        x += dir * speed * dt;

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
            // Reset cooldown
            shootCooldown = 0.3; // Bắn mỗi 0.3 giây

            List<Bullet> bullets = new ArrayList<>();
            // Tạo viên đạn bên trái
            bullets.add(new Bullet(this.x + 5, this.y));
            // Tạo viên đạn bên phải
            bullets.add(new Bullet(this.x + this.w - 5, this.y));

            return bullets;
        }
        return null; // Không thể bắn
    }

    @Override
    public void render(Graphics2D g) {
        // Thay vì vẽ hình chữ nhật, chúng ta vẽ hình ảnh
        if (paddleImage != null) {
            g.drawImage(paddleImage, (int) x, (int) y, (int) w, (int) h, null);
        } else {
            // Nếu không tải được ảnh, fallback về vẽ hình chữ nhật màu
            g.setColor(new Color(200, 220, 255));
            g.fill(new Rectangle2D.Double(x, y, w, h));
        }
        if (hasLasers) {
            g.setColor(Color.DARK_GRAY);
            // Súng trái
            g.fillRect((int) this.x, (int) this.y - 5, 10, 5);
            // Súng phải
            g.fillRect((int) (this.x + this.w - 10), (int) this.y - 5, 10, 5);
        }
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

        if (x < Constants.WALL_THICK) {
            x = Constants.WALL_THICK;
        }
        if (x + this.w > Constants.WIDTH - Constants.WALL_THICK) {
            x = Constants.WIDTH - Constants.WALL_THICK - this.w;
        }
    }

    public void setX(double v) {
        this.x = v;
    }

    public double centerX() {
        return this.x + this.w / 2.0;
    }
}
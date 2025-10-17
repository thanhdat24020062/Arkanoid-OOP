// src/com/nhom_4/arkanoid/entity/PowerUp.java
package com.nhom_4.arkanoid.entity;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class PowerUp {
    private double x, y;
    private final double width = 20;
    private final double height = 20;
    private final double speed = 150; // Tốc độ rơi
    private boolean active = true;

    private final PowerUpType type;

    public PowerUp(double x, double y, PowerUpType type) {
        // Khởi tạo ở giữa viên gạch
        this.x = x - width / 2;
        this.y = y - height / 2;
        this.type = type;
    }

    public void update(double dt) {
        y += speed * dt;
        // Nếu ra khỏi màn hình thì vô hiệu hóa
        if (y > com.nhom_4.arkanoid.config.Constants.HEIGHT) {
            active = false;
        }
    }

    public void render(Graphics2D g) {
        g.setColor(getColor());
        g.fillRect((int) x, (int) y, (int) width, (int) height);

        // Vẽ chữ cái để phân biệt
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        String text = "";
        switch (type) {
            case WIDEN_PADDLE:
                text = "W";
                break;
            case EXTRA_LIFE:
                text = "L";
                break;
            case FIREBALL:
                text = "F";
            case LASER_PADDLE:
                text = "S";
                break;
        }
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        g.drawString(text, (int) (x + (width - textWidth) / 2), (int) (y + 15));
    }

    private Color getColor() {
        switch (type) {
            case WIDEN_PADDLE:
                return Color.BLUE;
            case EXTRA_LIFE:
                return Color.GREEN;
            case FIREBALL:
                return Color.RED;
            case LASER_PADDLE:
                return Color.MAGENTA;
            case MULTI_BALL:
                return Color.CYAN;
            default:
                return Color.GRAY;
        }
    }

    public Rectangle2D.Double getRect() {
        return new Rectangle2D.Double(x, y, width, height);
    }

    public PowerUpType getType() {
        return type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
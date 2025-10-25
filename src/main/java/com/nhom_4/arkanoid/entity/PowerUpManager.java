package com.nhom_4.arkanoid.entity;

import com.nhom_4.arkanoid.config.Constants;
import com.nhom_4.arkanoid.ui.HUD;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class PowerUpManager {
    private final List<PowerUp> activePowerUps;
    private final Random random;

    public PowerUpManager() {
        this.activePowerUps = new ArrayList<>();
        this.random = new Random();
    }

    /**
     * Thử tạo một power-up tại vị trí của viên gạch bị phá.
     * 
     * @param brickX Tọa độ x của viên gạch
     * @param brickY Tọa độ y của viên gạch
     */
    public void spawnPowerUp(double brickX, double brickY) {
        // test nên để 100%
        if (random.nextDouble() < 1) {
            // Chọn ngẫu nhiên một loại power-up
            PowerUpType[] types = PowerUpType.values();
            PowerUpType randomType = types[random.nextInt(types.length)];
            activePowerUps.add(new PowerUp(brickX, brickY, randomType));
        }
    }

    public void update(double dt, Paddle paddle, HUD hud, List<Ball> balls) {
        Iterator<PowerUp> iterator = activePowerUps.iterator();
        while (iterator.hasNext()) {
            PowerUp p = iterator.next();
            p.update(dt);

            // Kiểm tra va chạm với paddle
            if (p.getRect().intersects(paddle.getRect())) {
                applyEffect(p, paddle, hud, balls);
                p.setActive(false); // Vô hiệu hóa sau khi va chạm
            }

            // Xóa power-up nếu nó không còn active (rơi ra ngoài hoặc đã va chạm)
            if (!p.isActive()) {
                iterator.remove();
            }
        }
    }

    private void applyEffect(PowerUp p, Paddle paddle, HUD hud, List<Ball> balls) {
        switch (p.getType()) {
            case WIDEN_PADDLE:
                // Tăng chiều rộng paddle, nhưng có giới hạn
                double newWidth = Math.min(paddle.getW() + Constants.PADDLE_WIDTH * 0.1, Constants.PADDLE_WIDTH * 1.5);
                paddle.setW(newWidth);
                break;
            case EXTRA_LIFE:
                hud.addLife();
                break;
            case FIREBALL: // <-- Thêm case này
                for (Ball b : balls) {
                    b.activateFireball(5.0);
                }
                break;
            case LASER_PADDLE:
                paddle.activateLasers(7.0);
                break;
            case MULTI_BALL:
                if (!balls.isEmpty()) {
                    Ball baseBall = balls.get(0);
                    Ball clone1 = baseBall.cloneAndLaunch(-15);
                    Ball clone2 = baseBall.cloneAndLaunch(15);
                    balls.add(clone1);
                    balls.add(clone2);
                }
                break;
            default:
                break;
        }
    }

    public void render(Graphics2D g) {
        for (PowerUp p : activePowerUps) {
            p.render(g);
        }
    }

    /**
     * Xóa tất cả power-up khi bắt đầu game mới hoặc qua màn.
     */
    public void reset() {
        activePowerUps.clear();
    }
}
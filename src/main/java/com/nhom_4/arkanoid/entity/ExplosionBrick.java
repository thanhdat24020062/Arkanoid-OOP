package com.nhom_4.arkanoid.entity;

import com.nhom_4.arkanoid.gfx.Animation;
import com.nhom_4.arkanoid.gfx.Assets;

import java.awt.Image;
import java.util.List;

public class ExplosionBrick extends Brick {
    private List<Brick> bricks;
    private PowerUpManager powerUpManager;
    private boolean exploded = false;

    public ExplosionBrick(double x, double y, double w, double h, int health,
                          Image image, int type, List<Brick> bricks, PowerUpManager powerUpManager) {
        super(x, y, w, h, health, image, type);

        animation = new Animation(Assets.explosionBrick, 0.1, 0);

        this.bricks = bricks;
        this.powerUpManager = powerUpManager;
    }

    public void explode() {
        exploded = true;

        double cx = centerX();
        double cy = centerY();

        double rangeX = this.getW() * 1.8;
        double rangeY = this.getH() * 1.8;

        for (Brick b : bricks) {
            if (b == this || !b.isAlive() || b.isUnBreakable()) continue;

            double dx = Math.abs(b.centerX() - cx);
            double dy = Math.abs(b.centerY() - cy);

            if (dx <= rangeX && dy <= rangeY) {
                b.hit();
                if (!b.isAlive()) {
                    powerUpManager.spawnPowerUp(b.centerX(), b.centerY());
                }
            }
        }
    }
}


package com.nhom_4.arkanoid.entity;

import com.nhom_4.arkanoid.gfx.Animation;
import com.nhom_4.arkanoid.gfx.Assets;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ExplosionBrick extends Brick {
    private final List<Brick> bricks;

    /**
     * khởi tạo gạch nổ
     * @param x tọa độ x
     * @param y tọa độ y
     * @param w chiều dài
     * @param h chiều cao
     * @param health máu
     * @param image ảnh
     * @param type loại gạch (loại 6)
     * @param bricks danh sách gạch liền kề ảnh hưởng bởi vụ nổ
     * @param powerUpManager quản lí powerUp rơi cho gạch bị nổ
     */
    public ExplosionBrick(double x, double y, double w, double h, int health,
            Image image, int type, List<Brick> bricks, PowerUpManager powerUpManager) {
        super(x, y, w, h, health, image, type);
        if (!Assets.explosionBrick.isEmpty()) {
            animation = new Animation(Assets.explosionBrick, 0.1, 0);
        }

        this.bricks = bricks;
    }

    public List<Brick> explode() {
        double cx = centerX();
        double cy = centerY();

        double rangeX = this.getW() * 1.5;
        double rangeY = this.getH() * 1.5;

        List<Brick> affected = new ArrayList<>();

        for (Brick b : bricks) {
            if (b == this || !b.isAlive() || b.isUnBreakable())
                continue;

            double dx = Math.abs(b.centerX() - cx);
            double dy = Math.abs(b.centerY() - cy);

            if (dx <= rangeX && dy <= rangeY) {
                affected.add(b);
            }
        }
        return affected;
    }
}

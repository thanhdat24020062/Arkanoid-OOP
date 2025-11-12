package com.nhom_4.arkanoid.entity;

import com.nhom_4.arkanoid.gfx.Animation;
import com.nhom_4.arkanoid.gfx.Assets;

import java.awt.*;

public class Brick extends Entity {

    protected transient Animation animation;
    private int health;
    private int type;
    private transient Image brickImage;

    /**
     * khởi tạo brick
     * @param x tọa độ x
     * @param y tọa độ y
     * @param w chiều dài
     * @param h chiều rộng
     * @param health máu
     * @param image ảnh
     */
    public Brick(double x, double y, double w, double h, int health, Image image) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.health = health;
        this.brickImage = image;
        this.type = 0;
    }

    /**
     khởi tạo brick
     * @param x tọa độ x
     * @param y tọa độ y
     * @param w chiều dài
     * @param h chiều rộng
     * @param health máu
     * @param image ảnh
     * @param type loại gạch (0: unbreakable; 1,2,3,4,5: normal brick; 6: explosion brick; 40,50: broken brick)
     */
    public Brick(double x, double y, double w, double h, int health, Image image, int type) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.health = health;
        this.brickImage = image;
        this.type = type;
        if (type == 4) {
            if (!Assets.goldBrick.isEmpty()) {
                animation = new Animation(Assets.goldBrick, 0.1, 2);
            }
        } else if (type == 5) {
            if (!Assets.silverBrick.isEmpty()) {
                animation = new Animation(Assets.silverBrick, 0.1, 2); // 0.2s/frame
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (isAlive()) {
            g.drawImage(brickImage, (int) x, (int) y, (int) w, (int) h, null);
        }
    }

    public boolean isAlive() {
        return health > 0;
    }

    public boolean isUnBreakable() {
        return health >= 9;
    }

    public boolean hit() {
        if (health < 9) {
            health--;
        }
        if (isAlive()) {
            updateTexture();
        }
        return health <= 0;
    }

    private void updateTexture() {
        if (type == 4 && health == 1) {
            type = 40;
            brickImage = Assets.bricks.get(40);
            animation = null;
        }
        if (type == 5 && health == 1) {
            type = 50;
            brickImage = Assets.bricks.get(50);
            animation = null;
        }
    }

    public void destroy() {
        this.health = 0;
    }

    public double centerX() {
        return x + w / 2.0;
    }

    public double centerY() {
        return y + h / 2.0;
    }

    @Override
    public void update(double dt) {
        if (animation != null) {
            animation.update(dt);
            brickImage = animation.getCurrentFrame();
        }
    }

    /**
     * tải lại hình ảnh và animation cho gạch khi load game
     */
    public void restoreAfterLoad() {
        if (type >= 0) {
            brickImage = Assets.bricks.get(type);
        }

        // Phục hồi animation nếu là brick đặc biệt
        if (type == 4 && !Assets.goldBrick.isEmpty()) {
            animation = new Animation(Assets.goldBrick, 0.1, 2);
        } else if (type == 5 && !Assets.silverBrick.isEmpty()) {
            animation = new Animation(Assets.silverBrick, 0.1, 2);
        } else if (type == 6 && !Assets.explosionBrick.isEmpty()) {
            animation = new Animation(Assets.explosionBrick, 0.1, 0);
        }
        updateTexture();
    }
}
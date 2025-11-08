package com.nhom_4.arkanoid.entity;

import java.awt.Graphics2D;
import java.awt.Image;

import com.nhom_4.arkanoid.gfx.Animation;
import com.nhom_4.arkanoid.gfx.Assets;

public class Brick extends Entity {

    private int health;
    private int type;
    private Image brickImage;
    private Animation animation;

    public Brick(double x, double y, double w, double h, int health, Image image) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.health = health;
        this.brickImage = image;
        this.type = -1;
    }

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
        if (type == 5 && health == 1) {
            type = 6;
            brickImage = Assets.bricks.get(6);
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
}
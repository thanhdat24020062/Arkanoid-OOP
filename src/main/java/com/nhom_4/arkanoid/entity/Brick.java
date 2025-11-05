package com.nhom_4.arkanoid.entity;

import com.nhom_4.arkanoid.audio.Sound;

import java.awt.Graphics2D;
import java.awt.Image;

public class Brick extends Entity {

    private int health;
    private final Image brickImage;

    public Brick(double x, double y, double w, double h, int health, Image image) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.health = health;
        this.brickImage = image;
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

    public boolean hit() {
        if (health < 9) {
            health--;
        }
        return health <= 0;
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
    }
}
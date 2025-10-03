package com.yourname.arkanoid.entity;

import java.awt.*;
import java.awt.geom.*;

public class Brick extends Entity {
    private int hp;
    private Color color;

    public Brick(double x, double y, double w, double h, int hp) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.hp = hp;
        this.color = pick(hp);
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public boolean hit() {
        hp--;
        color = pick(Math.max(hp, 0));
        return hp <= 0;
    }

    private Color pick(int p) {
        switch (p) {
            case 3:
                return new Color(255, 90, 90);
            case 2:
                return new Color(255, 160, 80);
            case 1:
                return new Color(120, 200, 255);
            default:
                return new Color(60, 70, 90, 100);
        }
    }

    @Override
    public void update(double dt) {
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(color);
        g.fill(new RoundRectangle2D.Double(x, y, w, h, 8, 8));
        g.setColor(new Color(255, 255, 255, 40));
        g.draw(new RoundRectangle2D.Double(x + 1, y + 1, w - 2, h - 2, 8, 8));
    }
}
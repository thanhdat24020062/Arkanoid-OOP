package com.nhom_4.arkanoid.entity;

import java.awt.*;
import java.awt.geom.*;

import com.nhom_4.arkanoid.config.Constants;

public class Paddle extends Entity {
    private double speed;

    public Paddle(double x, double y, double w, double h, double speed) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.speed = speed;
    }

    public void update(double dt, double dir) {
        x += dir * speed * dt;
        if (x < Constants.WALL_THICK)
            x = Constants.WALL_THICK;
        if (x + w > Constants.WIDTH - Constants.WALL_THICK)
            x = Constants.WIDTH - Constants.WALL_THICK - w;
    }

    @Override
    public void update(double dt) {
        /* not used */ }

    public double centerX() {
        return x + w / 2.0;
    }

    public void setX(double v) {
        this.x = v;
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(new Color(200, 220, 255));
        g.fill(new RoundRectangle2D.Double(x, y, w, h, 10, 10));
    }
}
package com.nhom_4.arkanoid.entity;

import java.awt.*;
import java.awt.geom.*;
import java.util.Random;

public class Ball extends Entity {
    private double vx, vy, r;
    private boolean stickToPaddle = true;
    private final Random rng = new Random();

    public Ball(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.w = this.h = r * 2;
    }

    public void launchRandomUp() {
        double base = Math.toRadians(60 + rng.nextInt(60)); // 60..120 deg
        vx = 260 * Math.cos(base) * (rng.nextBoolean() ? 1 : -1);
        vy = -260 * Math.sin(base);
        stickToPaddle = false;
    }

    public void stickToPaddle(boolean v) {
        this.stickToPaddle = v;
    }

    public boolean isSticking() {
        return stickToPaddle;
    }

    public void setX(double v) {
        this.x = v;
    }

    public void setY(double v) {
        this.y = v;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVx(double v) {
        vx = v;
    }

    public void setVy(double v) {
        vy = v;
    }

    public double getR() {
        return r;
    }

    public void limitSpeed(double cap) {
        double s = Math.hypot(vx, vy);
        if (s > cap) {
            double f = cap / s;
            vx *= f;
            vy *= f;
        }
    }

    public void speedUp(double mul, double cap) {
        vx *= mul;
        vy *= mul;
        limitSpeed(cap);
        if (Math.abs(vy) < 120)
            vy = Math.copySign(120, vy == 0 ? -1 : vy);
    }

    @Override
    public void update(double dt) {
        x += vx * dt;
        y += vy * dt;
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(new Color(255, 245, 180));
        g.fill(new Ellipse2D.Double(x - r, y - r, r * 2, r * 2));
    }

    @Override
    public Rectangle2D.Double getRect() {
        return new Rectangle2D.Double(x - r, y - r, r * 2, r * 2);
    }
}
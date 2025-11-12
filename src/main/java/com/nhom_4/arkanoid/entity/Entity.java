package com.nhom_4.arkanoid.entity;

import java.awt.*;
import java.awt.geom.*;
import java.io.Serializable;

public abstract class Entity implements Serializable {
    protected double x, y, w, h;

    public abstract void update(double dt);

    public abstract void render(Graphics2D g);

    public Rectangle2D.Double getRect() {
        return new Rectangle2D.Double(x, y, w, h);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getW() {
        return w;
    }

    public double getH() {
        return h;
    }
}
package com.nhom_4.arkanoid.entity;

import com.nhom_4.arkanoid.gfx.Animation;
import com.nhom_4.arkanoid.gfx.Assets;

import java.awt.*;

public class ExplosionEffect extends Entity {
    private transient Animation animation;
    private boolean finished = false;

    public ExplosionEffect(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w * 3;
        this.h = h * 3;
        if (!Assets.explosionEffect.isEmpty()) {
            this.animation = new Animation(Assets.explosionEffect, 0.06, 0);
        }
    }

    public void update(double dt) {
        if (animation != null) {
            animation.update(dt);
            if (animation.getCurrentFrame() == Assets.explosionEffect.get(Assets.explosionEffect.size()-1)) {
                finished = true;
            }
        }
    }

    public void render(Graphics2D g) {
        if (!finished && animation != null) {
            Image frame = animation.getCurrentFrame();
            if (frame != null) {
                g.drawImage(frame, (int) x, (int) y, (int) w, (int) h,null);
            }
        }
    }

    public boolean isFinished() {
        return finished;
    }
}

package com.nhom_4.arkanoid.gfx;

import java.awt.*;
import java.util.List;

public class Animation {
    private final List<Image> frames;
    private int currentFrame;
    private double frameTimer = 0;
    private final double frameDuration;
    private final double loopDelay;
    private double loopTimer = 0;

    public Animation(List<Image> frames, double frameDuration, double loopDelay) {
        this.frames = frames;
        this.frameDuration = frameDuration;
        this.loopDelay = loopDelay;
    }

    public void update(double dt) {
        if (frames == null || frames.isEmpty()) {
            return;
        }

        if (currentFrame >= frames.size()) {
            loopTimer += dt;
            if (loopTimer >= loopDelay) {
                currentFrame = 0;
                loopTimer = 0;
                frameTimer = 0;
            }
            return;
        }

        frameTimer += dt;
        if (frameTimer >= frameDuration) {
            frameTimer -= frameDuration;
            currentFrame++;
            if (currentFrame >= frames.size()) {
                loopTimer = 0;
            }
        }
    }

    public Image getCurrentFrame() {
        if (frames == null || frames.isEmpty()) {
            return null;
        }
        if (currentFrame >= frames.size()) {
            return frames.getFirst();
        }
        return frames.get(currentFrame);
    }
}

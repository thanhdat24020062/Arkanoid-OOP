package com.nhom_4.arkanoid.ui;

import java.awt.*;
import java.io.Serializable;

public class HUD implements Serializable {
    private int score = 0;
    private int lives = 3;
    private transient int fps = 0;

    private final Font small = new Font(Font.SANS_SERIF, Font.BOLD, 16);

    public void reset() {
        score = 0;
        lives = 3;
    }

    public void addLife() {
        if (this.lives < 3) {
            this.lives++;
        } else {
            addScore(100);
        }
    }

    public void addScore(int s) {
        score += s;
    }

    public int getScore() {
        return score;
    }

    public void loseLife() {
        lives--;
    }

    public int getLives() {
        return lives;
    }

    public void setFps(int v) {
        this.fps = v;
    }

    public void render(Graphics2D g) {
        g.setFont(small);
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 16, 16);
        g.drawString("Lives: " + lives + "/3", 140, 16);
        g.drawString("FPS: " + fps, 240, 16);
    }

}
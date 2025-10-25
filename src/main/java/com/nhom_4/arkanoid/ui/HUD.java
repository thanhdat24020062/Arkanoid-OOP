package com.nhom_4.arkanoid.ui;

import java.awt.*;

public class HUD {
    private int score = 0;
    private int lives = 3;
    private int fps = 0;

    private final Font small = new Font(Font.SANS_SERIF, Font.BOLD, 16);

    public void reset() {
        score = 0;
        lives = 3;
    }

    public void addLife() {
        this.lives++;
    }

    public void addScore(int s) {
        score += s;
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
        g.drawString("Lives: " + lives, 140, 16);
        g.drawString("FPS: " + fps, 240, 16);
    }

}
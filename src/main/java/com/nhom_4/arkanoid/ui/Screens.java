package com.nhom_4.arkanoid.ui;

import java.awt.*;

import com.nhom_4.arkanoid.config.Constants;
import com.nhom_4.arkanoid.gfx.Renderer;

public class Screens {
    private final Font big = new Font(Font.SANS_SERIF, Font.BOLD, 36);
    private final Font small = new Font(Font.SANS_SERIF, Font.BOLD, 16);

    public void drawBackground(Graphics2D g) {
        Paint old = g.getPaint();
        g.setPaint(new GradientPaint(0, 0, new Color(20, 24, 36), 0, Constants.HEIGHT, new Color(18, 18, 22)));
        g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
        g.setPaint(old);
    }

    public void drawWalls(Graphics2D g) {
        g.setColor(new Color(60, 70, 90));
        g.fillRect(0, 0, Constants.WIDTH, Constants.TOP_WALL);
        g.fillRect(0, 0, Constants.WALL_THICK, Constants.HEIGHT);
        g.fillRect(Constants.WIDTH - Constants.WALL_THICK, 0, Constants.WALL_THICK, Constants.HEIGHT);
    }

    public void drawCenterText(Graphics2D g, String title, String sub) {
        g.setColor(new Color(255, 255, 255, 230));
        Renderer.drawCenteredString(g, title, Constants.HEIGHT / 2 - 20, big, g.getColor());
        g.setColor(Color.WHITE);
        Renderer.drawCenteredString(g, sub, Constants.HEIGHT / 2 + 12, small, g.getColor());
    }
}
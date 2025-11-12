package com.nhom_4.arkanoid.ui;

import java.awt.*;

import com.nhom_4.arkanoid.config.Constants;

public class Screens {
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

    public void drawCenterText(Graphics2D g, String title, String subtitle) {
        g.setFont(new Font("Arial", Font.BOLD, 48));
        FontMetrics fm = g.getFontMetrics();
        int titleWidth = fm.stringWidth(title);
        g.setColor(Color.WHITE);
        g.drawString(title, (Constants.WIDTH - titleWidth) / 2, Constants.HEIGHT / 2 - 20);

        g.setFont(new Font("Arial", Font.PLAIN, 24));
        fm = g.getFontMetrics();
        g.setColor(Color.LIGHT_GRAY);

        String[] lines = subtitle.split("\n");
        int y = Constants.HEIGHT / 2 + 20;

        for (String line : lines) {
            int lineWidth = fm.stringWidth(line);
            g.drawString(line, (Constants.WIDTH - lineWidth) / 2, y);
            y += fm.getHeight();
        }
    }

    public void drawDeathLine(Graphics2D g) {
        Stroke oldStroke = g.getStroke();
        Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0);
        g.setStroke(dashed);
        g.setColor(new Color(255, 0, 0, 150));
        int yPos = (Constants.HEIGHT - 50) + (int) Constants.PADDLE_HEIGHT + 5;
        g.drawLine(Constants.WALL_THICK, yPos, Constants.WIDTH - Constants.WALL_THICK, yPos);
        g.setStroke(oldStroke);
    }

}
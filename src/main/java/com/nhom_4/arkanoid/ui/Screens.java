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


    // Trong file: Screens.java

    public void drawDeathLine(Graphics2D g) {
        // Lưu lại nét vẽ cũ để không ảnh hưởng đến các đối tượng khác
        Stroke oldStroke = g.getStroke();

        // Tạo một nét vẽ mới (dạng đường đứt nét)
        // new float[]{9} nghĩa là 9 pixel vẽ, 9 pixel trống
        Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0);
        g.setStroke(dashed);

        // Chọn màu (đỏ, hơi trong suốt)
        g.setColor(new Color(255, 0, 0, 150));

        // Vị trí của đường kẻ (ngang bằng với đáy của tường)
        int yPos = (Constants.HEIGHT - 50) + (int) Constants.PADDLE_HEIGHT + 5;

        // Vẽ đường kẻ từ tường trái sang tường phải
        g.drawLine(Constants.WALL_THICK, yPos, Constants.WIDTH - Constants.WALL_THICK, yPos);

        // Phục hồi lại nét vẽ cũ
        g.setStroke(oldStroke);
    }

    public void drawCenterText(Graphics2D g, String title, String sub) {
        g.setColor(new Color(255, 255, 255, 230));
        Renderer.drawCenteredString(g, title, Constants.HEIGHT / 2 - 20, big, g.getColor());
        g.setColor(Color.WHITE);
        Renderer.drawCenteredString(g, sub, Constants.HEIGHT / 2 + 12, small, g.getColor());
    }
}
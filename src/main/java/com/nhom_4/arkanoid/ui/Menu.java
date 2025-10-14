package com.nhom_4.arkanoid.ui;

import java.awt.*;
import com.nhom_4.arkanoid.config.Constants;
import com.nhom_4.arkanoid.gfx.Renderer;
import com.nhom_4.arkanoid.input.MouseInput;
import com.nhom_4.arkanoid.audio.Sound;

public class Menu {
    public enum Action {
        NONE, START, HELP_TOGGLE, EXIT
    }

    private final String[] items = { "START", "HOW TO PLAY", "EXIT" };
    private int selected = 0;
    private boolean showHelp = false;

    // bounds cho 3 nút để bắt click
    private final Rectangle[] btn = {
            new Rectangle(), new Rectangle(), new Rectangle()
    };

    private final Font titleFont = new Font(Font.SANS_SERIF, Font.BOLD, 42);
    private final Font itemFont = new Font(Font.SANS_SERIF, Font.BOLD, 20);
    private final Font helpFont = new Font(Font.MONOSPACED, Font.PLAIN, 14);

    public boolean isHelpVisible() {
        return showHelp;
    }

    /** Xử lý click chuột vào nút */
    public Action update(MouseInput m) {
        Point p = m.consumeClick(); // lấy 1 lần
        if (p == null)
            return Action.NONE; // chưa có click mới thì thôi

        for (int i = 0; i < items.length; i++) {
            if (btn[i].contains(p)) { // Rectangle có contains(Point)
                selected = i;
                Sound.blip();
                return choose(i); // START / HELP_TOGGLE / EXIT
            }
        }
        return Action.NONE;
    }

    private void move(int dir) {
        int old = selected;
        selected = (selected + dir + items.length) % items.length;
        if (old != selected)
            Sound.tick();
    }

    private Action choose(int idx) {
        switch (idx) {
            case 0:
                return Action.START;
            case 1:
                showHelp = !showHelp;
                return Action.HELP_TOGGLE;
            case 2:
                return Action.EXIT;
        }
        return Action.NONE;
    }

    /** Vẽ nền + tiêu đề + các nút + (tuỳ chọn) khung hướng dẫn */
    public void render(Graphics2D g) {
        Screens s = new Screens();
        s.drawBackground(g);
        s.drawWalls(g);

        g.setFont(titleFont);
        g.setColor(new Color(255, 255, 255, 230));
        Renderer.drawCenteredString(g, "ARKANOID", Constants.HEIGHT / 2 - 120, g.getFont(), g.getColor());

        int baseY = Constants.HEIGHT / 2 - 20;
        int gap = 70;

        // Cần metrics để tính bounds click trùng với nút vẽ
        FontMetrics fm = g.getFontMetrics(itemFont);
        int padX = 24, padY = 12;

        for (int i = 0; i < items.length; i++) {
            boolean focus = (i == selected);
            int y = baseY + i * gap;

            // vẽ nút
            Renderer.drawButton(g, items[i], Constants.WIDTH / 2, y, focus, itemFont);

            // update bounds để click
            int textW = fm.stringWidth(items[i]);
            int textH = fm.getAscent();
            int w = textW + padX * 2;
            int h = textH + padY * 2;
            int x = Constants.WIDTH / 2 - w / 2;
            int top = y - textH - padY;
            btn[i].setBounds(x, top, w, h);
        }

        if (showHelp)
            drawHelp(g);
    }

    private void drawHelp(Graphics2D g) {
        int w = 520, h = 200;
        int x = (Constants.WIDTH - w) / 2;
        int y = Constants.HEIGHT / 2 + 110;

        g.setColor(new Color(20, 24, 36, 220));
        g.fillRoundRect(x, y, w, h, 16, 16);
        g.setColor(new Color(255, 255, 255, 60));
        g.drawRoundRect(x, y, w, h, 16, 16);

        g.setFont(helpFont);
        g.setColor(Color.WHITE);
        int tx = x + 18, ty = y + 28, lh = 20;
        String[] lines = {
                "Controls:",
                "  ← / A : Move left",
                "  → / D : Move right",
                "  SPACE : Launch ball / Select",
                "  P     : Pause   |   R : Restart",
                "",
                "Goal: Break all bricks. Don't let the ball fall."
        };
        for (String s : lines) {
            g.drawString(s, tx, ty);
            ty += lh;
        }
    }
}

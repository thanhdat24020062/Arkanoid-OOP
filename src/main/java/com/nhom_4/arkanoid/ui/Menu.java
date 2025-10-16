package com.nhom_4.arkanoid.ui;

import java.awt.image.BufferedImage;
import java.awt.*;
import com.nhom_4.arkanoid.config.Constants;
import com.nhom_4.arkanoid.gfx.Assets;
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

    public void drawMenuBackground(Graphics2D g) {
        BufferedImage img = Assets.MENU_BG;
        if (img != null) {
            // Vẽ kiểu “cover” (giữ tỉ lệ, có thể crop 2 biên)
            double sw = img.getWidth(), sh = img.getHeight();
            double s = Math.max(Constants.WIDTH / sw, Constants.HEIGHT / sh);
            int dw = (int) Math.round(sw * s);
            int dh = (int) Math.round(sh * s);
            int dx = (Constants.WIDTH - dw) / 2;
            int dy = (Constants.HEIGHT - dh) / 2;
            g.drawImage(img, dx, dy, dw, dh, null);
        } else {
            // Fallback nếu thiếu ảnh
            Paint old = g.getPaint();
            g.setPaint(new GradientPaint(0, 0, new Color(20, 24, 36),
                    0, Constants.HEIGHT, new Color(18, 18, 22)));
            g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
            g.setPaint(old);
        }
        // Overlay mờ để chữ/nút nổi hơn
        g.setColor(new Color(0, 0, 0, 30));
        g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
    }

    /** Vẽ nền + tiêu đề + các nút + (tuỳ chọn) khung hướng dẫn */
    public void render(Graphics2D g, MouseInput mouseInput) {
        this.drawMenuBackground(g);
        int baseY = Constants.HEIGHT / 2 - 20;
        int gap = 120;

        Point mousePos = mouseInput.getPosition(); // thêm hàm này trong MouseInput nếu chưa có

        for (int i = 0; i < items.length; i++) {
            BufferedImage taget=null;
            if(i==0) taget=Assets.BUTTON_START;
            else if(i==1) taget=Assets.BUTTON_HOW_TO_PLAY;
            else taget=Assets.BUTTON_EXIT;
            int cy = baseY + i * gap; // centerY theo hàng
            Rectangle r = Renderer.drawButtonInMenu(
                    g,
                    taget, // ảnh nền nút bạn đã load
                    items[i], // text
                    Constants.WIDTH / 2, // centerX
                    cy, // centerY
                    Assets.fontPixels_40, 
                    mousePos,
                    0.20f, // scaleNormal
                    0.20f * 1.06f // scaleHover // để biết hover
            // , 1.0f, 1.06f // (tuỳ chọn) scale thường & khi hover
            );
            btn[i].setBounds(r); // cập nhật hitbox click
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

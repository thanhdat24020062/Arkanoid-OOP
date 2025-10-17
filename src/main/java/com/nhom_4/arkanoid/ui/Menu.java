package com.nhom_4.arkanoid.ui;

import java.awt.*;
import java.awt.image.BufferedImage;

import com.nhom_4.arkanoid.config.Constants;
import com.nhom_4.arkanoid.gfx.Renderer;
import com.nhom_4.arkanoid.input.MouseInput;
import com.nhom_4.arkanoid.audio.Sound;
import com.nhom_4.arkanoid.gfx.Assets;

public class Menu {
    public enum Action {
        NONE, START, HELP_TOGGLE, EXIT
    }

    private final String[] items = { "START", "HOW TO PLAY", "EXIT" };
    private int selected = 0;
    private boolean showHelp = false;

    // ====== Exit? overlay ======
    private boolean exitConfirm = false;
    private final Rectangle yesBtn = new Rectangle();
    private final Rectangle noBtn = new Rectangle();
    private final Rectangle exitPanel = new Rectangle(); // bounds của khung Exit?

    // bounds cho 3 nút để bắt click
    private final Rectangle[] btn = { new Rectangle(), new Rectangle(), new Rectangle() };

    private final Font titleFont = new Font(Font.SANS_SERIF, Font.BOLD, 42);
    private final Font itemFont = new Font(Font.SANS_SERIF, Font.BOLD, 20);
    private final Font helpFont = new Font(Font.MONOSPACED, Font.PLAIN, 14);

    public boolean isHelpVisible() {
        return showHelp;
    }

    /** Xử lý click chuột */
    public Action update(MouseInput m) {
        Point p = m.consumeClick();
        if (p == null)
            return Action.NONE;

        // Nếu đang mở Exit? thì chỉ xử lý Yes/No
        if (exitConfirm) {
            if (yesBtn.contains(p)) {
                Sound.blip();
                exitConfirm = false;
                return Action.EXIT; // Game xử lý thoát
            }
            if (noBtn.contains(p)) {
                Sound.tick();
                exitConfirm = false;
                return Action.NONE;
            }
            // click ra ngoài panel -> đóng
            if (!exitPanel.contains(p)) {
                Sound.tick();
                exitConfirm = false;
            }
            return Action.NONE;
        }

        // Bình thường: click các nút menu
        for (int i = 0; i < items.length; i++) {
            if (btn[i].contains(p)) {
                selected = i;
                Sound.blip();
                if (i == 0)
                    return Action.START;
                if (i == 1) {
                    showHelp = !showHelp;
                    return Action.HELP_TOGGLE;
                }
                if (i == 2) {
                    exitConfirm = true;
                    return Action.NONE;
                } // mở overlay
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

    public void drawMenuBackground(Graphics2D g) {
        BufferedImage img = Assets.MENU_BG;
        if (img != null) {
            double sw = img.getWidth(), sh = img.getHeight();
            double s = Math.max(Constants.WIDTH / sw, Constants.HEIGHT / sh);
            int dw = (int) Math.round(sw * s);
            int dh = (int) Math.round(sh * s);
            int dx = (Constants.WIDTH - dw) / 2;
            int dy = (Constants.HEIGHT - dh) / 2;
            g.drawImage(img, dx, dy, dw, dh, null);
        } else {
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

    /** Vẽ nền + các nút + (tuỳ chọn) Help + Exit? */
    public void render(Graphics2D g, MouseInput mouseInput) {
        this.drawMenuBackground(g);

        int baseY = Constants.HEIGHT / 2 - 20;
        int gap = 120;
        Point mousePos = mouseInput.getPosition();

        for (int i = 0; i < items.length; i++) {
            BufferedImage target = null;
            if (i == 0)
                target = Assets.BUTTON_START;
            else if (i == 1)
                target = Assets.BUTTON_HOW_TO_PLAY;
            else
                target = Assets.BUTTON_EXIT;

            int cy = baseY + i * gap;
            Point mousePosForMenu = exitConfirm ? null : mousePos;
            Rectangle r = Renderer.drawButtonInMenu(
                    g,
                    target,
                    items[i],
                    Constants.WIDTH / 2,
                    cy,
                    Assets.fontPixels_40 != null ? Assets.fontPixels_40 : titleFont,
                    mousePosForMenu,
                    1.00f,
                    1.06f);
            btn[i].setBounds(r);
        }

        if (showHelp)
            drawHelp(g);

        if (exitConfirm)
            drawExitConfirm(g, mouseInput); // luôn vẽ sau cùng (đè lên)
    }

    /** Khung hướng dẫn cũ */
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

    /** Vẽ overlay Exit? với 2 nút YES / NO */
    private void drawExitConfirm(Graphics2D g, MouseInput mouseInput) {
        // nền mờ
        g.setColor(new Color(0, 0, 0, 160));
        g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);

        int cw = Constants.WIDTH / 2;
        int ch = Constants.HEIGHT / 2;

        int panelW = 640;
        int panelH = 300;
        int px = cw - panelW / 2;
        int py = ch - panelH / 2;
        exitPanel.setBounds(px, py, panelW, panelH);

        // khung phong cách sci-fi đơn giản
        g.setColor(new Color(6, 18, 34, 230));
        g.fillRoundRect(px, py, panelW, panelH, 20, 20);
        g.setStroke(new BasicStroke(4f));
        g.setColor(new Color(0, 255, 255, 140));
        g.drawRoundRect(px + 6, py + 6, panelW - 12, panelH - 12, 16, 16);
        g.setFont(Assets.fontPixels_40 != null ? Assets.fontPixels_40.deriveFont(44f) : titleFont.deriveFont(44f));
        FontMetrics fm = g.getFontMetrics();
        String text = "EXIT?";
        int tx = cw - fm.stringWidth(text) / 2;
        int ty = py + 120 + (fm.getAscent() - fm.getDescent()) / 2;
        // viền chữ (stroke)
        g.setColor(Color.BLACK);
        g.drawString(text, tx + 2, ty + 2);
        g.setColor(Color.WHITE);
        g.drawString(text, tx, ty);
        // hai nút YES / NO
        Point mousePos = mouseInput.getPosition();
        int by = py + panelH - 70;
        int yesCx = cw - 110;
        int noCx = cw + 110;

        BufferedImage smallBtn = Assets.BUTTON_START;

        Rectangle rYes = Renderer.drawButtonInMenu(
                g, smallBtn, "YES", yesCx, by,
                Assets.fontPixels_40 != null ? Assets.fontPixels_40 : itemFont,
                mousePos, 0.8f, 0.8f * 1.06f);
        Rectangle rNo = Renderer.drawButtonInMenu(
                g, smallBtn, "NO", noCx, by,
                Assets.fontPixels_40 != null ? Assets.fontPixels_40 : itemFont,
                mousePos, 0.8f, 0.8f * 1.06f);
        yesBtn.setBounds(rYes);
        noBtn.setBounds(rNo);
    }
}

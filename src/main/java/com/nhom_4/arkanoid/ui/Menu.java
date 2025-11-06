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

    private boolean showHelp = false;

    // ====== Exit? overlay ======
    private boolean exitConfirm = false;
    private final Rectangle yesBtn = new Rectangle();
    private final Rectangle noBtn = new Rectangle();
    private final Rectangle exitPanel = new Rectangle(); // bounds của khung Exit?

    // bounds cho 3 nút để bắt click
    private final Rectangle[] btn = { new Rectangle(), new Rectangle(), new Rectangle() };

    private final Font titleFont = new Font(Font.SANS_SERIF, Font.BOLD, 42);

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
                // Sound.blip();
                exitConfirm = false;
                return Action.EXIT; // Game xử lý thoát
            }
            if (noBtn.contains(p)) {
                // Sound.tick();
                exitConfirm = false;
                return Action.NONE;
            }
            // click ra ngoài panel -> đóng
            if (!exitPanel.contains(p)) {
                // Sound.tick();
                exitConfirm = false;
            }
            return Action.NONE;
        }

        // Bình thường: click các nút menu
        for (int i = 0; i < items.length; i++) {
            if (btn[i].contains(p)) {
                // Sound.blip();
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

        int hoveredIndex = -1;
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

            // Xác định vị trí con chuột để đánh dấu hoveredIndex
            if (mousePosForMenu != null && r.contains(mousePosForMenu)) {
                hoveredIndex = i;
            }
        }

        playHoverIfChanged(hoveredIndex);

        if (showHelp)
            drawHelp(g);

        if (exitConfirm)
            drawExitConfirm(g, mouseInput); // luôn vẽ sau cùng (đè lên)
    }

    private static int lastHoverIndex = -1;

    private static void playHoverIfChanged(int current) {
        if (current != lastHoverIndex) {
            if (current != -1) {
                Sound.playMenuHoverSound();
            }
            lastHoverIndex = current;
        }
    }

    private static int lastExitHoverIndex = -1;

    private static void playExitHoverIfChanged(int current) {
        if (current != lastExitHoverIndex) {
            if (current != -1) {
                Sound.playMenuHoverSound();
            }
            lastExitHoverIndex = current;
        }
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

    private void drawExitConfirm(Graphics2D g, MouseInput mouseInput) {
        // nền mờ
        g.setColor(new Color(0, 0, 0, 160));
        g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);

        BufferedImage img = Assets.EXIT_DIALOG; // bạn đã load 900x600
        if (img == null)
            return; // nếu thiếu ảnh thì thôi

        // === Vẽ THEO KÍCH THƯỚC ẢNH GỐC (không scale) ===
        final int iw = img.getWidth(); // ví dụ 900
        final int ih = img.getHeight(); // ví dụ 600
        final int px = Constants.WIDTH / 2 - iw / 2;
        final int py = Constants.HEIGHT / 2 - ih / 2;
        g.drawImage(img, px, py, null);

        // vùng click tổng của panel
        exitPanel.setBounds(px, py, iw, ih);

        Point mousePos = mouseInput.getPosition();

        Rectangle rYes = Renderer.drawButtonInMenu(
                g,
                Assets.BUTTON_SMALL,
                "YES",
                Constants.WIDTH / 2 - 110,
                Constants.HEIGHT / 2 + 60,
                Assets.fontPixels_40 != null ? Assets.fontPixels_40 : titleFont,
                mousePos,
                1.00f,
                1.06f);
        Rectangle rNo = Renderer.drawButtonInMenu(
                g,
                Assets.BUTTON_SMALL,
                "NO",
                Constants.WIDTH / 2 + 100,
                Constants.HEIGHT / 2 + 60,
                Assets.fontPixels_40 != null ? Assets.fontPixels_40 : titleFont,
                mousePos,
                1.00f,
                1.06f);
        yesBtn.setBounds(rYes);
        noBtn.setBounds(rNo);

        int hoveredIndex = -1;
        if (mousePos != null) {
            if (rNo.contains(mousePos)) {
                hoveredIndex = 0;
            } else if (rYes.contains(mousePos)) {
                hoveredIndex = 1;
            }
        }

        playExitHoverIfChanged(hoveredIndex);

        // panel tổng + 2 nút (để click ra ngoài mới đóng)
        Rectangle union = new Rectangle(px, py, iw, ih);
        union.add(rYes);
        union.add(rNo);
        exitPanel.setBounds(union);
    }

}

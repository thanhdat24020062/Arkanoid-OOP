package com.nhom_4.arkanoid.gfx;

import java.awt.*;
import com.nhom_4.arkanoid.config.Constants;

public final class Renderer {
    private Renderer() {}

    public static void drawCenteredString(Graphics2D g, String text, int y, Font font, Color color) {
        if (text == null) return;
        Font old = g.getFont();
        Color oc = g.getColor();
        g.setFont(font);
        g.setColor(color);
        FontMetrics fm = g.getFontMetrics();
        int x = (Constants.WIDTH - fm.stringWidth(text)) / 2;
        g.drawString(text, x, y);
        g.setFont(old);
        g.setColor(oc);
    }

    /** Vẽ nút canh giữa (theo trục X), có highlight khi focus */
    public static void drawButton(Graphics2D g, String label, int centerX, int baselineY, boolean focus, Font font) {
        Font oldF = g.getFont(); Color oldC = g.getColor();
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        int textW = fm.stringWidth(label);
        int textH = fm.getAscent();
        int padX = 24, padY = 12;

        int w = textW + padX*2;
        int h = textH + padY*2;
        int x = centerX - w/2;
        int y = baselineY - textH;

        g.setColor(focus ? new Color(80,110,220) : new Color(60,70,90));
        g.fillRoundRect(x, y - padY, w, h, 16, 16);

        g.setColor(new Color(255,255,255,50));
        g.drawRoundRect(x, y - padY, w, h, 16, 16);

        g.setColor(Color.WHITE);
        g.drawString(label, centerX - textW/2, baselineY + padY/2);

        g.setFont(oldF); g.setColor(oldC);
    }
}

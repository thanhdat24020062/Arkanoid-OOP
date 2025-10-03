package com.yourname.arkanoid.gfx;
import java.awt.*;
import com.yourname.arkanoid.config.Constants;
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
}
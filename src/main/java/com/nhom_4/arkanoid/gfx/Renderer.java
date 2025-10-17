package com.nhom_4.arkanoid.gfx;

import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import com.nhom_4.arkanoid.config.Constants;

public final class Renderer {
    private Renderer() {
    }

    /**
     * Vẽ chữ có viền đen.
     * 
     * @param g2        Graphics2D đang vẽ
     * @param text      nội dung
     * @param fontName  tên font (vd: Font.SANS_SERIF hoặc "Arial")
     * @param fontSize  cỡ chữ (px)
     * @param x         toạ độ X (theo baseline)
     * @param y         toạ độ Y (theo baseline)
     * @param fillColor màu chữ (phần trong)
     */
    public static void renderText(Graphics2D g2,
            String text,
            Font font,
            float x, float y,
            Color fillColor) {
        if (text == null || text.isEmpty())
            return;

        // Khử răng cưa để chữ & viền mượt
        Object oldAA = g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        GlyphVector gv = font.createGlyphVector(g2.getFontRenderContext(), text);
        Shape textShape = gv.getOutline(x, y); // y là baseline!

        // Độ dày viền tỉ lệ theo cỡ chữ (ổn cho đa số font)
        float strokeWidth = Math.max(1f, font.getSize2D() * 0.10f);

        // Lưu trạng thái
        Paint oldPaint = g2.getPaint();
        Stroke oldStroke = g2.getStroke();

        // Viền đen
        g2.setPaint(new Color(0, 0, 0, 255));
        g2.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.draw(textShape);

        // Tô màu chữ
        g2.setPaint(fillColor);
        g2.fill(textShape);

        // Khôi phục
        g2.setPaint(oldPaint);
        g2.setStroke(oldStroke);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldAA);
    }

    // Vẽ 1 nút menu: nền = ảnh, chữ ở giữa; sáng chữ khi hover.
    // mousePos: tọa độ chuột hiện tại (có thể null nếu bạn chưa có), ví dụ từ
    // MouseInput.getPosition()
    public static Rectangle drawButtonInMenu(Graphics2D g,
            BufferedImage bg,
            String text,
            int centerX, int centerY,
            Font font,
            Point mousePos) {
        return drawButtonInMenu(g, bg, text, centerX, centerY, font, mousePos, 1.0f, 1.06f);
    }


    // Bản có scale khi hover (phóng nhẹ để “có hồn”)
    // Giữ nguyên import/đầu hàm của bạn
    public static Rectangle drawButtonInMenu(Graphics2D g,
            BufferedImage bg,
            String text,
            int centerX, int centerY,
            Font font,
            Point mousePos,
            float scaleNormal,
            float scaleHover) {

        if (bg == null)
            return new Rectangle(0, 0, 0, 0);
        if (text == null)
            text = "";

        Object oldInterp = g.getRenderingHint(RenderingHints.KEY_INTERPOLATION);
        Object oldAA = g.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
        Font oldFont = g.getFont();
        Paint oldPaint = g.getPaint();

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Hover check dùng khung "normal"
        int wN = Math.max(1, Math.round(bg.getWidth() * scaleNormal));
        int hN = Math.max(1, Math.round(bg.getHeight() * scaleNormal));
        int xN = centerX - wN / 2;
        int yN = centerY - hN / 2;
        boolean hovered = (mousePos != null && new Rectangle(xN, yN, wN, hN).contains(mousePos));

        // Scale ảnh theo trạng thái
        float scale = hovered ? scaleHover : scaleNormal;
        int w = Math.max(1, Math.round(bg.getWidth() * scale));
        int h = Math.max(1, Math.round(bg.getHeight() * scale));
        int x = centerX - w / 2;
        int y = centerY - h / 2;

        // Vẽ nền ảnh
        g.drawImage(bg, x, y, w, h, null);
        float textScale = hovered ? 1.10f : 1.00f;
        Font fontScaled = font.deriveFont((float) (font.getSize2D()*textScale));

        // strokeWidth phải trùng công thức trong renderText để bounds khớp
        float strokeWidth = Math.max(1f, fontScaled.getSize2D() * 0.10f);

        // Tạo outline chữ tại gốc (0,0)
        java.awt.font.GlyphVector gv = fontScaled.createGlyphVector(g.getFontRenderContext(), text);
        java.awt.Shape outline0 = gv.getOutline();

        // Bounds đã tính CẢ VIỀN
        java.awt.geom.Rectangle2D vb = new java.awt.BasicStroke(
                strokeWidth, java.awt.BasicStroke.CAP_ROUND, java.awt.BasicStroke.JOIN_ROUND)
                .createStrokedShape(outline0).getBounds2D();

        // Đặt tâm chữ đúng (centerX, centerY)
        float bx = (float) (centerX - vb.getCenterX());
        float by = (float) (centerY - vb.getCenterY());

        // Màu chữ (fill)
        Color fillNormal = new Color(255, 255, 255, 230);
        Color fillHover = new Color(255, 225, 0, 255);
        Color fillColor = hovered ? fillHover : fillNormal;

        // (tuỳ chọn) làm tròn để tránh nửa pixel
        bx = Math.round(bx);
        by = Math.round(by);

        // VẼ bằng chính fontScaled (overload nhận Font)
        if (hovered)
            Renderer.renderText(g, text, Assets.fontPixels_44, bx, by, fillColor);
        else {
            Renderer.renderText(g, text, Assets.fontPixels_40, bx, by, fillColor);

        }

        // Restore
        if (oldInterp != null)
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, oldInterp);
        if (oldAA != null)
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldAA);
        g.setFont(oldFont);
        g.setPaint(oldPaint);

        return new Rectangle(x, y, w, h);
    }


}

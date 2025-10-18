package com.nhom_4.arkanoid.util;

import javax.imageio.ImageIO;

import com.nhom_4.arkanoid.gfx.Assets;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.*;

public class Files {
    public static BufferedImage loadImageCP(String cpPath, int targetW, int targetH) {
        try (InputStream in = Files.class.getResourceAsStream(cpPath)) {
            if (in == null)
                return null;

            BufferedImage src = ImageIO.read(in);
            if (src == null)
                return null;

            // Tạo ảnh đích đúng kích thước yêu cầu
            BufferedImage dst = new BufferedImage(targetW, targetH, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = dst.createGraphics();
            // Hint chất lượng để scale mượt
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Vẽ ảnh nguồn vào khung mới (kéo giãn/ép bẹp nếu tỉ lệ khác)
            g2.drawImage(src, 0, 0, targetW, targetH, null);
            g2.dispose();

            return dst;
        } catch (Exception e) {
            return null;
        }
    }

    public static Image loadImage(String pathOrCp) {
        try {
            java.net.URL url = Files.class.getResource(pathOrCp);
            if (url != null) {
                // Dùng ImageIO để load đồng bộ, tránh ảnh “chưa kịp decode”
                BufferedImage bi = ImageIO.read(url);
                if (bi != null)
                    return bi;
            }

            File f = new File(pathOrCp);
            if (f.exists()) {
                BufferedImage bi = ImageIO.read(f);
                if (bi != null)
                    return bi;
            }

            String fsAbs = f.getAbsolutePath();
            throw new IllegalArgumentException(
                    "Không tìm thấy ảnh. Classpath='" + pathOrCp + "', FileSystem='" + fsAbs + "'");

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Font loadFont(String cpPath, int style, float size) {
        try (InputStream is = Assets.class.getResourceAsStream(cpPath)) {
            if (is == null) {
                throw new IllegalArgumentException("Font not found on classpath: " + cpPath);
            }
            Font base = Font.createFont(Font.TRUETYPE_FONT, is);
            return base.deriveFont(style, size);
        } catch (Exception e) {
            System.err.println("[Assets] Failed to load font " + cpPath + " -> " + e);
            return new Font(Font.SANS_SERIF, style, Math.round(size));
        }
    }
}

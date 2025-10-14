package com.nhom_4.arkanoid.audio;

/** Âm đơn giản cho menu; sau này có thể thay bằng phát .wav */
public final class Sound {
    private Sound() {}
    public static void tick() { // đổi mục
        try { java.awt.Toolkit.getDefaultToolkit().beep(); } catch (Exception ignored) {}
    }
    public static void blip() { // xác nhận/chọn
        try { java.awt.Toolkit.getDefaultToolkit().beep(); } catch (Exception ignored) {}
    }
}

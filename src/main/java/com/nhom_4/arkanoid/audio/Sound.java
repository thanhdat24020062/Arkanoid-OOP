package com.nhom_4.arkanoid.audio;

// Stub: có thể dùng javax.sound.sampled để phát .wav
public final class Sound {
    private Sound() {
    }

    public static void beep() {
        try {
            java.awt.Toolkit.getDefaultToolkit().beep();
        } catch (Exception ignored) {
        }
    }
}
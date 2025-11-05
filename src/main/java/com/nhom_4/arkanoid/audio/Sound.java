package com.nhom_4.arkanoid.audio;

import javax.sound.sampled.*;
import java.util.Objects;

public final class Sound {
    private static Clip[] boundClips = new Clip[5];
    private static Clip[] breakClips = new Clip[3];
    private static Clip[] shootClips = new Clip[3];

    private Sound() {}

    public static void init() {
        for (int i = 0; i < boundClips.length; i++) {
            boundClips[i] = loadClip("/sounds/BoundSound.wav");
        }
        for (int i = 0; i < breakClips.length; i++) {
            breakClips[i] = loadClip("/sounds/BreakSound.wav");
        }
        for (int i = 0; i < shootClips.length; i++) {
            shootClips[i] = loadClip("/sounds/ShootSound.wav");
        }
    }

    private static Clip loadClip(String path) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(Objects.requireNonNull(Sound.class.getResourceAsStream(path)));
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            return clip;
        } catch (Exception e) {
            System.err.println("Không thể load âm thanh: " + path);
            return null;
        }
    }

    private static void play(Clip[] clips) {
        if (clips == null) return;
        for (Clip clip : clips) {
            if (!clip.isRunning()) {
                clip.setFramePosition(0);
                clip.start();
                return;
            }
        }
        // Nếu tất cả đang chạy, dùng clip đầu tiên và reset
        clips[0].stop();
        clips[0].setFramePosition(0);
        clips[0].start();
    }

    public static void playBoundSound() {
        play(boundClips);
    }

    public static void playBreakSound() {
        play(breakClips);
    }

    public static void playShootSound() {
        play(shootClips);
    }

    public static void tick() { // đổi mục
        try { java.awt.Toolkit.getDefaultToolkit().beep(); } catch (Exception ignored) {}
    }
    public static void blip() { // xác nhận/chọn
        try { java.awt.Toolkit.getDefaultToolkit().beep(); } catch (Exception ignored) {}
    }
}

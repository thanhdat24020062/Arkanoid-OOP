package com.nhom_4.arkanoid.audio;

import javax.sound.sampled.Clip;

public final class Sound extends Audio {
    private static final Clip[] boundClips = new Clip[5];
    private static final Clip[] breakClips = new Clip[3];
    private static final Clip[] shootClips = new Clip[3];
    private static final Clip[] metalClips = new Clip[3];
    private static final Clip[] menuHoverClips = new Clip[5];
    private static Clip gainLifeClip;
    private static Clip gainShootClip;
    private static Clip gainPowerClip;
    private static Clip popClip;
    private static Clip gameOverClip;

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
        for (int i = 0; i < metalClips.length; i++) {
            metalClips[i] = loadClip("/sounds/MetalSound.wav");
        }
        for (int i = 0; i < menuHoverClips.length; i++) {
            menuHoverClips[i] = loadClip("/sounds/BoundSound.wav");
        }
        gainLifeClip = loadClip("/sounds/GainLifeSound.wav");
        gainShootClip = loadClip("/sounds/GainShootSound.wav");
        gainPowerClip = loadClip("/sounds/GainPowerSound.wav");
        popClip = loadClip("/sounds/PopSound.wav");
        gameOverClip = loadClip("/sounds/GameOverSound.wav");

        if (boundClips[0] != null) {
            boundClips[0].start();
            boundClips[0].setFramePosition(0);
            boundClips[0].stop();
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
        //Nếu tất cả đang chạy, dùng clip đầu tiên và reset
        clips[0].stop();
        clips[0].setFramePosition(0);
        clips[0].start();
    }

    private static void play(Clip clip) {
        if (clip != null && !clip.isRunning()) {
            clip.setFramePosition(0);
            clip.start();
        }
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

    public static void playMetalSound() {
        play(metalClips);
    }

    public static void playMenuHoverSound() {
        play(menuHoverClips);
    }

    public static void playGainLifeSound() {
        play(gainLifeClip);
    }

    public static void playGainShootSound() {
        play(gainShootClip);
    }

    public static void playGainPowerSound() {
        play(gainPowerClip);
    }

    public static void playPopSound() {
        play(popClip);
    }

    public static void playGameOverSound() {
        play(gameOverClip);
    }
}

package com.nhom_4.arkanoid.audio;

import javax.sound.sampled.Clip;

public final class Music extends Audio {
    private static Clip menuClip;
    private static Clip playingClip;

    private Music() {}

    public static void init() {
        menuClip = loadClip("/sounds/MenuMusic.wav");
        playingClip = loadClip("/sounds/PlayingMusic1.wav");
    }

    private static void play(Clip clip) {
        if (clip != null && !clip.isRunning()) {
            clip.setFramePosition(0);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    private static void stop(Clip clip) {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public static void playMenuMusic() {
        play(menuClip);
    }

    public static void stopMenuMusic() {
        stop(menuClip);
    }

    public static void playPlayingMusic() {
        play(playingClip);
    }

    public static void stopPlayingMusic() {
        stop(playingClip);
    }
}
package com.nhom_4.arkanoid.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class Music {
    private static final ExecutorService musicPool = Executors.newSingleThreadExecutor();

    private static Clip menuClip;
    private static Clip playingClip;

    private Music() {}

    public static void init() {
        menuClip = loadClip("/sounds/MenuMusic.wav");
        playingClip = loadClip("/sounds/PlayingMusic1.wav");
    }

    private static Clip loadClip(String path) {
        try {
            URL url = Music.class.getResource(path);
            if (url == null) {
                System.err.println("Không thể load âm thanh: " + path);
                return null;
            }

            try (AudioInputStream ais = AudioSystem.getAudioInputStream(url)) {
                Clip clip = AudioSystem.getClip();
                clip.open(ais);
                return clip;
            }
        } catch (Exception e) {
            System.err.println("Không thể load âm thanh: " + path);
            e.printStackTrace();
            return null;
        }
    }

    private static void play(Clip clip, boolean loop) {
        if (clip == null) return;
        musicPool.submit(() -> {
            if (!clip.isRunning()) {
                clip.setFramePosition(0);
                if (loop) {
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                } else {
                    clip.start();
                }
            }
        });
    }

    private static void stop(Clip clip) {
        if (clip == null) return;
        musicPool.submit(() -> {
            if (clip.isRunning()) {
                clip.stop();
            }
        });
    }

    public static void playMenuMusic() {
        play(menuClip, true);
    }

    public static void stopMenuMusic() {
        stop(menuClip);
    }

    public static void playPlayingMusic() {
        play(playingClip, true);
    }

    public static void stopPlayingMusic() {
        stop(playingClip);
    }

    public static void shutdown() {
        musicPool.shutdownNow();
    }
}
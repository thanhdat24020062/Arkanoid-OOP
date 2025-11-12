package com.nhom_4.arkanoid.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class Sound {
    private static final ExecutorService audioPool = Executors.newFixedThreadPool(5);

    private static final String BASE_PATH = "/sounds/";

    private static final String BOUND = BASE_PATH + "BoundSound.wav";
    private static final String BREAK = BASE_PATH + "BreakSound.wav";
    private static final String SHOOT = BASE_PATH + "ShootSound.wav";
    private static final String METAL = BASE_PATH + "MetalSound.wav";
    private static final String MENU_HOVER = BASE_PATH + "BoundSound.wav";
    private static final String EXPLOSION = BASE_PATH + "ExplosionSound.wav";
    private static final String GAIN_LIFE = BASE_PATH + "GainLifeSound.wav";
    private static final String GAIN_SHOOT = BASE_PATH + "GainShootSound.wav";
    private static final String GAIN_POWER = BASE_PATH + "GainPowerSound.wav";
    private static final String POP = BASE_PATH + "PopSound.wav";
    private static final String GAME_OVER = BASE_PATH + "GameOverSound.wav";

    private Sound() {
    }

    private static void play(String soundPath) {
        audioPool.submit(() -> {
            try (AudioInputStream ais = AudioSystem.getAudioInputStream(
                    Objects.requireNonNull(Sound.class.getResource(soundPath)))) {

                Clip clip = AudioSystem.getClip();
                clip.open(ais);
                clip.start();

            } catch (Exception e) {
                System.err.println("Failed to play sound: " + soundPath);
                e.printStackTrace();
            }
        });
    }

    public static void playBoundSound() {
        play(BOUND);
    }

    public static void playBreakSound() {
        play(BREAK);
    }

    public static void playShootSound() {
        play(SHOOT);
    }

    public static void playMetalSound() {
        play(METAL);
    }

    public static void playMenuHoverSound() {
        play(MENU_HOVER);
    }

    public static void playExplosionSound() {
        play(EXPLOSION);
    }

    public static void playGainLifeSound() {
        play(GAIN_LIFE);
    }

    public static void playGainShootSound() {
        play(GAIN_SHOOT);
    }

    public static void playGainPowerSound() {
        play(GAIN_POWER);
    }

    public static void playPopSound() {
        play(POP);
    }

    public static void playGameOverSound() {
        play(GAME_OVER);
    }

    public static void shutdown() {
        audioPool.shutdown();
    }
}

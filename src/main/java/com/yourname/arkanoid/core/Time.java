package com.yourname.arkanoid.core;

public final class Time {
    private Time() {
    }

    private static long lastNs = System.nanoTime();
    private static double fps;

    public static double tickAndGetDelta(double targetUPS) {
        long now = System.nanoTime();
        double dt = (now - lastNs) / 1_000_000_000.0;
        lastNs = now;
        // lock-step theo targetUPS (nếu muốn), ở đây trả dt thực để linh hoạt
        return Math.min(dt, 0.05); // clamp tránh giật
    }

    public static void setFPS(double fpsValue) {
        fps = fpsValue;
    }

    public static double getFPS() {
        return fps;
    }
}
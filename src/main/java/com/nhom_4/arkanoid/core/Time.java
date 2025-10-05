package com.nhom_4.arkanoid.core;

public final class Time {
    private Time() {// ko cho khởi tạo đối tượng Time 
    }

    private static long lastNs = System.nanoTime();// mốc thời gian hiện tại 
    private static double fps;

    /**
     *  tính khoảng thời gian giữa lần gọi hàm trước và hiện tại
     * @param targetUPS // điều chỉnh số lần uppdate logic game mỗi giây (hiện tại chưa dùng)
     * @return
     */
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
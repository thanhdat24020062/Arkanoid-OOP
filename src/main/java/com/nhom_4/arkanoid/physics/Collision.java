package com.nhom_4.arkanoid.physics;

import java.util.List;

import com.nhom_4.arkanoid.audio.Sound;
import com.nhom_4.arkanoid.config.Constants;
import com.nhom_4.arkanoid.entity.Ball;

public final class Collision {
    private Collision() {
    }

    public static void reflectBallOnWalls(Ball ball) {
        // trái/phải
        if (ball.getX() - ball.getR() < 0) {
            ball.setX(ball.getR());
            ball.setVx(Math.abs(ball.getVx()));
            Sound.playBoundSound();
        } else if (ball.getX() + ball.getR() > Constants.WIDTH) {
            ball.setX(Constants.WIDTH - ball.getR());
            ball.setVx(-Math.abs(ball.getVx()));
            Sound.playBoundSound();
        }
        // top
        if (ball.getY() - ball.getR() < 0) {
            ball.setY(ball.getR());
            ball.setVy(Math.abs(ball.getVy()));
            Sound.playBoundSound();
        }
    }

    // --- Thêm phương thức phản xạ cho nhiều bóng ---
    public static void reflectBallOnWallsList(List<Ball> balls) {
        for (Ball ball : balls) {
            reflectBallOnWalls(ball);
        }
    }
}
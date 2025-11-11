package com.nhom_4.arkanoid.physics;

import java.util.List;

import com.nhom_4.arkanoid.audio.Sound;
import com.nhom_4.arkanoid.config.Constants;
import com.nhom_4.arkanoid.entity.Ball;

import static com.nhom_4.arkanoid.config.Constants.*;

public final class Collision {
    private Collision() {
    }

    public static void reflectBallOnWalls(Ball ball) {
        // trái/phải
        if (ball.getX() - ball.getR() < WALL_THICK) {
            ball.setX(ball.getR() + WALL_THICK);
            ball.setVx(Math.abs(ball.getVx()));
            Sound.playBoundSound();
        } else if (ball.getX() + ball.getR() > Constants.WIDTH - WALL_THICK) {
            ball.setX(Constants.WIDTH - WALL_THICK - ball.getR());
            ball.setVx(-Math.abs(ball.getVx()));
            Sound.playBoundSound();
        }
        // top
        if (ball.getY() - ball.getR() < TOP_WALL) {
            ball.setY(ball.getR() + TOP_WALL);
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
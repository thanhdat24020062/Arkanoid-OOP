package com.nhom_4.arkanoid.physics;

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
        } else if (ball.getX() + ball.getR() > Constants.WIDTH) {
            ball.setX(Constants.WIDTH - ball.getR());
            ball.setVx(-Math.abs(ball.getVx()));
        }
        // top
        if (ball.getY() - ball.getR() < 0) {
            ball.setY(ball.getR());
            ball.setVy(Math.abs(ball.getVy()));
        }
    }
}
package com.yourname.arkanoid.physics;

import com.yourname.arkanoid.entity.Ball;
import com.yourname.arkanoid.config.Constants;

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
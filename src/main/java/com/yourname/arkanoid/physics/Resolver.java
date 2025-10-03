package com.yourname.arkanoid.physics;

import java.awt.geom.*;
import com.yourname.arkanoid.entity.*;

public final class Resolver {
    private Resolver() {
    }

    public static void resolveBallBrick(Ball ball, Brick br) {
        Rectangle2D.Double rct = br.getRect();
        // tính overlap tối thiểu theo trục
        double overlapLeft = (ball.getX() + ball.getR()) - rct.x;
        double overlapRight = (rct.x + rct.width) - (ball.getX() - ball.getR());
        double overlapTop = (ball.getY() + ball.getR()) - rct.y;
        double overlapBottom = (rct.y + rct.height) - (ball.getY() - ball.getR());
        double minX = Math.min(overlapLeft, overlapRight);
        double minY = Math.min(overlapTop, overlapBottom);

        if (minX < minY) {
            if (overlapLeft < overlapRight) {
                ball.setX(rct.x - ball.getR() - 0.1);
                ball.setVx(-Math.abs(ball.getVx()));
            } else {
                ball.setX(rct.x + rct.width + ball.getR() + 0.1);
                ball.setVx(Math.abs(ball.getVx()));
            }
        } else {
            if (overlapTop < overlapBottom) {
                ball.setY(rct.y - ball.getR() - 0.1);
                ball.setVy(-Math.abs(ball.getVy()));
            } else {
                ball.setY(rct.y + rct.height + ball.getR() + 0.1);
                ball.setVy(Math.abs(ball.getVy()));
            }
        }
    }
}
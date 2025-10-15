package com.nhom_4.arkanoid.physics;

import java.awt.geom.Rectangle2D;
import com.nhom_4.arkanoid.entity.Ball;
import com.nhom_4.arkanoid.entity.Brick;

public class Resolver {
    public static void resolveBallBrick(Ball ball, Brick brick) {
        Rectangle2D.Double ballRect = ball.getRect();
        Rectangle2D.Double brickRect = brick.getRect();

        // Tìm vùng giao nhau giữa bóng và gạch
        Rectangle2D.Double intersection = new Rectangle2D.Double();
        Rectangle2D.intersect(ballRect, brickRect, intersection);

        // Nếu chiều rộng vùng giao nhau nhỏ hơn chiều cao
        // -> va chạm xảy ra ở cạnh trái hoặc phải của gạch
        if (intersection.width < intersection.height) {
            // Đảo ngược vận tốc X
            ball.setVx(-ball.getVx());

            // Đẩy nhẹ quả bóng ra khỏi gạch theo chiều ngang
            if (ball.getX() < brick.centerX()) { // Bóng ở bên trái gạch
                ball.setX(ball.getX() - intersection.width);
            } else { // Bóng ở bên phải gạch
                ball.setX(ball.getX() + intersection.width);
            }

        } else { // Ngược lại -> va chạm xảy ra ở cạnh trên hoặc dưới của gạch
            // Đảo ngược vận tốc Y
            ball.setVy(-ball.getVy());

            // Đẩy nhẹ quả bóng ra khỏi gạch theo chiều dọc
            if (ball.getY() < brick.centerY()) { // Bóng ở bên trên gạch
                ball.setY(ball.getY() - intersection.height);
            } else { // Bóng ở bên dưới gạch
                ball.setY(ball.getY() + intersection.height);
            }
        }
    }
}
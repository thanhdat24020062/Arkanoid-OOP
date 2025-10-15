package com.nhom_4.arkanoid.core;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;

import com.nhom_4.arkanoid.config.Constants;
import com.nhom_4.arkanoid.entity.*;
import com.nhom_4.arkanoid.input.KeyInput;
import com.nhom_4.arkanoid.level.*;
import com.nhom_4.arkanoid.physics.*;
import com.nhom_4.arkanoid.ui.*;

public class Game {
    private GameState state = GameState.MENU;
    private final HUD hud = new HUD();
    private final Screens screens = new Screens();
    private final PowerUpManager powerUpManager = new PowerUpManager();
    private final List<Bullet> bullets = new ArrayList<>();

    private final LevelManager levelManager = new LevelManager();

    private Paddle paddle;
    private Ball ball;
    private java.util.List<Brick> bricks;

    private KeyInput keys;

    public void setFps(int fps) {
        hud.setFps(fps);
    }

    public void bindInput(KeyInput k) {
        this.keys = k;
    }

    public Game() {
        startNewGame();
    }

    private void startNewGame() {
        hud.reset();
        levelManager.reset();
        powerUpManager.reset();
        bullets.clear();
        loadCurrentLevel();
        state = GameState.MENU;
    }

    private void loadCurrentLevel() {
        Level lvl = levelManager.getCurrentLevel();
        this.bricks = lvl.spawnBricks();
        bullets.clear();

        double pw = Constants.PADDLE_WIDTH;
        paddle = new Paddle(Constants.WIDTH / 2.0 - pw / 2.0,
                Constants.HEIGHT - 50, pw, Constants.PADDLE_HEIGHT, Constants.PADDLE_SPEED);

        ball = new Ball(paddle.centerX(), paddle.getY() - Constants.BALL_RADIUS - 2, Constants.BALL_RADIUS);
        ball.stickToPaddle(true);

        // Tắt các hiệu ứng khi qua màn mới
        paddle.deactivateLasers();
        ball.deactivateFireball();
        powerUpManager.reset();
    }

    public void update(double dt) {
        switch (state) {
            case MENU:
            case PAUSED:
                if (keys.consumeSpace())
                    state = GameState.PLAYING;
                break;
            case GAME_OVER:
            case YOU_WIN:
                if (keys.isPressedR())
                    startNewGame();
                break;
            case PLAYING:
                // Cập nhật paddle
                double dir = (keys.isLeft() ? -1 : 0) + (keys.isRight() ? 1 : 0);
                paddle.update(dt, dir);

                // Cập nhật bóng và xử lý bắn/phóng
                if (ball.isSticking()) {
                    // Nếu bóng đang dính, Space sẽ phóng bóng
                    ball.setX(paddle.centerX());
                    ball.setY(paddle.getY() - ball.getR() - 2);
                    if (keys.consumeSpace()) {
                        ball.launchRandomUp();
                    }
                } else {
                    // Nếu bóng đang bay, Space sẽ bắn súng
                    if (paddle.hasLasers()) {
                        List<Bullet> newBullets = paddle.shoot();
                        if (newBullets != null) {
                            bullets.addAll(newBullets);
                        }
                    }
                    ball.update(dt);
                }

                // Cập nhật power-ups và đạn
                powerUpManager.update(dt, paddle, hud, ball);
                handleBullets(dt);

                // Xử lý va chạm
                Collision.reflectBallOnWalls(ball);
                handleBricks();
                handlePaddleCollision();

                // Kiểm tra thắng/thua
                checkWinLoseConditions();

                // Tạm dừng
                if (keys.isPressedP())
                    state = GameState.PAUSED;
                if (keys.isPressedR())
                    startNewGame();
                break;
        }
    }

    private void handlePaddleCollision() {
        if (!ball.isSticking() && ball.getRect().intersects(paddle.getRect())) {
            ball.setY(paddle.getY() - ball.getR() - 0.1);
            ball.setVy(-Math.abs(ball.getVy()));
            double hit = (ball.getX() - paddle.centerX()) / (paddle.getW() / 2.0);
            ball.setVx(ball.getVx() + hit * 180);
            ball.limitSpeed(Constants.BALL_SPEED_CAP);
        }
    }

    private void checkWinLoseConditions() {
        // Thua
        if (ball.getY() - ball.getR() > Constants.HEIGHT) {
            hud.loseLife();
            if (hud.getLives() <= 0) {
                state = GameState.GAME_OVER;
            } else {
                // Hồi sinh bóng và paddle, tắt mọi hiệu ứng
                ball.deactivateFireball();
                paddle.deactivateLasers();
                ball.stickToPaddle(true);
                ball.setVx(0);
                ball.setVy(0);
                paddle.setX(Constants.WIDTH / 2.0 - paddle.getW() / 2.0);
            }
        }
        // Thắng
        if (allCleared()) {
            if (levelManager.nextLevel()) {
                loadCurrentLevel();
                state = GameState.PAUSED;
            } else {
                state = GameState.YOU_WIN;
            }
        }
    }

    private void handleBullets(double dt) {
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            bullet.update(dt);

            boolean hit = false;
            for (Brick brick : bricks) {
                if (brick.isAlive() && bullet.getRect().intersects(brick.getRect())) {
                    brick.destroy();
                    hud.addScore(50);
                    hit = true;
                    break;
                }
            }

            if (!bullet.isActive() || hit) {
                bulletIterator.remove();
            }
        }
    }

    private void handleBricks() {
        Rectangle2D.Double brect = ball.getRect();
        for (Brick br : bricks) {
            if (!br.isAlive())
                continue;

            if (brect.intersects(br.getRect())) {
                if (ball.isFireball()) {
                    br.destroy();
                    hud.addScore(50);
                } else {
                    Resolver.resolveBallBrick(ball, br);
                    boolean destroyed = br.hit();
                    if (destroyed) {
                        hud.addScore(50);
                        powerUpManager.spawnPowerUp(br.centerX(), br.centerY());
                    } else {
                        hud.addScore(10);
                    }
                    ball.speedUp(Constants.BALL_SPEEDUP_MUL, Constants.BALL_SPEED_CAP);
                }
                break;
            }
        }
    }

    private boolean allCleared() {
        for (Brick b : bricks)
            if (b.isAlive())
                return false;
        return true;
    }

    public void render(Graphics2D g) {
        screens.drawBackground(g);
        screens.drawWalls(g);

        for (Brick br : bricks)
            if (br.isAlive())
                br.render(g);

        paddle.render(g);
        ball.render(g);

        for (Bullet b : bullets) {
            b.render(g);
        }

        powerUpManager.render(g);
        hud.render(g);

        // Overlay trạng thái game
        switch (state) {
            case MENU:
                screens.drawCenterText(g, "ARKANOID", "Press SPACE to start");
                break;
            case PAUSED:
                screens.drawCenterText(g, "PAUSED", "Press SPACE to resume");
                break;
            case GAME_OVER:
                screens.drawCenterText(g, "GAME OVER", "Press R to restart");
                break;
            case YOU_WIN:
                screens.drawCenterText(g, "YOU WIN!", "Press R to play again");
                break;
        }
    }
}
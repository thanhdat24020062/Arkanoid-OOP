package com.nhom_4.arkanoid.core;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;

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

    private final LevelManager levelManager = new LevelManager();

    private Paddle paddle;
    private Ball ball;
    private java.util.List<Brick> bricks;

    private KeyInput keys;

    public void bindInput(KeyInput k) {
        this.keys = k;
    }

    public Game() {
        startNewGame();
    }

    private void startNewGame() {
        hud.reset();
        levelManager.reset();
        loadCurrentLevel();
        state = GameState.MENU;
    }

    private void loadCurrentLevel() {
        Level lvl = levelManager.getCurrentLevel();
        this.bricks = lvl.spawnBricks();
        double pw = Constants.PADDLE_WIDTH;
        paddle = new Paddle(Constants.WIDTH / 2.0 - pw / 2.0,
                Constants.HEIGHT - 50, pw, Constants.PADDLE_HEIGHT, Constants.PADDLE_SPEED);
        ball = new Ball(paddle.centerX(), paddle.getY() - Constants.BALL_RADIUS - 2, Constants.BALL_RADIUS);
        ball.stickToPaddle(true);
    }

    public void update(double dt) {
        switch (state) {
            case MENU:
                if (keys.consumeSpace())
                    state = GameState.PLAYING;
                break;
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
                // input → paddle
                double dir = (keys.isLeft() ? -1 : 0) + (keys.isRight() ? 1 : 0);
                paddle.update(dt, dir);

                // ball
                if (ball.isSticking()) {
                    ball.setX(paddle.centerX());
                    ball.setY(paddle.getY() - ball.getR() - 2);
                    if (keys.consumeSpace()) {
                        ball.launchRandomUp();
                    }
                } else {
                    ball.update(dt);
                }

                // walls
                Collision.reflectBallOnWalls(ball);

                // paddle collision
                if (!ball.isSticking() && ball.getRect().intersects(paddle.getRect())) {
                    ball.setY(paddle.getY() - ball.getR() - 0.1);
                    ball.setVy(-Math.abs(ball.getVy()));
                    double hit = (ball.getX() - paddle.centerX()) / (paddle.getW() / 2.0); // -1..1
                    ball.setVx(ball.getVx() + hit * 180);
                    ball.limitSpeed(Constants.BALL_SPEED_CAP);
                }

                // bricks
                handleBricks();

                // lose
                if (ball.getY() - ball.getR() > Constants.HEIGHT) {
                    hud.loseLife();
                    if (hud.getLives() <= 0) {
                        state = GameState.GAME_OVER;
                    } else {
                        ball.stickToPaddle(true);
                        ball.setVx(0);
                        ball.setVy(0);
                        paddle.setX(Constants.WIDTH / 2.0 - paddle.getW() / 2.0);
                    }
                }
                // win level
                if (allCleared()) {
                    if (levelManager.nextLevel()) {
                        loadCurrentLevel();
                        state = GameState.PAUSED; // nghỉ 1 nhịp
                    } else {
                        state = GameState.YOU_WIN;
                    }
                }

                // pause
                if (keys.isPressedP())
                    state = GameState.PAUSED;
                if (keys.isPressedR())
                    startNewGame();
                break;
        }
    }

    private boolean allCleared() {
        for (Brick b : bricks)
            if (b.isAlive())
                return false;
        return true;
    }

    private void handleBricks() {
        Rectangle2D.Double brect = ball.getRect();
        for (Brick br : bricks) {
            if (!br.isAlive())
                continue;
            if (brect.intersects(br.getRect())) {
                Resolver.resolveBallBrick(ball, br);
                boolean destroyed = br.hit();
                if (destroyed)
                    hud.addScore(50);
                else
                    hud.addScore(10);
                ball.speedUp(Constants.BALL_SPEEDUP_MUL, Constants.BALL_SPEED_CAP);
                brect = ball.getRect(); // refresh sau khi thay đổi vị trí/vel
            }
        }
    }

    public void render(Graphics2D g) {
        // nền + tường
        screens.drawBackground(g);
        screens.drawWalls(g);

        // bricks
        for (Brick br : bricks)
            if (br.isAlive())
                br.render(g);

        // paddle + ball
        paddle.render(g);
        ball.render(g);

        // HUD
        hud.render(g);

        // overlay trạng thái
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
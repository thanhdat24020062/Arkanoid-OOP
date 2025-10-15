package com.nhom_4.arkanoid.core;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.nhom_4.arkanoid.config.Constants;
import com.nhom_4.arkanoid.entity.Ball;
import com.nhom_4.arkanoid.entity.Brick;
import com.nhom_4.arkanoid.entity.Bullet;
import com.nhom_4.arkanoid.entity.Paddle;
import com.nhom_4.arkanoid.entity.PowerUpManager;
import com.nhom_4.arkanoid.input.KeyInput;
import com.nhom_4.arkanoid.physics.Collision;
import com.nhom_4.arkanoid.physics.Resolver;
import com.nhom_4.arkanoid.ui.HUD;
import com.nhom_4.arkanoid.ui.Screens;

public class Game {
    private GameState state = GameState.MENU;
    private final HUD hud = new HUD();
    private final Screens screens = new Screens();
    private final PowerUpManager powerUpManager = new PowerUpManager();
    private final List<Bullet> bullets = new ArrayList<>();

    // --- LOGIC LEVEL ĐƠN GIẢN HÓA ---
    private List<int[][]> levelMaps;
    private int currentLevelIndex;
    // ------------------------------------

    private Paddle paddle;
    private Ball ball;
    private List<Brick> bricks;
    private KeyInput keys;

    public Game() {
        loadAllLevelMaps(); // Tải tất cả các map khi khởi tạo
        startNewGame();
    }

    // --- CÁC PHƯƠNG THỨC QUẢN LÝ LEVEL MỚI ---
    private void loadAllLevelMaps() {
        levelMaps = new ArrayList<>();

        // Map 1: Một vài hàng gạch
        levelMaps.add(new int[][] {
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2 },
                { 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1 },
                { 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2 },
                { 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2 },
                { 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2 },
        });

        // Map 2: Map bàn cờ
        levelMaps.add(new int[][] {
                { 9, 0, 2, 0, 2, 0, 2, 0, 2, 0, 9, 0 },
                { 0, 9, 0, 1, 0, 1, 0, 1, 0, 9, 0, 9 },
                { 9, 0, 2, 0, 2, 0, 2, 0, 2, 0, 9, 0 },
                { 0, 9, 0, 1, 0, 1, 0, 1, 0, 9, 0, 9 },
        });
    }

    private List<Brick> spawnBricksForCurrentLevel() {
        List<Brick> newBricks = new ArrayList<>();
        int[][] currentMap = levelMaps.get(currentLevelIndex);

        List<String> brickImagePaths = List.of(
                "res/images/brick_blue.png",
                "res/images/brick_cyan.png",
                "res/images/brick_gold.png",
                "res/images/brick_orange.png",
                "res/images/brick_silver_2.png");
        Random random = new Random();

        double playableWidth = Constants.WIDTH - (2 * Constants.WALL_THICK);
        int numColumns = currentMap[0].length;
        double brickWidth = playableWidth / numColumns;
        double brickHeight = brickWidth / 2.5;

        for (int i = 0; i < currentMap.length; i++) {
            for (int j = 0; j < currentMap[i].length; j++) {
                int health = currentMap[i][j];
                if (health == 0)
                    continue;

                double brickX = Constants.WALL_THICK + j * brickWidth;
                double brickY = Constants.TOP_OFFSET + i * brickHeight;

                String randomImagePath = brickImagePaths.get(random.nextInt(brickImagePaths.size()));

                newBricks.add(new Brick(brickX, brickY, brickWidth, brickHeight, health, randomImagePath));
            }
        }
        return newBricks;
    }

    private boolean nextLevel() {
        currentLevelIndex++;
        return currentLevelIndex < levelMaps.size();
    }
    // -----------------------------------------

    private void startNewGame() {
        hud.reset();
        powerUpManager.reset();
        bullets.clear();
        currentLevelIndex = 0; // Bắt đầu từ màn 1
        loadCurrentLevel();
        state = GameState.MENU;
    }

    private void loadCurrentLevel() {
        this.bricks = spawnBricksForCurrentLevel();
        bullets.clear();

        double pw = Constants.PADDLE_WIDTH;
        paddle = new Paddle(Constants.WIDTH / 2.0 - pw / 2.0, Constants.HEIGHT - 50, pw, Constants.PADDLE_HEIGHT,
                Constants.PADDLE_SPEED);
        ball = new Ball(paddle.centerX(), paddle.getY() - Constants.BALL_RADIUS - 2, Constants.BALL_RADIUS);

        resetAndStickBall();
    }

    private void resetAndStickBall() {
        paddle.deactivateLasers();
        ball.deactivateFireball();
        ball.stickToPaddle(true);
        ball.setVx(0);
        ball.setVy(0);
        paddle.setX(Constants.WIDTH / 2.0 - paddle.getW() / 2.0);
    }

    // --- PHẦN CÒN LẠI CỦA FILE GAME.JAVA ---
    // (Toàn bộ các hàm update, render, và các hàm xử lý khác giữ nguyên như file
    // hoàn chỉnh cuối cùng tôi đã gửi)
    // Dưới đây là các hàm đó để bạn tiện copy & paste.

    public void bindInput(KeyInput k) {
        this.keys = k;
    }

    public void setFps(int fps) {
        hud.setFps(fps);
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
                updatePlayingState(dt);
                break;
        }
    }

    private void updatePlayingState(double dt) {
        double dir = (keys.isLeft() ? -1 : 0) + (keys.isRight() ? 1 : 0);
        paddle.update(dt, dir);

        if (ball.isSticking()) {
            ball.setX(paddle.centerX());
            ball.setY(paddle.getY() - ball.getR() - 2);
            if (keys.consumeSpace())
                ball.launchRandomUp();
        } else {
            if (paddle.hasLasers()) {
                List<Bullet> newBullets = paddle.shoot();
                if (newBullets != null)
                    bullets.addAll(newBullets);
            }
            ball.update(dt);
        }

        powerUpManager.update(dt, paddle, hud, ball);
        handleBullets(dt);

        Collision.reflectBallOnWalls(ball);
        handleBricks();
        handlePaddleCollision();

        checkWinLoseConditions();

        if (keys.isPressedP())
            state = GameState.PAUSED;
        if (keys.isPressedR())
            startNewGame();
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
        if (ball.getY() - ball.getR() > Constants.HEIGHT) {
            hud.loseLife();
            if (hud.getLives() <= 0)
                state = GameState.GAME_OVER;
            else
                resetAndStickBall();
        }
        if (allCleared()) {
            if (nextLevel()) {
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
            if (!bullet.isActive() || hit)
                bulletIterator.remove();
        }
    }

    private void handleBricks() {
        Rectangle2D.Double ballRect = ball.getRect();
        for (Brick br : bricks) {
            if (!br.isAlive())
                continue;

            if (ballRect.intersects(br.getRect())) {
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
        screens.drawDeathLine(g);

        for (Brick br : bricks)
            if (br.isAlive())
                br.render(g);

        paddle.render(g);
        ball.render(g);

        for (Bullet b : bullets)
            b.render(g);

        powerUpManager.render(g);
        hud.render(g);

        renderOverlay(g);
    }

    private void renderOverlay(Graphics2D g) {
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
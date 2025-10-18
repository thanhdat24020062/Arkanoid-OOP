package com.nhom_4.arkanoid.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.awt.Image;
import com.nhom_4.arkanoid.ui.Menu;

import com.nhom_4.arkanoid.config.Constants;
import com.nhom_4.arkanoid.entity.Ball;
import com.nhom_4.arkanoid.entity.Brick;
import com.nhom_4.arkanoid.entity.Bullet;
import com.nhom_4.arkanoid.entity.Paddle;
import com.nhom_4.arkanoid.entity.PowerUpManager;
import com.nhom_4.arkanoid.gfx.Assets;
import com.nhom_4.arkanoid.gfx.Renderer;
import com.nhom_4.arkanoid.input.KeyInput;
import com.nhom_4.arkanoid.input.MouseInput;
import com.nhom_4.arkanoid.physics.Collision;
import com.nhom_4.arkanoid.physics.Resolver;
import com.nhom_4.arkanoid.ui.HUD;
import com.nhom_4.arkanoid.ui.Screens;

public class Game {
    private GameState state = GameState.MENU;
    private final HUD hud = new HUD();
    private final Screens screens = new Screens();
    private final Menu menu = new Menu();
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
    private MouseInput mouse;
 private boolean showPressSpace = true;

    public void setFps(int fps) {
        hud.setFps(fps);
    }

    public void bindInput(KeyInput k) {
        this.keys = k;
    }

    public void bindInput(MouseInput m) {
        this.mouse = m;
    }
    public Game() {
        loadAllLevelMaps(); // Tải tất cả các map khi khởi tạo
        startNewGame();
    }

    // --- CÁC PHƯƠNG THỨC QUẢN LÝ LEVEL MỚI ---
    private void loadAllLevelMaps() {
        levelMaps = new ArrayList<>();

        // Map 1: Một vài hàng gạch
        levelMaps.add(new int[][] {
                { 0, 0, 1, 1, 2, 2, 2, 1, 1, 2, 2, 2, 1, 1, 0, 0 },
                { 0, 1, 2, 1, 0, 0, 1, 2, 2, 1, 0, 0, 1, 2, 1, 0 },
                { 1, 2, 1, 2, 1, 1, 0, 0, 0, 0, 1, 1, 2, 1, 2, 1 },
                { 1, 2, 0, 2, 2, 2, 1, 0, 0, 1, 2, 2, 2, 0, 2, 1 },
                { 1, 2, 0, 1, 1, 2, 2, 1, 1, 2, 2, 1, 1, 0, 2, 1 },
                { 1, 2, 1, 1, 0, 0, 1, 2, 2, 1, 0, 0, 1, 1, 2, 1 },
                { 0, 1, 2, 2, 1, 0, 0, 1, 1, 0, 0, 1, 2, 2, 1, 0 },
                { 0, 0, 1, 1, 2, 1, 1, 0, 0, 1, 1, 2, 1, 1, 0, 0 }
        });

        // Map 2: Map bàn cờ
        levelMaps.add(new int[][] {
                { 0, 0, 1, 1, 2, 2, 2, 1, 1, 2, 2, 2, 1, 1, 0, 0 },
                { 0, 1, 2, 1, 0, 2, 1, 2, 2, 1, 2, 0, 1, 2, 1, 0 },
                { 1, 2, 1, 2, 1, 0, 1, 2, 2, 1, 0, 1, 2, 1, 2, 1 },
                { 1, 2, 2, 1, 0, 1, 2, 2, 2, 2, 1, 0, 1, 2, 2, 1 },
                { 1, 2, 2, 1, 0, 1, 2, 2, 2, 2, 1, 0, 1, 2, 2, 1 },
                { 1, 2, 1, 2, 1, 0, 1, 2, 2, 1, 0, 1, 2, 1, 2, 1 },
                { 0, 1, 2, 1, 0, 2, 1, 2, 2, 1, 2, 0, 1, 2, 1, 0 },
                { 0, 0, 1, 1, 2, 2, 2, 1, 1, 2, 2, 2, 1, 1, 0, 0 }
        });
    }

    // Trong file Game.java

    // Trong file Game.java
    private List<Brick> spawnBricksForCurrentLevel() {
        List<Brick> newBricks = new ArrayList<>();
        int[][] currentMap = levelMaps.get(currentLevelIndex);

        double playableWidth = Constants.WIDTH - (2 * Constants.WALL_THICK);
        int numColumns = currentMap[0].length;
        double brickWidth = playableWidth / numColumns;
        double brickHeight = brickWidth / 2.5;

        Random random = new Random();

        for (int i = 0; i < currentMap.length; i++) {
            for (int j = 0; j < currentMap[i].length; j++) {
                int health = currentMap[i][j];
                if (health == 0)
                    continue;

                double brickX = Constants.WALL_THICK + j * brickWidth;
                double brickY = Constants.TOP_OFFSET + i * brickHeight;

                // Lấy một đối tượng Image đã được tải sẵn từ "kho" Assets
                Image randomBrickImage = Assets.bricks.get(random.nextInt(Assets.bricks.size()));

                // Đưa trực tiếp đối tượng Image đó cho Brick mới
                newBricks.add(new Brick(brickX, brickY, brickWidth, brickHeight, health, randomBrickImage));
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

    public void update(double dt) {
        switch (state) {
            case MENU:
                Menu.Action act = menu.update(mouse);
                if (act == Menu.Action.START) {
                    showPressSpace = true;
                    state = GameState.PLAYING;
                } else if (act == Menu.Action.EXIT)
                    System.exit(0);
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
            if (keys.consumeSpace()) {
                        ball.launchRandomUp();
                        showPressSpace = false;// ẩn showPressSpace
                    }
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
               menu.render(g, mouse);
                break;
            case PLAYING:
                if (showPressSpace) {
                    Renderer.renderText(g, "Press Space to Start", Assets.fontPixels_40,Constants.WIDTH/2-250, Constants.HEIGHT/2, Color.WHITE);
                }
                break;        
//Sẽ làm màn hình pause, game_over, you_win sau, cấm tk nào động vào đoạn này!
            case PAUSED:
                break;
            case GAME_OVER:

                break;
            case YOU_WIN:

                break;
        }
    }
}
package com.nhom_4.arkanoid.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.awt.Image;

import com.nhom_4.arkanoid.audio.Sound;
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
import com.nhom_4.arkanoid.util.Pair;

public class Game {
    private GameState state = GameState.MENU;
    private final HUD hud = new HUD();
    private final Screens screens = new Screens();
    private final Menu menu = new Menu();
    private final PowerUpManager powerUpManager = new PowerUpManager();
    private final List<Bullet> bullets = new ArrayList<>();
    private List<Pair<Integer, Integer>[][]> levelMaps;

    private int currentLevelIndex;

    private Paddle paddle;
    private List<Ball> balls;
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
        loadAllLevelMaps();
        startNewGame();
    }

    private void loadAllLevelMaps() {
        levelMaps = new ArrayList<>();
        levelMaps.add(new Pair[][] {
                { new Pair(2, 5), new Pair(2, 5), new Pair(2, 5), new Pair(2, 5), new Pair(2, 5), new Pair(2, 5),
                        new Pair(2, 5), new Pair(2, 5), new Pair(2, 5), new Pair(2, 5), new Pair(2, 5), new Pair(2, 5),
                        new Pair(2, 5), new Pair(2, 5), new Pair(2, 5) },
                { new Pair(1, 4), new Pair(1, 4), null, new Pair(1, 4), new Pair(1, 4), null, new Pair(1, 4),
                        new Pair(1, 4), null, new Pair(1, 4), new Pair(1, 4), null, new Pair(1, 4), new Pair(1, 4),
                        new Pair(1, 4) },
                { new Pair(1, 3), null, new Pair(1, 3), new Pair(1, 3), null, new Pair(1, 3), new Pair(1, 3),
                        new Pair(1, 3), new Pair(1, 3), new Pair(1, 3), null, new Pair(1, 3), new Pair(1, 3), null,
                        new Pair(1, 3) },
                { new Pair(1, 2), new Pair(1, 2), null, new Pair(1, 2), new Pair(1, 2), null, new Pair(1, 2),
                        new Pair(1, 2), null, new Pair(1, 2), new Pair(1, 2), null, new Pair(1, 2), new Pair(1, 2),
                        new Pair(1, 2) },
                { new Pair(1, 1), null, new Pair(1, 1), new Pair(1, 1), null, new Pair(1, 1), new Pair(1, 1),
                        new Pair(1, 1), new Pair(1, 1), new Pair(1, 1), null, new Pair(1, 1), new Pair(1, 1), null,
                        new Pair(1, 1) },
                { new Pair(1, 2), new Pair(1, 2), null, new Pair(1, 2), new Pair(1, 2), null, new Pair(1, 2),
                        new Pair(1, 2), null, new Pair(1, 2), new Pair(1, 2), null, new Pair(1, 2), new Pair(1, 2),
                        new Pair(1, 2) },
                { new Pair(1, 3), null, new Pair(1, 3), new Pair(1, 3), null, new Pair(1, 3), new Pair(1, 3),
                        new Pair(1, 3), new Pair(1, 3), new Pair(1, 3), null, new Pair(1, 3), new Pair(1, 3), null,
                        new Pair(1, 3) },
                { new Pair(1, 4), new Pair(1, 4), null, new Pair(1, 4), new Pair(1, 4), null, new Pair(1, 4),
                        new Pair(1, 4), null, new Pair(1, 4), new Pair(1, 4), null, new Pair(1, 4), new Pair(1, 4),
                        new Pair(1, 4) },
                { new Pair(2, 5), new Pair(2, 5), new Pair(2, 5), new Pair(2, 5), new Pair(2, 5), new Pair(2, 5),
                        new Pair(2, 5), new Pair(2, 5), new Pair(2, 5), new Pair(2, 5), new Pair(2, 5), new Pair(2, 5),
                        new Pair(2, 5), new Pair(2, 5), new Pair(2, 5) }
        });

        levelMaps.add(new Pair[][] {
                { new Pair(2, 5), new Pair(2, 5), new Pair(2, 5), new Pair(2, 5), new Pair(2, 5), new Pair(2, 5),
                        new Pair(2, 5), new Pair(2, 5), new Pair(2, 5), new Pair(2, 5), new Pair(2, 5), new Pair(2, 5),
                        new Pair(2, 5), new Pair(2, 5), new Pair(2, 5) },

                { new Pair(1, 4), new Pair(1, 4), new Pair(1, 4), new Pair(1, 4), null, null, new Pair(1, 4),
                        new Pair(1, 4), new Pair(1, 4), null, null, new Pair(1, 4), new Pair(1, 4), new Pair(1, 4),
                        new Pair(1, 4) },

                { new Pair(1, 3), null, new Pair(1, 3), null, new Pair(1, 3), new Pair(1, 3), null,
                        new Pair(2, 2), null, new Pair(1, 3), new Pair(1, 3), null, new Pair(1, 3), null,
                        new Pair(1, 3) },

                { new Pair(1, 2), new Pair(1, 2), null, new Pair(1, 2), new Pair(1, 2), null, new Pair(1, 2),
                        new Pair(2, 1), new Pair(1, 2), null, new Pair(1, 2), new Pair(1, 2), null, new Pair(1, 2),
                        new Pair(1, 2) },

                { new Pair(1, 1), null, new Pair(1, 1), new Pair(1, 1), null, new Pair(2, 3), new Pair(1, 1),
                        new Pair(2, 5), new Pair(1, 1), new Pair(2, 3), null, new Pair(1, 1), new Pair(1, 1), null,
                        new Pair(1, 1) },

                { new Pair(1, 2), new Pair(1, 2), null, new Pair(1, 2), new Pair(1, 2), null, new Pair(1, 2),
                        new Pair(2, 1), new Pair(1, 2), null, new Pair(1, 2), new Pair(1, 2), null, new Pair(1, 2),
                        new Pair(1, 2) },

                { new Pair(1, 3), null, new Pair(1, 3), null, new Pair(1, 3), new Pair(1, 3), null,
                        new Pair(2, 2), null, new Pair(1, 3), new Pair(1, 3), null, new Pair(1, 3), null,
                        new Pair(1, 3) },

                { new Pair(1, 4), new Pair(1, 4), new Pair(1, 4), new Pair(1, 4), null, null, new Pair(1, 4),
                        new Pair(1, 4), new Pair(1, 4), null, null, new Pair(1, 4), new Pair(1, 4), new Pair(1, 4),
                        new Pair(1, 4) },

                { new Pair(2, 5), new Pair(2, 5), new Pair(2, 5), new Pair(2, 5), new Pair(2, 5), new Pair(2, 5),
                        new Pair(2, 5), new Pair(2, 5), new Pair(2, 5), new Pair(2, 5), new Pair(2, 5), new Pair(2, 5),
                        new Pair(2, 5), new Pair(2, 5), new Pair(2, 5) }
        });

    }

    private List<Brick> spawnBricksForCurrentLevel() {
        List<Brick> newBricks = new ArrayList<>();
        Pair<Integer, Integer>[][] currentMap = levelMaps.get(currentLevelIndex);

        double playableWidth = Constants.WIDTH - (2 * Constants.WALL_THICK);
        int numColumns = currentMap[0].length;
        double brickWidth = playableWidth / numColumns;
        double brickHeight = brickWidth / 2.5;

        for (int i = 0; i < currentMap.length; i++) {
            for (int j = 0; j < currentMap[i].length; j++) {
                Pair<Integer, Integer> brickData = currentMap[i][j];

                // Bỏ qua nếu là khoảng trống (null)
                if (brickData == null)
                    continue;

                int health = brickData.first;
                int brickType = brickData.second;

                // Lấy ảnh tương ứng với loại gạch từ "nhà kho" Assets
                Image brickImage = Assets.bricks.get(brickType);

                // Nếu không tìm thấy ảnh cho loại gạch đó, bỏ qua để tránh lỗi
                if (brickImage == null) {
                    System.err.println("Cảnh báo: Không tìm thấy ảnh cho loại gạch " + brickType);
                    continue;
                }

                double brickX = Constants.WALL_THICK + j * brickWidth;
                double brickY = Constants.TOP_OFFSET + i * brickHeight;

                newBricks.add(new Brick(brickX, brickY, brickWidth, brickHeight, health, brickImage));
            }
        }
        return newBricks;
    }

    private boolean nextLevel() {
        currentLevelIndex++;
        return currentLevelIndex < levelMaps.size();
    }

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
                
        balls = new ArrayList<>();
        Ball initialBall = new Ball(paddle.centerX(), paddle.getY() - Constants.BALL_RADIUS - 2, Constants.BALL_RADIUS);
        balls.add(initialBall);

        resetAndStickBall();
    }

    private void resetAndStickBall() {
        paddle.deactivateLasers();
        for (Ball b : balls) {
            b.deactivateFireball();
            b.stickToPaddle(true);
            b.setVx(0);
            b.setVy(0);
        }

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
        
        for (Ball b : balls) {
            if (b.isSticking()) {
                b.setX(paddle.centerX());
                b.setY(paddle.getY() - b.getR() - 2);
                if (keys.consumeSpace()) {
                    b.launchRandomUp();
                    showPressSpace = false;
                }
            } else {
                b.update(dt);
            }
        }

        if (paddle.hasLasers()) {
            List<Bullet> newBullets = paddle.shoot();
            if (newBullets != null)
                bullets.addAll(newBullets);
        }

        powerUpManager.update(dt, paddle, hud, balls);
        handleBullets(dt);

        Collision.reflectBallOnWallsList(balls);
        handleBricks();
        handlePaddleCollision();

        checkWinLoseConditions();

        if (keys.isPressedP())
            state = GameState.PAUSED;
        if (keys.isPressedR())
            startNewGame();
    }

    private void handlePaddleCollision() {
        for (Ball b : balls) {
            if (!b.isSticking() && b.getRect().intersects(paddle.getRect())) {
                Sound.playBoundSound();
                b.setY(paddle.getY() - b.getR() - 0.1);
                b.setVy(-Math.abs(b.getVy()));
                double hit = (b.getX() - paddle.centerX()) / (paddle.getW() / 2.0);
                b.setVx(b.getVx() + hit * 180);
                b.limitSpeed(Constants.BALL_SPEED_CAP);
            }
        }
    }

    private void checkWinLoseConditions() {
        Iterator<Ball> it = balls.iterator();
        while (it.hasNext()) {
            Ball b = it.next();
            if (b.getY() - b.getR() > Constants.HEIGHT) {
                it.remove();
            }
        }

        if (balls.isEmpty()) {
            hud.loseLife(); 
            if (hud.getLives() > 0) {
                Ball newBall = new Ball(paddle.centerX(), paddle.getY() - Constants.BALL_RADIUS - 2, Constants.BALL_RADIUS);
                newBall.stickToPaddle(true);
                newBall.setVx(0);
                newBall.setVy(0);
                balls.add(newBall);
                paddle.deactivateLasers(); 
                //showPressSpace = true;     
            } else {
                state = GameState.GAME_OVER;
            }
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
                    boolean destroyed = brick.hit();
                    hud.addScore(destroyed ? 50 : 10);
                    hit = true;

                    if (destroyed) {
                        powerUpManager.spawnPowerUp(brick.centerX(), brick.centerY());
                    }
                    break;
                }
            }
            if (!bullet.isActive() || hit)
                bulletIterator.remove();
        }
    }

    private void handleBricks() {
        for (Ball b : balls) {
            Rectangle2D.Double ballRect = b.getRect();
            for (Brick br : bricks) {
                if (!br.isAlive()) continue;

                if (ballRect.intersects(br.getRect())) {

                    if (!b.isFireball()) {
                        Resolver.resolveBallBrick(b, br);
                        //Âm thanh nảy bóng
                        Sound.playBoundSound();
                    } else {
                        Sound.playBreakSound();
                    }
                    boolean destroyed = br.hit();
                    if (destroyed) {
                        hud.addScore(50);
                        powerUpManager.spawnPowerUp(br.centerX(), br.centerY());
                    } else {
                        hud.addScore(10);
                    }
                    b.speedUp(Constants.BALL_SPEEDUP_MUL, Constants.BALL_SPEED_CAP);
                    break;
                }
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
        for (Ball b : balls) {
            b.render(g);
        }

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
                    Renderer.renderText(g, "Press Space to Start", Assets.fontPixels_40, Constants.WIDTH / 2 - 250,
                            Constants.HEIGHT / 2, Color.WHITE);
                }
                break;
            case PAUSED:
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
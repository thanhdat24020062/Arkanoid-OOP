package com.nhom_4.arkanoid.core;

import com.nhom_4.arkanoid.config.Constants;
import com.nhom_4.arkanoid.input.KeyInput;
import com.nhom_4.arkanoid.input.MouseInput;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    private static GamePanel instance;
    private Game game;
    private final KeyInput keys = new KeyInput();
    private final MouseInput mouse = new MouseInput();
    private Thread loop;
    private boolean running = false;

    private GamePanel() {
        this.game = Game.createOrLoadGame(keys, mouse);
        setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        setBackground(Constants.BG_COLOR);
        setFocusable(true);
        addKeyListener(keys);
        game.bindInput(keys);

        game.bindInput(mouse);
        addMouseListener(mouse);
        addMouseMotionListener(mouse); // nếu muốn hover sau này
        start();
    }

    public static GamePanel getInstance() {
        if (instance == null) {
            instance = new GamePanel();
        }
        return instance;
    }

    private void start() {
        running = true;
        loop = new Thread(this, "GameLoop");
        loop.start();
    }

    @Override
    public void run() {
        final double targetUPS = Constants.TARGET_UPS; // 120
        final double nsPerUpdate = 1_000_000_000.0 / targetUPS;
        long last = System.nanoTime();
        double acc = 0;
        long fpsTimer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            acc += (now - last) / nsPerUpdate;
            last = now;
            while (acc >= 1) {
                game.update(1.0 / targetUPS);
                acc -= 1;
            }
            repaint();
            frames++;
            if (System.currentTimeMillis() - fpsTimer >= 1000) {
                game.setFps(frames);
                frames = 0;
                fpsTimer += 1000;
            }
            Toolkit.getDefaultToolkit().sync();
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render((Graphics2D) g);
    }

    // đảm bảo có focus sau khi add vào JFrame
    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();
    }
}
package com.nhom_4.arkanoid.ui;

import com.nhom_4.arkanoid.gfx.Assets;
import com.nhom_4.arkanoid.input.MouseInput;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.List;

import static com.nhom_4.arkanoid.config.Constants.*;

public class LeaderBoardScreen implements Serializable {
    private final Rectangle backButton = new Rectangle(20, 20, 120, 60);
    private boolean backPressed = false;

    public boolean isBackPressed() {
        return backPressed;
    }

    public void update(MouseInput mouse) {
        backPressed = false;
        Point p = mouse.consumeClick();
        if (p != null && backButton.contains(p)) {
            backPressed = true;
        }
    }

    public void render(Graphics2D g) {
        if (Assets.LEADERBOARD != null)
            g.drawImage(Assets.LEADERBOARD, 0, 0, WIDTH, HEIGHT, null);

        // Tiêu đề
        g.setFont(Assets.fontPixels_44 != null ? Assets.fontPixels_44 : new Font(Font.SANS_SERIF, Font.BOLD, 44));
        g.setColor(Color.WHITE);
        g.drawString("LEADERBOARD", WIDTH / 2 - 180, 80);

        // Điểm
        LeaderBoard lb = new LeaderBoard();
        List<PlayerScore> scores = lb.getScores();
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        int startY = 150, gap = 30;
        for (int i = 0; i < scores.size(); i++) {
            PlayerScore ps = scores.get(i);
            g.drawString((i + 1) + ". " + ps.getName() + " - " + ps.getScore(),
                    WIDTH / 2 - 120, startY + i * gap);
        }

        BufferedImage btnImg = Assets.BUTTON_SMALL;
        if (btnImg != null) {
            g.drawImage(btnImg, backButton.x, backButton.y, backButton.width, backButton.height, null);
        } else {
            System.err.println("Lỗi: Assets.BUTTON_SMALL chưa được load!");
        }

        // Text "Back" trên button
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        g.setColor(Color.WHITE);
        FontMetrics fm = g.getFontMetrics();
        String text = "BACK";
        int textX = backButton.x + (backButton.width - fm.stringWidth(text)) / 2;
        int textY = backButton.y + ((backButton.height - fm.getHeight()) / 2) + fm.getAscent();
        g.drawString(text, textX, textY);
    }
}

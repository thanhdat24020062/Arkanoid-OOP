package com.nhom_4.arkanoid;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.nhom_4.arkanoid.core.GamePanel;
import com.nhom_4.arkanoid.gfx.Assets;

public class App {
    public static void main(String[] args) {
        Assets.load();
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Arkanoid");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setResizable(false);
            f.setContentPane(new GamePanel());
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}
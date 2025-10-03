package com.yourname.arkanoid;

import javax.swing.*;
import com.yourname.arkanoid.core.GamePanel;

public class App {
    public static void main(String[] args) {
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
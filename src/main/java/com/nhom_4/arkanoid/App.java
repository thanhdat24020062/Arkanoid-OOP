package com.nhom_4.arkanoid;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.nhom_4.arkanoid.audio.Music;
import com.nhom_4.arkanoid.audio.Sound;
import com.nhom_4.arkanoid.core.GamePanel;
import com.nhom_4.arkanoid.core.Game;
import com.nhom_4.arkanoid.gfx.Assets;
import com.nhom_4.arkanoid.util.SaveLoadManager;

public class App {
    public static void main(String[] args) {
        Sound.init();
        Music.init();
        Assets.load();

        SwingUtilities.invokeLater(() -> {
            GamePanel panel = GamePanel.getInstance();
            JFrame f = new JFrame("Arkanoid");
            f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            f.setResizable(false);
            f.setContentPane(panel);
            f.pack();
            f.setLocationRelativeTo(null);
            f.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    Game gameToSave = panel.getGame();

                    if (gameToSave != null) {
                        SaveLoadManager.saveGame(gameToSave);
                        System.out.println("AutoSave thành công");
                    }
                    System.exit(0);
                }
            });
            f.setVisible(true);
        });
    }
}
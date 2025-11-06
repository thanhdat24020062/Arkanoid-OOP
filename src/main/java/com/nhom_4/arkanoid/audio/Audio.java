package com.nhom_4.arkanoid.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public abstract class Audio {
    protected static Clip loadClip(String path) {
        try {
            URL url = Music.class.getResource(path);
            if (url == null) {
                System.err.println("Không thể load âm thanh: " + path);
                return null;
            }

            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            return clip;
        } catch (Exception e) {
            System.err.println("Không thể load âm thanh: " + path);
            return null;
        }
    }
}

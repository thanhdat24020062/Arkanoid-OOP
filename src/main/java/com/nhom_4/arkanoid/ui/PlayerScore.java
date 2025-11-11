package com.nhom_4.arkanoid.ui;

import java.io.Serializable;

public class PlayerScore implements Comparable<PlayerScore>, Serializable {
    private final String name;
    private final int score;

    public PlayerScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() { return name; }
    public int getScore() { return score; }

    @Override
    public int compareTo(PlayerScore other) {
        return Integer.compare(other.score, this.score); // giảm dần
    }

    @Override
    public String toString() {
        return name + ":" + score;
    }
}

package com.nhom_4.arkanoid.ui;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaderBoard {
    private static final String FILE_NAME = "leaderboard.txt";
    private final List<PlayerScore> scores = new ArrayList<>();

    public LeaderBoard() {
        load();
    }

    public void addScore(String name, int score) {
        scores.add(new PlayerScore(name, score));
        Collections.sort(scores);
        if (scores.size() > 10) { // chỉ giữ top 10
            scores.subList(10, scores.size()).clear();
        }
        save();
    }

    public List<PlayerScore> getScores() {
        return new ArrayList<>(scores);
    }

    private void save() {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (PlayerScore ps : scores) {
                out.println(ps.getName() + ":" + ps.getScore());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void load() {
        scores.clear();
        File f = new File(FILE_NAME);
        if (!f.exists())
            return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    scores.add(new PlayerScore(name, score));
                }
            }
            Collections.sort(scores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

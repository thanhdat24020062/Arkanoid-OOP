package com.nhom_4.arkanoid.util;

import com.nhom_4.arkanoid.util.Pair;
import java.util.ArrayList;
import java.util.List;

public class LevelLoader {

    // 1. Phương thức load tất cả các level maps
    public static List<Pair<Integer, Integer>[][]> loadAllLevelMaps() {
        List<Pair<Integer, Integer>[][]> levelMaps = new ArrayList<>();

        // --- LEVEL 1 ---
        levelMaps.add(createLevel1());

        // --- LEVEL 2 ---
        levelMaps.add(createLevel2());
        
        // Thêm các level khác ở đây

        return levelMaps;
    }

    // 2. Định nghĩa chi tiết từng level trong các phương thức riêng
    private static Pair<Integer, Integer>[][] createLevel1() {
        // Cấu trúc map Level 1
        return new Pair[][] {
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
        };
    }

    private static Pair<Integer, Integer>[][] createLevel2() {
        // Cấu trúc map Level 2
        return new Pair[][] {
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
             // ... (Toàn bộ cấu trúc Level 2 cũ) ...
        };
    }
}
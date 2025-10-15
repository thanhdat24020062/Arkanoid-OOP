package com.nhom_4.arkanoid.level;

import com.nhom_4.arkanoid.entity.Brick;
import java.util.ArrayList;
import java.util.List;

// Chú ý: "implements Level" ở đây
public class MixedSizeLevel implements Level {

    // Ma trận thiết kế cho màn chơi này
    private final int[][] map = {
            // Gạch loại 3 (rộng gấp đôi) chiếm 2 ô, nên ô bên cạnh phải là 0
            { 1, 1, 3, 0, 1, 1 },
            { 2, 9, 2, 9, 2, 9 },
            // Gạch loại 4 (cao gấp đôi) cũng nên có hàng dưới là 0 để thoáng hơn
            { 4, 0, 0, 0, 0, 4 },
            { 0, 0, 0, 0, 0, 0 },
            { 1, 1, 1, 1, 1, 1 }
    };

    @Override // Thêm annotation này để cho rõ ràng
    public List<Brick> spawnBricks() {
        List<Brick> bricks = new ArrayList<>();

        double baseBrickWidth = 60;
        double baseBrickHeight = 20;
        double offsetX = 60;
        double offsetY = 60;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                int brickType = map[i][j];

                if (brickType == 0) {
                    continue;
                }

                double brickX = offsetX + j * baseBrickWidth;
                double brickY = offsetY + i * baseBrickHeight;

                double currentBrickWidth = baseBrickWidth;
                double currentBrickHeight = baseBrickHeight;
                int health = 1;

                switch (brickType) {
                    case 1:
                        health = 1;
                        break;
                    case 2:
                        health = 2;
                        break;
                    case 3:
                        health = 1;
                        currentBrickWidth = baseBrickWidth * 2;
                        break;
                    case 4:
                        health = 2;
                        currentBrickHeight = baseBrickHeight * 2;
                        break;
                    case 9:
                        health = 9;
                        break;
                    default:
                        continue;
                }

                Brick newBrick = new Brick(brickX, brickY, currentBrickWidth, currentBrickHeight, health);
                bricks.add(newBrick);
            }
        }
        return bricks;
    }

}

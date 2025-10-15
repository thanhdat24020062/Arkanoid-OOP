package com.nhom_4.arkanoid.level;

import com.nhom_4.arkanoid.entity.Brick;
import java.util.List;

public interface Level {
    List<Brick> spawnBricks();
}
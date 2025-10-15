package com.nhom_4.arkanoid.level_backup;

// File này chỉ dùng để lưu trữ các thiết kế map, game sẽ không dùng đến
public class MapData {

    public static int[][] full() {
        return new int[][] {
                { 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2 },
                { 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1 },
                { 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2 },
                { 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1 },
        };
    }

    public static int[][] checker() {
        return null;
        // ... dán code map checker của bạn vào đây ...
    }

    public static int[][] diagonal() {
        return null;
        // ... dán code map diagonal của bạn vào đây ...
    }
}
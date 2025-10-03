package com.yourname.arkanoid.level;


public final class LevelLoader {
    private LevelLoader() {}

    public static int[][] full(){
        int rows = 6, cols = 10;
        int[][] a = new int[rows][cols];
        for (int r=0;r<rows;r++)
        for (int c=0;c<cols;c++)
        a[r][c] = (r<2)?3: (r<4?2:1);
        return a;
    }

    public static int[][] diagonal(){
        int rows = 7, cols = 11;
        int[][] a = new int[rows][cols];
        for (int r=0;r<rows;r++)
        for (int c=0;c<cols;c++)
        a[r][c] = (c-r>=0 && c-r<=2) ? (3-(c-r)) : ((r+c)%3==0?1:0);
        return a;
    }

    public static int[][] checker(){
        int rows = 8, cols = 12;
        int[][] a = new int[rows][cols];
        for (int r=0;r<rows;r++)
        for (int c=0;c<cols;c++)
        a[r][c] = ((r+c)%2==0) ? ((r%3)+1) : 0;
        return a;
    }
}
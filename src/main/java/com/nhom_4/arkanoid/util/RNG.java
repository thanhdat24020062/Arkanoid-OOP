package com.nhom_4.arkanoid.util;

import java.util.Random;

public final class RNG {
    private RNG() {
    }

    private static final Random R = new Random();

    public static Random get() {
        return R;
    }
}
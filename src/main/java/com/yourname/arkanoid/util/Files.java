package com.yourname.arkanoid.util;

import java.io.*;

public final class Files {
    private Files() {
    }

    public static InputStream res(String path) {
        return Files.class.getResourceAsStream(path);
    }
}
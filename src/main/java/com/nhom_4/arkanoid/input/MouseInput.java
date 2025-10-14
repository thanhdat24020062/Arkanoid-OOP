package com.nhom_4.arkanoid.input;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicBoolean;

public class MouseInput extends MouseAdapter {
    private volatile int clickX, clickY;
    private final AtomicBoolean pending = new AtomicBoolean(false);

    @Override
    public void mouseClicked(MouseEvent e) {
        clickX = e.getX();
        clickY = e.getY();
        pending.set(true); // đánh dấu có click mới
    }

    /** 
     * Trả về toạ độ click gần nhất (Point) nếu có, đồng thời reset cờ.
     * Nếu chưa có click mới, trả về null.
     */
    public Point consumeClick() {
        return pending.getAndSet(false) ? new Point(clickX, clickY) : null;
    }
}

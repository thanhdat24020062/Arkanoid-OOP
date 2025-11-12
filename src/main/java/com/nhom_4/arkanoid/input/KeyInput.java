package com.nhom_4.arkanoid.input;

import java.awt.event.*;

public class KeyInput implements KeyListener {
    private volatile boolean left, right;
    private volatile boolean pressedP, pressedR;
    private volatile boolean spaceOnce;
    private volatile boolean pressedEsc;

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isPressedP() {
        return pressedP;
    }

    public boolean isPressedR() {
        return pressedR;
    }

    public boolean isPressedEsc() {
        return pressedEsc;
    }

    public boolean consumeSpace() {
        boolean s = spaceOnce;
        spaceOnce = false;
        return s;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                left = true;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                right = true;
                break;
            case KeyEvent.VK_SPACE:
                spaceOnce = true;
                break;
            case KeyEvent.VK_P:
                pressedP = true;
                break;
            case KeyEvent.VK_R:
                pressedR = true;
                break;
            case KeyEvent.VK_ESCAPE:
                pressedEsc = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                left = false;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                right = false;
                break;
            case KeyEvent.VK_SPACE:
                spaceOnce = false;
                break;
            case KeyEvent.VK_P:
                pressedP = false;
                break;
            case KeyEvent.VK_R:
                pressedR = false;
                break;
            case KeyEvent.VK_ESCAPE:
                pressedEsc = false;
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
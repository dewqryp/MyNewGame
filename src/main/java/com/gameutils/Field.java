package com.gameutils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Field extends JPanel {
    private final List<Apple> apples = new ArrayList<>();

    private boolean animating;

    public void startAnimation() {
        if (!animating) {
            animating = true;
            Timer timer = new Timer(30, e -> updateGame());
            timer.start();
        }
    }

    private void updateGame() {
        for (Apple apple : apples) {
            apple.update();
        }

        repaint();
    }
}

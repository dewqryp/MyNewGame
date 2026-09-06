package com.game;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private final Field field;

    public GameFrame() {

        field = new Field();

        setTitle("Apple Toss");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(field, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    public Field getField() {
        return field;
    }
}

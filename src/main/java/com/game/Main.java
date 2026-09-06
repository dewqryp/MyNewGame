package com.game;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            GameFrame frame = new GameFrame();

            frame.setVisible(true);
            frame.getField().requestFocusInWindow();
        });
    }
}

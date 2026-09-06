package com.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class Field extends JPanel {
    public static final float GRAVITY = 0.5f;

    private final List<Apple> apples = new ArrayList<>();

    private final Physicist player;
    private final Tree tree;

    public Field() {

        player = new Physicist(100, 100);
        tree = new Tree(400, 300);

        setPreferredSize(new Dimension(800, 500));
    }

    public void tossApple() {

        Apple apple = player.takeApple();

        apple.toss(
                player.getAimingAngle(),
                player.getAimingForce()
        );

        apples.add(apple);
    }

    public void updateGame() {

        for (Apple apple : apples) {
            apple.step();
        }

        repaint();
    }
    private void setUpControls()
    {
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();
        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "aimLeft");
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "aimRight");
        inputMap.put(KeyStroke.getKeyStroke("UP"), "increaseForce");
        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "decreaseForce");
        inputMap.put(KeyStroke.getKeyStroke("SPACE"), "tossApple");
        actionMap.put("aimLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.setAimingAngle(player.getAimingAngle() - 5);
            }

        });
        actionMap.put("aimRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.setAimingAngle(player.getAimingAngle() + 5);
            }

        });
        actionMap.put("increaseForce", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.setAimingForce(player.getAimingForce() + 5);
            }

        });
        actionMap.put("decreaseForce", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.setAimingForce(player.getAimingForce() - 5);
            }

        });
        actionMap.put("tossApple", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tossApple();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.fillRect(player.getX() - 10, player.getY() - 20, 20, 40);
        g.fillRect(tree.getX() - 10, tree.getY() - 50, 20, 50);
        for(Apple apple : apples) {
            g.fillOval(apple.getX() - 8, apple.getY() - 8, 16, 16);
        }

    }
}

package com.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class Field extends JPanel {
    public static final float GRAVITY = 0.5f;
    public static final int GROUND_Y = 470;

    private final List<Apple> apples = new ArrayList<>();

    private final Physicist player;
    private final Tree tree;
    private final Timer gameLoop;
    private int score;

    public Field() {

        player = new Physicist(100, 100);
        tree = new Tree(400, 300);

        setPreferredSize(new Dimension(800, 500));
        setFocusable(true);
        gameLoop = new Timer(16, e -> updateGame());
        gameLoop.start();
        setUpControls();
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
            if (!apple.isActive()) {
                continue;
            }

            apple.step();

            if (isHitTree(apple)) {
                apple.setPosition(tree.getX(), tree.getY());
                apple.setActive(false);
                score++;
            }
        }

        apples.removeIf(apple -> !apple.isActive());
        repaint();
    }

    private boolean isHitTree(Apple apple) {
        int distanceX = apple.getX() - tree.getX();
        int distanceY = apple.getY() - tree.getY();
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
        return distance < 20;
    }

    private void setUpControls() {
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

                if (player.getAimingAngle() > 0) {
                    player.setAimingAngle(player.getAimingAngle() - 5);
                    repaint();
                }
            }

        });
        actionMap.put("aimRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.getAimingAngle() >= 360) {
                    player.setAimingAngle(0);
                } else {
                    player.setAimingAngle(player.getAimingAngle() + 5);
                    repaint();
                }
            }

        });
        actionMap.put("increaseForce", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.getAimingForce() >= 100) {
                    player.setAimingForce(0);
                }
                player.setAimingForce(player.getAimingForce() + 5);
                repaint();
            }

        });
        actionMap.put("decreaseForce", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.getAimingForce() > 0) {
                    player.setAimingForce(player.getAimingForce() - 5);
                    repaint();
                }
            }

        });
        actionMap.put("tossApple", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tossApple();
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawBackground(g2d);
        drawGround(g2d);
        drawTrajectory(g2d);
        drawPlayer(g2d);
        drawTree(g2d);
        drawApples(g2d);
        drawHud(g2d);

        g2d.dispose();
    }

    private void drawBackground(Graphics2D g2d) {
        GradientPaint sky = new GradientPaint(0, 0, new Color(135, 206, 235), 0, getHeight(), new Color(240, 248, 255));
        g2d.setPaint(sky);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(new Color(255, 255, 255, 70));
        for (int i = 0; i < 10; i++) {
            int x = 50 + i * 80;
            int y = 60 + (i % 3) * 25;
            g2d.fillOval(x, y, 4, 4);
        }
    }

    private void drawGround(Graphics2D g2d) {
        g2d.setColor(new Color(89, 172, 76));
        g2d.fillRect(0, GROUND_Y, getWidth(), getHeight() - GROUND_Y);

        g2d.setColor(new Color(118, 90, 51));
        for (int i = 0; i < getWidth(); i += 30) {
            g2d.fillRect(i, GROUND_Y, 15, 8);
        }
    }

    private void drawTrajectory(Graphics2D g2d) {
        float force = player.getAimingForce();
        int aimLength = 20 + (int) (force * 5);
        float thickness = 1 + force / 10;
        g2d.setStroke(new BasicStroke(thickness));
        g2d.setColor(new Color(255, 255, 255, 180));

        double radians = Math.toRadians(player.getAimingAngle());
        int endX = player.getX() + (int) (Math.cos(radians) * aimLength);
        int endY = player.getY() - (int) (Math.sin(radians) * aimLength);
        g2d.drawLine(player.getX(), player.getY(), endX, endY);
    }

    private void drawPlayer(Graphics2D g2d) {
        g2d.setColor(new Color(72, 72, 72));
        g2d.fillRect(player.getX() - 10, player.getY() - 20, 20, 40);

        g2d.setColor(new Color(240, 180, 120));
        g2d.fillOval(player.getX() - 12, player.getY() - 30, 24, 18);
    }

    private void drawTree(Graphics2D g2d) {
        g2d.setColor(new Color(92, 64, 28));
        g2d.fillRect(tree.getX() - 12, tree.getY() - 60, 24, 60);

        g2d.setColor(new Color(54, 128, 52));
        g2d.fillOval(tree.getX() - 42, tree.getY() - 90, 84, 58);
        g2d.fillOval(tree.getX() - 58, tree.getY() - 70, 54, 48);
        g2d.fillOval(tree.getX() + 4, tree.getY() - 70, 54, 48);

        g2d.setColor(new Color(91, 143, 59));
        g2d.fillOval(tree.getX() - 22, tree.getY() - 45, 44, 26);
    }

    private void drawApples(Graphics2D g2d) {
        for (Apple apple : apples) {
            g2d.setColor(new Color(180, 30, 30));
            g2d.fillOval(apple.getX() - 8, apple.getY() - 8, 16, 16);
            g2d.setColor(new Color(255, 255, 255, 120));
            g2d.fillOval(apple.getX() - 3, apple.getY() - 5, 5, 5);
        }
    }

    private void drawHud(Graphics2D g2d) {
        g2d.setColor(new Color(30, 30, 30, 180));
        g2d.fillRoundRect(12, 12, 210, 72, 14, 14);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("SansSerif", Font.BOLD, 16));
        g2d.drawString("Angle: " + player.getAimingAngle(), 24, 36);
        g2d.drawString("Force: " + player.getAimingForce(), 24, 58);
        g2d.drawString("Score: " + score, 24, 80);
    }
}

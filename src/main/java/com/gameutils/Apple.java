package com.gameutils;

public class Apple {
    private float x;
    private float y;
    private float velocityX;
    private float velocityY;

    public Apple() {
        x = 0;
        y = 0;
    }

    public void toss(float angle, float force) {
        double radians = Math.toRadians(angle);

        velocityX = (float)(Math.cos(radians) * force);
        velocityY = (float)(Math.sin(radians) * force);
    }

    public void update() {
        x += velocityX;
        y += velocityY;

        velocityY += 0.5f;
    }
}

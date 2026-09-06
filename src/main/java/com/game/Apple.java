package com.game;



public class Apple implements GamePiece {
    private int x;
    private int y;

    private float velocityX;
    private float velocityY;

    private final float mass = 1.0f;
    private boolean active = true;

    public Apple(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void toss(float angle, float force) {

        double radians = Math.toRadians(angle);

        velocityX = (float)
                (force * Math.cos(radians) / mass);

        velocityY = (float)
                (-force * Math.sin(radians) / mass);
    }

    public void step() {
        if (!active) {
            return;
        }

        velocityY += Field.GRAVITY;
        x += (int) velocityX;
        y += (int) velocityY;

        if (y >= Field.GROUND_Y) {
            y = Field.GROUND_Y;
            active = false;
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

package com.game;

public class Physicist implements GamePiece {
    private int x;
    private int y;

    private float aimingAngle;
    private float aimingForce;

    public Physicist(int x, int y) {
        this.x = x;
        this.y = y;
        aimingAngle = 45;
        aimingForce = 10;
    }

    public float getAimingAngle() {
        return aimingAngle;
    }

    public void setAimingAngle(float aimingAngle) {
        this.aimingAngle = aimingAngle;
    }

    public float getAimingForce() {
        return aimingForce;
    }

    public void setAimingForce(float aimingForce) {
        this.aimingForce = aimingForce;
    }

    public Apple takeApple() {
        return new Apple(x, y);
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

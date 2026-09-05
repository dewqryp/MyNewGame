package com.gameutils;

public class Player {

        private float aimingAngle;
        private float aimingForce;
        private Apple aimingApple;

        public Player() {
            aimingAngle = 45;
            aimingForce = 50;
            getNewApple();
        }

        public void setAimingAngle(float angle) {
            aimingAngle = angle;
        }

        public void setAimingForce(float force) {
            aimingForce = force;
        }

        public Apple takeApple() {
            Apple apple = aimingApple;
            aimingApple = null;
            return apple;
        }

        public void getNewApple() {
            aimingApple = new Apple();
        }
}

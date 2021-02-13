package com.example.brimossea;

import android.graphics.PointF;

public class PlayerState {
    public boolean hasShield;
    private int numCredits;
    private int lives;
    private float restartX;
    private float restartY;
    PlayerState() {
        hasShield = false;
        lives = 3;
        numCredits = 0;
    }


    public void saveLocation(PointF location) {
        // The location saves each time the player uses a teleport
        restartX = location.x;
        restartY = location.y;
    }
    public PointF loadLocation() {
        // Used every time the player loses a life
        return new PointF(restartX, restartY);
    }

    public int getLives(){
        return lives;
    }

    public void gotCredit(){
        numCredits += 5;
    }
    public int getCredits(){
        return numCredits;
    }
    public void loseLife(){
        lives--;
    }
    public void addLife(){
        lives++;
    }
    public void resetLives(){
        lives = 3;
    }
    public void resetCredits(){
        lives = 0; }

    public void activateShield() {
        hasShield = true;
    }

    public void deactivateShield(){
        hasShield = false;
    }
}

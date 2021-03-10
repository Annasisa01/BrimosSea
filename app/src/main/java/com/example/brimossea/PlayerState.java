package com.example.brimossea;

import android.graphics.PointF;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class PlayerState {
    public boolean hasShield;
    private int numCredits;
    private int lives;
    private boolean reachedEnd;
    Shield shield;



    private long shieldStartTime;
    public final long SHIELD_TIMEOUT = 10000;
    PlayerState() {
        reachedEnd = false;
        hasShield = false;
        lives = 3;
        numCredits = 0;
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

    public void activateShield() {
        hasShield = true;
        shieldStartTime = System.currentTimeMillis();
    }

    public void deactivateShield(){
        hasShield = false;
        shield.setActive(false);
        shield.setVisible(false);
        System.out.println("deactivated shield " + shield);
    }

    public long getShieldStartTime() {
        return shieldStartTime;
    }

    public void setShield(GameObject go) {
        System.out.println("Just seet shield to " + (Shield) go);
        shield = (Shield) go;
    }

//    public long getSHIELD_TIMEOUT() {
//        return SHIELD_TIMEOUT;
//    }
}

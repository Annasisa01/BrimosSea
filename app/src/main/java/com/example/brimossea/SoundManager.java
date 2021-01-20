package com.example.brimossea;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

public class SoundManager {
    MediaPlayer backgorund;
    MediaPlayer feeding;
    MediaPlayer hurt;
    MediaPlayer gameover;
    MediaPlayer powerup;

    public void loadSound(Context context){
        try {
            backgorund = MediaPlayer.create(context, R.raw.background);
            feeding = MediaPlayer.create(context, R.raw.wavy);
//            hurt = MediaPlayer.create(context,R.raw.hurt);
//            gameover = MediaPlayer.create(context,R.raw.gameover);
//            powerup = MediaPlayer.create(context,R.raw.powerup);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playSound(String sound){
        switch (sound){
            case "background":
                backgorund.start();
                backgorund.setLooping(true);
                break;
            case "wavy":
                feeding.start();
                break;
            case "hurt":
                hurt.start();
                break;
            case "gameover":
                gameover.start();
                break;

            case "powerup":
                powerup.start();
                break;
        }
    }

    public void stopSound(String sound){
        switch (sound){
            case "background":
                backgorund.pause();
                break;
            case "wavy":
                feeding.pause();
                break;
            case "hurt":
                hurt.pause();
                break;
            case "gameover":
                gameover.pause();
                break;

            case "powerup":
                powerup.pause();
                break;
        }
    }
}

package com.example.brimossea;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;

public class GameActivity extends Activity {
    // Our object to handle the View
    private BSView gameView;
    //Our object to handle the sound
    SoundManager sm;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String level = "LevelCave";
        sm = new SoundManager();
        sm.loadSound(this);
        sm.playSound("background");

        // Get a Display object to access screen details
        Display display = getWindowManager().getDefaultDisplay();

        // Load the resolution into a Point object
        Point size = new Point();
        display.getSize(size);
        Intent intent = getIntent();
        //Receive the next level as string from the previous activity to the current one we want to
        //load
        if(intent.hasExtra("nextLevel")){
            level = intent.getStringExtra("nextLevel");
        }
        gameView = new BSView(this,size.x,size.y,level);
        // Make our platformView the view for the Activity
        setContentView(gameView);
    }


    // Method that pauses our thread
    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    //Method that resumes our thread
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    // If the player hits the back button, quit the app
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            sm.stopSound("background");
            finish();
            return true;
        }
        return false;
    }
}

package com.example.brimossea;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {
    SoundManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sm = new SoundManager();
        sm.loadSound(this);
        sm.playSound("background");
        final Button playButton = (Button)findViewById(R.id.play_button);
        playButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,GameActivity.class);
        startActivity(intent);
        finish();
        sm.stopSound("background");
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
package com.example.brimossea;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EndActivity extends Activity {
    int lives;
    TextView highScore, currentScore, highScoreText;
    String currentLevel;
    Button restart;
    Button nextLevel;
    ArrayList<String> levels;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_screen);
        levels = new ArrayList<>();
        levels.add("LevelCave");
        levels.add("LevelTwo");
        levels.add("LevelThree");

        currentScore = findViewById(R.id.current_score);
        highScore = findViewById(R.id.high_score);
        restart = findViewById(R.id.restart_button);
        nextLevel = findViewById(R.id.nextLevel_button);
        highScoreText = findViewById(R.id.highScoreText);
        Intent intent = getIntent();
        if(intent.hasExtra("highScore") && intent.hasExtra("currentScore")) {
            int temp = intent.getIntExtra("currentScore",-1);
            currentScore.setText("" + intent.getIntExtra("currentScore",-1));
            highScore.setText("" + intent.getIntExtra("highScore",-1));
            currentLevel = intent.getStringExtra("currentLevel");
            lives = intent.getIntExtra("lives",-1);
        }

        if(levels.indexOf(currentLevel) == levels.size() - 1 || lives == 0) {
            nextLevel.setVisibility(View.INVISIBLE);
        }


        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),GameActivity.class);
                startActivity(intent);
                finish();
            }
        });

        nextLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nextLevelIndex = levels.indexOf(currentLevel) + 1 % levels.size();
                Intent intent = new Intent(getApplicationContext(),GameActivity.class);
                intent.putExtra("nextLevel", levels.get(nextLevelIndex));
                startActivity(intent);
                finish();
            }
        });
    }
}

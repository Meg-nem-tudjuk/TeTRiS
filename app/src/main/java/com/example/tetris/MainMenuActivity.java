package com.example.tetris;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity {

    boolean pressed = false;

    Button logout;
    Button newGame;
    Button leaderboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        logout = findViewById(R.id.logoutButton);
        newGame = findViewById(R.id.newGameButton);
        leaderboard = findViewById(R.id.leaderboardButton);

        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!pressed) {
                    pressed = true;
                    GlobalClass.setLoggedInPlayer(null);
                    //TODO: add -1 to sharedpreferences
                    startActivity(new Intent(MainMenuActivity.this, LoginActivity.class));
                    finish();
                }
                pressed = false;
            }
        });

        newGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!pressed) {
                    startActivity(new Intent(MainMenuActivity.this, GameActivity.class));
                }
                pressed = false;
            }
        });

        leaderboard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!pressed) {
                    startActivity(new Intent(MainMenuActivity.this, GameActivity.class));
                }
                pressed = false;
            }
        });
    }
}
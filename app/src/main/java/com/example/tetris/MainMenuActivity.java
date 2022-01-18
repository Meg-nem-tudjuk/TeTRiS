package com.example.tetris;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * this is the main menu activity
 */
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
        TextView loggedInPlayer = findViewById(R.id.loggedInPlayer);
        loggedInPlayer.setText("Bejelentkezve mint " + GlobalClass.getLoggedInPlayer().getUsername());

        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!pressed) {
                    pressed = true;
                    GlobalClass.setLoggedInPlayer(null);
                    SharedPreferences sharedPref = getSharedPreferences("loginData", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("id", -1);
                    editor.apply();
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
                    startActivity(new Intent(MainMenuActivity.this, LeaderboardActivity.class));
                }
                pressed = false;
            }
        });
    }
}
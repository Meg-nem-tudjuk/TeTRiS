package com.example.tetris;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Scanner;

/**
 * this is the login activity
 */
public class LoginActivity extends AppCompatActivity {

    public boolean pressed = false;

    EditText username;
    EditText password;
    CheckBox stayLoggedIn;
    Button login;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.usernameEditText);
        password = findViewById(R.id.passwordEditText);
        stayLoggedIn = findViewById(R.id.stayLoggedInChechBox);
        login = findViewById(R.id.loginButton);
        register = findViewById(R.id.registerButton);

        String file = GlobalClass.load(this);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.length() > 0) {
                String[] splitLine = line.split("\\|");
                GlobalClass.addPlayer(new Player(splitLine[0], splitLine[1], Integer.parseInt(splitLine[2])));
            }
        }
        SharedPreferences sharedPref = LoginActivity.this.getSharedPreferences("loginData", MODE_PRIVATE);
        if (sharedPref.getInt("id", -1) > -1) {
            GlobalClass.setLoggedInPlayer(GlobalClass.getPlayers().get(sharedPref.getInt("id", -1)));
            startActivity(new Intent(LoginActivity.this, MainMenuActivity.class));
            finish();
        }

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!pressed) {
                    pressed = true;
                    int loginIndex = 0;
                    while (loginIndex < GlobalClass.getPlayers().size() && !(GlobalClass.getPlayers().get(loginIndex).getUsername().equals(username.getText().toString()) && GlobalClass.getPlayers().get(loginIndex).getPasswordHash().equals(GlobalClass.hash(password.getText().toString())))) loginIndex++;
                    if (loginIndex < GlobalClass.getPlayers().size()) {
                        GlobalClass.setLoggedInPlayer(GlobalClass.getPlayers().get(loginIndex));
                        SharedPreferences sharedPref = LoginActivity.this.getSharedPreferences("loginData", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        if (stayLoggedIn.isChecked()) {
                            editor.putInt("id", loginIndex);
                            editor.apply();
                        }
                        else {
                            editor.putInt("id", -1);
                            editor.apply();
                        }
                        GlobalClass.setLoggedInPlayer(GlobalClass.getPlayers().get(loginIndex));
                        startActivity(new Intent(LoginActivity.this, MainMenuActivity.class));
                        finish();
                    }
                    else Toast.makeText(LoginActivity.this, "Hibás felhasználónév vagy jelszó", Toast.LENGTH_SHORT).show();
                    pressed = false;
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!pressed) {
                    pressed = true;
                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                }
            }
        });

        username.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                setEnability();
            }
        });

        password.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                setEnability();
            }
        });
    }

    /**
     * sets the enabled attribute of the login button
     */
    private void setEnability() {
        login.setEnabled(username.getText().toString().length() >= 1 && password.getText().toString().length() >= 6);
    }

    @Override
    protected void onResume() {
        super.onResume();
        pressed = false;
    }
}
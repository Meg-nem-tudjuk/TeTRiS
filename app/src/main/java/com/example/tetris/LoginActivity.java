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

        //TODO: check sharedpreferences

        username = findViewById(R.id.usernameEditText);
        password = findViewById(R.id.passwordEditText);
        stayLoggedIn = findViewById(R.id.stayLoggedInChechBox);
        login = findViewById(R.id.loginButton);
        register = findViewById(R.id.registerButton);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!pressed) {
                    pressed = true;
                    int loginIndex = 0;
                    while (loginIndex < GlobalClass.getPlayers().size() && !(GlobalClass.getPlayers().get(loginIndex).getUsername().equals(username.getText().toString()) && GlobalClass.getPlayers().get(loginIndex).getPasswordHash().equals(GlobalClass.hash(password.getText().toString())))) loginIndex++;
                    if (loginIndex < GlobalClass.getPlayers().size()) {
                        GlobalClass.setLoggedInPlayer(GlobalClass.getPlayers().get(loginIndex));
                        SharedPreferences sharedPref = LoginActivity.this.getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        if (stayLoggedIn.isChecked()) {
                            editor.putInt("id", loginIndex);
                            editor.apply();
                        }
                        else {
                            editor.putInt("id", -1);
                            editor.apply();
                        }
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

    private void setEnability() {
        login.setEnabled(username.getText().toString().length() >= 1 && password.getText().toString().length() >= 6);
    }

    @Override
    protected void onResume() {
        super.onResume();
        pressed = false;
    }
}
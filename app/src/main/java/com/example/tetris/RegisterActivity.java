package com.example.tetris;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * this is the register activity
 */
public class RegisterActivity extends AppCompatActivity {

    public boolean pressed = false;

    EditText username;
    EditText password;
    EditText confirmPassword;
    Button back;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.usernameEditText);
        password = findViewById(R.id.passwordEditText);
        confirmPassword = findViewById(R.id.confirmPasswordEditText);
        back = findViewById(R.id.backButton);
        register = findViewById(R.id.registerButton);

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!pressed) {
                    pressed = true;
                    finish();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!username.getText().toString().contains("|")) {
                    //pressed = true;
                    boolean taken = false;
                    for (int i = 0; i < GlobalClass.getPlayers().size(); i++) {
                        if (GlobalClass.getPlayers().get(i).getUsername().equals(username.getText().toString())) {
                            taken = true;
                            break;
                        }
                    }
                    if (taken) {
                        Toast.makeText(RegisterActivity.this, "A felhaználónév már foglalt", Toast.LENGTH_SHORT).show();
                    }
                    else if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
                        Toast.makeText(RegisterActivity.this, "A megadott jelszavak nem egyeznek", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Player newPlayer = new Player(username.getText().toString(), GlobalClass.hash(password.getText().toString()), 0);
                        GlobalClass.addPlayer(newPlayer);
                        String file = GlobalClass.load(RegisterActivity.this);
                        file += newPlayer.getUsername() + "|" + newPlayer.getPasswordHash() + "|0";
                        GlobalClass.save(RegisterActivity.this, file);
                        Toast.makeText(RegisterActivity.this, "Sikeres regisztráció", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                else Toast.makeText(RegisterActivity.this, "A felhasználónév illegális karaktert tartalmaz", Toast.LENGTH_SHORT).show();
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
     * sets the enabled attribute of the register button
     */
    private void setEnability() {
        register.setEnabled(username.getText().toString().length() >= 1 && password.getText().toString().length() >= 6);
    }
}
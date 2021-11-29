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
                //if (!pressed) {
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
                        Player newPlayer = new Player(username.getText().toString(), GlobalClass.hash(password.getText().toString()));
                        GlobalClass.addPlayer(newPlayer);
                        //TODO: add player to file
                        Toast.makeText(RegisterActivity.this, "Sikeres regisztráció", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                //}
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
        register.setEnabled(username.getText().toString().length() >= 1 && password.getText().toString().length() >= 6);
    }
}
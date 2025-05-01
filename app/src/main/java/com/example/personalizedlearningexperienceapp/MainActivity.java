package com.example.personalizedlearningexperienceapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton, signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        signupButton = findViewById(R.id.button2);

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            // simple ensure
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(MainActivity.this, "Please input username and password.", Toast.LENGTH_SHORT).show();
            } else {
                // jump to TaskActivity and convey username
                Intent intent = new Intent(MainActivity.this, TaskActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
            }
        });

        // click set up button
        signupButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SetupActivity.class);
            startActivity(intent);
        });
    }
}

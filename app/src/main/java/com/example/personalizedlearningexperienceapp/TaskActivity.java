package com.example.personalizedlearningexperienceapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class TaskActivity extends AppCompatActivity {

    private TextView usernameText;
    private Button enterTaskButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        // get username
        String username = getIntent().getStringExtra("username");
        if (username == null) username = "User";

        usernameText = findViewById(R.id.usernameText);
        usernameText.setText(username);

        enterTaskButton = findViewById(R.id.enterTaskButton);
        String finalUsername = username;
        enterTaskButton.setOnClickListener(v -> {
            Intent intent = new Intent(TaskActivity.this, QuestionActivity.class);
            intent.putExtra("username", finalUsername);
            startActivity(intent);
        });
    }
}

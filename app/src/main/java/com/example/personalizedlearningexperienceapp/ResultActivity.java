package com.example.personalizedlearningexperienceapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    private LinearLayout resultContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        resultContainer = findViewById(R.id.resultContainer);

        ArrayList<String> questions = getIntent().getStringArrayListExtra("questions");
        ArrayList<String> answers = getIntent().getStringArrayListExtra("answers");

        if (questions != null && answers != null) {
            for (int i = 0; i < questions.size(); i++) {
                TextView question = new TextView(this);
                question.setText((i + 1) + ". " + questions.get(i));
                question.setTextSize(16);
                question.setTypeface(null, Typeface.BOLD);
                resultContainer.addView(question);

                TextView answer = new TextView(this);
                answer.setText("Your Answer: " + answers.get(i));
                answer.setPadding(0, 0, 0, 16);
                resultContainer.addView(answer);
            }
        }

        findViewById(R.id.continueBtn).setOnClickListener(v -> finish());
    }
}

package com.example.personalizedlearningexperienceapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QuestionActivity extends AppCompatActivity {

    private LinearLayout questionContainer;
    private Button submitBtn;

    private final List<RadioGroup> answerGroups = new ArrayList<>();
    private final List<QuizItem> quizItems = new ArrayList<>();
    private final List<String> selectedAnswers = new ArrayList<>();

    private final String API_URL = "http://127.0.0.1:5000/getQuiz?topic=Movies";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        questionContainer = findViewById(R.id.questionContainer);
        submitBtn = findViewById(R.id.submitBtn);

        fetchQuizFromAPI();

        submitBtn.setOnClickListener(v -> {
            selectedAnswers.clear();

            for (RadioGroup group : answerGroups) {
                int selectedId = group.getCheckedRadioButtonId();
                if (selectedId != -1) {
                    RadioButton rb = group.findViewById(selectedId);
                    selectedAnswers.add(rb.getText().toString());
                } else {
                    selectedAnswers.add("No answer selected");
                }
            }

            ArrayList<String> questions = new ArrayList<>();
            for (QuizItem item : quizItems) {
                questions.add(item.question);
            }

            Intent intent = new Intent(this, ResultActivity.class);
            intent.putStringArrayListExtra("questions", questions);
            intent.putStringArrayListExtra("answers", new ArrayList<>(selectedAnswers));
            startActivity(intent);
        });
    }

    private void fetchQuizFromAPI() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(API_URL).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(QuestionActivity.this, "API 请求失败", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject json = new JSONObject(response.body().string());
                        JSONArray quizArray = json.getJSONArray("quiz");

                        runOnUiThread(() -> {
                            for (int i = 0; i < quizArray.length(); i++) {
                                try {
                                    JSONObject obj = quizArray.getJSONObject(i);
                                    QuizItem item = new QuizItem();
                                    item.question = obj.getString("question");
                                    item.correct_answer = obj.getString("correct_answer");

                                    JSONArray options = obj.getJSONArray("options");
                                    item.options = new ArrayList<>();
                                    for (int j = 0; j < options.length(); j++) {
                                        item.options.add(options.getString(j));
                                    }

                                    quizItems.add(item);
                                    showQuestion(item, i + 1);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void showQuestion(QuizItem item, int number) {
        TextView questionView = new TextView(this);
        questionView.setText(number + ". " + item.question);
        questionView.setTextSize(18);
        questionView.setTypeface(null, Typeface.BOLD);
        questionView.setPadding(0, 20, 0, 8);
        questionContainer.addView(questionView);

        RadioGroup group = new RadioGroup(this);
        group.setOrientation(RadioGroup.VERTICAL);
        for (String option : item.options) {
            RadioButton rb = new RadioButton(this);
            rb.setText(option);
            group.addView(rb);
        }
        answerGroups.add(group);
        questionContainer.addView(group);
    }

    public static class QuizItem {
        public String question;
        public String correct_answer;
        public List<String> options;
    }
}

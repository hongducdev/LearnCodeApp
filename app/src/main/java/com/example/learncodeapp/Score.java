package com.example.learncodeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Score extends AppCompatActivity {
    Button btnBack;
    TextView txtScore, numCorrect;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        btnBack = findViewById(R.id.btnBack);
        txtScore = findViewById(R.id.score);
        numCorrect = findViewById(R.id.numCorrect);

        String numCorrectAnswers = getIntent().getStringExtra("NUM_CORRECT");
        String score = getIntent().getStringExtra("SCORE");

        numCorrect.setText(numCorrectAnswers);
        txtScore.setText(score);

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(Score.this, HomePage.class);
            startActivity(intent);
            Score.this.finish();
        });
    }
}
package com.example.learncodeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class IntroductionPomodoro extends AppCompatActivity {

    Button btnStartPomodoro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction_pomodoro);

        btnStartPomodoro = findViewById(R.id.btnStartPomodoro);

        btnStartPomodoro.setOnClickListener(v -> {
            Intent intent = new Intent(IntroductionPomodoro.this, CountdownClock.class);
            startActivity(intent);
        });
    }
}
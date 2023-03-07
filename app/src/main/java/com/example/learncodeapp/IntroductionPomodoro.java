package com.example.learncodeapp;

import static com.example.learncodeapp.Splash.catList;
import static com.example.learncodeapp.Splash.selected_course_index;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class IntroductionPomodoro extends AppCompatActivity {

    Button btnStartPomodoro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction_pomodoro);

        btnStartPomodoro = findViewById(R.id.btnStartPomodoro);

        // Change header
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle(catList.get(selected_course_index).getName());
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_question);
        View view =getSupportActionBar().getCustomView();
        ImageButton imageButton= (ImageButton)view.findViewById(R.id.action_bar_back);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update database and back
                Intent intent = new Intent(IntroductionPomodoro.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnStartPomodoro.setOnClickListener(v -> {
            Intent intent = new Intent(IntroductionPomodoro.this, CountdownClock.class);
            startActivity(intent);
        });
    }
}
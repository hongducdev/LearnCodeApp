package com.example.learncodeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomePage extends AppCompatActivity {

    TextView tvNameHomePage; RelativeLayout course;
    ImageView avatarUser;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        tvNameHomePage = findViewById(R.id.tvNameHomePage);
        course = findViewById(R.id.courseName);
        avatarUser = findViewById(R.id.avatarUser);

        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        String name = sharedPreferences.getString("username", null);

        tvNameHomePage.setText(name);

        course.setOnClickListener(v -> {
                Intent intent = new Intent(HomePage.this, Question.class);
                startActivity(intent);
            });

        avatarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userPage = new Intent(HomePage.this, UserPage.class);

                startActivity(userPage);
            }
        });
    }
}
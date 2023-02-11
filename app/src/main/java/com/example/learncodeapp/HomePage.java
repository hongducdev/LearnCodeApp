package com.example.learncodeapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomePage extends AppCompatActivity {

    TextView tvNameHomePage;
    RelativeLayout course;
    LinearLayout courses;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        tvNameHomePage = findViewById(R.id.tvNameHomePage);
        course = findViewById(R.id.courseName);

        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("username", "Bạn học");

        tvNameHomePage.setText(name);

        course.setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, Question.class);
            startActivity(intent);
        });
    }
}

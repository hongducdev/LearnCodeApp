package com.example.learncodeapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomePage extends AppCompatActivity {

    TextView tvNameHomePage;
    RelativeLayout course;
    ImageView avatarUser;
    LinearLayout courses;

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
                Intent intent = new Intent(HomePage.this, IntroductionCourse.class);
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

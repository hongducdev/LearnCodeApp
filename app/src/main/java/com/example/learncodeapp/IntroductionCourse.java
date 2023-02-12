package com.example.learncodeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

public class IntroductionCourse extends AppCompatActivity {

    TextView introductionCourse;
    Button btnStart;
    TextView titleIntroduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction_course);

        titleIntroduct = findViewById(R.id.titleIntroduct);
        introductionCourse = findViewById(R.id.introductionCourse);
        btnStart = findViewById(R.id.btnStart);

        // Change header
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_question);
        View view =getSupportActionBar().getCustomView();
        ImageButton imageButton= (ImageButton)view.findViewById(R.id.action_bar_back);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        titleIntroduct.setText("Giới thiệu về " + getIntent().getStringExtra("course"));
        introductionCourse.setText(getIntent().getStringExtra("courseIntroduct"));

        btnStart.setOnClickListener(v -> {
            Intent intent = new Intent(IntroductionCourse.this, Question.class);
            startActivity(intent);
        });
    }
}
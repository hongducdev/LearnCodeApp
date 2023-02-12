package com.example.learncodeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Question extends AppCompatActivity {
    TextView nameBar;
    LinearLayout questionLayout;
    TextView questionName, timer, size, totalSize;
    RadioButton answer1, answer2, answer3, answer4;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // Initialize Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference questionRef = database.getReference("course-questions");


        // Change header
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_question);
        View view =getSupportActionBar().getCustomView();

        String mSaved = getString(R.string.app_name);
        nameBar = findViewById(R.id.nameBar);

        nameBar.setText("HTML cơ bản");

        ImageButton imageButton= (ImageButton)view.findViewById(R.id.action_bar_back);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        questionLayout = findViewById(R.id.questionLayout);
        questionName = findViewById(R.id.questionName);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);
        timer = findViewById(R.id.timer);

        List<QuestionModel> questionList = new ArrayList<>();

        questionRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    for (DataSnapshot snapshot : task.getResult().getChildren()) {
                        QuestionModel question = snapshot.getValue(QuestionModel.class);
                        questionList.add(question);
                    }
                }
            }
        });
    }

        private void displayQuestion(QuestionModel question) {
            questionName.setText(question.getQuestion());
            answer1.setText(question.getAnswer1());
            answer2.setText(question.getAnswer2());
            answer3.setText(question.getAnswer3());
            answer4.setText(question.getAnswer4());
        }
}
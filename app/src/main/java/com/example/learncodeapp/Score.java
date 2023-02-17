package com.example.learncodeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

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

        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        cộng thêm điểm vào database
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("username", null);

        db.collection("users")
                        .whereEqualTo("username", name)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String id = document.getId();
                                    int scoreInt = Integer.parseInt(score);
                                    String scoreOldString = document.getString("score");
                                    int scoreOld = Integer.parseInt(scoreOldString);
                                    int scoreNew = scoreOld + scoreInt;

                                    String sumScore = String.valueOf(scoreNew);

                                    db.collection("users").document(id).update("score", sumScore);
                                }
                            }
                        });

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(Score.this, HomePage.class);
            startActivity(intent);
            Score.this.finish();
        });
    }
}
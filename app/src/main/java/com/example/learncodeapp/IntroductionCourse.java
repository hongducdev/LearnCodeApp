package com.example.learncodeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction_course);

        introductionCourse = findViewById(R.id.introductionCourse);
        btnStart = findViewById(R.id.btnStart);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("courses")
            .whereEqualTo("name", "HTML cơ bản")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String introduction = document.getString("introduction");
                            introductionCourse.setText(introduction);
                        }
                    } else {
                        Toast.makeText(IntroductionCourse.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
}
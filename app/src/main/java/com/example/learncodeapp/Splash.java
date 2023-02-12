package com.example.learncodeapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Splash extends AppCompatActivity {

    private TextView appName;

//    public static List<CourseModel> catList = new ArrayList<>();
    public static List<String> catList = new ArrayList<>();
    public static int selected_course_index = 0;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        appName = findViewById(R.id.appName);

        Typeface typeface = ResourcesCompat.getFont(this, R.font.blacklist);
        appName.setTypeface(typeface);

//        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation);
//        appName.setAnimation(anim);

        firestore = FirebaseFirestore.getInstance();

        new Thread() {
            public void run() {
                // sleep(3000);
                loadData();

            }
        }.start();

    }

    private void loadData() {
        catList.clear();

        firestore.collection("courseCategories").document("categories")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();

                            if (doc.exists()) {

                                for (int i = 1; i <= doc.getData().size(); i++) {

                                    String courseID = doc.getString("course" + String.valueOf(i));
//                                    String courseName = doc.getString("course" + String.valueOf(i) + "_name");

//                                    catList.add(new CourseModel(courseID, courseName));
                                    catList.add(courseID);

                                }
                                Intent intent = new Intent(Splash.this, MainActivity.class);
                                startActivity(intent);
                                Splash.this.finish();

                            } else {
                                Toast.makeText(Splash.this, "No Category Document Exists!", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        } else {

                            Toast.makeText(Splash.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}

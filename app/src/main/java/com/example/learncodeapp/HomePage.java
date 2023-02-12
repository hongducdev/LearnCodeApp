package com.example.learncodeapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import static com.example.learncodeapp.Splash.catList;

public class HomePage extends AppCompatActivity {

    TextView tvNameHomePage;
    RelativeLayout course;
    ImageView avatarUser, courseImage;
    GridView courses;
    Dialog loadingDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        tvNameHomePage = findViewById(R.id.tvNameHomePage);
        courses = findViewById(R.id.courses);
        avatarUser = findViewById(R.id.avatarUser);
        courseImage = findViewById(R.id.courseImage);

        loadingDialog = new Dialog(HomePage.this);
        loadingDialog.setContentView(R.layout.loading_progressbar);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        String name = sharedPreferences.getString("username", null);

        tvNameHomePage.setText(name);

        List<String> courseImageList = new ArrayList<>();
        List<String> courseIntroductList = new ArrayList<>();

        db.collection("courses").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {

                        courseIntroductList.add((String) document.getData().get("introduction"));
                        courseImageList.add((String) document.getData().get("image_src"));
//                        courseList.add(courseName);
                        CourseGridAdapter adapter = new CourseGridAdapter(catList, courseImageList, courseIntroductList);
                        courses.setAdapter(adapter);

                        loadingDialog.dismiss();
                    }
                } else {
                    Toast.makeText(HomePage.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
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

    
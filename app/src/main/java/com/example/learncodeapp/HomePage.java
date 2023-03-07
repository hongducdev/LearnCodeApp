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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

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
    LinearLayout chiaSeKienThuc, dongHoDemNguoc, layout3, layout4;
    ImageView avatarUser, courseImage;
    ListView lvRank;
    GridView courses;
    Dialog loadingDialog;
    ArrayList<RankModel> rankList = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        tvNameHomePage = findViewById(R.id.tvNameHomePage);
        courses = findViewById(R.id.courses);
        avatarUser = findViewById(R.id.avatarUser);
        courseImage = findViewById(R.id.courseImage);
        chiaSeKienThuc = findViewById(R.id.chiaSeKienThuc);
        dongHoDemNguoc = findViewById(R.id.dongHoDemNguoc);
        layout3 = findViewById(R.id.layout3);
        layout4 = findViewById(R.id.layout4);

        loadingDialog = new Dialog(HomePage.this);
        loadingDialog.setContentView(R.layout.loading_progressbar);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);

        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.getData().get("username").equals(sharedPreferences.getString("username", null))) {
                            tvNameHomePage.setText((String) document.getData().get("name"));
                        }
                    }
                } else {
                    Toast.makeText(HomePage.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

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


                    }

                } else {
                    Toast.makeText(HomePage.this, "Error", Toast.LENGTH_SHORT).show();
                }
                loadingDialog.dismiss();
            }
        });



        avatarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userPage = new Intent(HomePage.this, UserPage.class);
                startActivity(userPage);
            }
        });

        lvRank = findViewById(R.id.lvRank);
        db.collection("users").get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String usernameRank = (String) document.getData().get("name");
                            String scoreRank = (String) document.getData().get("score");
//                            conver score to int
                            assert scoreRank != null;
                            int scoreRankInt = Integer.parseInt(scoreRank);
                            rankList.add(new RankModel(usernameRank, scoreRankInt));

//                            sắp xếp lại người chơi theo điểm
                            for (int i = 0; i < rankList.size(); i++) {
                                for (int j = i + 1; j < rankList.size(); j++) {
                                    if (rankList.get(i).getScore() < rankList.get(j).getScore()) {
                                        RankModel temp = rankList.get(i);
                                        rankList.set(i, rankList.get(j));
                                        rankList.set(j, temp);
                                    }
                                }
                            }

//                            hiển thị 5 người chơi có điểm cao nhất
                            if (rankList.size() > 5) {
                                rankList.remove(5);
                            }

//                            hiển thị người dùng có số điểm lớn hơn 0
                            for (int i = 0; i < rankList.size(); i++) {
                                if (rankList.get(i).getScore() == 0) {
                                    rankList.remove(i);
                                }
                            }

                            RankAdapter adapter = new RankAdapter(HomePage.this, rankList);
                            lvRank.setAdapter(adapter);

                        }
                    } else {
                        Toast.makeText(HomePage.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        chiaSeKienThuc.setOnClickListener(v -> {
            Intent chiaSeKienThuc = new Intent(HomePage.this, KnowledgeShare.class);
            startActivity(chiaSeKienThuc);
        });

        dongHoDemNguoc.setOnClickListener(v -> {
            Intent dongHoDemNguoc = new Intent(HomePage.this, IntroductionPomodoro.class);
            startActivity(dongHoDemNguoc);
        });

        layout3.setOnClickListener(v -> {
            Intent layout3 = new Intent(HomePage.this, contact_page.class);
            startActivity(layout3);
        });

        layout4.setOnClickListener(v -> {
            Intent layout4 = new Intent(HomePage.this, comment.class);
            startActivity(layout4);
        });
    }
}

    
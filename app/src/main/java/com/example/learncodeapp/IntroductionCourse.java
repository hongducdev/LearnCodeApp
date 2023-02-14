package com.example.learncodeapp;

import static com.example.learncodeapp.Splash.catList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class IntroductionCourse extends AppCompatActivity {

    TextView introductionCourse;
    Button btnStart;
    TextView titleIntroduct;
    Dialog loadingDialog;
    int course_id;

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
        

        loadingDialog = new Dialog(IntroductionCourse.this);
        loadingDialog.setContentView(R.layout.loading_progressbar);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();


        titleIntroduct.setText("Giới thiệu về " + getIntent().getStringExtra("course"));
        introductionCourse.setText(getIntent().getStringExtra("courseIntroduct"));
        course_id = getIntent().getIntExtra("course_id", 1);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("courses").document("course" + String.valueOf(course_id)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();

                    if (doc.exists()) {

                        btnStart.setOnClickListener(v -> {
                            Intent intent = new Intent(IntroductionCourse.this, Question.class);
                            intent.putExtra("course_id", course_id);
                            startActivity(intent);
                        });
                        loadingDialog.dismiss();

                    } else {
                        Toast.makeText(IntroductionCourse.this, "No Document Exists!", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                } else {

                    Toast.makeText(IntroductionCourse.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
                loadingDialog.dismiss();
            }
        });

    }
}
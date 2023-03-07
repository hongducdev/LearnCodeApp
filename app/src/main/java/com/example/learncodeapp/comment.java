package com.example.learncodeapp;

import static com.example.learncodeapp.Splash.catList;
import static com.example.learncodeapp.Splash.selected_course_index;
import static java.text.DateFormat.getDateTimeInstance;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class comment extends AppCompatActivity {

    EditText edtComment;
    Button btnComment;
    ListView lvComment;
    Map<String, Object> comment = new HashMap<>();
    ArrayList<CommentModel> commentList = new ArrayList<>();
    TextView txtNoData;
    Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        edtComment = findViewById(R.id.edtComment);
        btnComment = findViewById(R.id.btnComment);
        lvComment = findViewById(R.id.lvComment);

        // Change header
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle(catList.get(selected_course_index).getName());
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_question);
        View view = getSupportActionBar().getCustomView();
        ImageButton imageButton = (ImageButton) view.findViewById(R.id.action_bar_back);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update database and back
                Intent intent = new Intent(comment.this, IntroductionPomodoro.class);
                startActivity(intent);
                finish();
            }
        });

        CommentAdapter commentAdapter = new CommentAdapter(comment.this, commentList);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);

        loadingDialog = new Dialog(comment.this);
        loadingDialog.setContentView(R.layout.loading_progressbar);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commentContent = edtComment.getText().toString();

                if (commentContent.isEmpty() || commentContent.equals(" ") || commentContent.equals("")) {
                    edtComment.setError("Vui lòng nhập đánh giá của bạn");
                    return;
                }

                comment.put("username", sharedPreferences.getString("username", ""));
                comment.put("name", sharedPreferences.getString("name", ""));
                comment.put("comment", commentContent);
                comment.put("timestamp", getTimeDate(System.currentTimeMillis()));

                db.collection("comments").add(comment)
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(comment.this, "Bình luận thành công", Toast.LENGTH_SHORT).show();
                            commentAdapter.notifyDataSetChanged();
                            edtComment.setText("");

                            commentList.clear();

                            // sort by timestamp

                            db.collection("comments")
                                    .orderBy("timestamp", Query.Direction.ASCENDING)
                                    .get().addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {

//                                                if (commentList.isEmpty()) {
//                                                    txtNoData.setVisibility(View.VISIBLE);
//                                                } else {
//                                                    txtNoData.setVisibility(View.GONE);
//                                                }

                                                if (Objects.equals(document.getString("username"), sharedPreferences.getString("username", ""))) {
                                                    edtComment.setVisibility(View.GONE);
                                                    btnComment.setVisibility(View.GONE);
                                                }

                                                String name = document.getString("name");
                                                String username = document.getString("username");
                                                String comment = document.getString("comment");
                                                String timestamp = document.getString("timestamp");

                                                commentList.add(new CommentModel(name, comment, timestamp, username));
                                                lvComment.setAdapter(commentAdapter);
                                                commentAdapter.notifyDataSetChanged();
                                            }
                                        }
                                    });
                        })
                        .addOnFailureListener(e -> Toast.makeText(comment.this, "Bình luận thất bại", Toast.LENGTH_SHORT).show());

                commentAdapter.notifyDataSetChanged();

            }
        });

        db.collection("comments")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

//                            if (commentList.isEmpty()) {
//                                txtNoData.setVisibility(View.VISIBLE);
//                            } else {
//                                txtNoData.setVisibility(View.GONE);
//                            }

                            if (Objects.equals(document.getString("username"), sharedPreferences.getString("username", ""))) {
                                edtComment.setVisibility(View.GONE);
                                btnComment.setVisibility(View.GONE);
                            }

                            String name = document.getString("name");
                            String username = document.getString("username");
                            String comment = document.getString("comment");
                            String timestamp = document.getString("timestamp");

                            commentList.add(new CommentModel(name, comment, timestamp, username));
                            lvComment.setAdapter(commentAdapter);
                            commentAdapter.notifyDataSetChanged();
                            loadingDialog.dismiss();
                        }
                    }

                    loadingDialog.dismiss();
                });
    }

    public static String getTimeDate(long timestamp) {
        try {
            DateFormat dateFormat = getDateTimeInstance();
            Date netDate = (new Date(timestamp));
            return dateFormat.format(netDate);
        } catch (Exception e) {
            return "date";
        }
    }
}
package com.example.learncodeapp;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;


public class Question extends AppCompatActivity implements View.OnClickListener {
    TextView nameBar;
    LinearLayout questionLayout;
    TextView questionName, timer, size, totalSize;
    RadioButton opt1, opt2, opt3, opt4;
    List<QuestionModel> questionList;
    int quesNum;
    int score;
    FirebaseFirestore firestore;
    int course_id;
    Dialog loadingDialog;
    CountDownTimer countDown;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // Change header
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_question);
        View view = getSupportActionBar().getCustomView();
        ImageButton imageButton = (ImageButton) view.findViewById(R.id.action_bar_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        nameBar = findViewById(R.id.nameBar);
        nameBar.setText("HTML cơ bản");

        questionLayout = findViewById(R.id.questionLayout);
        questionName = findViewById(R.id.questionName);
        opt1 = findViewById(R.id.opt1);
        opt2 = findViewById(R.id.opt2);
        opt3 = findViewById(R.id.opt3);
        opt4 = findViewById(R.id.opt4);
        timer = findViewById(R.id.timer);
        size = findViewById(R.id.size);
        totalSize = findViewById(R.id.totalSize);

        opt1.setOnClickListener(this);
        opt2.setOnClickListener(this);
        opt3.setOnClickListener(this);
        opt4.setOnClickListener(this);

        questionList = new ArrayList<>();
        firestore = FirebaseFirestore.getInstance();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        loadingDialog = new Dialog(Question.this);
        loadingDialog.setContentView(R.layout.loading_progressbar);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();

        course_id = getIntent().getIntExtra("course_id", 1);

        getQuestionList();
        score = 0;


    }

    private void getQuestionList() {

        questionList.clear();

        firestore.collection("courses").document("course" + String.valueOf(course_id)).collection("questions").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        questionList.add(new QuestionModel(doc.getString("question"), doc.getString("A"), doc.getString("B"), doc.getString("C"), doc.getString("D"), Integer.valueOf(doc.getString("answer"))));
                    }
                } else {
                    Toast.makeText(Question.this, "Error", Toast.LENGTH_SHORT).show();
                }


                setQuestion();
                loadingDialog.dismiss();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingDialog.dismiss();
            }
        });
    }

    private void setQuestion() {
        timer.setText(String.valueOf(10));

        questionName.setText(questionList.get(0).getQuestion());
        opt1.setText(questionList.get(0).getOption1());
        opt2.setText(questionList.get(0).getOption2());
        opt3.setText(questionList.get(0).getOption3());
        opt4.setText(questionList.get(0).getOption4());

        // số câu hỏi / tổng số câu hỏi
        size.setText(String.valueOf(1));
        totalSize.setText(String.valueOf(questionList.size()));

        startTimer();
        quesNum = 0;
    }

    private void startTimer() {
        countDown = new CountDownTimer(10 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished < 10000)
                    timer.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                changeQuestion();
            }
        };
        countDown.start();
    }

    @Override
    public void onClick(View v) {

        int selectedOption = 0;

        switch (v.getId()) {
            case R.id.opt1:
                selectedOption = 1;
                break;
            case R.id.opt2:
                selectedOption = 2;
                break;
            case R.id.opt3:
                selectedOption = 3;
                break;
            case R.id.opt4:
                selectedOption = 4;
                break;
            default:
        }

        countDown.cancel();
        checkAnswer(selectedOption, v);
    }

    private void checkAnswer(int selectedOption, View view) {

        if (selectedOption == questionList.get(quesNum).getCorrectAnswer()) {
            //Right Answer
            ((RadioButton) view).setButtonTintList(ColorStateList.valueOf(Color.GREEN));
            ((RadioButton) view).setTextColor(ColorStateList.valueOf(Color.GREEN));
            score++;

        } else {
            //Wrong Answer
            ((RadioButton) view).setButtonTintList(ColorStateList.valueOf(Color.RED));
            ((RadioButton) view).setTextColor(ColorStateList.valueOf(Color.RED));

            switch (questionList.get(quesNum).getCorrectAnswer()) {
                case 1:
                    opt1.setButtonTintList(ColorStateList.valueOf(Color.GREEN));
                    opt1.setTextColor(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 2:
                    opt2.setButtonTintList(ColorStateList.valueOf(Color.GREEN));
                    opt2.setTextColor(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 3:
                    opt3.setButtonTintList(ColorStateList.valueOf(Color.GREEN));
                    opt3.setTextColor(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 4:
                    opt4.setButtonTintList(ColorStateList.valueOf(Color.GREEN));
                    opt4.setTextColor(ColorStateList.valueOf(Color.GREEN));
                    break;

            }

        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeQuestion();
            }
        }, 2000);

    }


    private void changeQuestion() {
        if (quesNum < questionList.size() - 1) {
            quesNum++;

            playAnim(questionName, 0, 0);
            playAnim(opt1, 0, 1);
            playAnim(opt2, 0, 2);
            playAnim(opt3, 0, 3);
            playAnim(opt4, 0, 4);

            size.setText(String.valueOf(quesNum + 1));
            totalSize.setText(String.valueOf(questionList.size()));

            timer.setText(String.valueOf(10));
            startTimer();

        } else {
            // Go to Score Activity
            Intent intent = new Intent(Question.this, Score.class);
            intent.putExtra("NUM_CORRECT", String.valueOf(score) + "/" + String.valueOf(questionList.size()));
            int newScore = (int) ((score * 100) / questionList.size());
            intent.putExtra("SCORE", String.valueOf(newScore));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            //QuestionActivity.this.finish();
        }


    }


    private void playAnim(final View view, final int value, final int viewNum) {

        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (value == 0) {
                    switch (viewNum) {
                        case 0:
                            ((TextView) view).setText(questionList.get(quesNum).getQuestion());
                            break;
                        case 1:
                            ((RadioButton) view).setText(questionList.get(quesNum).getOption1());
                            break;
                        case 2:
                            ((RadioButton) view).setText(questionList.get(quesNum).getOption2());
                            break;
                        case 3:
                            ((RadioButton) view).setText(questionList.get(quesNum).getOption3());
                            break;
                        case 4:
                            ((RadioButton) view).setText(questionList.get(quesNum).getOption4());
                            break;

                    }


                    if (viewNum != 0) {
                        ((RadioButton) view).setButtonTintList(ColorStateList.valueOf(Color.parseColor("#41c375")));
                        ((RadioButton) view).setTextColor(ColorStateList.valueOf(Color.BLACK));
                        ((RadioButton) view).setChecked(false);
                    }

                    playAnim(view, 1, viewNum);

                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        countDown.cancel();
    }


}

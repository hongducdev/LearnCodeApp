package com.example.learncodeapp;

import static com.example.learncodeapp.Splash.catList;
import static com.example.learncodeapp.Splash.selected_course_index;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.BreakIterator;

public class CountdownClock extends AppCompatActivity {

    TextView tvCountdownMinute, tvCountdownSecond;
    Button btnStart, btnReset, btn25, btn45, btn60;
    CountDownTimer timer;

    int milliLeft, min, sec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown_clock);

        tvCountdownMinute = findViewById(R.id.tvCountdownMinute);
        tvCountdownSecond = findViewById(R.id.tvCountdownSecond);
        btnStart = findViewById(R.id.btnStart);
        btnReset = findViewById(R.id.btnReset);
        btn25 = findViewById(R.id.btn25);
        btn45 = findViewById(R.id.btn45);
        btn60 = findViewById(R.id.btn60);

        // Change header
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle(catList.get(selected_course_index).getName());
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_question);
        View view =getSupportActionBar().getCustomView();
        ImageButton imageButton= (ImageButton)view.findViewById(R.id.action_bar_back);

        btn25.setOnClickListener(v -> {
            tvCountdownMinute.setText("25");
            tvCountdownSecond.setText("00");
        });

        btn45.setOnClickListener(v -> {
            tvCountdownMinute.setText("45");
            tvCountdownSecond.setText("00");
        });

        btn60.setOnClickListener(v -> {
            tvCountdownMinute.setText("60");
            tvCountdownSecond.setText("00");
        });

        btnStart.setOnClickListener(v -> {
            if (btnStart.getText().toString().equals("Start")) {
                btnStart.setText("Pause");
                timerStart(Integer.parseInt(tvCountdownMinute.getText().toString()) * 60 * 1000 + Integer.parseInt(tvCountdownSecond.getText().toString()) * 1000);
            } else if (btnStart.getText().toString().equals("Pause")) {
                btnStart.setText("Resume");
                timerPause();
            } else if (btnStart.getText().toString().equals("Resume")) {
                btnStart.setText("Pause");
                timerResume();
            }
        });

        btnReset.setOnClickListener(v -> {
            timer.cancel();
            tvCountdownMinute.setText("25");
            tvCountdownSecond.setText("00");
            btn25.setEnabled(true);
            btn45.setEnabled(true);
            btn60.setEnabled(true);
        });

    }

    public void timerStart(long timeLengthMilli) {
        timer = new CountDownTimer(timeLengthMilli, 1000) {


            @Override
            public void onTick(long milliTillFinish) {
                milliLeft = (int) milliTillFinish;
                min = (int) (milliTillFinish / (1000 * 60));
                sec = (int) ((milliTillFinish / 1000) - min * 60);

                tvCountdownMinute.setText(String.format("%02d", min));
                tvCountdownSecond.setText(String.format("%02d", sec));

//                    disable button
                btn25.setEnabled(false);
                btn45.setEnabled(false);
                btn60.setEnabled(false);
                Log.i("Tick", "Tock");
            }

            @Override
            public void onFinish() {
                tvCountdownMinute.setText("00");
                tvCountdownSecond.setText("00");
                Toast.makeText(CountdownClock.this, "Hết thời gian!", Toast.LENGTH_SHORT).show();
                MediaPlayer mediaPlayer = MediaPlayer.create(CountdownClock.this, R.raw.alarm);
                mediaPlayer.start();
                btn25.setEnabled(true);
                btn45.setEnabled(true);
                btn60.setEnabled(true);
                btnStart.setText("Start");
            }
        };
        timer.start();
    }

    public void timerPause() {
        timer.cancel();
    }

    private void timerResume() {
        Log.i("min", Long.toString(min));
        Log.i("Sec", Long.toString(sec));
        timerStart(milliLeft);
    }
}
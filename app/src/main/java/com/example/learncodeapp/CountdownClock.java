package com.example.learncodeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CountdownClock extends AppCompatActivity {

    TextView tvCountdownMinute, tvCountdownSecond;
    Button btnStart, btnReset, btn25, btn45, btn60;
    CountDownTimer Timer;

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
            Timer = new CountDownTimer(Integer.parseInt(tvCountdownMinute.getText().toString()) * 60 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    int minutes = (int) (millisUntilFinished / 1000) / 60;
                    int seconds = (int) (millisUntilFinished / 1000) % 60;
                    tvCountdownMinute.setText(String.format("%02d", minutes));
                    tvCountdownSecond.setText(String.format("%02d", seconds));
                }

                @Override
                public void onFinish() {
                    tvCountdownMinute.setText("00");
                    tvCountdownSecond.setText("00");
                    Toast.makeText(CountdownClock.this, "Hết thời gian!", Toast.LENGTH_SHORT).show();
                    MediaPlayer mediaPlayer = MediaPlayer.create(CountdownClock.this, R.raw.alarm);
                    mediaPlayer.start();

                }
            }.start();
        });

        btnReset.setOnClickListener(v -> {
            Timer.cancel();
            tvCountdownMinute.setText("25");
            tvCountdownSecond.setText("00");
        });

    }
}
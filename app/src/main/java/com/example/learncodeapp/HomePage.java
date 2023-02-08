package com.example.learncodeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class HomePage extends AppCompatActivity {

    TextView tvNameHomePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        tvNameHomePage = findViewById(R.id.tvNameHomePage);

        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("username", "Bạn học");

        tvNameHomePage.setText(name);
    }
}
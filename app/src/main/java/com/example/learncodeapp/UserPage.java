package com.example.learncodeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class UserPage extends AppCompatActivity {

    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        btnLogout = findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(v -> {
            finish();
            Intent intent = new Intent(UserPage.this, MainActivity.class);
            startActivity(intent);
        });
    }

}
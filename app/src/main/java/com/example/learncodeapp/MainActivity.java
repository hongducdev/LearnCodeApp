package com.example.learncodeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView moveLogin;
    Button moveRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moveRegister = (Button) findViewById(R.id.moveRegister);
        moveLogin = (TextView) findViewById(R.id.moveLogin);

        moveRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveRegisterPage = new Intent(MainActivity.this, RegisterPage.class);
                startActivity(moveRegisterPage);
            }
        });

        moveLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveLoginPage = new Intent(MainActivity.this, LoginPage.class);
                startActivity(moveLoginPage);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);

        String username = sharedPreferences.getString("username", null);
        String password = sharedPreferences.getString("password", null);

        if (username != null && password != null) {
            Intent intent = new Intent(MainActivity.this, HomePage.class);
            startActivity(intent);
        }
    }
}
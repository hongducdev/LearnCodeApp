package com.example.learncodeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

public class LoginPage extends AppCompatActivity {

    TextView tvRegister;
    Button btnLogin;
    EditText edtUsername, edtPassword;
    boolean passwordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        tvRegister = findViewById(R.id.tvRegister);
        btnLogin = findViewById(R.id.btnLogin);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginPage.this, RegisterPage.class);
            startActivity(intent);
        });

        // TODO: Toggle password visibility
        edtPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (edtPassword.getRight() - edtPassword.getCompoundDrawables()[Right].getBounds().width())) {
                        if (passwordVisible) {
                            edtPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_close, 0);
                            edtPassword.setInputType(129);
                            passwordVisible = false;
                        } else {
                            edtPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_open, 0);
                            edtPassword.setInputType(145);
                            passwordVisible = true;
                        }
                        return true;
                    }
                }

                return false;
            }
        });


        // TODO: Add login logic
        btnLogin.setOnClickListener(v -> {
            String username = edtUsername.getText().toString();
            String password = edtPassword.getText().toString();

            // Check if username and password is empty
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginPage.this, "Vui lòng nhập dầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                db.collection("users")
                    .whereEqualTo("username", username)
                    .whereEqualTo("password", password)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().size() > 0) {
                                    // TODO: Save user data to shared preferences

                                    SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("username", username);
                                    editor.putString("password", password);
                                    editor.putString("name", task.getResult().getDocuments().get(0).getString("name"));

                                    editor.apply();

                                    // Go to home page
                                    Toast.makeText(LoginPage.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginPage.this, HomePage.class);
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(LoginPage.this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(LoginPage.this, "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
        });

    }
}
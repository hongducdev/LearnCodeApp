package com.example.learncodeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterPage extends AppCompatActivity {

    TextView tvLogin;
    EditText edtUsername, edtPassword, edtConfirmPassword;
    Button btnRegister;
    boolean passwordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        tvLogin = findViewById(R.id.tvLogin);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);

        btnRegister = findViewById(R.id.btnRegister);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> user = new HashMap<>();

        tvLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterPage.this, LoginPage.class);
            startActivity(intent);
        });

        // TODO: Toggle password visibility
        // FIXME: Sửa lỗi hiển thị
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

        // TODO: Add register logic
        btnRegister.setOnClickListener(v -> {
            String username = edtUsername.getText().toString();
            String password = edtPassword.getText().toString();
            String confirmPassword = edtConfirmPassword.getText().toString();

            // Check if username and password is empty
            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }

            // Check if password and confirm password is the same
            else if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Mật khẩu và xác nhận mật khẩu không giống nhau", Toast.LENGTH_SHORT).show();
            } else if (password.length() < 6) {
                Toast.makeText(this, "Mật khẩu phải trên 6 kí tự", Toast.LENGTH_SHORT).show();
            } else if (password.length() > 12) {
                Toast.makeText(this, "Mật khẩu không quá 12 kí tự", Toast.LENGTH_SHORT).show();
            } else {
                // chuẩn hóa username và password
                username = username.trim();
                password = password.trim();

                // kiểm tra username đã tồn tại chưa
                String finalUsername = username;
                String finalPassword = password;
                db.collection("users")
                    .whereEqualTo("username", username)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if (queryDocumentSnapshots.isEmpty()) {
                            // username chưa tồn tại
                            // thêm user vào database
                            user.put("username", finalUsername);
                            user.put("password", finalPassword);
                            db.collection("users")
                                .add(user)
                                .addOnSuccessListener(documentReference -> {
                                    Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterPage.this, LoginPage.class);
                                    startActivity(intent);
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                                });
                        } else {
                            // username đã tồn tại
                            Toast.makeText(this, "Tên dăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    });

            }
        });
    }
}
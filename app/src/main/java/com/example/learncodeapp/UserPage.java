package com.example.learncodeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

public class UserPage extends AppCompatActivity {

    Button btnLogout, btnChangeName;
    EditText edtChangeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        btnLogout = findViewById(R.id.btnLogout);
        btnChangeName = findViewById(R.id.btnChangeName);
        edtChangeName = findViewById(R.id.edtChangeName);


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .whereEqualTo("username", getIntent().getStringExtra("username"))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            edtChangeName.setText(document.getString("name"));
                        }
                    }
                });


        btnLogout.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(UserPage.this, MainActivity.class);
            startActivity(intent);

        });
    }

}
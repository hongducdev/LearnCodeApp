package com.example.learncodeapp;

import static com.example.learncodeapp.Splash.catList;
import static com.example.learncodeapp.Splash.selected_course_index;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

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

        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

//        lấy dữ liệu từ firebase từ username trong user
        db.collection("users").whereEqualTo("username", sharedPreferences.getString("username", ""))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            edtChangeName.setText(document.getString("name"));
                        }
                    }
                });

        // Change header
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle("Thông tin cá nhân");
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_question);
        View view =getSupportActionBar().getCustomView();
        ImageButton imageButton= (ImageButton)view.findViewById(R.id.action_bar_back);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update database and back
                Intent intent = new Intent(UserPage.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
//        đổi tên
        btnChangeName.setOnClickListener(v -> {

            db.collection("users").whereEqualTo("username", sharedPreferences.getString("username", ""))
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                db.collection("users").document(document.getId()).update("name", edtChangeName.getText().toString());
                            }
                        }
                    });
            db.collection("comments").whereEqualTo("username", sharedPreferences.getString("username", ""))
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                db.collection("comments").document(document.getId()).update("name", edtChangeName.getText().toString());
                            }
                        }
                    });
            editor.putString("name", edtChangeName.getText().toString());
            Toast.makeText(this, "Thay đổi tên thành công", Toast.LENGTH_SHORT).show();
        });

        btnLogout.setOnClickListener(v -> {
            editor.clear();
            editor.apply();

            Intent intent = new Intent(UserPage.this, MainActivity.class);
            startActivity(intent);

        });
    }

}
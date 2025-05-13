package com.example.newspaper;

import android.app.Application;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newspaper.database.repositories.UserRepository;
import com.example.newspaper.models.User;

public class RegisterActivity extends AppCompatActivity {
    private EditText edName, etEmail, etPassword;
    private Button btnRegister;
    private TextView linkToLogin;
    private ImageView backBtn;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userRepository = new UserRepository((Application) getApplicationContext());

        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(v -> {
            String username = ((EditText)findViewById(R.id.edName)).getText().toString();
            String email = ((EditText)findViewById(R.id.etAddress)).getText().toString();
            String password = ((EditText)findViewById(R.id.etDate)).getText().toString();

            User newUser = new User(username, email, password);

            userRepository.registerUser(newUser, new UserRepository.RegistrationCallback() {
                @Override
                public void onSuccess() {
                    runOnUiThread(() -> {
                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                }

                @Override
                public void onError(String errorMessage) {
                    runOnUiThread(() -> {
                        Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    });
                }
            });
        });
    }
}
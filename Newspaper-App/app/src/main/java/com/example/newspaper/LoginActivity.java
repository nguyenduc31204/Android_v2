package com.example.newspaper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newspaper.database.repositories.UserRepository;
import com.example.newspaper.models.User;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private UserRepository userRepository;
    TextView linkToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userRepository = new UserRepository(getApplication());

        etEmail = findViewById(R.id.etAddress);
        etPassword = findViewById(R.id.etDate);
        btnLogin = findViewById(R.id.btnLogin);
        linkToRegister = findViewById(R.id.linkToRegister);
        linkToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            userRepository.login(email, password, new UserRepository.LoginCallback() {
                @Override
                public void onSuccess(User user) {
                    runOnUiThread(() -> {
                        // Lưu thông tin đăng nhập và chuyển đến màn hình chính
                        saveLoginInfo(user);
                        Toast.makeText(LoginActivity.this, user.getUsername() + " đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    });
                }

                @Override
                public void onError(String errorMessage) {
                    runOnUiThread(() ->
                            Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show()
                    );
                }
            });
        });
    }

    private void saveLoginInfo(User user) {
        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
//        prefs.edit().clear().apply();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.putLong("userId", user.getId());
        editor.putString("userEmail", user.getEmail());
        editor.putString("userName", user.getUsername());
        editor.apply();
    }
}

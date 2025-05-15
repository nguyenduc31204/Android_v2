package com.example.newspaper;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.newspaper.database.repositories.UserRepository;
import com.example.newspaper.models.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UserDetailActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView imgAvatar;
    private ImageButton btnAddPhoto;
    private EditText edName, etEmail, etBirthday, etGender, etPhone, etAddress;
    private Button btnUpdate;
    private UserRepository userRepository;
    private Uri selectedImageUri;
    private int currentUserId;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_infor);

        userRepository = new UserRepository(getApplication());
        initViews();

        // Lấy USER_ID từ Intent
        currentUserId = (int) getIntent().getLongExtra("USER_ID", -1);

        Log.d("UpdateInforActivity", "Received USER_ID: " + currentUserId);

        if (currentUserId == -1) {
            Toast.makeText(this, "Lỗi: Không xác định người dùng", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadUserInfo();
        setupEvents();
    }

    private void initViews() {
        imgAvatar = findViewById(R.id.imgAvatar);
        btnAddPhoto = findViewById(R.id.btnAddPhoto);
        edName = findViewById(R.id.edName);
        etEmail = findViewById(R.id.etEmail);
        etBirthday = findViewById(R.id.etDate);
        etGender = findViewById(R.id.etGender);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        btnUpdate = findViewById(R.id.btnUpdate);

        findViewById(R.id.backBtn).setOnClickListener(v -> finish());
    }

    private void loadUserInfo() {
        userRepository.getUserById(currentUserId).observe(this, user -> {
            if (user != null) {
                currentUser = user;
                displayUserInfo(user);
            } else {
                Toast.makeText(this, "Không tìm thấy người dùng", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void displayUserInfo(User user) {
        edName.setText(user.getUsername() != null ? user.getUsername() : "");
        etEmail.setText(user.getEmail() != null ? user.getEmail() : "");
        if (user.getDob() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            etBirthday.setText(sdf.format(user.getDob()));
        } else {
            etBirthday.setText("");
        }
        etGender.setText(user.getMale() != null ? (user.getMale() ? "Nam" : "Nữ") : "Khác");
        etPhone.setText(user.getPhone() != null ? user.getPhone() : "");
        etAddress.setText(user.getCity() != null ? user.getCity() : "");
    }

    private void setupEvents() {
        btnAddPhoto.setOnClickListener(v -> openImageChooser());
        etBirthday.setOnClickListener(v -> showDatePickerDialog());
        btnUpdate.setOnClickListener(v -> updateUserInfo());
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    etBirthday.setText(selectedDate);
                },
                year, month, day);
        datePickerDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            imgAvatar.setImageURI(selectedImageUri);
        }
    }

    private void updateUserInfo() {
        if (!validateInput()) {
            return;
        }

        if (currentUser == null) {
            Toast.makeText(this, "Lỗi: Không tải được thông tin người dùng", Toast.LENGTH_SHORT).show();
            return;
        }

        User updatedUser = new User();
        updatedUser.setId(currentUserId);
        updatedUser.setUsername(edName.getText().toString().trim());
        updatedUser.setEmail(etEmail.getText().toString().trim());
        updatedUser.setPasswordHash(currentUser.getPasswordHash()); // Giữ nguyên mật khẩu

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String birthdayStr = etBirthday.getText().toString().trim();
            if (!birthdayStr.isEmpty()) {
                Date dob = sdf.parse(birthdayStr);
                updatedUser.setDob(dob);
            } else {
                updatedUser.setDob(null);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Ngày sinh không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        String genderStr = etGender.getText().toString().trim();
        if (genderStr.equalsIgnoreCase("Nam")) {
            updatedUser.setMale(true);
        } else if (genderStr.equalsIgnoreCase("Nữ")) {
            updatedUser.setMale(false);
        } else {
            updatedUser.setMale(null);
        }

        updatedUser.setPhone(etPhone.getText().toString().trim());
        updatedUser.setCity(etAddress.getText().toString().trim());

        userRepository.updateUser(updatedUser, new UserRepository.UpdateCallback() {
            @Override
            public void onSuccess() {
                runOnUiThread(() -> {
                    Toast.makeText(UserDetailActivity.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                });
            }

            @Override
            public void onError(String errorMessage) {
                runOnUiThread(() -> {
                    Toast.makeText(UserDetailActivity.this, "Lỗi: " + errorMessage, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private boolean validateInput() {
        if (edName.getText().toString().trim().isEmpty()) {
            edName.setError("Vui lòng nhập họ tên");
            return false;
        }
        if (etEmail.getText().toString().trim().isEmpty()) {
            etEmail.setError("Vui lòng nhập email");
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString().trim()).matches()) {
            etEmail.setError("Email không hợp lệ");
            return false;
        }
        if (etPhone.getText().toString().trim().length() < 10) {
            etPhone.setError("Số điện thoại không hợp lệ");
            return false;
        }
        return true;
    }
}
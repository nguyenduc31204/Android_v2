package com.example.newspaper;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.newspaper.database.repositories.UserRepository;
import com.example.newspaper.models.User;
import com.example.newspaper.ui.adapters.view_models.UserViewModel;
import com.example.newspaper.ui.fragments.FragmentHome;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UpdateInforActivity extends AppCompatActivity {
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
        userRepository = new UserRepository(getApplication());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_infor);

        initViews();

        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // Lấy ID người dùng hiện tại

//        currentUserId = getIntent().getIntExtra("USER_ID", -1);
        if (currentUserId == -1) {
            SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
            currentUserId = prefs.getInt("userId", -1);
        }
        Log.d("UserIdDebug", "USER_ID from Intent: " + currentUserId);
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



        // Nút back
        findViewById(R.id.backBtn).setOnClickListener(v -> finish());
    }

    private int getCurrentUserId() {
        return getIntent().getIntExtra("USER_ID", -1);
    }

    private int getCurrentUserIdFromPrefs() {
        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        return prefs.getInt("USER_ID", -1);
    }


    private void loadUserInfo() {
        if (currentUserId == -1) {
            Toast.makeText(this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        userRepository.getUserById(currentUserId).observe(this, user -> {
            if (user != null) {
                currentUser = user;
                displayUserInfo(user);
            }
        });
    }

    private void displayUserInfo(User user) {
        edName.setText(user.getUsername());
        etEmail.setText(user.getEmail());
        if (user.getDob() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String formattedDate = sdf.format(user.getDob());
            etBirthday.setText(formattedDate);
        } else {
            etBirthday.setText("");
        }

        Boolean isMale = user.getMale();
        if (isMale != null) {
            etGender.setText(isMale ? "Nam" : "Nữ");
        } else {
            etGender.setText("");
        }

        etPhone.setText(user.getPhone());
        etAddress.setText(user.getCity());

//        if (user.getAvatarUrl() != null && !user.getAvatarUrl().isEmpty()) {
//            ImageUtils.loadImage(this, user.getAvatarUrl(), imgAvatar);
//        }
    }

    private void setupEvents() {
        // Chọn ảnh đại diện
        btnAddPhoto.setOnClickListener(v -> openImageChooser());

        // Date picker cho ngày sinh
        etBirthday.setOnClickListener(v -> showDatePickerDialog());

        // Cập nhật thông tin
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
        // Validate dữ liệu
        if (!validateInput()) {
            return;
        }

        User updatedUser = new User();
        updatedUser.setPasswordHash(currentUser.getPasswordHash());

        updatedUser.setId(currentUserId);
        updatedUser.setUsername(edName.getText().toString().trim());
        updatedUser.setEmail(etEmail.getText().toString().trim());
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String birthdayStr = etBirthday.getText().toString().trim();
            Date dob = sdf.parse(birthdayStr);
            updatedUser.setDob(dob);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Ngày sinh không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }
        updatedUser.setMale(etGender.getText().toString().equalsIgnoreCase("Nam"));

        updatedUser.setPhone(etPhone.getText().toString().trim());
        updatedUser.setCity(etAddress.getText().toString().trim());

//        if (selectedImageUri != null) {
//            String imagePath = ImageUtils.saveImageToInternalStorage(this, selectedImageUri);
//            updatedUser.setAvatarUri(imagePath);
//        }

        userRepository.updateUser(updatedUser, new UserRepository.UpdateCallback() {
            @Override
            public void onSuccess() {
                runOnUiThread(() -> {
                    Toast.makeText(UpdateInforActivity.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
//                    finish();
                });
            }

            @Override
            public void onError(String errorMessage) {
                runOnUiThread(() -> {
                    Toast.makeText(UpdateInforActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private boolean validateInput() {
        if (edName.getText().toString().trim().isEmpty()) {
            edName.setError("Vui lòng nhập họ tên");
            return false;
        }

        else if (etEmail.getText().toString().trim().isEmpty()) {
            etEmail.setError("Vui lòng nhập email");
            return false;
        }

        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString().trim()).matches()) {
            etEmail.setError("Email không hợp lệ");
            return false;
        }

        else if (etPhone.getText().toString().trim().length() < 10) {
            etPhone.setError("Số điện thoại không hợp lệ");
            return false;
        }

        return true;
    }
}
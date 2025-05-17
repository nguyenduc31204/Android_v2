package com.example.newspaper;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.newspaper.database.repositories.UserRepository;
import com.example.newspaper.models.User;
import com.example.newspaper.ui.adapters.UserAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ManageUsersActivity extends AppCompatActivity {

    private ListView listViewUsers;
    private Uri selectedImageUri;
    private UserRepository userRepository;
    private UserAdapter userAdapter;
    private TextInputEditText searchEditText;
    private List<User> filteredUserList;
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private List<User> userList;
    private ActivityResultLauncher<Intent> addUserLauncher;
    private ImageView activeAvatarImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        userRepository = new UserRepository(getApplication());
        filteredUserList = new ArrayList<>();
        listViewUsers = findViewById(R.id.listViewUsers);
        searchEditText = findViewById(R.id.searchEditText);
        userList = new ArrayList<>();
        userAdapter = new UserAdapter(this, filteredUserList);


        listViewUsers.setAdapter(userAdapter);
        FloatingActionButton fabAddUser = findViewById(R.id.fabAddUser);
        fabAddUser.setOnClickListener(v -> showAddUserDialog());

        addUserLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        loadUsers();
                        Toast.makeText(this, "Danh sách người dùng đã được cập nhật", Toast.LENGTH_SHORT).show();
                    }
                });

        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        if (selectedImageUri != null && activeAvatarImageView != null) {
                            activeAvatarImageView.setImageURI(selectedImageUri);
                            Log.d("ManageUsersActivity", "Image selected: " + selectedImageUri.toString());
                        }
                    }
                });

        loadUsers();

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterUsers(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        //click vào người dùng
        listViewUsers.setOnItemClickListener((parent, view, position, id) -> {
            User user = userList.get(position);
            showEditUserDialog(user);
        });

        // long click để xóa
        listViewUsers.setOnItemLongClickListener((parent, view, position, id) -> {
            User user = filteredUserList.get(position);
            showDeleteConfirmationDialog(user);
            return true;
        });
    }

    private void showEditUserDialog(User user) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_user, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        ImageView imgAvatar = dialogView.findViewById(R.id.imgAvatar);
        ImageButton btnAddPhoto = dialogView.findViewById(R.id.btnAddPhoto);
        EditText edName = dialogView.findViewById(R.id.edName);
        EditText etEmail = dialogView.findViewById(R.id.etEmail);
        EditText etBirthday = dialogView.findViewById(R.id.etDate);
        EditText etGender = dialogView.findViewById(R.id.etGender);
        EditText etPhone = dialogView.findViewById(R.id.etPhone);
        EditText etAddress = dialogView.findViewById(R.id.etAddress);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        Button btnUpdate = dialogView.findViewById(R.id.btnUpdate);

        activeAvatarImageView = imgAvatar;
        if (user.getAvatarUrl() != null) {
            try {
                imgAvatar.setImageURI(Uri.parse(user.getAvatarUrl()));
                Log.d("ManageUsersActivity", "Loaded existing avatar: " + user.getAvatarUrl());
            } catch (Exception e) {
                Log.e("ManageUsersActivity", "Error loading avatar: " + e.getMessage());
            }
        }

        // Populate user info
        edName.setText(user.getUsername() != null ? user.getUsername() : "");
        etEmail.setText(user.getEmail() != null ? user.getEmail() : "");
        if (user.getDob() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            etBirthday.setText(sdf.format(user.getDob()));
        }
        etGender.setText(user.getMale() != null ? (user.getMale() ? "Nam" : "Nữ") : "Khác");
        etPhone.setText(user.getPhone() != null ? user.getPhone() : "");
        etAddress.setText(user.getCity() != null ? user.getCity() : "");

        AlertDialog dialog = builder.create();

        //  photo selection
        btnAddPhoto.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            imagePickerLauncher.launch(intent);
        });

//        imagePickerLauncher.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
//                selectedImageUri = result.getData().getData();
//                if (selectedImageUri != null) {
//                    imgAvatar.setImageURI(selectedImageUri);
//                }
//            }
//        });

        //  date picker
        etBirthday.setOnClickListener(v -> {
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
        });

        //  Cancel button
        btnCancel.setOnClickListener(v -> {
            activeAvatarImageView = null;
            dialog.dismiss();
        });

        //  Update button
        btnUpdate.setOnClickListener(v -> {
            if (!validateEditInput(edName, etEmail, etPhone)) {
                return;
            }

            User updatedUser = new User();
            updatedUser.setId((int)user.getId());
            updatedUser.setUsername(edName.getText().toString().trim());
            updatedUser.setEmail(etEmail.getText().toString().trim());
            updatedUser.setPasswordHash(user.getPasswordHash());
            updatedUser.setRole(user.getRole());
            updatedUser.setAvatarUrl(selectedImageUri != null ? selectedImageUri.toString() : user.getAvatarUrl());

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
                        Toast.makeText(ManageUsersActivity.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        activeAvatarImageView = null;
                        loadUsers();
                    });
                }

                @Override
                public void onError(String errorMessage) {
                    runOnUiThread(() -> {
                        Toast.makeText(ManageUsersActivity.this, "Lỗi: " + errorMessage, Toast.LENGTH_SHORT).show();
                    });
                }
            });
        });

        dialog.show();
    }
    private boolean validateEditInput(EditText edName, EditText etEmail, EditText etPhone) {
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
//    private void setupListViewListeners() {
//        listViewUsers.setOnItemClickListener((parent, view, position, id) -> {
//            User user = userList.get(position);
//            showEditUserDialog(user);
//        });
//
//        listViewUsers.setOnItemLongClickListener((parent, view, position, id) -> {
//            User user = userList.get(position);
//            showDeleteConfirmationDialog(user, position);
//            return true;
//        });
//    }
private void showAddUserDialog() {
    View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_user, null);
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setView(dialogView);

    EditText etUsername = dialogView.findViewById(R.id.etUsername);
    EditText etEmail = dialogView.findViewById(R.id.etEmail);
    EditText etPassword = dialogView.findViewById(R.id.etPassword);
    Button btnCancel = dialogView.findViewById(R.id.btnCancel);
    Button btnSave = dialogView.findViewById(R.id.btnSave);

    AlertDialog dialog = builder.create();

    btnCancel.setOnClickListener(v -> dialog.dismiss());

    // Save button
    btnSave.setOnClickListener(v -> {
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPasswordHash(password);
        newUser.setRole("user");

        userRepository.registerUser(newUser, new UserRepository.RegistrationCallback() {
            @Override
            public void onSuccess() {
                runOnUiThread(() -> {
                    Toast.makeText(ManageUsersActivity.this, "Thêm người dùng thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    loadUsers();
                });
            }

            @Override
            public void onError(String errorMessage) {
                runOnUiThread(() -> {
                    if (errorMessage.equals("Email đã được đăng ký")) {
                        etEmail.setError(errorMessage);
                        etEmail.requestFocus();
                    } else if (errorMessage.contains("tên người dùng")) {
                        etUsername.setError(errorMessage);
                        etUsername.requestFocus();
                    } else if (errorMessage.contains("email hợp lệ")) {
                        etEmail.setError(errorMessage);
                        etEmail.requestFocus();
                    } else if (errorMessage.contains("Mật khẩu")) {
                        etPassword.setError(errorMessage);
                        etPassword.requestFocus();
                    } else {
                        Toast.makeText(ManageUsersActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    });

    dialog.show();
}
    private boolean validateInput(String username, String email, String password,
                                  EditText etUsername, EditText etEmail, EditText etPassword) {
        if (username.isEmpty()) {
            etUsername.setError("Vui lòng nhập tên người dùng");
            return false;
        }
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Vui lòng nhập email hợp lệ");
            return false;
        }

        if (password.isEmpty() || password.length() < 6) {
            etPassword.setError("Mật khẩu phải có ít nhất 6 ký tự");
            return false;
        }
        return true;
    }
    private void filterUsers(String query) {
        filteredUserList.clear();
        if (query.isEmpty()) {
            filteredUserList.addAll(userList);
        } else {
            String lowerCaseQuery = query.toLowerCase(Locale.getDefault());
            for (User user : userList) {
                if (user.getUsername() != null && user.getUsername().toLowerCase(Locale.getDefault()).contains(lowerCaseQuery)) {
                    filteredUserList.add(user);
                }
            }
        }
        userAdapter.notifyDataSetChanged();
    }


    private void loadUsers() {
        userRepository.getAllUsers().observe(this, users -> {
            if (users != null) {
                userList.clear();
                userList.addAll(users);
                filterUsers(searchEditText.getText().toString());
            }
        });
    }


    private void showDeleteConfirmationDialog(User user) {
        new AlertDialog.Builder(this)
                .setTitle("Xóa người dùng")
                .setMessage("Bạn có chắc chắn muốn xóa " + user.getUsername() + "?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    userRepository.deleteUser(user, new UserRepository.DeleteCallback() {
                        @Override
                        public void onSuccess() {
                            runOnUiThread(() -> {
                                userList.remove(user);
                                filterUsers(searchEditText.getText().toString());
                                Toast.makeText(ManageUsersActivity.this, "Đã xóa người dùng", Toast.LENGTH_SHORT).show();
                            });
                        }

                        @Override
                        public void onError(String errorMessage) {
                            runOnUiThread(() ->
                                    Toast.makeText(ManageUsersActivity.this, errorMessage, Toast.LENGTH_SHORT).show()
                            );
                        }
                    });
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

}
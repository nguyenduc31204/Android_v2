package com.example.newspaper;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ManageUsersActivity extends AppCompatActivity {

    private ListView listViewUsers;
    private UserRepository userRepository;
    private UserAdapter userAdapter;
    private TextInputEditText searchEditText;
    private List<User> filteredUserList;
    private List<User> userList;
    private ActivityResultLauncher<Intent> addUserLauncher;

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
        fabAddUser.setOnClickListener(v -> {
            Intent intent = new Intent(ManageUsersActivity.this, AddUserActivity.class);
            addUserLauncher.launch(intent);
        });

        addUserLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        loadUsers();
                        Toast.makeText(this, "Danh sách người dùng đã được cập nhật", Toast.LENGTH_SHORT).show();
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
            User user = filteredUserList.get(position); // đã lọc
            Intent intent = new Intent(ManageUsersActivity.this, UserDetailActivity.class);
            intent.putExtra("USER_ID", user.getId());
            startActivity(intent);
        });

        // long click để xóa
        listViewUsers.setOnItemLongClickListener((parent, view, position, id) -> {
            User user = filteredUserList.get(position);
            showDeleteConfirmationDialog(user);
            return true;
        });
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